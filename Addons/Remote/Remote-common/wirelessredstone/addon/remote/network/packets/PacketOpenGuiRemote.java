/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version. This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details. You should have received a copy of the GNU
 * Lesser General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>
 */
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
