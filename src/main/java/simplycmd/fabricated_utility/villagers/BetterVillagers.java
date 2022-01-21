package simplycmd.fabricated_utility.villagers;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import simplycmd.fabricated_utility.villagers.impl.GuiEvents;

import java.util.List;

import static simplycmd.fabricated_utility.Main.CYCLETRADES_PACKET_C2S;

public class BetterVillagers {
    private BetterVillagers(){}
    public static void init() {
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
    }
}
