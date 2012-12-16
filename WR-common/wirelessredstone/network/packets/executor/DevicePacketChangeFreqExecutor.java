package wirelessredstone.network.packets.executor;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import net.minecraftforge.common.DimensionManager;
import wirelessredstone.api.IDevicePacketExecutor;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.device.WirelessDeviceData;
import wirelessredstone.network.packets.PacketWireless;
import wirelessredstone.network.packets.PacketWirelessDevice;

public class DevicePacketChangeFreqExecutor implements IDevicePacketExecutor {

	@Override
	public void execute(PacketWireless p, World world, EntityPlayer entityplayer) {
		if (p instanceof PacketWirelessDevice) {
			PacketWirelessDevice packet = (PacketWirelessDevice)p;
			IWirelessDeviceData data = WirelessDeviceData.getDeviceData(
					WirelessDeviceData.class,
					packet.getType(),
					packet.getDeviceID(),
					packet.getName(),
					DimensionManager.getWorld(
							packet.getDimension()
					),
					entityplayer
			);
			int dFreq = Integer.parseInt(packet.getFreq());
			int oldFreq = Integer.parseInt(data.getFreq().toString());
			data.setFreq(Integer.toString(oldFreq + dFreq));
			data.setState(packet.getState());
		}
	}
}
