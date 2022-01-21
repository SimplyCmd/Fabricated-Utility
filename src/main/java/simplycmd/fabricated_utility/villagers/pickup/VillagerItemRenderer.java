package simplycmd.fabricated_utility.villagers.pickup;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.VillagerEntityRenderer;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import simplycmd.fabricated_utility.Main;

public class VillagerItemRenderer extends ItemRenderer {

    private VillagerEntityRenderer renderer;

    public VillagerItemRenderer(TextureManager manager, BakedModelManager bakery, ItemColors colors, BuiltinModelItemRenderer builtinModelItemRenderer) {
        super(manager, bakery, colors, builtinModelItemRenderer);
    }

    @Override
    public void renderItem(ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model) {

    }

    public static EntityRendererFactory.Context createEntityRendererContext() {
        return new EntityRendererFactory.Context(MinecraftClient.getInstance().getEntityRenderDispatcher(), MinecraftClient.getInstance().getItemRenderer(), MinecraftClient.getInstance().getResourceManager(), MinecraftClient.getInstance().getEntityModelLoader(), MinecraftClient.getInstance().textRenderer);
    }

}