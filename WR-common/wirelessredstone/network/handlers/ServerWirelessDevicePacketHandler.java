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
package wirelessredstone.network.handlers;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ModLoader;
import net.minecraft.src.INetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import wirelessredstone.api.IDevicePacketExecutor;
import wirelessredstone.api.IEtherPacketExecutor;
import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.ether.RedstoneEther;
import wirelessredstone.ether.RedstoneEtherNode;
import wirelessredstone.network.ServerPacketHandler;
import wirelessredstone.network.packets.PacketRedstoneEther;
import wirelessredstone.network.packets.PacketWirelessDevice;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessR;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessT;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

/**
 * A server-side RedstoneEther packet sub-handler.
 * 
 * @author Eurymachus
 */
public class ServerWirelessDevicePacketHandler implements IPacketHandler {
	private static Map<Integer, IDevicePacketExecutor> executors = new HashMap<Integer, IDevicePacketExecutor>();
	/**
	 * Register an executor with the server-side packet sub-handler.
	 * 
	 * @param commandID Command ID for the executor to handle.
	 * @param executor The executor
	 */
	public static void registerPacketHandler(int commandID, IDevicePacketExecutor executor) {
		executors.put(commandID, executor);
	}
	
	/**
	 * Receive a packet from the handler.<br>
	 * Assembles the packet into an ether packet and routes to handlePacket().
	 */
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		EntityPlayer entityplayer = (EntityPlayer) player;
		World world = entityplayer.worldObj;
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(
				packet.data));
		try {
			// Assemble packet
			int packetID = data.read();
			PacketWirelessDevice pRE = new PacketWirelessDevice();
			pRE.readData(data);
			// Route to handlePacket()
			handlePacket(pRE, world, entityplayer);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Handles a received packet.
	 * 
	 * @param packet The received packet
	 * @param world The world object
	 * @param entityplayer The sending player.
	 */
	private void handlePacket(PacketWirelessDevice packet, World world, EntityPlayer entityplayer) {
		LoggerRedstoneWireless.getInstance(
				"ServerRedstoneEtherPacketHandler"
		).write(
				world.isRemote,
				"handlePacket(" + packet.toString()+ ", world," + entityplayer.username + ")",
				LoggerRedstoneWireless.LogLevel.DEBUG
		);
		// Fetch the command.
		int command = packet.getCommand();
		
		// Execute the command.
		if ( executors.containsKey(command)) {
			executors.get(command).execute(packet, world, entityplayer);
		} else {
			LoggerRedstoneWireless.getInstance(
					"ServerRedstoneEtherPacketHandler"
			).write(
					world.isRemote,
					"handlePacket(" + packet.toString()+ ", world," + entityplayer.username + ") - UNKNOWN COMMAND",
					LoggerRedstoneWireless.LogLevel.WARNING
			);
		}
	}
}
