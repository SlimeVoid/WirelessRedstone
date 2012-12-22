package wirelessredstone.network.packets;

import wirelessredstone.network.packets.core.PacketIds;
import net.minecraft.world.World;

public class PacketWirelessAddon extends PacketWireless {

	public PacketWirelessAddon() {
		super(PacketIds.ADDON);
	}

	@Override
	public boolean targetExists(World world) {
		return false;
	}

}
