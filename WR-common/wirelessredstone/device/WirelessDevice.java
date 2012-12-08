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
package wirelessredstone.device;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import net.minecraftforge.common.DimensionManager;
import wirelessredstone.api.IWirelessDevice;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.data.WirelessCoordinates;

/**
 * A wireless device.<br>
 * Contains the device data.
 * 
 * @author Eurymachus
 */
public abstract class WirelessDevice implements IWirelessDevice {
	
	private IWirelessDeviceData data;
	
	protected WirelessDevice(World world, IWirelessDeviceData data) {
		if (data != null) {
			this.data = data;	
		} else {
			LoggerRedstoneWireless.getInstance(
				LoggerRedstoneWireless.filterClassName(
						this.getClass().toString()
				)).write(
					world.isRemote,
					"Tried to create a Wireless Device with no data",
					LoggerRedstoneWireless.LogLevel.DEBUG
			);
			throw new RuntimeException("Tried to create a Wireless Device with no data");
		}
	}
	
	private WirelessDevice() {}
	
	protected WirelessDevice(World world, EntityLiving entity) {
		this();
		this.data = WirelessDeviceData.getDeviceData(this.getDeviceDataClass(), this.getName(), entity.getHeldItem(), world, entity);
	}

	/**
	 * Get the name for this device
	 * 
	 * @return deviceName
	 */
	public abstract String getName();

	/**
	 * Get the owner.
	 * 
	 * @return Device owner
	 */
	public Entity getOwner() {
		return this.data.getOwner();
	}

	@Override
	public String getFreq() {
		return this.data.getFreq();
	}

	@Override
	public WirelessCoordinates getCoords() {
		return this.data.getCoords();
	}
	
	@Override
	public World getWorld() {
		return DimensionManager.getWorld(this.data.getDimension());
	}

	/**
	 * Set the owner.
	 * 
	 * @param entityplayer Device owner
	 */
	public void setOwner(Entity entity) {
		this.data.setOwnerID(entity);
	}

	@Override
	public void setFreq(String freq) {
		this.data.setFreq(freq);
	}
	
	@Override
	public void setCoords(int x, int y, int z) {
		this.data.setCoords(x, y, z);
	}

	/**
	 * Get the device data.
	 * 
	 * @return Device data
	 */
	@Override
	public abstract Class<? extends IWirelessDeviceData> getDeviceDataClass();

	@Override
	public void activate(World world, Entity entity) {
		this.data.setState(true);
		this.doDeactivateCommand();
	}

	@Override
	public void deactivate(World world, Entity entity) {
		this.data.setState(false);
		this.doDeactivateCommand();
	}
	
	@Override
	public abstract void doActivateCommand();
	
	@Override
	public abstract void doDeactivateCommand();
	
	@Override
	public boolean isBeingHeld() {
		Entity entity = this.getOwner();
		if (entity instanceof EntityLiving) {
			EntityLiving entityliving = (EntityLiving)entity;
			ItemStack itemstack = entityliving.getHeldItem();
			return itemstack != null
					&& WirelessDeviceData.getDeviceData(this.getDeviceDataClass(), this.getName(), itemstack, this.getWorld(),
							this.getOwner()).getFreq() == this.getFreq();
		}
		return false;
	}
	
}
