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

import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.Packet250CustomPayload;
import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.network.ServerPacketHandler;
import wirelessredstone.network.packets.PacketRedstoneWirelessOpenGui;
import wirelessredstone.network.packets.PacketWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;

/**
 * A server-side GUI packet sub-handler.
 * 
 * @author ali4z
 */
public class ServerGuiPacketHandler extends SubPacketHandler {

	@Override
	protected PacketWireless createNewPacketWireless() {
		return null;
	}

	/**
	 * Send a GUI packet to specified player.
	 * 
	 * @param player Receiving player.
	 * @param entity TileEntity of the wireless block which is being accessed.
	 */
	public static void sendGuiPacketTo(EntityPlayerMP player, TileEntityRedstoneWireless entity) {
		// Assemble a OpenGUI packet.
		PacketRedstoneWirelessOpenGui packet = new PacketRedstoneWirelessOpenGui(
				entity);

		LoggerRedstoneWireless.getInstance(
				"ServerGuiPacketHandler"
		).write(
				false,
				"sendGuiPacketTo(" + player.username+", entity)",
				LoggerRedstoneWireless.LogLevel.DEBUG
		);

		// Send the packet.
		ServerPacketHandler.sendPacketTo(
				player,
				(Packet250CustomPayload) packet.getPacket());
	}
}
