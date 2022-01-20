package simplycmd.fabricated_utility.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.screen.MerchantScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
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
public class MerchantScreenMixin extends Screen {
    private final MerchantScreen screen = ((MerchantScreen) (Object) this);
    private final MerchantScreenHandler handler = screen.getScreenHandler();

    protected MerchantScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    public void render(CallbackInfo ci) {
        if (handler.getExperience() <= 0) {
            this.addDrawableChild(
                new TexturedButtonWidget(screen.width / 2 + 104, screen.height / 2 - 22, 20, 18, 0, 0, 19,
                    new Identifier(Main.MOD_ID, "textures/gui/container/cycletrades.png"), (buttonWidget) -> {
                    ClientPlayNetworking.send(Main.CYCLETRADES_PACKET_C2S.getIdentifier(), PacketByteBufs.empty());
                })
            );
        }
    }
}