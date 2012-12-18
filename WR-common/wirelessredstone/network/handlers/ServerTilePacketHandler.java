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

import net.minecraft.src.World;
import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.network.ServerPacketHandler;
import wirelessredstone.network.packets.PacketRedstoneWirelessCommands;
import wirelessredstone.network.packets.PacketWireless;
import wirelessredstone.network.packets.PacketWirelessTile;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;

/**
 * A server-side Tile packet sub-handler.
 * 
 * @author ali4z
 */
public class ServerTilePacketHandler extends SubPacketHandler {

	@Override
	protected PacketWireless createNewPacketWireless() {
		return null;
	}

	/**
	 * Broadcast a wireless tile.
	 * 
	 * @param tileentity The tile to broadcast.
	 * @param world The world object.
	 */
	public static void sendWirelessTileToAll(TileEntityRedstoneWireless tileentity, World world) {
		LoggerRedstoneWireless.getInstance(
				"ServerTilePacketHandler"
		).write(
				world.isRemote,
				"sendWirelessTileToAll()",
				LoggerRedstoneWireless.LogLevel.DEBUG
		);
		
		// Assemble packet.
		PacketWirelessTile packet = new PacketWirelessTile(
				PacketRedstoneWirelessCommands.wirelessCommands.fetchTile.toString(),
					tileentity);
		
		// Broadcast packet.
		ServerPacketHandler.broadcastPacket(packet
				.getPacket());
	}
}
