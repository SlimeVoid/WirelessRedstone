package wirelessredstone.network.packets;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.World;
import net.minecraftforge.common.DimensionManager;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.core.WRCore;
import wirelessredstone.device.WirelessDeviceData;
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
		super(PacketIds.DEVICE, new PacketPayload(3, 0, 3, 1));
		this.setName(name);
	}

	public PacketWirelessDevice(int x, int y, int z, IWirelessDeviceData data) {
		this(data.getName());
		this.setPosition(x, y, z, 0);
		this.setID(data.getID());
		this.setFreq(data.getFreq());
		this.setState(data.getState());
		this.setItemData(data.getType());
		this.setOwnerID(data.getOwnerID());
		this.setDimensionID(data.getDimension());
	}

	public void setID(int id) {
		this.payload.setIntPayload(0, id);
	}

	public void setOwnerID(int ownerID) {
		this.payload.setIntPayload(1, ownerID);
	}

	public void setDimensionID(int dimensionID) {
		this.payload.setIntPayload(2, dimensionID);
	}

	public void setName(String name) {
		this.payload.setStringPayload(1, name);
	}

	public void setItemData(String itemData) {
		this.payload.setStringPayload(2, itemData);
	}

	public int getDeviceID() {
		return this.payload.getIntPayload(0);
	}

	public String getName() {
		return this.payload.getStringPayload(1);
	}

	public String getType() {
		return this.payload.getStringPayload(2);
	}

	public int getOwnerID() {
		return this.payload.getIntPayload(1);
	}

	public int getDimension() {
		return this.payload.getIntPayload(2);
	}

	@Override
	public boolean targetExists(World world) {
		return false;
	}
	
	public IWirelessDeviceData getDeviceData(World world, EntityLiving entityliving) {
		IWirelessDeviceData data = WirelessDeviceData.getDeviceData(
				WirelessDeviceData.class,
				this.getType(),
				this.getDeviceID(),
				this.getName(),
				DimensionManager.getWorld(this.getDimension()),
				WRCore.getEntityByID(world, entityliving, this.getOwnerID()));
		data.setCoords(this.xPosition, this.yPosition, this.zPosition);
		data.setFreq(this.getFreq());
		data.setState(this.getState());
		return data;
	}
}
