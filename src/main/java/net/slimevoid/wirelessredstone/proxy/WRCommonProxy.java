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
package net.slimevoid.wirelessredstone.proxy;

import java.io.File;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.slimevoid.wirelessredstone.api.ICommonProxy;
import net.slimevoid.wirelessredstone.core.WirelessRedstone;
import net.slimevoid.wirelessredstone.core.lib.ConfigurationLib;
import net.slimevoid.wirelessredstone.core.lib.GuiLib;
import net.slimevoid.wirelessredstone.inventory.ContainerRedstoneWireless;
import net.slimevoid.wirelessredstone.network.handlers.AddonPacketHandler;
import net.slimevoid.wirelessredstone.network.handlers.DevicePacketHandler;
import net.slimevoid.wirelessredstone.network.handlers.GuiPacketHandler;
import net.slimevoid.wirelessredstone.network.handlers.RedstoneEtherPacketHandler;
import net.slimevoid.wirelessredstone.network.handlers.TilePacketHandler;
import net.slimevoid.wirelessredstone.network.packets.PacketRedstoneWirelessCommands;
import net.slimevoid.wirelessredstone.network.packets.core.PacketIds;
import net.slimevoid.wirelessredstone.network.packets.executor.EtherPacketChangeFreqExecutor;
import net.slimevoid.wirelessredstone.network.packets.executor.EtherPacketFetchEtherExecutor;
import net.slimevoid.wirelessredstone.network.packets.executor.EtherPacketRXAddExecutor;
import net.slimevoid.wirelessredstone.network.packets.executor.EtherPacketRXRemExecutor;
import net.slimevoid.wirelessredstone.network.packets.executor.EtherPacketTXAddExecutor;
import net.slimevoid.wirelessredstone.network.packets.executor.EtherPacketTXRemExecutor;
import net.slimevoid.wirelessredstone.network.packets.executor.EtherPacketTXSetStateExecutor;
import net.slimevoid.wirelessredstone.tileentity.TileEntityRedstoneWireless;
import cpw.mods.fml.common.network.NetworkRegistry;

public class WRCommonProxy implements ICommonProxy {

    @Override
    public void registerRenderInformation() {
    }

    @Override
    public void registerConfiguration(File configFile) {
        ConfigurationLib.CommonConfig(configFile);
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == GuiLib.GUIID_INVENTORY) {
            TileEntity tileentity = world.getTileEntity(x,
                                                        y,
                                                        z);
            if (tileentity != null
                && tileentity instanceof TileEntityRedstoneWireless) {
                return new ContainerRedstoneWireless((TileEntityRedstoneWireless) tileentity);
            }
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public String getMinecraftDir() {
        return ".";
    }

    @Override
    public void registerTileEntitySpecialRenderer(Class<? extends TileEntity> clazz) {
    }

    @Override
    public void addOverrides() {
    }

    @Override
    public void init() {
        NetworkRegistry.INSTANCE.registerGuiHandler(WirelessRedstone.instance,
                                                    WirelessRedstone.proxy);
    }

    @Override
    public void initPacketHandlers() {
        // //////////////////////
        // Initialise Handlers //
        // //////////////////////

        // Ether Packets
        RedstoneEtherPacketHandler etherPacketHandler = new RedstoneEtherPacketHandler();
        // Executors
        etherPacketHandler.registerServerExecutor(PacketRedstoneWirelessCommands.wirelessCommands.changeFreq.toString(),
                                                 new EtherPacketChangeFreqExecutor());
        etherPacketHandler.registerServerExecutor(PacketRedstoneWirelessCommands.wirelessCommands.addTransmitter.toString(),
                                                 new EtherPacketTXAddExecutor());
        etherPacketHandler.registerServerExecutor(PacketRedstoneWirelessCommands.wirelessCommands.setTransmitterState.toString(),
                                                 new EtherPacketTXSetStateExecutor());
        etherPacketHandler.registerServerExecutor(PacketRedstoneWirelessCommands.wirelessCommands.remTransmitter.toString(),
                                                 new EtherPacketTXRemExecutor());
        etherPacketHandler.registerServerExecutor(PacketRedstoneWirelessCommands.wirelessCommands.addReceiver.toString(),
                                                 new EtherPacketRXAddExecutor());
        etherPacketHandler.registerServerExecutor(PacketRedstoneWirelessCommands.wirelessCommands.remReceiver.toString(),
                                                 new EtherPacketRXRemExecutor());
        etherPacketHandler.registerServerExecutor(PacketRedstoneWirelessCommands.wirelessCommands.fetchEther.toString(),
                                                 new EtherPacketFetchEtherExecutor());
        WirelessRedstone.handler.registerPacketHandler(PacketIds.ETHER,
                                                       etherPacketHandler);

        // Device Packets
        DevicePacketHandler devicePacketHandler = new DevicePacketHandler();
        WirelessRedstone.handler.registerPacketHandler(PacketIds.DEVICE,
                                                       devicePacketHandler);
        // GUI Packets
        GuiPacketHandler guiPacketHandler = new GuiPacketHandler();
        WirelessRedstone.handler.registerPacketHandler(PacketIds.GUI,
                                                       guiPacketHandler);
        // TODO GUI Executors (Should be none)
        // Tile Packets
        TilePacketHandler tilePacketHandler = new TilePacketHandler();
        WirelessRedstone.handler.registerPacketHandler(PacketIds.TILE,
                                                       tilePacketHandler);
        // Addon
        AddonPacketHandler addonPacketHandler = new AddonPacketHandler();
        WirelessRedstone.handler.registerPacketHandler(PacketIds.ADDON,
                                                       addonPacketHandler);
    }

    @Override
    public void login(INetHandler handler, NetworkManager manager) {
    }
}
