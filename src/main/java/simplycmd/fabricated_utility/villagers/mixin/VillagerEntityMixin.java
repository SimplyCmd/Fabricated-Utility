package simplycmd.fabricated_utility.villagers.mixin;

import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import simplycmd.fabricated_utility.Main;

@Mixin(VillagerEntity.class)
public class VillagerEntityMixin {
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
}
