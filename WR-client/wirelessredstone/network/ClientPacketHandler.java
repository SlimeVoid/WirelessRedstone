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
package wirelessredstone.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.HashMap;
import java.util.Map;

import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.network.handlers.ClientSubPacketHandler;

import net.minecraft.src.ModLoader;
import net.minecraft.src.INetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class ClientPacketHandler implements IPacketHandler {
	
	private static Map<Integer, ClientSubPacketHandler> clientHandlers = new HashMap<Integer, ClientSubPacketHandler>();
		
	public static void registerPacketHandler(int packetID, ClientSubPacketHandler handler) {
		clientHandlers.put(packetID, handler);
	}

	/**
	 * Retrieves the registered sub-handler from the server side list
	 * 
	 * @param packetID
	 * @return the sub-handler
	 */
	public static ClientSubPacketHandler getPacketHandler(int packetID) {
		if (!clientHandlers.containsKey(packetID)) {
			LoggerRedstoneWireless
			.getInstance(LoggerRedstoneWireless.filterClassName(ClientPacketHandler.class.toString())
			).write(
					false,
					"Tried to get a Packet Handler for ID: " + packetID + " that has not been registered.",
					LoggerRedstoneWireless.LogLevel.WARNING
			);
			throw new RuntimeException("Tried to get a Packet Handler for ID: " + packetID + " that has not been registered.");
		}
		return clientHandlers.get(packetID);
	}
	
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(packet.data));
		try {
			int packetID = data.read();
			
			if ( clientHandlers.containsKey(packetID) )
				clientHandlers.get(packetID).onPacketData(manager, packet, player);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}

	public static void sendPacket(Packet250CustomPayload packet) {
		ModLoader.getMinecraftInstance().getSendQueue().addToSendQueue(packet);
	}
}
