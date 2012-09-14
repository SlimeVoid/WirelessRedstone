/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version. This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details. You should have received a copy of the GNU
 * Lesser General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>
 */
package wirelessredstone.data;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import wirelessredstone.api.IWirelessDevice;

/**
 * A wireless device.<br>
 * Contains the owner, world, device data and it's coordinates.
 * 
 * @author Eurymachus
 */
public class WirelessDevice implements IWirelessDevice {
	protected EntityPlayer owner;
	protected World world;
	protected WirelessDeviceData data;
	protected WirelessCoordinates coords;

	/**
	 * Get the owner.
	 * 
	 * @return Device owner
	 */
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

	/**
	 * Set the owner.
	 * 
	 * @param entityplayer Device owner
	 */
	public void setOwner(EntityPlayer entityplayer) {
		this.owner = entityplayer;
	}

	@Override
	public void setFreq(String freq) {
		this.data.setFreq(freq);
	}

	/**
	 * Get the device data.
	 * 
	 * @return Device data
	 */
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
