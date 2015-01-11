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
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.slimevoid.library.util.helpers.PacketHelper;
import net.slimevoid.wirelessredstone.api.ICommonProxy;
import net.slimevoid.wirelessredstone.core.WirelessRedstone;
import net.slimevoid.wirelessredstone.core.lib.ConfigurationLib;
import net.slimevoid.wirelessredstone.core.lib.GuiLib;
import net.slimevoid.wirelessredstone.inventory.ContainerRedstoneWireless;
import net.slimevoid.wirelessredstone.network.packets.PacketRedstoneEther;
import net.slimevoid.wirelessredstone.network.packets.executor.EtherPacketChangeFreqExecutor;
import net.slimevoid.wirelessredstone.network.packets.executor.EtherPacketFetchEtherExecutor;
import net.slimevoid.wirelessredstone.network.packets.executor.EtherPacketRXAddExecutor;
import net.slimevoid.wirelessredstone.network.packets.executor.EtherPacketRXRemExecutor;
import net.slimevoid.wirelessredstone.network.packets.executor.EtherPacketTXAddExecutor;
import net.slimevoid.wirelessredstone.network.packets.executor.EtherPacketTXRemExecutor;
import net.slimevoid.wirelessredstone.network.packets.executor.EtherPacketTXSetStateExecutor;
import net.slimevoid.wirelessredstone.tileentity.TileEntityRedstoneWireless;

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
            TileEntity tileentity = world.getTileEntity(new BlockPos(x,
                                                        y,
                                                        z));
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
        // Executors
        PacketHelper.registerServerExecutor(EtherPacketChangeFreqExecutor.class, PacketRedstoneEther.class, 0);
        //PacketHelper.registerServerExecutor(EtherPacketTXAddExecutor.class, PacketRedstoneEther.class, 1);
        //PacketHelper.registerServerExecutor(EtherPacketTXSetStateExecutor.class, PacketRedstoneEther.class, 2);
        //PacketHelper.registerServerExecutor(EtherPacketTXRemExecutor.class, PacketRedstoneEther.class, 3);
        //PacketHelper.registerServerExecutor(EtherPacketRXAddExecutor.class, PacketRedstoneEther.class, 4);
        //PacketHelper.registerServerExecutor(EtherPacketRXRemExecutor.class, PacketRedstoneEther.class, 5);
        //PacketHelper.registerServerExecutor(EtherPacketFetchEtherExecutor.class, PacketRedstoneEther.class, 6);

        // Device Packets
        
        // GUI Packets
        
        // TODO GUI Executors (Should be none)
        
        // Tile Packets
        
        // Addon
    }

    @Override
    public void login(INetHandler handler, NetworkManager manager) {
    }
}
