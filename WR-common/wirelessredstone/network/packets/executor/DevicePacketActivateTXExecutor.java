package wirelessredstone.network.packets.executor;

import net.minecraft.src.World;
import wirelessredstone.api.IWirelessDevice;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.device.WirelessTransmitterDevice;

public class DevicePacketActivateTXExecutor extends DevicePacketActivateExecutor {

	@Override
	protected IWirelessDevice getDevice(World world, IWirelessDeviceData deviceData) {
		return new WirelessTransmitterDevice(world, deviceData);
	}
}
