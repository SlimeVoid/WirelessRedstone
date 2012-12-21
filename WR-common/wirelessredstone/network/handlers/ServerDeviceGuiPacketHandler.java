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

import net.minecraft.entity.player.EntityPlayerMP;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.network.ServerPacketHandler;
import wirelessredstone.network.packets.PacketRedstoneWirelessOpenGuiDevice;
import wirelessredstone.network.packets.PacketWireless;

/**
 * A server-side GUI packet sub-handler.
 * 
 * @author Eurymachus
 */
public class ServerDeviceGuiPacketHandler extends SubPacketHandler {

	@Override
	protected PacketWireless createNewPacketWireless() {
		return new PacketRedstoneWirelessOpenGuiDevice();
	}

	/**
	 * Send a GUI packet to specified player.
	 * 
	 * @param player Receiving player.
	 * @param devicedata the device data to send.
	 */
	public static void sendGuiPacketTo(EntityPlayerMP player, IWirelessDeviceData devicedata) {
		// Assemble a OpenGUI packet.
		
		LoggerRedstoneWireless.getInstance(
				"ServerDeviceGuiPacketHandler"
		).write(
				false,
				"sendGuiPacketTo(" + player.username+", entity)",
				LoggerRedstoneWireless.LogLevel.DEBUG
		);

		PacketRedstoneWirelessOpenGuiDevice packet = new PacketRedstoneWirelessOpenGuiDevice(devicedata);
		
		// Send the packet.
		sendGuiPacketTo(player, packet);
	}

	/**
	 * Send a GUI packet to specified player.
	 * 
	 * @param player Receiving player.
	 * @param packet the packet data to send.
	 */
	public static void sendGuiPacketTo(EntityPlayerMP player, PacketRedstoneWirelessOpenGuiDevice packet) {
		// Assemble a OpenGUI packet.
		
		LoggerRedstoneWireless.getInstance(
				"ServerDeviceGuiPacketHandler"
		).write(
				false,
				"sendGuiPacketTo(" + player.username+", entity)",
				LoggerRedstoneWireless.LogLevel.DEBUG
		);
		
		// Send the packet.
		ServerPacketHandler.sendPacketTo(
				player,
				packet.getPacket());
	}
}
