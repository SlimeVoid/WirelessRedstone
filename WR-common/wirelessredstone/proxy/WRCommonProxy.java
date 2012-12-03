package wirelessredstone.proxy;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.NetHandler;
import net.minecraft.src.INetworkManager;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import wirelessredstone.api.ICommonProxy;
import wirelessredstone.network.ServerPacketHandler;
import wirelessredstone.network.handlers.ServerGuiPacketHandler;
import wirelessredstone.network.handlers.ServerRedstoneEtherPacketHandler;
import wirelessredstone.network.handlers.ServerTilePacketHandler;
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
	public void initPacketHandlers() {
		/////////////////////
		// Server Handlers //
		/////////////////////
		// Ether Packets
		ServerPacketHandler.registerPacketHandler(
				PacketIds.ETHER,
				new ServerRedstoneEtherPacketHandler());
		// Executors
		ServerRedstoneEtherPacketHandler.registerPacketHandler(
				PacketRedstoneWirelessCommands.changeFreq.getCommand(),
				new EtherPacketChangeFreqExecutor());
		ServerRedstoneEtherPacketHandler.registerPacketHandler(
				PacketRedstoneWirelessCommands.addTransmitter.getCommand(),
				new EtherPacketTXAddExecutor());
		ServerRedstoneEtherPacketHandler.registerPacketHandler(
				PacketRedstoneWirelessCommands.setTransmitterState.getCommand(),
				new EtherPacketTXSetStateExecutor());
		ServerRedstoneEtherPacketHandler.registerPacketHandler(
				PacketRedstoneWirelessCommands.remTransmitter.getCommand(),
				new EtherPacketTXRemExecutor());
		ServerRedstoneEtherPacketHandler.registerPacketHandler(
				PacketRedstoneWirelessCommands.addReceiver.getCommand(),
				new EtherPacketRXAddExecutor());
		ServerRedstoneEtherPacketHandler.registerPacketHandler(
				PacketRedstoneWirelessCommands.remReceiver.getCommand(),
				new EtherPacketRXRemExecutor());
		ServerRedstoneEtherPacketHandler.registerPacketHandler(
				PacketRedstoneWirelessCommands.fetchEther.getCommand(),
				new EtherPacketFetchEtherExecutor());
		// GUI Packets
		ServerPacketHandler.registerPacketHandler(
				PacketIds.GUI,
				new ServerGuiPacketHandler());
		// Tile Packets
		ServerPacketHandler.registerPacketHandler(
				PacketIds.TILE,
				new ServerTilePacketHandler());
	}
}
