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
package wirelessredstone.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.INetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.World;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

/**
 * Packet handler, server-side.
 * 
 * @author ali4z
 */
public class ServerPacketHandler implements IPacketHandler {
	private static Map<Integer, IPacketHandler> commonHandlers = new HashMap<Integer, IPacketHandler>();

	/**
	 * Register a sub-handler with the server-side packet handler.
	 * 
	 * @param packetID Packet ID for the sub-handler to handle.
	 * @param handler The sub-handler.
	 */
	public static void registerPacketHandler(int packetID, IPacketHandler handler) {
		commonHandlers.put(packetID, handler);
	}

	/**
	 * The server-side packet handler receives a packet.<br>
	 * Fetches the packet ID and routes it on to sub-handlers.
	 */
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(
				packet.data));
		try {
			int packetID = data.read();

			if (commonHandlers.containsKey(packetID))
				commonHandlers.get(packetID).onPacketData(
						manager,
						packet,
						player);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * Send a packet to specified player.
	 * 
	 * @param player Receiving player.
	 * @param packet Packet to send.
	 */
	public static void sendPacketTo(EntityPlayerMP player, Packet250CustomPayload packet) {
		player.playerNetServerHandler.netManager
				.addToSendQueue(packet);
	}

	/**
	 * Broadcast a packet to everyone.
	 * 
	 * @param packet Packet to broadcast.
	 */
	public static void broadcastPacket(Packet250CustomPayload packet) {
		World[] worlds = DimensionManager.getWorlds();
		for (int i = 0; i < worlds.length; i++) {

			for (int j = 0; j < worlds[i].playerEntities.size(); j++) {
				sendPacketTo(
						(EntityPlayerMP) worlds[i].playerEntities.get(j),
						packet);
			}
		}
	}
}
