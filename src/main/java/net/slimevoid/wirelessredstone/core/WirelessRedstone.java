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
package net.slimevoid.wirelessredstone.core;

import net.slimevoid.library.network.handlers.PacketPipeline;
import net.slimevoid.library.util.helpers.PacketHelper;
import net.slimevoid.wirelessredstone.api.ICommonProxy;
import net.slimevoid.wirelessredstone.core.lib.CoreLib;
import net.slimevoid.wirelessredstone.network.packets.PacketRedstoneWirelessCommands;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

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

    public static PacketPipeline   handler = new PacketPipeline();

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
        PacketRedstoneWirelessCommands.registerCommands();
        WirelessRedstone.proxy.initPacketHandlers();
        PacketHelper.registerHandler(CoreLib.MOD_CHANNEL,
                                     handler);
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
