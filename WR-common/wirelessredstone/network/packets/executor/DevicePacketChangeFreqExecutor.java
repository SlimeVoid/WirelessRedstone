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
					packet.getID(),
					packet.getName(),
					DimensionManager.getWorld(
							packet.getDimension()
					),
					entityplayer
			);
			data.setCoords(packet.xPosition, packet.yPosition, packet.zPosition);
			data.setFreq(packet.getFreq());
			data.setState(packet.getState());
		}
	}
}
