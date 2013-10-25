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
package wirelessredstone.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;

/**
 * Wireless Redstone TileEntity override.<br>
 * Used for injecting code into the Wireless Redstone TileEntity.<br>
 * Useful for addons that changes the mechanics of the tileentity.<br>
 * NOTE: All methods must be implemented, content is optional.
 * 
 * @author Eurymachus
 * 
 */
public interface ITileEntityRedstoneWirelessOverride {
	/**
	 * 
	 * Is triggered before updateEntity().
	 * 
	 * @param tileentity
	 *            The TileEntity
	 * @return Exits prematurely if true, skipping existing code.
	 */
	public boolean beforeUpdateEntity(TileEntityRedstoneWireless tileentity);

	/**
	 * Is triggered after updateEntity().
	 * 
	 * @param tileentity
	 *            The TileEntity
	 */
	public void afterUpdateEntity(TileEntityRedstoneWireless tileentity);

	/**
	 * Is triggered before handleData().
	 * 
	 * @param tileEntityRedstoneWireless
	 *            The TileEntity
	 * @param data
	 *            WR data
	 * @return Exits prematurely if true, skipping existing code.
	 */
	public boolean beforeHandleData(TileEntityRedstoneWireless tileEntityRedstoneWireless, IRedstoneWirelessData data);

	public boolean beforeIsUseableByPlayer(TileEntityRedstoneWireless tileEntityRedstoneWireless, EntityPlayer entityplayer);

	public boolean afterIsUseableByPlayer(TileEntityRedstoneWireless tileEntityRedstoneWireless, EntityPlayer entityplayer, boolean output);

	public boolean handlesExtraNBTTags();

	public void writeToNBT(TileEntityRedstoneWireless tileEntityRedstoneWireless, NBTTagCompound nbttagcompound);

	public void readFromNBT(TileEntityRedstoneWireless tileEntityRedstoneWireless, NBTTagCompound nbttagcompound);

	public boolean handleInventory();

	public ItemStack getStackInSlot(TileEntityRedstoneWireless tileEntityRedstoneWireless, int i, ItemStack itemstack);

	public ItemStack decrStackSize(TileEntityRedstoneWireless tileEntityRedstoneWireless, int i, int j);

	public ItemStack getStackInSlotOnClosing(TileEntityRedstoneWireless tileEntityRedstoneWireless, int i, ItemStack itemstack);
}
