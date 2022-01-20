package simplycmd.fabricated_utility.mixin;

import net.minecraft.screen.MerchantScreenHandler;
import net.minecraft.util.Identifier;
import simplycmd.fabricated_utility.Main;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.MerchantScreen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MerchantScreen.class)
public class MerchantScreenMixin {
    @Inject(method = "render", at = @At("TAIL"))
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        MerchantScreen screen = ((MerchantScreen) (Object) this);
        MerchantScreenHandler handler = screen.getScreenHandler();
        if (handler.getExperience() <= 0) {
            TexturedButtonWidget button = new TexturedButtonWidget(screen.width / 2 + 104, screen.height / 2 - 22, 20, 18, 0, 0, 19,
                new Identifier(Main.MOD_ID, "textures/gui/container/cycletrades.png"), (buttonWidget) -> {
                    //ClientPlayNetworking.send(Main.CYCLETRADES_PACKET.getIdentifier(), PacketByteBufs.empty());
                    MinecraftClient.getInstance().player.sendTradeOffers(syncId, offers, levelProgress, experience, leveled, refreshable);
                });
            button.render(matrices, mouseX, mouseY, delta);
        }
    }
}