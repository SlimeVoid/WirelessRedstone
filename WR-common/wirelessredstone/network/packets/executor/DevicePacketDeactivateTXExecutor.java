package wirelessredstone.network.packets.executor;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import wirelessredstone.api.IDevicePacketExecutor;
import wirelessredstone.api.IWirelessDevice;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.data.WirelessDeviceData;
import wirelessredstone.data.WirelessTransmitterDevice;
import wirelessredstone.network.packets.PacketWirelessDevice;

public class DevicePacketDeactivateTXExecutor extends DevicePacketDeactivateExecutor {

	@Override
	protected IWirelessDevice getDevice(World world, IWirelessDeviceData deviceData) {
		return new WirelessTransmitterDevice(world, deviceData);
	}
}
