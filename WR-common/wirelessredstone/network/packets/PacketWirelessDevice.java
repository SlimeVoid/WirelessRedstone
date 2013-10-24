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

import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.device.WirelessDeviceData;
import wirelessredstone.network.packets.core.PacketIds;
import wirelessredstone.network.packets.core.PacketPayload;

/**
 * Used to send Redstone Device packet data
 * 
 * @author Eurymachus
 * 
 */
public class PacketWirelessDevice extends PacketWireless implements
		IWirelessDeviceData {

	public PacketWirelessDevice() {
		super(PacketIds.DEVICE);
	}

	public PacketWirelessDevice(String name) {
		this();
		this.payload = new PacketPayload(2, 0, 3, 2);
		this.setDeviceName(name);
	}

	public PacketWirelessDevice(IWirelessDeviceData data) {
		this(data.getDeviceName());
		this.setDeviceID(data.getDeviceID());
		this.setDeviceFreq(data.getDeviceFreq());
		this.setDeviceState(data.getDeviceState());
		this.setDeviceType(data.getDeviceType());
		this.setDeviceDimension(data.getDeviceDimension());
		this.isForced(false);
	}

	@Override
	public void setDeviceID(int id) {
		this.payload.setIntPayload(	0,
									id);
	}

	@Override
	public void setDeviceDimension(int dimensionID) {
		this.payload.setIntPayload(	1,
									dimensionID);
	}

	@Override
	public void setDeviceFreq(String freq) {
		this.setFreq(freq);
	}

	@Override
	public void setDeviceState(boolean state) {
		this.setState(state);
	}

	@Override
	public String getDeviceFreq() {
		return this.getFreq();
	}

	@Override
	public int getDeviceDimension() {
		return this.payload.getIntPayload(1);
	}

	@Override
	public int getDeviceID() {
		return this.payload.getIntPayload(0);
	}

	@Override
	public void setDeviceName(String name) {
		this.payload.setStringPayload(	1,
										name);
	}

	@Override
	public void setDeviceType(String devicetype) {
		this.payload.setStringPayload(	2,
										devicetype);
	}

	@Override
	public String getDeviceName() {
		return this.payload.getStringPayload(1);
	}

	@Override
	public String getDeviceType() {
		return this.payload.getStringPayload(2);
	}

	@Override
	public boolean getDeviceState() {
		return this.getState();
	}

	public void isForced(boolean isForced) {
		this.payload.setBoolPayload(1,
									isForced);
	}

	public boolean isForced() {
		return this.payload.getBoolPayload(1);
	}

	@Override
	public boolean targetExists(World world) {
		return false;
	}

	public IWirelessDeviceData getDeviceData(Class<? extends IWirelessDeviceData> deviceDataClass, World world, EntityLiving entityliving) {
		return WirelessDeviceData.getDeviceData(deviceDataClass,
												this.getDeviceType(),
												this.getDeviceID(),
												this.getDeviceName(),
												world,
												entityliving);
	}
}
