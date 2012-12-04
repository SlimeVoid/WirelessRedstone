package wirelessredstone.network.handlers;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.INetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.World;

import wirelessredstone.api.IPacketExecutor;
import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.network.packets.PacketWireless;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public abstract class ClientSubPacketHandler implements IPacketHandler {
	private Map<String, IPacketExecutor> executors = new HashMap<String, IPacketExecutor>();
	/**
	 * Register an executor with the client-side packet sub-handler.
	 * 
	 * @param commandID Command ID for the executor to handle.
	 * @param executor The executor
	 */
	public void registerPacketHandler(String commandString, IPacketExecutor executor) {
		if (executors.containsKey(commandString)) {
			LoggerRedstoneWireless.getInstance(
					LoggerRedstoneWireless.filterClassName(this.toString())
			).write(
					false,
					"Command String [" + commandString + "] already registered.",
					LoggerRedstoneWireless.LogLevel.ERROR
			);
			throw new RuntimeException("Command String [" + commandString + "] already registered.");
		}
		executors.put(commandString, executor);
	}

	/**
	 * Receive a packet from the handler.<br>
	 * Assembles the packet into an wireless packet and routes to handlePacket().
	 */
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		EntityPlayer entityplayer = (EntityPlayer)player;
		World world = entityplayer.worldObj;
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(packet.data));
		try {
			int packetID = data.read();
			PacketWireless pW = this.createNewPacketWireless();
			pW.readData(data);
			handlePacket(pW, world, entityplayer);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Abstract method for returning a new instance of PacketWireless
	 * 
	 * @return new PacketWireless
	 */
	protected abstract PacketWireless createNewPacketWireless();
	
	/**
	 * Handles a received packet.
	 * 
	 * @param packet The received packet
	 * @param world The world object
	 * @param entityplayer The sending player.
	 */
	protected void handlePacket(PacketWireless packet, World world, EntityPlayer entityplayer) {
		LoggerRedstoneWireless.getInstance(
				LoggerRedstoneWireless.filterClassName(this.getClass().toString())
		).write(
				world.isRemote,
				"handlePacket("+packet.toString()+")",
				LoggerRedstoneWireless.LogLevel.DEBUG
		);
		
		// Fetch the command.
		int command = packet.getCommand();
		
		// Execute the command.
		if ( executors.containsKey(command)) {
			executors.get(command).execute(packet, world, entityplayer);
		} else {
			LoggerRedstoneWireless.getInstance(
					LoggerRedstoneWireless.filterClassName(this.getClass().toString())
			).write(
					world.isRemote,
					"handlePacket(" + entityplayer.username + "," + packet.toString()+") - UNKNOWN COMMAND",
					LoggerRedstoneWireless.LogLevel.WARNING
			);
		}
	}
}
