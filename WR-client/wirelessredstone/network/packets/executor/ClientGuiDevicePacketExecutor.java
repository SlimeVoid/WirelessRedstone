package wirelessredstone.network.packets.executor;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import wirelessredstone.api.IPacketExecutor;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.core.WRCore;
import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.device.WirelessDeviceData;
import wirelessredstone.network.packets.PacketWireless;

public abstract class ClientGuiDevicePacketExecutor implements IPacketExecutor {

	@Override
	public void execute(PacketWireless packet, World world, EntityPlayer entityplayer) {
		LoggerRedstoneWireless.getInstance(
				"ClientGuiDevicePacketExecutor"
		).write(
				world.isRemote,
				"handlePacket(" + packet.toString()+")",
				LoggerRedstoneWireless.LogLevel.DEBUG
		);

		IWirelessDeviceData devicedata = WirelessDeviceData.getDeviceData(
				this.getDeviceDataClass(),
				((IWirelessDeviceData)packet).getDeviceType(),
				((IWirelessDeviceData)packet).getDeviceID(),
				((IWirelessDeviceData)packet).getDeviceName(),
				world,
				entityplayer);
		devicedata.setDeviceFreq(packet.getDeviceFreq());
		
		WRCore.proxy.activateGUI(world, entityplayer, devicedata);
	}
	
	protected abstract Class<? extends IWirelessDeviceData> getDeviceDataClass();
}
