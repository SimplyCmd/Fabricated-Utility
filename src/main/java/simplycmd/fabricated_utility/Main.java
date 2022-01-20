package simplycmd.fabricated_utility;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.simplycmd.featherlib.registry.ID;

public class Main implements ModInitializer, ClientModInitializer {
	public static final String MOD_ID = "fabricated_utility";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	public static final ID CYCLETRADES_PACKET = new ID(Main.MOD_ID, "cycletrades");

	@Override
	public void onInitialize() {
		LOGGER.info(MOD_ID + " activated :O");
	}

	@Override
	public void onInitializeClient() {
	}
}