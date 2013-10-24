package wirelessredstone.addon.camouflager.network.packets;

import wirelessredstone.network.packets.PacketWirelessAddon;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;

public class PacketCamouflagerSetRef extends PacketWirelessAddon {

	public PacketCamouflagerSetRef() {
		super();
	}

	public PacketCamouflagerSetRef(String command, int side, TileEntityRedstoneWireless inventory) {
		this();
		this.setCommand(command);
		this.setPosition(	inventory.xCoord,
							inventory.yCoord,
							inventory.zCoord,
							side);
	}
}
