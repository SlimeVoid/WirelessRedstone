package wirelessredstone.network.packets.executor;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import wirelessredstone.api.IDevicePacketExecutor;
import wirelessredstone.api.IWirelessDevice;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.network.packets.PacketWireless;
import wirelessredstone.network.packets.PacketWirelessDevice;

public abstract class DevicePacketDeactivateExecutor implements IDevicePacketExecutor {
	
	protected IWirelessDevice device;
	
	@Override
	public void execute(PacketWireless packet, World world, EntityPlayer entityplayer) {
		IWirelessDeviceData deviceData = ((PacketWirelessDevice)packet).getDeviceData(world, entityplayer);
		this.getDevice(world, deviceData).deactivate(world, entityplayer);
	}

	protected abstract IWirelessDevice getDevice(World world, IWirelessDeviceData deviceData);
}
