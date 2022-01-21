package simplycmd.fabricated_utility.villagers.pickup;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Comparator;

public class VillagerItem extends Item implements FabricItem {
    private final CachedMap<ItemStack, VillagerEntity> cachedVillagers;
    public VillagerItem() {
        super(new Item.Settings().maxCount(1));
        cachedVillagers = new CachedMap<>(10_000, ITEM_COMPARATOR);
        DispenserBlock.registerBehavior(this, (source, stack) -> {
            Direction direction = source.getBlockState().get(DispenserBlock.FACING);
            BlockPos blockpos = source.getPos().offset(direction);
            World world = source.getWorld();
            VillagerEntity villager = getVillager(world, stack);
            villager.updatePositionAndAngles(blockpos.getX() + 0.5D, blockpos.getY(), blockpos.getZ() + 0.5D, direction.asRotation(), 0F);
            world.spawnEntity(villager);
            stack.decrement(1);
            return stack;
        });
    }
    public static final Comparator<ItemStack> ITEM_COMPARATOR = (item1, item2) -> {
        int cmp = item2.getItem().hashCode() - item1.getItem().hashCode();
        if (cmp != 0) {
            return cmp;
        } else {
            cmp = item2.getDamage() - item1.getDamage();
            if (cmp != 0) {
                return cmp;
            } else {
                NbtCompound c1 = item1.getNbt();
                NbtCompound c2 = item2.getNbt();
                if (c1 == null && c2 == null) {
                    return 0;
                } else if (c1 == null) {
                    return 1;
                } else {
                    return c2 == null ? -1 : c1.hashCode() - c2.hashCode();
                }
            }
        }
    };

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (world.isClient()) {
            return ActionResult.SUCCESS;
        } else {
            ItemStack itemstack = context.getStack();
            BlockPos blockpos = context.getBlockPos();
            Direction direction = context.getPlayerFacing();
            BlockState blockstate = world.getBlockState(blockpos);

            if (!blockstate.getCollisionShape(world, blockpos).isEmpty()) {
                blockpos = blockpos.offset(direction);
            }

            VillagerEntity villager = getVillager(world, itemstack);

            villager.setPos(blockpos.getX() + 0.5D, blockpos.getY(), blockpos.getZ() + 0.5);

            if (world.spawnEntity(villager)) {
                itemstack.decrement(1);
            }

            return ActionResult.CONSUME;
        }
    }

    @Override
    @Environment(EnvType.CLIENT)
    public Text getName(ItemStack stack) {
        World world = MinecraftClient.getInstance().world;
        if (world == null) {
            return super.getName(stack);
        } else {
            VillagerEntity villager = getVillagerFast(world, stack);
            if (!villager.hasCustomName() && villager.isBaby()) {
                return new LiteralText("Baby ").append(new TranslatableText(EntityType.VILLAGER.getTranslationKey()));
            }
            return villager.getDisplayName();
        }
    }

    public VillagerEntity getVillagerFast(World world, ItemStack stack) {
        return cachedVillagers.get(stack, () -> getVillager(world, stack));
    }

    public void setVillager(ItemStack stack, VillagerEntity villager) {
        NbtCompound compound = stack.getOrCreateSubNbt("villager");
        villager.writeCustomDataToNbt(compound);
        if (villager.hasCustomName()) {
            stack.setCustomName(villager.getCustomName());
        }
    }

    public VillagerEntity getVillager(World world, ItemStack stack) {
        NbtCompound compound = stack.getOrCreateSubNbt("villager");
        if (compound == null) {
            compound = new NbtCompound();
        }

        VillagerEntity villager = new VillagerEntity(EntityType.VILLAGER, world);
        villager.readCustomDataFromNbt(compound);

        if (stack.hasCustomName()) {
            villager.setCustomName(stack.getName());
        }

        villager.hurtTime = 0;
        villager.headYaw = 0F;
        villager.prevHeadYaw = 0F;
        return villager;
    }
}
