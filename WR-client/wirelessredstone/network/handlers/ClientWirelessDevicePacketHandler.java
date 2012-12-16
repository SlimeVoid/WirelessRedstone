package wirelessredstone.network.handlers;

import wirelessredstone.network.packets.PacketWireless;
import wirelessredstone.network.packets.PacketWirelessDevice;

public class ClientWirelessDevicePacketHandler extends SubPacketHandler {

	@Override
	protected PacketWireless createNewPacketWireless() {
		return new PacketWirelessDevice();
	}

}
