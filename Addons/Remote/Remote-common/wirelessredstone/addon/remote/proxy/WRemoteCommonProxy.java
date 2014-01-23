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
package wirelessredstone.addon.remote.proxy;

import java.io.File;
import java.util.HashMap;
import java.util.TreeMap;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import wirelessredstone.addon.remote.api.IRemoteCommonProxy;
import wirelessredstone.addon.remote.core.WirelessRemote;
import wirelessredstone.addon.remote.inventory.ContainerWirelessRemote;
import wirelessredstone.addon.remote.inventory.WirelessRemoteDevice;
import wirelessredstone.addon.remote.network.packets.PacketRemoteCommands;
import wirelessredstone.addon.remote.network.packets.executor.RemoteChangeFreqExecutor;
import wirelessredstone.addon.remote.overrides.RedstoneEtherOverrideRemote;
import wirelessredstone.api.IWirelessDevice;
import wirelessredstone.core.lib.GuiLib;
import wirelessredstone.ether.RedstoneEther;
import wirelessredstone.network.ServerPacketHandler;
import wirelessredstone.network.packets.core.PacketIds;
import cpw.mods.fml.common.network.NetworkRegistry;

public class WRemoteCommonProxy implements IRemoteCommonProxy {

	@Override
	public void registerRenderInformation() {
	}

	@Override
	public void registerConfiguration(File configFile) {
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == GuiLib.GUIID_DEVICE) {
			WirelessRemoteDevice device = new WirelessRemoteDevice(world, player);
			return new ContainerWirelessRemote(device);
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
		NetworkRegistry.instance().registerGuiHandler(	WirelessRemote.instance,
														WirelessRemote.proxy);
		PacketRemoteCommands.registerCommands();
		WirelessRemoteDevice.remoteTransmitters = new HashMap();
		WirelessRemoteDevice.remoteWirelessCoords = new TreeMap();
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
		ServerPacketHandler.getPacketHandler(PacketIds.DEVICE).registerPacketHandler(	PacketRemoteCommands.remoteCommands.changeFreq.toString(),
																						new RemoteChangeFreqExecutor());
	}

	@Override
	public void connectionClosed(INetworkManager manager) {
		// TODO Auto-generated method stub

	}

	@Override
	public void activateRemote(World world, EntityLivingBase entityliving) {
		WirelessRemoteDevice.activateWirelessRemote(world,
													entityliving);
	}

	@Override
	public boolean deactivateRemote(World world, EntityLivingBase entityliving) {
		return WirelessRemoteDevice.deactivateWirelessRemote(	world,
																entityliving);
	}

	@Override
	public void addOverrides() {
		RedstoneEther.getInstance().addOverride(new RedstoneEtherOverrideRemote());
	}

	@Override
	public boolean isRemoteOn(World world, EntityLivingBase entityplayer, String freq) {
		if (WirelessRemoteDevice.remoteTransmitters.containsKey(entityplayer)) {
			IWirelessDevice remote = WirelessRemoteDevice.remoteTransmitters.get(entityplayer);
			return remote.getFreq() == freq;
		}
		return false;
	}
}
