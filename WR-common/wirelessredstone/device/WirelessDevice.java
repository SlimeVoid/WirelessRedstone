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
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.World;
import net.minecraftforge.common.DimensionManager;
import wirelessredstone.api.IWirelessDevice;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.data.WirelessCoordinates;
import wirelessredstone.network.ClientPacketHandler;
import wirelessredstone.network.packets.PacketWirelessDevice;
import wirelessredstone.network.packets.PacketWirelessDeviceCommands;

/**
 * A wireless device.<br>
 * Contains the device data.
 * 
 * @author Eurymachus
 */
public abstract class WirelessDevice implements IWirelessDevice {
	
	protected IWirelessDeviceData data;
	protected int xCoord, yCoord, zCoord;
	protected EntityLiving owner;
	
	protected WirelessDevice(World world, EntityLiving entity, IWirelessDeviceData data) {
		if (data != null) {
			this.data = data;
		} else {
			this.data = WirelessDeviceData.getDeviceData(this.getDeviceDataClass(), this.getName(), entity.getHeldItem(), world, entity);
		}
		this.owner = entity;
		this.setCoords((int)owner.posX, (int)owner.posY, (int)owner.posZ);
	}
	
	@Override
	public abstract String getName();

	@Override
	public EntityLiving getOwner() {
		return this.owner;
	}
	
	@Override
	public void setOwner(EntityLiving entity) {
		this.owner = entity;
	}

	@Override
	public String getFreq() {
		return this.data.getFreq();
	}

	@Override
	public void setCoords(WirelessCoordinates coords) {
		int x = coords.getX(),
			y = coords.getY(),
			z = coords.getZ();
		this.setCoords(x, y, z);
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
		return DimensionManager.getWorld(this.data.getDimension());
	}

	@Override
	public void setFreq(String freq) {
		this.data.setFreq(freq);
	}

	/**
	 * Get the device data class.
	 * 
	 * @return Device data class
	 */
	protected abstract Class<? extends IWirelessDeviceData> getDeviceDataClass();

	@Override
	public void activate(World world, Entity entity) {
		if (!world.isRemote) {
			this.data.setState(true);
			this.doActivateCommand();
		}
	}

	@Override
	public void deactivate(World world, Entity entity) {
		if (!world.isRemote) {
			this.data.setState(false);
			this.doDeactivateCommand();
		}
	}
	
	@Override
	public abstract void doActivateCommand();
	
	@Override
	public abstract void doDeactivateCommand();
	
	@Override
	public boolean isBeingHeld() {
		Entity entity = this.getOwner();
		if (entity != null && entity instanceof EntityLiving) {
			EntityLiving entityliving = (EntityLiving)entity;
			ItemStack itemstack = entityliving.getHeldItem();
			return itemstack != null
					&& WirelessDeviceData.getDeviceData(this.getDeviceDataClass(), this.getName(), itemstack, this.getWorld(),
							this.getOwner()).getFreq() == this.getFreq();
		}
		return false;
	}
	
}
