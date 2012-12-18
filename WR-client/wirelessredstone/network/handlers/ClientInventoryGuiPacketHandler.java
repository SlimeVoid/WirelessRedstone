package wirelessredstone.network.handlers;

import wirelessredstone.network.packets.PacketRedstoneWirelessOpenGuiInventory;
import wirelessredstone.network.packets.PacketWireless;

public class ClientInventoryGuiPacketHandler extends SubPacketHandler {

	@Override
	protected PacketWireless createNewPacketWireless() {
		return new PacketRedstoneWirelessOpenGuiInventory();
	}
}
