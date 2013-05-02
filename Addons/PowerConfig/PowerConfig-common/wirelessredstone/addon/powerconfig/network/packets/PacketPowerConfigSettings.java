package wirelessredstone.addon.powerconfig.network.packets;

import wirelessredstone.network.packets.PacketWirelessAddon;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;

public class PacketPowerConfigSettings extends PacketWirelessAddon {
	
	public PacketPowerConfigSettings() {
		super();
	}

	public PacketPowerConfigSettings(String command, int side, TileEntityRedstoneWireless inventory) {
		this();
		this.setCommand(command);
		this.setPosition(inventory.xCoord, inventory.yCoord, inventory.zCoord, side);
	}
}
