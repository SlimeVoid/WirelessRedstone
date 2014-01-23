package wirelessredstone.addon.powerdirector.network.packets;

import wirelessredstone.network.packets.PacketWirelessAddon;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;

public class PacketPowerDirectorSettings extends PacketWirelessAddon {

	public PacketPowerDirectorSettings() {
		super();
	}

	public PacketPowerDirectorSettings(String command, int side, TileEntityRedstoneWireless inventory) {
		this();
		this.setCommand(command);
		this.setPosition(	inventory.xCoord,
							inventory.yCoord,
							inventory.zCoord,
							side);
	}
}
