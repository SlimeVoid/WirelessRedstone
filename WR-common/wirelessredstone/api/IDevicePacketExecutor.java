package wirelessredstone.api;

import wirelessredstone.network.packets.PacketWirelessDevice;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;

public interface IDevicePacketExecutor {
	/**
	 * Execute the packet.
	 * 
	 * @param packet The redstone wireless device packet.
	 * @param world The world object.
	 * @param entityplayer The player object
	 */
	public void execute(PacketWirelessDevice packet, World world, EntityPlayer entityplayer);
}
