package wirelessredstone.network.packets;

import net.minecraft.src.World;
import wirelessredstone.data.WirelessDeviceData;
import wirelessredstone.network.packets.core.PacketIds;
import wirelessredstone.network.packets.core.PacketPayload;

/**
 * Used to send Redstone Device packet data
 * 
 * @author Eurymachus
 * 
 */
public class PacketWirelessDevice extends PacketWireless {
	public PacketWirelessDevice() {
		super(PacketIds.DEVICE);

	}

	public PacketWirelessDevice(String name) {
		super(PacketIds.DEVICE, new PacketPayload(1, 0, 2, 1));
		this.setName(name);
	}

	public PacketWirelessDevice(WirelessDeviceData data) {
		this(data.getName());
		this.setID(data.getID());
		this.setFreq(data.getFreq());
		this.setState(data.getState());
	}

	public void setID(int id) {
		this.payload.setIntPayload(0, id);
	}

	public void setName(String name) {
		this.payload.setStringPayload(1, name);
	}

	public int getID() {
		return this.payload.getIntPayload(0);
	}

	public String getName() {
		return this.payload.getStringPayload(1);
	}

	@Override
	public boolean targetExists(World world) {
		return false;
	}
}
