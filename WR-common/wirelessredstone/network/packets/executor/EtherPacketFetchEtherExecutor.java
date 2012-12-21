package wirelessredstone.network.packets.executor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import wirelessredstone.api.IEtherPacketExecutor;
import wirelessredstone.network.handlers.ServerRedstoneEtherPacketHandler;
import wirelessredstone.network.packets.PacketWireless;

public class EtherPacketFetchEtherExecutor implements IEtherPacketExecutor {

	@Override
	public void execute(PacketWireless packet, World world, EntityPlayer entityplayer) {
		if (entityplayer instanceof EntityPlayerMP) {
			ServerRedstoneEtherPacketHandler.sendEtherTilesTo((EntityPlayerMP)entityplayer);
		}
	}
}