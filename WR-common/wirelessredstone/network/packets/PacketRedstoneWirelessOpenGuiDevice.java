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
package wirelessredstone.network.packets;

import net.minecraft.world.World;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.network.packets.core.PacketIds;
import wirelessredstone.network.packets.core.PacketPayload;

/**
 * Used to send Wireless Gui packet information
 * 
 * @author Eurymachus
 * 
 */
public class PacketRedstoneWirelessOpenGuiDevice extends PacketWireless
		implements IWirelessDeviceData {

	public PacketRedstoneWirelessOpenGuiDevice() {
		super(PacketIds.DEVICEGUI);
	}

	public PacketRedstoneWirelessOpenGuiDevice(IWirelessDeviceData devicedata) {
		this();
		this.setCommand(PacketRedstoneWirelessCommands.wirelessCommands.sendDeviceGui.toString());
		this.payload = new PacketPayload(1, 0, 4, 0);
		this.setDeviceFreq(devicedata.getDeviceFreq());
		this.setDeviceID(devicedata.getDeviceID());
		this.setDeviceName(devicedata.getDeviceName());
		this.setDeviceType(devicedata.getDeviceType());
	}

	@Override
	public void setDeviceID(int deviceID) {
		this.payload.setIntPayload(	0,
									deviceID);
	}

	@Override
	public void setDeviceType(String deviceType) {
		this.payload.setStringPayload(	1,
										deviceType);
	}

	@Override
	public void setDeviceName(String deviceName) {
		this.payload.setStringPayload(	2,
										deviceName);
	}

	@Override
	public int getDeviceID() {
		return this.payload.getIntPayload(0);
	}

	@Override
	public String getDeviceType() {
		return this.payload.getStringPayload(1);
	}

	@Override
	public String getDeviceName() {
		return this.payload.getStringPayload(2);
	}

	@Override
	public void setDeviceDimension(int dimensionID) {
	}

	@Override
	public void setDeviceFreq(String freq) {
		this.setFreq(freq);
	}

	@Override
	public String getDeviceFreq() {
		return this.getFreq();
	}

	@Override
	public boolean getDeviceState() {
		return this.getState();
	}

	@Override
	public void setDeviceState(boolean state) {
		this.setState(state);
	}

	@Override
	public int getDeviceDimension() {
		return 0;
	}

	@Override
	public String toString() {
		return this.payload.getIntPayload(0) + " - (" + xPosition + ","
				+ yPosition + "," + zPosition + ")["
				+ this.payload.getStringPayload(0) + "]";
	}

	@Override
	public boolean targetExists(World world) {
		// TODO Auto-generated method stub
		return false;
	}
}
