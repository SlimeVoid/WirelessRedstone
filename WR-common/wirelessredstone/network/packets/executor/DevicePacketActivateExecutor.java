package wirelessredstone.network.packets.executor;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import wirelessredstone.api.IDevicePacketExecutor;
import wirelessredstone.api.IWirelessDevice;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.network.packets.PacketWireless;
import wirelessredstone.network.packets.PacketWirelessDevice;

public abstract class DevicePacketActivateExecutor implements IDevicePacketExecutor {
	
	@Override
	public void execute(PacketWireless packet, World world, EntityPlayer entityplayer) {
		IWirelessDeviceData deviceData = ((PacketWirelessDevice)packet).getDeviceData(this.getDeviceDataClass(), world, entityplayer);
		this.getDevice(world, entityplayer, deviceData).activate(world, entityplayer);
	}

	protected abstract Class<? extends IWirelessDeviceData> getDeviceDataClass();

	protected abstract IWirelessDevice getDevice(World world, EntityLiving entityliving, IWirelessDeviceData deviceData);
}
