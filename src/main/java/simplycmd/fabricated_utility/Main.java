package simplycmd.fabricated_utility;

import io.github.simplycmd.featherlib.registry.ID;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import simplycmd.fabricated_utility.villagers.impl.GuiEvents;

import java.util.List;

public class Main implements ModInitializer {
    public static final String MOD_ID = "fabricated_utility";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static final ID CYCLETRADES_PACKET_C2S = new ID(Main.MOD_ID, "cycletrades_c2s");

    @Override
    public void onInitialize() {
        GuiEvents.init();
        ServerPlayNetworking.registerGlobalReceiver(CYCLETRADES_PACKET_C2S.getIdentifier(), (server, player, handler, buffer, sender) -> server.execute(() -> {
			final Vec3d vec3d = player.getPos();
			final List<MerchantEntity> list = player.getWorld().getEntitiesByClass(MerchantEntity.class, new Box(vec3d.getX() - 8.0, vec3d.getY() - 5.0, vec3d.getZ() - 8.0, vec3d.getX() + 8.0, vec3d.getY() + 5.0, vec3d.getZ() + 8.0), entity -> true);
			for (MerchantEntity entity : list) {
				if (entity.getCurrentCustomer() == player) {
					if (!entity.isLeveledMerchant()) return;
					GuiEvents.onCycleTrades(player, entity);
				}
			}
		}));
        LOGGER.info(MOD_ID + " activated :O");
    }
}
