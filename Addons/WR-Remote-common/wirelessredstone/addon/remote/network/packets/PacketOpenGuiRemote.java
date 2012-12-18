package wirelessredstone.addon.remote.network.packets;

import wirelessredstone.addon.remote.data.WirelessRemoteData;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.network.packets.PacketRedstoneWirelessOpenGuiDevice;

/**
 * Used to send Wireless Gui packet information
 * 
 * @author Eurymachus
 * 
 */
public class PacketOpenGuiRemote extends PacketRedstoneWirelessOpenGuiDevice {

	public PacketOpenGuiRemote(IWirelessDeviceData devicedata) {
		super(devicedata);
		this.setCommand(PacketRemoteCommands.remoteCommands.openGui.toString());
	}

	protected Class<? extends IWirelessDeviceData> getDeviceDataClass() {
		return WirelessRemoteData.class;
	}
}
