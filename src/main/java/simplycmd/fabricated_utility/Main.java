package simplycmd.fabricated_utility;

import io.github.simplycmd.featherlib.registry.ID;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.entity.VillagerEntityRenderer;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import simplycmd.fabricated_utility.villagers.BetterVillagers;
import simplycmd.fabricated_utility.villagers.impl.GuiEvents;
import simplycmd.fabricated_utility.villagers.pickup.VillagerItem;

import static simplycmd.fabricated_utility.villagers.pickup.VillagerItemRenderer.createEntityRendererContext;

public class Main implements ModInitializer, ClientModInitializer {
    private VillagerEntityRenderer renderer;
    //public static final KeyBinding PICK_UP_VILLAGER = new KeyBinding("key.easy_villagers.pick_up", GLFW.GLFW_KEY_V, "category.easy_villagers");
    public static final String MOD_ID = "fabricated_utility";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static final VillagerItem VILLAGER_ITEM = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "villager_item"), new VillagerItem());
    public static final ID CYCLETRADES_PACKET_C2S = new ID(Main.MOD_ID, "cycletrades_c2s");

    @Override
    public void onInitialize() {
        //KeyBindingHelper.registerKeyBinding(PICK_UP_VILLAGER);
        GuiEvents.init();
        BetterVillagers.init();
        LOGGER.info(MOD_ID + " activated :O");
    }

    @Override
    public void onInitializeClient() {
        BuiltinItemRendererRegistry.INSTANCE.register(VILLAGER_ITEM, ((stack, mode, matrices, vertexConsumers, light, overlay) -> {
            if (renderer == null) {
                renderer = new VillagerEntityRenderer(createEntityRendererContext());
            }
            renderer.render(Main.VILLAGER_ITEM.getVillagerFast(MinecraftClient.getInstance().world, stack), 0F, false ? 1F : 0F, matrices, vertexConsumers, light);
        }));
    }
}
