package wirelessredstone.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.World;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class ServerPacketHandler implements IPacketHandler {

	private static Map<Integer,IPacketHandler> commonHandlers = new HashMap<Integer,IPacketHandler>();
		
	public static void reigsterPacketHandler(int packetID, IPacketHandler handler) {
		commonHandlers.put(packetID, handler);
	}
	
	@Override
	public void onPacketData(NetworkManager manager, Packet250CustomPayload packet, Player player) {
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(packet.data));
		try {
			int packetID = data.read();
			
			if ( commonHandlers.containsKey(packetID) )
				commonHandlers.get(packetID).onPacketData(manager, packet, player);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	public static void sendPacketTo(EntityPlayerMP player, Packet250CustomPayload packet) {
		((EntityPlayerMP)player).serverForThisPlayer.theNetworkManager.addToSendQueue(packet);
	}
	
	public static void broadcastPacket(Packet250CustomPayload packet) {
		World[] worlds = DimensionManager.getWorlds();
		for (int i = 0; i < worlds.length; i++) {

			for (int j = 0; j < worlds[i].playerEntities.size(); j++) {
				sendPacketTo(
						(EntityPlayerMP) worlds[i].playerEntities.get(j),
						packet
				);
			}
		}
	}
}
