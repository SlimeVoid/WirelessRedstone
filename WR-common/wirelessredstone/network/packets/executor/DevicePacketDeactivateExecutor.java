package wirelessredstone.network.packets.executor;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import wirelessredstone.api.IDevicePacketExecutor;
import wirelessredstone.api.IWirelessDevice;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.network.packets.PacketWireless;
import wirelessredstone.network.packets.PacketWirelessDevice;

public abstract class DevicePacketDeactivateExecutor implements IDevicePacketExecutor {
	
	@Override
	public void execute(PacketWireless packet, World world, EntityPlayer entityplayer) {
		IWirelessDeviceData deviceData = ((PacketWirelessDevice)packet).getDeviceData(this.getDeviceDataClass(), world, entityplayer);
		this.getDevice(world, entityplayer, deviceData).deactivate(world, entityplayer, ((PacketWirelessDevice)packet).isForced());
	}

	protected abstract Class<? extends IWirelessDeviceData> getDeviceDataClass();

	protected abstract IWirelessDevice getDevice(World world, EntityLiving entityliving, IWirelessDeviceData deviceData);
}
