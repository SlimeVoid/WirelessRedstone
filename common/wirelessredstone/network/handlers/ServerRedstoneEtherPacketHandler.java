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
import java.util.List;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.ether.RedstoneEther;
import wirelessredstone.ether.RedstoneEtherNode;
import wirelessredstone.network.ServerPacketHandler;
import wirelessredstone.network.packets.PacketRedstoneEther;
import wirelessredstone.network.packets.PacketRedstoneWirelessCommands;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessR;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessT;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

/**
 * A server-side RedstoneEther packet sub-handler.
 * 
 * @author ali4z
 */
public class ServerRedstoneEtherPacketHandler implements IPacketHandler {

	/**
	 * Receive a packet from the handler.<br>
	 * Assembles the packet into an ether packet and routes to handlePacket().
	 */
	@Override
	public void onPacketData(NetworkManager manager, Packet250CustomPayload packet, Player player) {
		EntityPlayer entityplayer = (EntityPlayer) player;
		World world = entityplayer.worldObj;
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(
				packet.data));
		try {
			// Assemble packet
			int packetID = data.read();
			PacketRedstoneEther pRE = new PacketRedstoneEther();
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
	 * @param player The sending player.
	 */
	private void handlePacket(PacketRedstoneEther packet, World world, EntityPlayer player) {
		LoggerRedstoneWireless
				.getInstance("RedstoneEtherPacketHandler")
					.write(
							"handlePacket:" + ((EntityPlayer) player).username + ":" + packet.toString(),
							LoggerRedstoneWireless.LogLevel.DEBUG);

		// Change frequency command was given.
		// Route to handleChangeFreq()
		if (packet.getCommand() == PacketRedstoneWirelessCommands.changeFreq
				.getCommand()) {
			handleChangeFreq(packet, world);

			// Add transmitter command was given.
			// Route to handleAddTransmitter()
		} else if (packet.getCommand() == PacketRedstoneWirelessCommands.addTransmitter
				.getCommand()) {
			handleAddTransmitter(packet, world);

			// Set transmitter state command was given.
			// Route to handleSetTransmitterState()
		} else if (packet.getCommand() == PacketRedstoneWirelessCommands.setTransmitterState
				.getCommand()) {
			handleSetTransmitterState(packet, world);

			// Remove transmitter command was given.
			// Route to handleRemTransmitter()
		} else if (packet.getCommand() == PacketRedstoneWirelessCommands.remTransmitter
				.getCommand()) {
			handleRemTransmitter(packet, world);

			// Command unknown. Log a warning.
		} else {
			LoggerRedstoneWireless
					.getInstance("RedstoneEtherPacketHandler")
						.write(
								"handlePacket:" + ((EntityPlayer) player).username + ":" + packet.toString() + "UNKNOWN COMMAND",
								LoggerRedstoneWireless.LogLevel.WARNING);
		}
	}

	/**
	 * Handle a change frequency command.<br>
	 * Changes the frequency for a node, updates said node then broadcasts the
	 * change to all clients.
	 * 
	 * @param packet The packet.
	 * @param world The world object
	 */
	private void handleChangeFreq(PacketRedstoneEther packet, World world) {
		// Fetch the tile from the packet
		TileEntity entity = packet.getTarget(world);

		if (entity instanceof TileEntityRedstoneWireless) {
			// Assemble frequencies.
			int dFreq = Integer.parseInt(packet.getFreq());
			int oldFreq = Integer
					.parseInt(((TileEntityRedstoneWireless) entity)
							.getFreq()
								.toString());

			// Set the frequency to the tile
			((TileEntityRedstoneWireless) entity).setFreq(Integer
					.toString(oldFreq + dFreq));
			entity.onInventoryChanged();
			
			// Makr the block for update with the world.
			world.markBlockNeedsUpdate(
					packet.xPosition,
					packet.yPosition,
					packet.zPosition);

			// Broadcast change to all clients.
			sendEtherTileToAll((TileEntityRedstoneWireless) entity, world);
		}
	}

	/**
	 * Handle a add transmitter command.<br>
	 * Adds the transmitter to the ether.
	 * 
	 * @param packet The packet.
	 * @param world The world object
	 */
	private void handleAddTransmitter(PacketRedstoneEther packet, World world) {
		RedstoneEther.getInstance().addTransmitter(
				world,
				packet.xPosition,
				packet.yPosition,
				packet.zPosition,
				packet.getFreq());
	}

	/**
	 * Handle a set transmitter state command.<br>
	 * Sets the transmitter's state on the ether.
	 * 
	 * @param packet The packet.
	 * @param world The world object
	 */
	private void handleSetTransmitterState(PacketRedstoneEther packet, World world) {
		RedstoneEther.getInstance().setTransmitterState(
				world,
				packet.xPosition,
				packet.yPosition,
				packet.zPosition,
				packet.getFreq(),
				packet.getState());
	}

	/**
	 * Handle a remove transmitter command.<br>
	 * Removes the transmitter from the ether.
	 * 
	 * @param packet The packet.
	 * @param world The world object
	 */
	private void handleRemTransmitter(PacketRedstoneEther packet, World world) {
		RedstoneEther.getInstance().remTransmitter(
				world,
				packet.xPosition,
				packet.yPosition,
				packet.zPosition,
				packet.getFreq());
	}

	
	
	
	/**
	 * Broadcasts an ether tile to all clients.
	 * 
	 * @param entity The ether tile.
	 * @param world The world object.
	 */
	public static void sendEtherTileToAll(TileEntityRedstoneWireless entity, World world) {
		// Assemble packet.
		PacketRedstoneEther packet = new PacketRedstoneEther(entity, world);

		LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write(
				"sendEtherTileToAll:" + packet.toString(),
				LoggerRedstoneWireless.LogLevel.DEBUG);
		
		// Broadcast packet.
		ServerPacketHandler.broadcastPacket((Packet250CustomPayload) packet
				.getPacket());
	}

	/**
	 * Send an ether tile to a specific player.
	 * 
	 * @param entityplayermp The receiving player.
	 * @param entity The ether tile.
	 * @param world The world object.
	 */
	public static void sendEtherTileTo(EntityPlayerMP entityplayermp, TileEntityRedstoneWireless entity, World world) {
		// Assemble packet.
		PacketRedstoneEther packet = new PacketRedstoneEther(entity, world);

		LoggerRedstoneWireless
				.getInstance("PacketHandlerOutput")
					.write(
							"sendEtherTileTo:" + entityplayermp.username + ":" + packet.toString(),
							LoggerRedstoneWireless.LogLevel.DEBUG);

		// Send packet.
		ServerPacketHandler.sendPacketTo(
				entityplayermp,
				(Packet250CustomPayload) packet.getPacket());
	}

	/**
	 * Broadcast a ether node to all clients.
	 * 
	 * @param node Ether node.
	 */
	public static void sendEtherNodeTileToAll(RedstoneEtherNode node) {
		// Fetch required data.
		World world = ModLoader
				.getMinecraftServerInstance()
					.worldServerForDimension(0);
		TileEntity entity = world.getBlockTileEntity(node.i, node.j, node.k);
		
		
		if (entity instanceof TileEntityRedstoneWireless) {
			// Send the required data.
			sendEtherTileToAll((TileEntityRedstoneWireless) entity, world);
		}
	}

	/**
	 * Send all ether node to a specific player.
	 * 
	 * @param entityplayermp Receiving player.
	 */
	public static void sendEtherTilesTo(EntityPlayerMP entityplayermp) {
		LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write(
				"sendEtherTilesTo" + entityplayermp.username,
				LoggerRedstoneWireless.LogLevel.DEBUG);

		// Prepare required data.
		PacketRedstoneEther packet;
		World world = ModLoader
				.getMinecraftServerInstance()
					.worldServerForDimension(0);

		// Fetch all receivers
		List<RedstoneEtherNode> list = RedstoneEther.getInstance().getRXNodes();
		
		// Send each receivers to the player.
		for (RedstoneEtherNode node : list) {
			TileEntity entity = world
					.getBlockTileEntity(node.i, node.j, node.k);
			if (entity instanceof TileEntityRedstoneWirelessR) {
				sendEtherTileTo(
						entityplayermp,
						(TileEntityRedstoneWirelessR) entity,
						world);
			}
		}

		// Fetch all transmitters
		list = RedstoneEther.getInstance().getTXNodes();
		
		// Send all transmitters to the player.
		for (RedstoneEtherNode node : list) {
			TileEntity entity = world
					.getBlockTileEntity(node.i, node.j, node.k);
			if (entity instanceof TileEntityRedstoneWirelessT) {
				sendEtherTileTo(
						entityplayermp,
						(TileEntityRedstoneWirelessT) entity,
						world);
			}
		}
	}

	/**
	 * Broadcast all ether nodes
	 */
	public static void sendEtherTilesToAll() {
		LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write(
				"sendEtherTilesToAll",
				LoggerRedstoneWireless.LogLevel.DEBUG);

		// Prepare required data.
		PacketRedstoneEther packet;
		World world = ModLoader
				.getMinecraftServerInstance()
					.worldServerForDimension(0);

		// Fetch all receivers
		List<RedstoneEtherNode> list = RedstoneEther.getInstance().getRXNodes();
		
		// Broadcast all receivers
		for (RedstoneEtherNode node : list) {
			TileEntity entity = world
					.getBlockTileEntity(node.i, node.j, node.k);
			if (entity instanceof TileEntityRedstoneWirelessR) {
				sendEtherTileToAll((TileEntityRedstoneWirelessR) entity, world);
			}
		}

		// Fetch all transmitters
		list = RedstoneEther.getInstance().getTXNodes();
		
		// Broadcast all transmitters
		for (RedstoneEtherNode node : list) {
			TileEntity entity = world
					.getBlockTileEntity(node.i, node.j, node.k);
			if (entity instanceof TileEntityRedstoneWirelessT) {
				sendEtherTileToAll((TileEntityRedstoneWirelessT) entity, world);
			}
		}
	}

	/**
	 * Broadcast a set of transmitters and receivers to all.
	 * 
	 * @param txs
	 * @param rxs
	 */
	public static void sendEtherFrequencyTilesToAll(List<RedstoneEtherNode> txs, List<RedstoneEtherNode> rxs) {
		LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write(
				"sendEtherFrequencyTilesToAll",
				LoggerRedstoneWireless.LogLevel.DEBUG);

		// Assemble required data.
		World world = ModLoader
				.getMinecraftServerInstance()
					.worldServerForDimension(0);

		// Broadcast receivers.
		for (RedstoneEtherNode node : rxs) {
			TileEntity entity = world
					.getBlockTileEntity(node.i, node.j, node.k);
			if (entity instanceof TileEntityRedstoneWirelessR) {
				sendEtherTileToAll((TileEntityRedstoneWirelessR) entity, world);
			}
		}

		// Broadcast transmitters.
		for (RedstoneEtherNode node : txs) {
			TileEntity entity = world
					.getBlockTileEntity(node.i, node.j, node.k);
			if (entity instanceof TileEntityRedstoneWirelessT) {
				sendEtherTileToAll((TileEntityRedstoneWirelessT) entity, world);
			}
		}
	}
}
