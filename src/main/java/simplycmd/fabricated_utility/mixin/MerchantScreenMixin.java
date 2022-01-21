package simplycmd.fabricated_utility.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.screen.MerchantScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.village.Merchant;
import simplycmd.fabricated_utility.Main;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.screen.ingame.MerchantScreen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MerchantScreen.class)
public class MerchantScreenMixin {
    private final MerchantScreen screen = ((MerchantScreen) (Object) this);
    private final MerchantScreenHandler handler = screen.getScreenHandler();
    private final Merchant merchant = ((MerchantScreenHandlerAccessor) handler).getMerchant();

    @Inject(method = "render", at = @At("TAIL"))
    public void render(CallbackInfo ci) {
        System.out.println(merchant.getExperience());
        if (fabricated_utility$isValid() || /*temporary*/true) {
            screen.addDrawableChild(
                new TexturedButtonWidget(screen.width / 2 + 104, screen.height / 2 - 22, 20, 18, fabricated_utility$isValid() ? 0 : 21, 0, fabricated_utility$isValid() ? 19 : 0,
                    new Identifier(Main.MOD_ID, "textures/gui/container/cycletrades.png"), (buttonWidget) -> {
                    if (fabricated_utility$isValid())
                    ClientPlayNetworking.send(Main.CYCLETRADES_PACKET_C2S.getIdentifier(), PacketByteBufs.empty());
                })
            );
        }
    }

    private boolean fabricated_utility$isValid() {
        //System.out.println(merchant instanceof VillagerEntity);
        return merchant.getExperience() <= 0 && merchant.isLeveledMerchant();
    }
}