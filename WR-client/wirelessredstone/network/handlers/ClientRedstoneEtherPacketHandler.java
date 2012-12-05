package wirelessredstone.network.handlers;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.INetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.World;
import wirelessredstone.api.IEtherPacketExecutor;
import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.network.ClientPacketHandler;
import wirelessredstone.network.packets.PacketRedstoneEther;
import wirelessredstone.network.packets.PacketWireless;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

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
		ClientPacketHandler.sendPacket((Packet250CustomPayload) packet.getPacket());
	}
}
