package wirelessredstone.addon.remote.network.packets.executor;

import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;
import wirelessredstone.addon.remote.data.WirelessRemoteData;
import wirelessredstone.addon.remote.data.WirelessRemoteDevice;
import wirelessredstone.api.IWirelessDevice;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.network.packets.executor.DevicePacketDeactivateExecutor;

public class DeactivateRemoteExecutor extends DevicePacketDeactivateExecutor {

	@Override
	protected IWirelessDevice getDevice(World world, EntityLiving entityliving, IWirelessDeviceData deviceData) {
		return new WirelessRemoteDevice(world, entityliving, deviceData);
	}

	@Override
	protected Class<? extends IWirelessDeviceData> getDeviceDataClass() {
		return WirelessRemoteData.class;
	}
}
