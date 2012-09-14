package wirelessredstone.network.handlers;

import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.World;
import wirelessredstone.network.ServerPacketHandler;
import wirelessredstone.network.packets.PacketRedstoneWirelessCommands;
import wirelessredstone.network.packets.PacketWirelessTile;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class ServerTilePacketHandler implements IPacketHandler {

	@Override
	public void onPacketData(NetworkManager manager, Packet250CustomPayload packet, Player player) {
	}

	public static void sendWirelessTileToAll(TileEntityRedstoneWireless tileentity, World world) {
		PacketWirelessTile packet = new PacketWirelessTile(
				PacketRedstoneWirelessCommands.fetchTile.getCommand(),
					tileentity);
		ServerPacketHandler.broadcastPacket((Packet250CustomPayload) packet
				.getPacket());
	}
}
