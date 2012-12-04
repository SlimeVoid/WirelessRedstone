package wirelessredstone.network.handlers;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.INetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import wirelessredstone.core.WRCore;
import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.network.packets.PacketRedstoneWirelessOpenGui;
import wirelessredstone.network.packets.PacketWireless;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class ClientGuiPacketHandler extends ClientSubPacketHandler {

	@Override
	protected PacketWireless createNewPacketWireless() {
		return new PacketRedstoneWirelessOpenGui();
	}
	@Override
	protected void handlePacket(PacketWireless packet, World world, EntityPlayer player ) {
		LoggerRedstoneWireless.getInstance(
				"ClientGuiPacketHandler"
		).write(
				world.isRemote,
				"handlePacket(" + packet.toString()+")",
				LoggerRedstoneWireless.LogLevel.DEBUG
		);

		TileEntity tileentity = packet.getTarget(world);
		WRCore.proxy.activateGUI(world, player, tileentity);
	}
}
