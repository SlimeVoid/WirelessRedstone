package wirelessredstone.network.packets;

import net.minecraft.src.World;
import wirelessredstone.data.WirelessDeviceData;
import wirelessredstone.network.packets.core.PacketPayload;

/**
 * Used to send Redstone Device packet data
 * 
 * @author Eurymachus
 * 
 */
public class PacketWirelessDevice extends PacketWireless {
	public PacketWirelessDevice(int packetId) {
		super(packetId);

	}

	public PacketWirelessDevice(int packetId, String name) {
		super(packetId, new PacketPayload(1, 0, 2, 1));
		this.setName(name);
	}

	public PacketWirelessDevice(int packetId, WirelessDeviceData data) {
		this(packetId, data.getName());
		this.setID(data.getID());
		this.setFreq(data.getFreq());
		this.setState(data.getState());
	}

	public void setID(int id) {
		this.payload.setIntPayload(0, id);
	}

	public void setName(String name) {
		this.payload.setStringPayload(0, name);
	}

	public int getID() {
		return this.payload.getIntPayload(0);
	}

	public String getName() {
		return this.payload.getStringPayload(0);
	}

	@Override
	public boolean targetExists(World world) {
		return false;
	}
}
