package simplycmd.fabricated_utility.villagers.mixin;

import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.village.Merchant;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(WanderingTraderEntity.class)
public abstract class WanderingTraderMixin implements Merchant {
    // hacky fix to disable the button
    @Override
    public int getExperience() {
        return 1;
    }
}
