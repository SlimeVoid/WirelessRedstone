package wirelessredstone.network.handlers;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import wirelessredstone.block.BlockRedstoneWireless;
import wirelessredstone.core.WRCore;
import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.ether.RedstoneEther;
import wirelessredstone.network.ClientPacketHandler;
import wirelessredstone.network.packets.PacketRedstoneEther;
import wirelessredstone.network.packets.PacketRedstoneWirelessCommands;
import wirelessredstone.network.packets.executor.IEtherPacketExecutor;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessR;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessT;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class ClientRedstoneEtherPacketHandler implements IPacketHandler {
	private static Map<Integer, IEtherPacketExecutor> executors = new HashMap<Integer, IEtherPacketExecutor>();
	/**
	 * Register an executor with the client-side packet sub-handler.
	 * 
	 * @param commandID Command ID for the executor to handle.
	 * @param executor The executor
	 */
	public static void registerPacketHandler(int commandID, IEtherPacketExecutor executor) {
		executors.put(commandID, executor);
	}
	
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
		
		// Fetch the command.
		int command = packet.getCommand();
		
		// Execute the command.
		if ( executors.containsKey(command)) {
			executors.get(command).execute(packet, world, entityplayer);
		} else {
			LoggerRedstoneWireless
					.getInstance("ClientRedstoneEtherPacketHandler")
						.write(
								"handlePacket:" + ((EntityPlayer) entityplayer).username + ":" + packet.toString() + "UNKNOWN COMMAND",
								LoggerRedstoneWireless.LogLevel.WARNING);
		}
	}

	public static void sendRedstoneEtherPacket(int command, int i, int j, int k, Object freq, boolean state) {
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
