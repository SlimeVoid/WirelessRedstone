package wirelessredstone.proxy;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.NetHandler;
import net.minecraft.src.INetworkManager;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import wirelessredstone.api.ICommonProxy;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.device.WirelessDevice;
import wirelessredstone.network.ServerPacketHandler;
import wirelessredstone.network.handlers.ServerGuiPacketHandler;
import wirelessredstone.network.handlers.ServerRedstoneEtherPacketHandler;
import wirelessredstone.network.handlers.ServerTilePacketHandler;
import wirelessredstone.network.handlers.ServerWirelessDevicePacketHandler;
import wirelessredstone.network.packets.PacketRedstoneWirelessCommands;
import wirelessredstone.network.packets.PacketWirelessDeviceCommands;
import wirelessredstone.network.packets.core.PacketIds;
import wirelessredstone.network.packets.executor.DevicePacketActivateRXExecutor;
import wirelessredstone.network.packets.executor.DevicePacketActivateTXExecutor;
import wirelessredstone.network.packets.executor.DevicePacketChangeFreqExecutor;
import wirelessredstone.network.packets.executor.DevicePacketDeactivateRXExecutor;
import wirelessredstone.network.packets.executor.DevicePacketDeactivateTXExecutor;
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
	public void openGUI(World world, EntityPlayer entityplayer, TileEntity tileentity) {
		if (!world.isRemote) {
			if (tileentity instanceof TileEntityRedstoneWireless) {
				ServerGuiPacketHandler.sendGuiPacketTo(
						(EntityPlayerMP) entityplayer,
						(TileEntityRedstoneWireless) tileentity);
			}
		}
	}

	@Override
	public void activateGUI(World world, EntityPlayer entityplayer, TileEntity tileentity) {
	}

	@Override
	public World getWorld() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntityPlayer getPlayer() {
		// TODO Auto-generated method stub
		return null;
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
	public void activateGUI(World world, EntityPlayer entityplayer, IWirelessDeviceData devicedata) {
	}

	@Override
	public void initPacketHandlers() {
		/////////////////////
		// Server Handlers //
		/////////////////////
		// TODO Re-Inititiate Handlers
		ServerPacketHandler.init();
		// Ether Packets
		ServerRedstoneEtherPacketHandler etherPacketHandler = new ServerRedstoneEtherPacketHandler();
		// Executors
		etherPacketHandler.registerPacketHandler(
				PacketRedstoneWirelessCommands.wirelessCommands.changeFreq.toString(),
				new EtherPacketChangeFreqExecutor());
		etherPacketHandler.registerPacketHandler(
				PacketRedstoneWirelessCommands.wirelessCommands.addTransmitter.toString(),
				new EtherPacketTXAddExecutor());
		etherPacketHandler.registerPacketHandler(
				PacketRedstoneWirelessCommands.wirelessCommands.setTransmitterState.toString(),
				new EtherPacketTXSetStateExecutor());
		etherPacketHandler.registerPacketHandler(
				PacketRedstoneWirelessCommands.wirelessCommands.remTransmitter.toString(),
				new EtherPacketTXRemExecutor());
		etherPacketHandler.registerPacketHandler(
				PacketRedstoneWirelessCommands.wirelessCommands.addReceiver.toString(),
				new EtherPacketRXAddExecutor());
		etherPacketHandler.registerPacketHandler(
				PacketRedstoneWirelessCommands.wirelessCommands.remReceiver.toString(),
				new EtherPacketRXRemExecutor());
		etherPacketHandler.registerPacketHandler(
				PacketRedstoneWirelessCommands.wirelessCommands.fetchEther.toString(),
				new EtherPacketFetchEtherExecutor());
		ServerPacketHandler.registerPacketHandler(
				PacketIds.ETHER,
				etherPacketHandler);
		// Wireless Device Packets
		ServerWirelessDevicePacketHandler devicePacketHandler = new ServerWirelessDevicePacketHandler();
		devicePacketHandler.registerPacketHandler(
				PacketWirelessDeviceCommands.deviceCommands.activateTX.toString(),
				new DevicePacketActivateTXExecutor());
		devicePacketHandler.registerPacketHandler(
				PacketWirelessDeviceCommands.deviceCommands.deactivateTX.toString(),
				new DevicePacketDeactivateTXExecutor());
		devicePacketHandler.registerPacketHandler(
				PacketWirelessDeviceCommands.deviceCommands.activateRX.toString(),
				new DevicePacketActivateRXExecutor());
		devicePacketHandler.registerPacketHandler(
				PacketWirelessDeviceCommands.deviceCommands.deactivateRX.toString(),
				new DevicePacketDeactivateRXExecutor());
		devicePacketHandler.registerPacketHandler(
				PacketWirelessDeviceCommands.deviceCommands.changeFreq.toString(),
				new DevicePacketChangeFreqExecutor());
		ServerPacketHandler.registerPacketHandler(
				PacketIds.DEVICE,
				devicePacketHandler);
		// TODO Wireless Device Executors
		// GUI Packets
		ServerGuiPacketHandler guiPacketHandler = new ServerGuiPacketHandler();
		ServerPacketHandler.registerPacketHandler(
				PacketIds.GUI,
				guiPacketHandler);
		// TODO GUI Executors (Should be none)
		// Tile Packets
		ServerTilePacketHandler tilePacketHandler = new ServerTilePacketHandler();
		ServerPacketHandler.registerPacketHandler(
				PacketIds.TILE,
				tilePacketHandler);
	}

	@Override
	public void connectionClosed(INetworkManager manager) {
		// TODO Auto-generated method stub
		
	}
}
