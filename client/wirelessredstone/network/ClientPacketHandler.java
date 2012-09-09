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

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import wirelessredstone.core.WRCore;
import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.ether.RedstoneEther;
import wirelessredstone.presentation.gui.GuiRedstoneWireless;
import wirelessredstone.presentation.gui.GuiRedstoneWirelessInventory;
import wirelessredstone.network.packets.PacketRedstoneEther;
import wirelessredstone.network.packets.PacketRedstoneWirelessOpenGui;
import wirelessredstone.network.packets.core.PacketIds;
import wirelessredstone.network.packets.core.PacketUpdate;
import wirelessredstone.network.packets.PacketWirelessTile;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessR;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessT;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class ClientPacketHandler implements IPacketHandler {
	
	private static Map<Integer,IPacketHandler> clientHandlers;
	
	public ClientPacketHandler() {
		clientHandlers = new HashMap<Integer,IPacketHandler>();
	}
	
	public static void reigsterPacketHandler(int packetID, IPacketHandler handler) {
		clientHandlers.put(packetID, handler);
	}
	
	@Override
	public void onPacketData(NetworkManager manager, Packet250CustomPayload packet, Player player) {
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(packet.data));
		try {
			int packetID = data.read();
			
			if ( clientHandlers.containsKey(packetID) )
				clientHandlers.get(packetID).onPacketData(manager, packet, player);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}

	public static class PacketHandlerOutput {
		public static void sendRedstoneEtherPacket(String command, int i,
				int j, int k, Object freq, boolean state) {
			PacketRedstoneEther packet = new PacketRedstoneEther(command);
			packet.setPosition(i, j, k, 0);
			packet.setFreq(freq);
			packet.setState(state);
			LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write(
					"sendRedstoneEtherPacket:" + packet.toString(),
					LoggerRedstoneWireless.LogLevel.DEBUG);
			ModLoader.getMinecraftInstance().getSendQueue()
					.addToSendQueue(packet.getPacket());
		}
	}
}
