package wirelessredstone.network.packets.executor;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.World;
import wirelessredstone.api.IEtherPacketExecutor;
import wirelessredstone.network.handlers.ServerRedstoneEtherPacketHandler;
import wirelessredstone.network.packets.PacketRedstoneEther;
import wirelessredstone.network.packets.PacketWireless;

public class EtherPacketFetchEtherExecutor implements IEtherPacketExecutor {

	@Override
	public void execute(PacketWireless packet, World world, EntityPlayer entityplayer) {
		if (entityplayer instanceof EntityPlayerMP) {
			ServerRedstoneEtherPacketHandler.sendEtherTilesTo((EntityPlayerMP)entityplayer);
		}
	}
}