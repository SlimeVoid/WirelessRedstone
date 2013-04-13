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
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.core.objectfactory.WirelessDeviceDataFactory;
import wirelessredstone.data.LoggerRedstoneWireless;

/**
 * Wireless device data.<br>
 * Contains ID, name, dimensions, frequency and state.<br>
 * Used for storing item data.
 * 
 * @author Eurymachus
 * 
 */
public abstract class WirelessDeviceData extends WorldSavedData implements IWirelessDeviceData {
	
	protected String type;
	protected int id;
	protected String name;
	protected int dimension;
	protected String freq;
	protected boolean state;

	public WirelessDeviceData(String index) {
		super(index);
	}
	
	/**
	 * Set the device's ID based on a itemstack
	 * 
	 * @param itemstack The itemstack.
	 */
	public void setDeviceID(ItemStack itemstack) {
		this.setDeviceID(itemstack.getItemDamage());
	}

	@Override
	public void setDeviceID(int id) {
		this.id = id;
		this.markDirty();
	}

	@Override
	public void setDeviceDimension(int dimensionID) {
		this.dimension = dimensionID;
	}

	@Override
	public void setDeviceType(String type) {
		this.type = type;
		this.markDirty();
	}

	@Override
	public void setDeviceName(String name) {
		this.name = name;
		this.markDirty();
	}

	@Override
	public void setDeviceFreq(String freq) {
		this.freq = freq;
		this.markDirty();
	}

	@Override
	public void setDeviceState(boolean state) {
		this.state = state;
		this.markDirty();
	}

	@Override
	public String getDeviceType() {
		return this.type;
	}

	@Override
	public int getDeviceID() {
		return this.id;
	}

	@Override
	public String getDeviceName() {
		return this.name;
	}

	@Override
	public int getDeviceDimension() {
		return this.dimension;
	}

	@Override
	public String getDeviceFreq() {
		return this.freq;
	}
	
	@Override
	public boolean getDeviceState() {
		return this.state;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		this.type = nbttagcompound.getString("index");
		this.id = nbttagcompound.getInteger("id");
		this.name = nbttagcompound.getString("name");
		this.dimension = nbttagcompound.getInteger("dimension");
		this.freq = nbttagcompound.getString("freq");
		this.state = nbttagcompound.getBoolean("state");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		nbttagcompound.setString("index", this.type);
		nbttagcompound.setInteger("id", this.id);
		nbttagcompound.setString("name", this.name);
		nbttagcompound.setInteger("dimension", this.dimension);
		nbttagcompound.setString("freq", this.freq);
		nbttagcompound.setBoolean("state", this.state);
	}

	public static IWirelessDeviceData getDeviceData(Class<? extends IWirelessDeviceData> wirelessData, String index, int id, String name, World world, Entity entity) {
		String worldIndex = index + "[" + id + "]";
		IWirelessDeviceData data = WirelessDeviceDataFactory
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
				world.setItemData(worldIndex, (WorldSavedData) data);
				data.setDeviceType(index);
				data.setDeviceID(id);
				data.setDeviceName(name);
				data.setDeviceDimension(world.provider.dimensionId);
				data.setDeviceFreq("0");
				data.setDeviceState(false);
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
		String index = itemstack.getItem().getUnlocalizedName();
		int id = itemstack.getItemDamage();
		String name = defaultName;
		return getDeviceData(wirelessData, index, id, name, world, entity);
	}

}
