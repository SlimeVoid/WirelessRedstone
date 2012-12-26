package wirelessredstone.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import wirelessredstone.network.packets.PacketWireless;

public interface IDevicePacketExecutor extends IPacketExecutor {
	/**
	 * Execute the packet.
	 * 
	 * @param packet The redstone wireless device packet.
	 * @param world The world object.
	 * @param entityplayer the player
	 */
	@Override
	public void execute(PacketWireless packet, World world, EntityPlayer entityplayer);
}
