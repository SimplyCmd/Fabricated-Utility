package simplycmd.fabricated_utility;

import io.github.simplycmd.featherlib.registry.ID;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import simplycmd.fabricated_utility.villagers.BetterVillagers;
import simplycmd.fabricated_utility.villagers.impl.GuiEvents;

public class Main implements ModInitializer {
    public static final String MOD_ID = "fabricated_utility";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static final ID CYCLETRADES_PACKET_C2S = new ID(Main.MOD_ID, "cycletrades_c2s");

    @Override
    public void onInitialize() {
        GuiEvents.init();
        BetterVillagers.init();
        LOGGER.info(MOD_ID + " activated :O");
    }
}
