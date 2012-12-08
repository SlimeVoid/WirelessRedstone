package wirelessredstone.network.packets.executor;

import net.minecraft.src.World;
import wirelessredstone.api.IWirelessDevice;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.device.WirelessReceiverDevice;

public class DevicePacketDeactivateRXExecutor extends DevicePacketDeactivateExecutor {

	@Override
	protected IWirelessDevice getDevice(World world, IWirelessDeviceData deviceData) {
		return new WirelessReceiverDevice(world, deviceData);
	}
}
