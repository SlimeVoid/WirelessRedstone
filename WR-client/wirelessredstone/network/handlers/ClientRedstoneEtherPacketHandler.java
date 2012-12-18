package wirelessredstone.network.handlers;

import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.network.ClientPacketHandler;
import wirelessredstone.network.packets.PacketRedstoneEther;
import wirelessredstone.network.packets.PacketWireless;

public class ClientRedstoneEtherPacketHandler extends SubPacketHandler {

	@Override
	protected PacketWireless createNewPacketWireless() {
		return new PacketRedstoneEther();
	}

	public static void sendRedstoneEtherPacket(String command, int i, int j, int k, Object freq, boolean state) {
		PacketRedstoneEther packet = new PacketRedstoneEther(command);
		packet.setPosition(i, j, k, 0);
		packet.setFreq(freq);
		packet.setState(state);
		LoggerRedstoneWireless.getInstance(
				"ClientRedstoneEtherPacketHandler"
		).write(
				true,
				"sendRedstoneEtherPacket(" + packet.toString()+")",
				LoggerRedstoneWireless.LogLevel.DEBUG
		);
		ClientPacketHandler.sendPacket(packet.getPacket());
	}
}
