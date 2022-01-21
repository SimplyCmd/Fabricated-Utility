package simplycmd.fabricated_utility;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.village.TradeOfferList;
import net.fabricmc.api.ClientModInitializer;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.simplycmd.featherlib.registry.ID;
import simplycmd.fabricated_utility.mixin.MerchantEntityAccessor;

public class Main implements ModInitializer, ClientModInitializer {
	public static final String MOD_ID = "fabricated_utility";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	public static final ID CYCLETRADES_PACKET_C2S = new ID(Main.MOD_ID, "cycletrades_c2s");
	public static final ID CYCLETRADES_PACKET_S2C = new ID(Main.MOD_ID, "cycletrades_s2c");

	@Override
	public void onInitialize() {
		ServerPlayNetworking.registerGlobalReceiver(CYCLETRADES_PACKET_C2S.getIdentifier(), (server, player, handler, buffer, sender) -> {
			server.execute(() -> {
				final Vec3d vec3d = player.getPos();
				final List<MerchantEntity> list = player.getWorld().getEntitiesByClass(MerchantEntity.class, new Box(vec3d.getX() - 8.0, vec3d.getY() - 5.0, vec3d.getZ() - 8.0, vec3d.getX() + 8.0, vec3d.getY() + 5.0, vec3d.getZ() + 8.0), entity -> true);
				for (MerchantEntity entity : list) {
					if (entity.getCurrentCustomer() == player) {
						((MerchantEntityAccessor)entity).setOffers(new TradeOfferList());
						entity.fillRecipes();
						entity.sendOffers(entity.getCurrentCustomer(), entity.getDisplayName(), entity instanceof VillagerEntity ? ((VillagerEntity) entity).getVillagerData().getLevel() : 0);
					}
				}
				//ServerPlayNetworking.send(player, Main.CYCLETRADES_PACKET_S2C.getIdentifier(), PacketByteBufs.empty());
			});
		});
		LOGGER.info(MOD_ID + " activated :O");
	}

	@Override
	public void onInitializeClient() {
		ClientPlayNetworking.registerGlobalReceiver(CYCLETRADES_PACKET_S2C.getIdentifier(), (client, handler, buffer, sender) -> {
			client.execute(() -> {
				final ClientPlayerEntity player = client.player;
				//client.setScreen(null);
			});
		});
	}
}
