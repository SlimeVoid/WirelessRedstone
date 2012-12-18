package wirelessredstone.network.packets;

import net.minecraft.src.World;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.network.packets.core.PacketIds;
import wirelessredstone.network.packets.core.PacketPayload;

/**
 * Used to send Wireless Gui packet information
 * 
 * @author Eurymachus
 * 
 */
public class PacketRedstoneWirelessOpenGuiDevice extends PacketWireless implements IWirelessDeviceData {

	public PacketRedstoneWirelessOpenGuiDevice() {
		super(PacketIds.DEVICEGUI);
	}

	public PacketRedstoneWirelessOpenGuiDevice(IWirelessDeviceData devicedata) {
		this();
		this.setCommand(PacketRedstoneWirelessCommands.wirelessCommands.sendDeviceGui
				.toString());
		this.payload = new PacketPayload(1, 0, 4, 0);
		this.setDeviceFreq(devicedata.getDeviceFreq());
		this.setDeviceID(devicedata.getDeviceID());
		this.setDeviceName(devicedata.getDeviceName());
		this.setDeviceType(devicedata.getDeviceType());
	}
	
	@Override
	public void setDeviceID(int deviceID) {
		this.payload.setIntPayload(0, deviceID);
	}
	
	@Override
	public void setDeviceType(String deviceType) {
		this.payload.setStringPayload(1, deviceType);
	}
	
	@Override
	public void setDeviceName(String deviceName) {
		this.payload.setStringPayload(2, deviceName);
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
	public int getDeviceDimension() {
		return 0;
	}

	@Override
	public String toString() {
		return this.payload.getIntPayload(0) + " - (" + xPosition + "," + yPosition + "," + zPosition + ")[" + this.payload
				.getStringPayload(0) + "]";
	}

	@Override
	public boolean targetExists(World world) {
		// TODO Auto-generated method stub
		return false;
	}
}
