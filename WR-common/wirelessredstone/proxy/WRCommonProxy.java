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
package wirelessredstone.proxy;

import java.io.File;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import wirelessredstone.api.ICommonProxy;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.core.lib.ConfigurationLib;
import wirelessredstone.network.ServerPacketHandler;
import wirelessredstone.network.handlers.ServerAddonPacketHandler;
import wirelessredstone.network.handlers.ServerDeviceGuiPacketHandler;
import wirelessredstone.network.handlers.ServerGuiPacketHandler;
import wirelessredstone.network.handlers.ServerRedstoneEtherPacketHandler;
import wirelessredstone.network.handlers.ServerTilePacketHandler;
import wirelessredstone.network.handlers.ServerWirelessDevicePacketHandler;
import wirelessredstone.network.packets.PacketRedstoneWirelessCommands;
import wirelessredstone.network.packets.core.PacketIds;
import wirelessredstone.network.packets.executor.EtherPacketChangeFreqExecutor;
import wirelessredstone.network.packets.executor.EtherPacketFetchEtherExecutor;
import wirelessredstone.network.packets.executor.EtherPacketRXAddExecutor;
import wirelessredstone.network.packets.executor.EtherPacketRXRemExecutor;
import wirelessredstone.network.packets.executor.EtherPacketTXAddExecutor;
import wirelessredstone.network.packets.executor.EtherPacketTXRemExecutor;
import wirelessredstone.network.packets.executor.EtherPacketTXSetStateExecutor;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;

public class WRCommonProxy implements ICommonProxy {

	@Override
	public void registerRenderInformation() {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerConfiguration(File configFile) {
		ConfigurationLib.CommonConfig(configFile);
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMinecraftDir() {
		// TODO Auto-generated method stub
		return ".";
	}

	@Override
	public void registerTileEntitySpecialRenderer(Class<? extends TileEntity> clazz) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addOverrides() {
	}

	@Override
	public void activateGUI(World world, EntityPlayer entityplayer, TileEntityRedstoneWireless tileentityredstonewireless) {
		if (!world.isRemote) {
			ServerGuiPacketHandler.sendGuiPacketTo(	(EntityPlayerMP) entityplayer,
													tileentityredstonewireless);
		}
	}

	@Override
	public void activateGUI(World world, EntityPlayer entityplayer, IWirelessDeviceData devicedata) {
		if (!world.isRemote) {
			ServerDeviceGuiPacketHandler.sendGuiPacketTo(	(EntityPlayerMP) entityplayer,
															devicedata);
		}
	}

	@Override
	public void init() {
	}

	@Override
	public World getWorld(NetHandler handler) {
		return null;
	}

	@Override
	public void login(NetHandler handler, INetworkManager manager, Packet1Login login) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initPacketHandlers() {
		// ///////////////////
		// Server Handlers //
		// ///////////////////
		// TODO Re-Inititiate Handlers
		ServerPacketHandler.init();
		// Ether Packets
		ServerRedstoneEtherPacketHandler etherPacketHandler = new ServerRedstoneEtherPacketHandler();
		// Executors
		etherPacketHandler.registerPacketHandler(	PacketRedstoneWirelessCommands.wirelessCommands.changeFreq.toString(),
													new EtherPacketChangeFreqExecutor());
		etherPacketHandler.registerPacketHandler(	PacketRedstoneWirelessCommands.wirelessCommands.addTransmitter.toString(),
													new EtherPacketTXAddExecutor());
		etherPacketHandler.registerPacketHandler(	PacketRedstoneWirelessCommands.wirelessCommands.setTransmitterState.toString(),
													new EtherPacketTXSetStateExecutor());
		etherPacketHandler.registerPacketHandler(	PacketRedstoneWirelessCommands.wirelessCommands.remTransmitter.toString(),
													new EtherPacketTXRemExecutor());
		etherPacketHandler.registerPacketHandler(	PacketRedstoneWirelessCommands.wirelessCommands.addReceiver.toString(),
													new EtherPacketRXAddExecutor());
		etherPacketHandler.registerPacketHandler(	PacketRedstoneWirelessCommands.wirelessCommands.remReceiver.toString(),
													new EtherPacketRXRemExecutor());
		etherPacketHandler.registerPacketHandler(	PacketRedstoneWirelessCommands.wirelessCommands.fetchEther.toString(),
													new EtherPacketFetchEtherExecutor());
		ServerPacketHandler.registerPacketHandler(	PacketIds.ETHER,
													etherPacketHandler);
		// Wireless Device Packets
		ServerWirelessDevicePacketHandler devicePacketHandler = new ServerWirelessDevicePacketHandler();
		ServerPacketHandler.registerPacketHandler(	PacketIds.DEVICE,
													devicePacketHandler);
		// TODO Wireless Device Executors
		// GUI Packets
		ServerGuiPacketHandler guiPacketHandler = new ServerGuiPacketHandler();
		ServerPacketHandler.registerPacketHandler(	PacketIds.GUI,
													guiPacketHandler);
		// TODO GUI Executors (Should be none)
		// Tile Packets
		ServerTilePacketHandler tilePacketHandler = new ServerTilePacketHandler();
		ServerPacketHandler.registerPacketHandler(	PacketIds.TILE,
													tilePacketHandler);
		// Addon
		ServerAddonPacketHandler addonPacketHandler = new ServerAddonPacketHandler();
		ServerPacketHandler.registerPacketHandler(	PacketIds.ADDON,
													addonPacketHandler);
	}

	@Override
	public void connectionClosed(INetworkManager manager) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doSomething(String command, World world, int x, int y, int z) {
		// TODO Auto-generated method stub

	}
}
