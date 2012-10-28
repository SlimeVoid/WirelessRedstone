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

import net.minecraft.src.INetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.World;
import wirelessredstone.network.ServerPacketHandler;
import wirelessredstone.network.packets.PacketRedstoneWirelessCommands;
import wirelessredstone.network.packets.PacketWirelessTile;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

/**
 * A server-side Tile packet sub-handler.
 * 
 * @author ali4z
 */
public class ServerTilePacketHandler implements IPacketHandler {

	/**
	 * Receive a packet from the handler.<br>
	 * Server-side Tile packet handler should never receive any packets, thus this does nothing.
	 */
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
	}

	/**
	 * Broadcast a wireless tile.
	 * 
	 * @param tileentity The tile to broadcast.
	 * @param world The world object.
	 */
	public static void sendWirelessTileToAll(TileEntityRedstoneWireless tileentity, World world) {
		// Assemble packet.
		PacketWirelessTile packet = new PacketWirelessTile(
				PacketRedstoneWirelessCommands.fetchTile.getCommand(),
					tileentity);
		
		// Broadcast packet.
		ServerPacketHandler.broadcastPacket((Packet250CustomPayload) packet
				.getPacket());
	}
}
