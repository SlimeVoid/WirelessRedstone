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
package wirelessredstone.addon.camouflager.proxy;

import java.io.File;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import wirelessredstone.addon.camouflager.core.WirelessCamouflager;
import wirelessredstone.addon.camouflager.inventory.ContainerCamouflagedRedstoneWireless;
import wirelessredstone.addon.camouflager.overrides.BlockCamouflageOverride;
import wirelessredstone.addon.camouflager.overrides.TileEntityCamouflageOverride;
import wirelessredstone.api.ICommonProxy;
import wirelessredstone.core.WRCore;
import wirelessredstone.core.lib.GuiLib;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;
import cpw.mods.fml.common.network.NetworkRegistry;

public class CamouCommonProxy implements ICommonProxy {

    @Override
    public void registerRenderInformation() {
    }

    @Override
    public void registerConfiguration(File configFile) {
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == GuiLib.GUIID_DEVICE) {
            TileEntity tileentity = world.getBlockTileEntity(x,
                                                             y,
                                                             z);
            if (tileentity != null
                && tileentity instanceof TileEntityRedstoneWireless) {
                return new ContainerCamouflagedRedstoneWireless(player.inventory, (TileEntityRedstoneWireless) tileentity);
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
    public void init() {
        NetworkRegistry.instance().registerGuiHandler(WirelessCamouflager.instance,
                                                      WirelessCamouflager.proxy);
    }

    @Override
    public World getWorld(NetHandler handler) {
        return null;
    }

    @Override
    public void login(NetHandler handler, INetworkManager manager, Packet1Login login) {
    }

    @Override
    public void initPacketHandlers() {
        // ///////////////////
        // Server Executor //
        // ///////////////////
    }

    @Override
    public void connectionClosed(INetworkManager manager) {
        // TODO Auto-generated method stub
    }

    @Override
    public void addOverrides() {
        WRCore.addOverrideToTransmitter(new BlockCamouflageOverride());
        WRCore.addOverrideToReceiver(new BlockCamouflageOverride());
        TileEntityRedstoneWireless.addOverride(new TileEntityCamouflageOverride());
    }
}
