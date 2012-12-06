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

import java.lang.reflect.Constructor;

import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.core.WRCore;
import wirelessredstone.core.objectfactory.WirelessDeviceDataFactory;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;
import net.minecraft.src.WorldSavedData;
import net.minecraftforge.common.DimensionManager;

/**
 * Wireless device data.<br>
 * Contains ID, name, dimensions, frequency and state.<br>
 * Used for storing item data.
 * 
 * @author Eurymachus
 * 
 */
public abstract class WirelessDeviceData extends WorldSavedData implements IWirelessDeviceData {
	protected int xCoord, yCoord, zCoord;
	protected int ownerid;
	protected String type;
	protected int id;
	protected String name;
	protected Byte dimension;
	protected String freq;
	protected boolean state;

	public WirelessDeviceData(String par1Str) {
		super(par1Str);
	}

	/**
	 * Set the device's coordinates.
	 * 
	 * @param coords Device Coordinates.
	 */
	@Override
	public void setCoords(WirelessCoordinates coords) {
		int x = coords.getX(),
			y = coords.getY(),
			z = coords.getZ();
		this.setCoords(x, y, z);
	}

	/**
	 * Set the device's coordinates.
	 * 
	 * @param x the xPosition.
	 * @param y the yPosition.
	 * @param z the zPosition
	 */
	@Override
	public void setCoords(int x, int y, int z) {
		this.xCoord = x;
		this.yCoord = y;
		this.zCoord = z;
		this.markDirty();
	}
	
	/**
	 * Set the device's owner based on a living entity
	 * 
	 * @param entityliving a living entity.
	 */
	@Override
	public void setOwnerID(Entity entity) {
		this.setOwnerID(entity.entityId);
	}

	/**
	 * Set the device's Owner ID.
	 * 
	 * @param id Owner ID.
	 */
	@Override
	public void setOwnerID(int ownerid) {
		this.ownerid = ownerid;
		this.markDirty();
	}
	
	/**
	 * Set the device's ID based on a itemstack
	 * 
	 * @param itemstack The itemstack.
	 */
	@Override
	public void setID(ItemStack itemstack) {
		this.setID(itemstack.getItemDamage());
	}

	/**
	 * Set the device's ID.
	 * 
	 * @param id Device ID.
	 */
	@Override
	public void setID(int id) {
		this.id = id;
		this.markDirty();
	}

	/**
	 * Set the device type.
	 * 
	 * @param type Device type.
	 * 		e.g. "item.wirelessredstone.remote"
	 */
	@Override
	public void setType(String type) {
		this.type = type;
		this.markDirty();
	}

	/**
	 * Set the device's name.
	 * 
	 * @param name Device name.
	 */
	@Override
	public void setName(String name) {
		this.name = name;
		this.markDirty();
	}

	/**
	 * Set the device's dimension based on world's worldType.
	 * 
	 * @param world The world object.
	 */
	@Override
	public void setDimension(World world) {
		this.dimension = (byte) world.provider.dimensionId;
		this.markDirty();
	}

	/**
	 * Set the device's frequency.
	 * 
	 * @param freq Device frequency.
	 * @return 
	 */
	@Override
	public void setFreq(String freq) {
		this.freq = freq;
		this.markDirty();
	}

	/**
	 * Set the device's state.
	 * 
	 * @param state Device state.
	 * @return 
	 */
	@Override
	public void setState(boolean state) {
		this.state = state;
		this.markDirty();
	}

	/**
	 * Get the device coordinates.
	 * 
	 * @return Device coordinates.
	 */
	@Override
	public WirelessCoordinates getCoords() {
		return new WirelessCoordinates(this.xCoord, this.yCoord, this.zCoord);
	}

	/**
	 * Get the owner.
	 * 
	 * @return entity.
	 */
	@Override
	public Entity getOwner() {
		return WRCore.getEntityByID(DimensionManager.getWorld(this.dimension), null, this.ownerid);
	}

	/**
	 * Get the id of the owner
	 * 
	 * @return owner ID
	 */
	public int getOwnerID() {
		return this.ownerid;
	}

	/**
	 * Get the device type.
	 * 
	 * @return Device type.
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * Get the device ID.
	 * 
	 * @return Device ID.
	 */
	public int getID() {
		return this.id;
	}

	/**
	 * Get the device name.
	 * 
	 * @return Device name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Get the device's dimension<br>
	 * 0 for normal world -1 for hell
	 * 
	 * @return Device dimension.
	 */
	public Byte getDimension() {
		return this.dimension;
	}

	/**
	 * Get the device's frequency.
	 * 
	 * @return Device frequency.
	 */
	public String getFreq() {
		return this.freq;
	}

	/**
	 * Get the device's state.
	 * 
	 * @return Device state.
	 */
	public boolean getState() {
		return this.state;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		this.xCoord = nbttagcompound.getInteger("xCoord");
		this.yCoord = nbttagcompound.getInteger("yCoord");
		this.zCoord = nbttagcompound.getInteger("zCoord");
		this.ownerid = nbttagcompound.getInteger("ownerid");
		this.type = nbttagcompound.getString("index");
		this.id = nbttagcompound.getInteger("id");
		this.name = nbttagcompound.getString("name");
		this.dimension = nbttagcompound.getByte("dimension");
		this.freq = nbttagcompound.getString("freq");
		this.state = nbttagcompound.getBoolean("state");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		nbttagcompound.setInteger("xCoord", this.xCoord);
		nbttagcompound.setInteger("yCoord", this.yCoord);
		nbttagcompound.setInteger("zCoord", this.zCoord);
		nbttagcompound.setInteger("ownerid", this.ownerid);
		nbttagcompound.setString("index", this.type);
		nbttagcompound.setInteger("id", this.id);
		nbttagcompound.setString("name", this.name);
		nbttagcompound.setByte("dimension", this.dimension);
		nbttagcompound.setString("freq", this.freq);
		nbttagcompound.setBoolean("state", this.state);
	}

	public static IWirelessDeviceData getDeviceData(Class<? extends IWirelessDeviceData> wirelessData, String index, int id, String name, World world, Entity entity) {
		String worldIndex = index + "[" + id + "]";
		WirelessDeviceData data = WirelessDeviceDataFactory
				.getDeviceDataFromFactory(
						world,
						wirelessData,
						worldIndex,
						true
				);
		if (data == null) {
			data = WirelessDeviceDataFactory
					.getDeviceDataFromFactory(
							world,
							wirelessData,
							worldIndex,
							false
					);
			if (data != null) {
				world.setItemData(worldIndex, data);
				data.setOwnerID(entity);
				data.setType(index);
				data.setID(id);
				data.setName(name);
				data.setDimension(world);
				data.setFreq("0");
				data.setState(false);
			} else {
				LoggerRedstoneWireless.getInstance(
						"WirelessDeviceData"
				).write(
						world.isRemote,
						"Index: " + worldIndex + ", not found for " + name,
						LoggerRedstoneWireless.LogLevel.DEBUG
				);
				throw new RuntimeException("Index: " + worldIndex + ", not found for " + name);
			}
		}
		return data;
	}

	public static IWirelessDeviceData getDeviceData(Class <? extends IWirelessDeviceData> wirelessData, String defaultName, ItemStack itemstack, World world, Entity entity) {
		String index = itemstack.getItem().getItemName();
		int id = itemstack.getItemDamage();
		String name = defaultName;
		return getDeviceData(wirelessData, index, id, name, world, entity);
	}

}
