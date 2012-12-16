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
		this.setCoords((int)entity.posX, (int)entity.posY, (int)entity.posZ);
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
		this.data.setState(true);
		if (!world.isRemote) {
			this.doActivateCommand();
		}
	}

	@Override
	public void deactivate(World world, Entity entity) {
		this.data.setState(false);
		if (!world.isRemote) {
			this.doDeactivateCommand();
		}
	}
	
	@Override
	public abstract void doActivateCommand();
	
	@Override
	public abstract void doDeactivateCommand();
	
	@Override
	public boolean isBeingHeld() {
		EntityLiving entityliving = this.getOwner();
		if (entityliving != null) {
			ItemStack itemstack = entityliving.getHeldItem();
			if (itemstack != null) {
				System.out.println("World: " + this.getWorld());
				System.out.println("Owner: " + entityliving.getEntityName());
				System.out.println("Holding: " + itemstack.getItemName());
				return WirelessDeviceData.getDeviceData(this.getDeviceDataClass(), this.getName(), itemstack, this.getWorld(),
						entityliving).getFreq() == this.getFreq();
			}
		}
		return false;
	}
	
}
