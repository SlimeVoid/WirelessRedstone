package wirelessredstone.data;

import wirelessredstone.api.IWirelessDevice;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;

public class WirelessDevice implements IWirelessDevice {

	protected EntityPlayer owner;
	protected World world;
	protected WirelessDeviceData data;
	protected WirelessCoordinates coords;

	public EntityPlayer getOwner() {
		return this.owner;
	}

	@Override
	public String getFreq() {
		return this.data.getFreq();
	}

	@Override
	public WirelessCoordinates getCoords() {
		return this.coords;
	}

	public void setOwner(EntityPlayer entityplayer) {
		this.owner = entityplayer;
	}

	@Override
	public void setFreq(String freq) {
		this.data.setFreq(freq);
	}

	public WirelessDeviceData getDeviceData() {
		return data;
	}

	@Override
	public void activate() {
	}

	@Override
	public void deactivate() {
	}
}
