package wirelessredstone.network.packets;

import net.minecraft.world.World;
import wirelessredstone.network.packets.core.PacketIds;

public class PacketWirelessAddon extends PacketWireless {

	public PacketWirelessAddon() {
		super(PacketIds.ADDON);
	}

	@Override
	public boolean targetExists(World world) {
		return false;
	}

}
