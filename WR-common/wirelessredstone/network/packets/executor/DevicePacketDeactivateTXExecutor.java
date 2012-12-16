package wirelessredstone.network.packets.executor;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.World;
import wirelessredstone.api.IWirelessDevice;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.device.WirelessTransmitterDevice;

public class DevicePacketDeactivateTXExecutor extends DevicePacketDeactivateExecutor {

	@Override
	protected IWirelessDevice getDevice(World world, EntityLiving entityliving, IWirelessDeviceData deviceData) {
		return new WirelessTransmitterDevice(world, entityliving, deviceData);
	}
}
