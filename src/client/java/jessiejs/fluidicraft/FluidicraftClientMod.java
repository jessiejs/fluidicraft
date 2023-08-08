package jessiejs.fluidicraft;

import net.fabricmc.api.ClientModInitializer;

public class FluidicraftClientMod implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		FluidicraftMod.LOGGER.info("Fluidicraft is running, stability is not guaranteed");
	}
}
