/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version. This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details. You should have received a copy of the GNU
 * Lesser General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>
 */
package wirelessredstone.addon.remote.core;

import wirelessredstone.addon.remote.core.lib.CoreLib;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
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
		modid = CoreLib.MOD_ID,
		name = CoreLib.MOD_NAME,
		version = CoreLib.MOD_VERSION,
		dependencies = CoreLib.MOD_DEPENDENCIES)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
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

	@Instance(CoreLib.MOD_ID)
	public static WirelessRemote	instance;

	/**
	 * Initialization
	 * 
	 * @param event
	 */
	@EventHandler
	public void WirelessRemoteInit(FMLInitializationEvent event) {

	}

	/**
	 * Pre-initialization
	 * 
	 * @param event
	 */
	@EventHandler
	public void WirelessRemotePreInit(FMLPreInitializationEvent event) {
	}

	/**
	 * Post-initialization
	 * 
	 * @param event
	 */
	@EventHandler
	public void WirelessRemotePostInit(FMLPostInitializationEvent event) {
		WRemoteCore.initialize();
	}
}
