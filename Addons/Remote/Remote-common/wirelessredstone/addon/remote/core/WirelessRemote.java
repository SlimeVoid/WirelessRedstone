package wirelessredstone.addon.remote.core;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

/**
 * Wireless Remote ModLoader initializing class.
 * 
 * @author Eurymachus
 */
@Mod(
		modid = "WirelessRemote",
		name = "Wireless Redstone - Wireless Remote",
		version = "2.0",
		dependencies = "after:WirelessRedstoneCore")
@NetworkMod(
		clientSideRequired = true,
		serverSideRequired = false)
/**
 * FML fascade class.
 * This class uses FML annotations and sorts initialization.
 * 
 * ConnectionHandler: 
 * ClientPacketHandler: 
 * ServerPacketHandler: 
 * 
 * @author Eurymachus, ali4z
 */
public class WirelessRemote {


	/**
	 * Initialization
	 * 
	 * @param event
	 */
	@Init
	public void WirelessRemoteInit(FMLInitializationEvent event) {
		
	}

	/**
	 * Pre-initialization
	 * 
	 * @param event
	 */
	@PreInit
	public void WirelessRemotePreInit(FMLPreInitializationEvent event) {
	}

	/**
	 * Post-initialization
	 * 
	 * @param event
	 */
	@PostInit
	public void WirelessRemotePostInit(FMLPostInitializationEvent event) {
		WRemoteCore.initialize();
	}
}
