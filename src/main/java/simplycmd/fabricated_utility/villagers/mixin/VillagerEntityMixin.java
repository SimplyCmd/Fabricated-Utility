package simplycmd.fabricated_utility.villagers.mixin;

import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.village.VillagerData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import simplycmd.fabricated_utility.Main;
import simplycmd.fabricated_utility.villagers.api.BetterVillagersExtension;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin implements BetterVillagersExtension {
    @Shadow public abstract VillagerData getVillagerData();

    @Shadow public abstract boolean canRestock();

    @Inject(method = "interactMob", at = @At("HEAD"))
    private void interact(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (!player.isSneaking()) return;
        pickUp((VillagerEntity) (Object) this, player);
    }

    private static void pickUp(VillagerEntity villager, PlayerEntity player) {
        ItemStack stack = new ItemStack(Main.VILLAGER_ITEM);

        Main.VILLAGER_ITEM.setVillager(stack, villager);

        if (player.getInventory().insertStack(stack)) {
            villager.discard();
        }
    }

    @Override
    public int betterVillagers$getLevel() {
        return this.getVillagerData().getLevel();
    }

    @Override
    public boolean betterVillagers$canRestock() {
        return this.canRestock();
    }

    @Override
    public boolean betterVillagers$supportsBetterVillagers() {
        return true;
    }
}
