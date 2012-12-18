package wirelessredstone.network.packets.executor;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import wirelessredstone.api.IPacketExecutor;
import wirelessredstone.core.WRCore;
import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.network.packets.PacketWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;

public class ClientGuiTilePacketExecutor implements IPacketExecutor {

	@Override
	public void execute(PacketWireless packet, World world, EntityPlayer entityplayer) {
		LoggerRedstoneWireless.getInstance(
				"ClientGuiTilePacketExecutor"
		).write(
				world.isRemote,
				"handlePacket(" + packet.toString()+")",
				LoggerRedstoneWireless.LogLevel.DEBUG
		);
		TileEntity tileentity = packet.getTarget(world);
		if (tileentity != null && tileentity instanceof TileEntityRedstoneWireless) {
			WRCore.proxy.activateGUI(world, entityplayer, (TileEntityRedstoneWireless)tileentity);
		}
	}
}