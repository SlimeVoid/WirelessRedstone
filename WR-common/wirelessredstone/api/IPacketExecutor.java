package wirelessredstone.api;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import wirelessredstone.network.packets.PacketWireless;

public interface IPacketExecutor {
	/**
	 * Execute the packet.
	 * 
	 * @param packet The Packet Data.
	 * @param world The world object.
	 * @param entityplayer The player associated with the current Packet Data
	 */
	public void execute(PacketWireless packet, World world, EntityPlayer entityplayer);
}
