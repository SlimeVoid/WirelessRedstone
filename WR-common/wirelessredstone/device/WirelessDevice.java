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

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import wirelessredstone.api.IWirelessDevice;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.data.WirelessCoordinates;

/**
 * A wireless device.<br>
 * Contains the device data.
 * 
 * @author Eurymachus
 */
public abstract class WirelessDevice implements IWirelessDevice {

	protected IWirelessDeviceData	data;
	protected int					xCoord, yCoord, zCoord;
	protected EntityLivingBase		owner;

	protected WirelessDevice(World world, EntityLivingBase entity, IWirelessDeviceData data) {
		if (data != null) {
			this.data = data;
		} else {
			this.data = WirelessDeviceData.getDeviceData(	this.getDeviceDataClass(),
															this.getName(),
															entity.getHeldItem(),
															world,
															entity);
		}
		this.owner = entity;
		this.setCoords(	(int) entity.posX,
						(int) entity.posY,
						(int) entity.posZ);
	}

	@Override
	public abstract String getName();

	@Override
	public EntityLivingBase getOwner() {
		return this.owner;
	}

	@Override
	public void setOwner(EntityLiving entity) {
		this.owner = entity;
	}

	@Override
	public String getFreq() {
		return this.data.getDeviceFreq();
	}

	@Override
	public void setCoords(WirelessCoordinates coords) {
		int x = coords.getX(), y = coords.getY(), z = coords.getZ();
		this.setCoords(	x,
						y,
						z);
	}

	@Override
	public void setCoords(int x, int y, int z) {
		this.xCoord = x;
		this.yCoord = y;
		this.zCoord = z;
	}

	@Override
	public WirelessCoordinates getCoords() {
		return new WirelessCoordinates(this.xCoord, this.yCoord, this.zCoord);
	}

	@Override
	public World getWorld() {
		return DimensionManager.getWorld(this.data.getDeviceDimension());
	}

	@Override
	public void setFreq(String freq) {
		this.data.setDeviceFreq(freq);
	}

	/**
	 * Get the device data class.
	 * 
	 * @return Device data class
	 */
	protected abstract Class<? extends IWirelessDeviceData> getDeviceDataClass();

	@Override
	public void activate(World world, Entity entity) {
		this.data.setDeviceState(true);
		if (!world.isRemote) {
			this.doActivateCommand();
		}
	}

	@Override
	public void deactivate(World world, Entity entity, boolean isForced) {
		this.data.setDeviceState(false);
		if (!world.isRemote) {
			this.doDeactivateCommand();
		}
	}

	@Override
	public abstract void doActivateCommand();

	@Override
	public abstract void doDeactivateCommand();

	protected abstract String getActivateCommand();

	protected abstract String getDeactivateCommand();

	@Override
	public boolean isBeingHeld() {
		EntityLivingBase entityliving = this.getOwner();
		if (entityliving != null) {
			ItemStack itemstack = entityliving.getHeldItem();
			if (itemstack != null) {
				return WirelessDeviceData.getDeviceData(this.getDeviceDataClass(),
														this.getName(),
														itemstack,
														this.getWorld(),
														entityliving).getDeviceFreq() == this.getFreq();
			}
		}
		return false;
	}

}
