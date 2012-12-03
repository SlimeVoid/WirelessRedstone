/*    
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package net.minecraft.src.wirelessredstone.addon.remote.smp.network;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.addon.remote.WirelessRemote;
import net.minecraft.src.wirelessredstone.addon.remote.data.WirelessRemoteData;
import net.minecraft.src.wirelessredstone.addon.remote.data.WirelessRemoteDevice;
import net.minecraft.src.wirelessredstone.addon.remote.smp.network.packet.PacketWirelessRemoteOpenGui;
import net.minecraft.src.wirelessredstone.addon.remote.smp.network.packet.PacketWirelessRemoteSettings;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketUpdate;

public class PacketHandlerWirelessRemote {

	public static void handlePacket(PacketUpdate packet, World world,
			EntityPlayer entityplayer) {
		if (packet instanceof PacketWirelessRemoteSettings)
			PacketHandlerInput.handleWirelessRemote(
					(PacketWirelessRemoteSettings) packet, world, entityplayer);
		else if (packet instanceof PacketWirelessRemoteOpenGui)
			PacketHandlerInput.handleWirelessRemoteOpenGui(
					(PacketWirelessRemoteOpenGui) packet, world, entityplayer);
	}

	private static class PacketHandlerInput {
		private static void handleWirelessRemote(
				PacketWirelessRemoteSettings packet, World world,
				EntityPlayer entityplayer) {
			LoggerRedstoneWireless.getInstance("PacketHandlerInput").write(
					"handleWirelessRemotePacket:" + packet.toString(),
					LoggerRedstoneWireless.LogLevel.DEBUG);
			String index = WirelessRemote.itemRemote.getItemName();
			WirelessRemoteData data = WirelessRemote.getDeviceData(index, packet.getRemoteID(), "Wireless Remote", world, entityplayer);
			data.setState(packet.getState());
		}

		public static void handleWirelessRemoteOpenGui(
				PacketWirelessRemoteOpenGui packet, World world,
				EntityPlayer entityplayer) {
			LoggerRedstoneWireless.getInstance("PacketHandlerInput").write(
					"handleWirelessRemoteGuiPacket:" + packet.toString(),
					LoggerRedstoneWireless.LogLevel.DEBUG);
			String index = WirelessRemote.itemRemote.getItemName();
			WirelessRemoteData data = WirelessRemote.getDeviceData(index,
					packet.getDeviceID(), "Wireless Remote", world,
					entityplayer);
			data.setFreq(packet.getFreq());
			WirelessRemote.activateGUI(world, entityplayer, data);
		}
	}

	public static class PacketHandlerOutput {
		public static void sendWirelessRemotePacket(String command,
				WirelessRemoteDevice remote) {
			PacketWirelessRemoteSettings packet = new PacketWirelessRemoteSettings(
					command);
			packet.setRemoteID(remote.getDeviceData().getID());
			packet.setPosition(remote.getCoords().getX(), remote.getCoords()
					.getY(), remote.getCoords().getZ());
			packet.setFreq(remote.getFreq());
			LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write(
					"sendWirelessRemotePacket:" + packet.toString(),
					LoggerRedstoneWireless.LogLevel.DEBUG);
			ModLoader.getMinecraftInstance().getSendQueue()
					.addToSendQueue(packet.getPacket());
		}

		public static void sendWirelessRemotePacket(String command, int id,
				Object freq) {
			PacketWirelessRemoteSettings packet = new PacketWirelessRemoteSettings(
					command);
			packet.setRemoteID(id);
			packet.setFreq(freq);
			LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write(
					"sendWirelessRemotePacket:" + packet.toString(),
					LoggerRedstoneWireless.LogLevel.DEBUG);
			ModLoader.getMinecraftInstance().getSendQueue()
					.addToSendQueue(packet.getPacket());
		}
	}
}