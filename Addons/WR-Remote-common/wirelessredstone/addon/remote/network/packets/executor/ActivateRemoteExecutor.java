package wirelessredstone.addon.remote.network.packets.executor;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.World;
import wirelessredstone.addon.remote.data.WirelessRemoteData;
import wirelessredstone.addon.remote.data.WirelessRemoteDevice;
import wirelessredstone.api.IWirelessDevice;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.network.packets.executor.DevicePacketActivateExecutor;

public class ActivateRemoteExecutor extends DevicePacketActivateExecutor {

	@Override
	protected IWirelessDevice getDevice(World world, EntityLiving entityliving, IWirelessDeviceData deviceData) {
		return new WirelessRemoteDevice(world, entityliving, deviceData);
	}

	@Override
	protected Class<? extends IWirelessDeviceData> getDeviceDataClass() {
		return WirelessRemoteData.class;
	}

}
