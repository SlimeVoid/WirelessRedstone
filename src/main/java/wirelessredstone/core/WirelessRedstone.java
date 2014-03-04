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
package wirelessredstone.core;

import wirelessredstone.api.ICommonProxy;
import wirelessredstone.core.lib.CoreLib;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * Wireless Redstone ModLoader initializing class.
 * 
 * @author ali4z
 */
@Mod(
        modid = CoreLib.MOD_ID,
        name = CoreLib.MOD_NAME,
        version = CoreLib.MOD_VERSION,
        dependencies = CoreLib.MOD_DEPENDENCIES)
/**
 * FML fascade class.
 * This class uses FML annotations and sorts initialization.
 * 
 * ConnectionHandler: RedstoneWirelessConnectionHandler
 * ClientPacketHandler: ClientPacketHandler
 * ServerPacketHandler: ServerPacketHandler
 * 
 * @author Eurymachus, ali4z
 */
public class WirelessRedstone {

    @SidedProxy(
            clientSide = CoreLib.MOD_CLIENT_PROXY,
            serverSide = CoreLib.MOD_COMMON_PROXY)
    public static ICommonProxy     proxy;

    @Instance(CoreLib.MOD_ID)
    public static WirelessRedstone instance;

    /**
     * Pre-initialization
     * 
     * @param event
     */
    @EventHandler
    public void WirelessRedstonePreInit(FMLPreInitializationEvent event) {
        proxy.registerConfiguration(event.getSuggestedConfigurationFile());
        WRCore.initialize();
    }

    /**
     * Initialization
     * 
     * @param event
     */
    @EventHandler
    public void WirelessRedstoneInit(FMLInitializationEvent event) {
    }

    /**
     * Post-initialization
     * 
     * @param event
     */
    @EventHandler
    public void WirelessRedstonePostInit(FMLPostInitializationEvent event) {
    }
}
