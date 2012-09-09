package wirelessredstone.network.handlers;

import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.network.ServerPacketHandler;
import wirelessredstone.network.packets.PacketRedstoneWirelessOpenGui;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class ServerGuiPacketHandler implements IPacketHandler {

	@Override
	public void onPacketData(NetworkManager manager, Packet250CustomPayload packet, Player player) {}
	
	public static void sendGuiPacketTo(EntityPlayerMP player, TileEntityRedstoneWireless entity) {
		PacketRedstoneWirelessOpenGui packet = new PacketRedstoneWirelessOpenGui(entity);

		LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write(
				"sendGuiPacketTo:" + player.username,
				LoggerRedstoneWireless.LogLevel.DEBUG
		);

		ServerPacketHandler.sendPacketTo(
				player, 
				(Packet250CustomPayload) packet.getPacket()
		);
	}
}
