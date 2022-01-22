package simplycmd.fabricated_utility.villagers.mixin;

import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.village.Merchant;
import org.spongepowered.asm.mixin.Mixin;
import simplycmd.fabricated_utility.villagers.api.BetterVillagersExtension;

@Mixin(WanderingTraderEntity.class)
public abstract class WanderingTraderMixin implements Merchant, BetterVillagersExtension {
    // hacky fix to disable the button
    @Override
    public int getExperience() {
        return 1;
    }

    @Override
    public int betterVillagers$getLevel() {
        return 1;
    }

    @Override
    public boolean betterVillagers$canRestock() {
        return false;
    }

    @Override
    public boolean betterVillagers$supportsBetterVillagers() {
        return false;
    }
}
