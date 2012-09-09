package wirelessredstone.network.handlers;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.ether.RedstoneEther;
import wirelessredstone.network.ClientPacketHandler;
import wirelessredstone.network.packets.PacketRedstoneEther;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessR;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessT;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class ClientRedstoneEtherPacketHandler implements IPacketHandler {

	@Override
	public void onPacketData(NetworkManager manager, Packet250CustomPayload packet, Player player) {
		EntityPlayer entityplayer = (EntityPlayer)player;
		World world = entityplayer.worldObj;
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(packet.data));
		try {
			int packetID = data.read();
			PacketRedstoneEther pRE = new PacketRedstoneEther();
			pRE.readData(data);
			handlePacket(pRE, world, entityplayer);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private static void handlePacket(PacketRedstoneEther packet, World world, EntityPlayer entityplayer) {
		LoggerRedstoneWireless.getInstance("ClientRedstoneEtherPacketHandler").write(
				"handlePacket:" + 
				packet.toString(),
				LoggerRedstoneWireless.LogLevel.DEBUG
		);
		
		TileEntity tileentity = packet.getTarget(world);
		
		if (packet.getCommand().equals("addTransmitter")) {
			handleAddTransmitter(packet,world,tileentity);
		} else if (packet.getCommand().equals("addReceiver")) {
			handleAddReceiver(packet,world,tileentity);
		}
	}
	private static void handleAddTransmitter(PacketRedstoneEther packet, World world, TileEntity tileentity) {
		if (
				tileentity != null && 
				tileentity instanceof TileEntityRedstoneWirelessT
		) {
			((TileEntityRedstoneWireless) tileentity).setFreq(packet.getFreq().toString());
		} else {
			tileentity = new TileEntityRedstoneWirelessT();
			((TileEntityRedstoneWireless) tileentity).setFreq(packet.getFreq().toString());
			world.setBlockTileEntity(
					packet.xPosition,
					packet.yPosition, 
					packet.zPosition, 
					tileentity
			);

		}
		RedstoneEther.getInstance().addTransmitter(
				world,
				packet.xPosition, 
				packet.yPosition, 
				packet.zPosition,
				packet.getFreq().toString()
		);
	}
	
	private static void handleAddReceiver(PacketRedstoneEther packet, World world, TileEntity tileentity) {
		if (
				tileentity != null && 
				tileentity instanceof TileEntityRedstoneWirelessR
		) {
			((TileEntityRedstoneWireless) tileentity).setFreq(packet.getFreq().toString());
		} else {
			tileentity = new TileEntityRedstoneWirelessR();
			((TileEntityRedstoneWireless) tileentity).setFreq(packet.getFreq().toString());
			world.setBlockTileEntity(
					packet.xPosition,
					packet.yPosition, 
					packet.zPosition, 
					tileentity
			);
		}
		RedstoneEther.getInstance().addReceiver(
				world,
				packet.xPosition, 
				packet.yPosition, 
				packet.zPosition,
				packet.getFreq().toString()
		);
	}

	public static void sendRedstoneEtherPacket(String command, int i, int j, int k, Object freq, boolean state) {
		PacketRedstoneEther packet = new PacketRedstoneEther(command);
		packet.setPosition(i, j, k, 0);
		packet.setFreq(freq);
		packet.setState(state);
		LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write(
				"sendRedstoneEtherPacket:" + packet.toString(),
				LoggerRedstoneWireless.LogLevel.DEBUG
		);
		ClientPacketHandler.sendPacket((Packet250CustomPayload) packet.getPacket());
	}
}
