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
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class ClientGuiPacketHandler implements IPacketHandler {

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		EntityPlayer entityplayer = (EntityPlayer)player;
		World world = entityplayer.worldObj;
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(packet.data));
		try {
			int packetID = data.read();
			PacketRedstoneWirelessOpenGui pORW = new PacketRedstoneWirelessOpenGui();
			pORW.readData(data);
			handlePacket(pORW, world, entityplayer);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void handlePacket(PacketRedstoneWirelessOpenGui packet, World world, EntityPlayer player ) {
		LoggerRedstoneWireless.getInstance("ClientGuiPacketHandler").write(
				"openGUI:" + packet.toString(),
				LoggerRedstoneWireless.LogLevel.DEBUG
		);

		TileEntity tileentity = packet.getTarget(world);
		WRCore.proxy.activateGUI(world, player, tileentity);
	}
}
