package wirelessredstone.addon.remote.proxy;

import java.util.HashMap;
import java.util.TreeMap;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import wirelessredstone.addon.remote.api.IRemoteCommonProxy;
import wirelessredstone.addon.remote.data.WirelessRemoteData;
import wirelessredstone.addon.remote.data.WirelessRemoteDevice;
import wirelessredstone.addon.remote.network.packets.PacketOpenGuiRemote;
import wirelessredstone.addon.remote.network.packets.PacketRemoteCommands;
import wirelessredstone.addon.remote.network.packets.executor.ActivateRemoteExecutor;
import wirelessredstone.addon.remote.network.packets.executor.DeactivateRemoteExecutor;
import wirelessredstone.addon.remote.network.packets.executor.RemoteChangeFreqExecutor;
import wirelessredstone.addon.remote.network.packets.executor.RemoteChangeReceiverFreqExecutor;
import wirelessredstone.addon.remote.overrides.RedstoneEtherOverrideRemote;
import wirelessredstone.api.IWirelessDevice;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.ether.RedstoneEther;
import wirelessredstone.network.ServerPacketHandler;
import wirelessredstone.network.handlers.ServerDeviceGuiPacketHandler;
import wirelessredstone.network.packets.core.PacketIds;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;

public class WRemoteCommonProxy implements IRemoteCommonProxy {

	@Override
	public void registerRenderInformation() {
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
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
	public void activateGUI(World world, EntityPlayer entityplayer, TileEntityRedstoneWireless tileentityredstonewireless) {
	}

	@Override
	public void activateGUI(World world, EntityPlayer entityplayer, IWirelessDeviceData devicedata) {
		if (!world.isRemote) {
			if (devicedata instanceof WirelessRemoteData) {
				ServerDeviceGuiPacketHandler.sendGuiPacketTo(
						(EntityPlayerMP) entityplayer,
						new PacketOpenGuiRemote(devicedata));
			}
		}
	}

	@Override
	public World getWorld() {
		return null;
	}

	@Override
	public EntityPlayer getPlayer() {
		return null;
	}

	@Override
	public void init() {
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
		/////////////////////
		// Server Executor //
		/////////////////////
		ServerPacketHandler.getPacketHandler(PacketIds.DEVICE).registerPacketHandler(
				PacketRemoteCommands.remoteCommands.deactivate.toString(),
				new DeactivateRemoteExecutor());
		ServerPacketHandler.getPacketHandler(PacketIds.DEVICE).registerPacketHandler(
				PacketRemoteCommands.remoteCommands.activate.toString(),
				new ActivateRemoteExecutor());
		ServerPacketHandler.getPacketHandler(PacketIds.ETHER).registerPacketHandler(
				PacketRemoteCommands.remoteCommands.updateReceiver.toString(),
				new RemoteChangeReceiverFreqExecutor());
		ServerPacketHandler.getPacketHandler(PacketIds.DEVICE).registerPacketHandler(
				PacketRemoteCommands.remoteCommands.changeFreq.toString(),
				new RemoteChangeFreqExecutor());
	}

	@Override
	public void connectionClosed(INetworkManager manager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void activateRemote(World world, EntityLiving entityliving) {
		WirelessRemoteDevice.activateWirelessRemote(world, entityliving);
	}

	@Override
	public boolean deactivateRemote(World world, EntityLiving entityliving) {
		return WirelessRemoteDevice.deactivateWirelessRemote(world, entityliving);
	}

	@Override
	public void addOverrides() {
		RedstoneEther.getInstance().addOverride(new RedstoneEtherOverrideRemote());
	}

	@Override
	public boolean isRemoteOn(World world, EntityPlayer entityplayer, String freq) {
		if (WirelessRemoteDevice.remoteTransmitters.containsKey(entityplayer)) {
			IWirelessDevice remote = WirelessRemoteDevice.remoteTransmitters.get(entityplayer);
			return remote.getFreq() == freq;
		}
		return false;
	}

	@Override
	public void doSomething(String command, World world, int x, int y, int z) {
		// TODO Auto-generated method stub
		
	}
}
