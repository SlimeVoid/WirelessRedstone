package wirelessredstone.network.handlers;

import wirelessredstone.network.packets.PacketRedstoneWirelessOpenGuiDevice;
import wirelessredstone.network.packets.PacketWireless;

public class ClientDeviceGuiPacketHandler extends SubPacketHandler {

	@Override
	protected PacketWireless createNewPacketWireless() {
		return new PacketRedstoneWirelessOpenGuiDevice();
	}
}
