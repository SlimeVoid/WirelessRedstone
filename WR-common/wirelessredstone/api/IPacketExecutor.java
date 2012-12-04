package wirelessredstone.api;

import wirelessredstone.network.packets.PacketWireless;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;

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
