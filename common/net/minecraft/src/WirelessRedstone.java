/*    
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package net.minecraft.src;

import wirelessredstone.core.WRCore;
import wirelessredstone.network.ClientPacketHandler;
import wirelessredstone.network.ServerPacketHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
/**
 * Wireless Redstone ModLoader initializing class.
 * 
 * @author ali4z
 */
@Mod(
		modid = "WirelessRedstoneCore", 
		name = "A&E Wireless Redstone", 
		version = "1.6"
)
@NetworkMod(
		clientSideRequired = true,
		serverSideRequired = true,
		clientPacketHandlerSpec =
		@SidedPacketHandler(
				channels = { "WR" },
				packetHandler = ClientPacketHandler.class
				),
		serverPacketHandlerSpec =
		@SidedPacketHandler(
				channels = { "WR" },
				packetHandler = ServerPacketHandler.class
				),
		versionBounds = "[1.6]"
)
public class WirelessRedstone {

	/**
	 * Constructor sets the instance.
	 * @return 
	 */
	@Init
	public void WirelessRedstoneInit(FMLInitializationEvent event) {
		WRCore.initialize();
	}
	
	@PreInit
	public void WirelessRedstonePreInit(FMLPreInitializationEvent event) {
	}
	
	@PostInit
	public void WirelessRedstonePostInit(FMLPostInitializationEvent event) {
	}
}
