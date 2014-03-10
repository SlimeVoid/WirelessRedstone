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
package com.slimevoid.wirelessredstone.api;

import com.slimevoid.wirelessredstone.tileentity.TileEntityRedstoneWireless;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

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
    public boolean beforeHandleData(TileEntityRedstoneWireless tileEntityRedstoneWireless, IWirelessData device);

    /**
     * Triggered before checking isUseableByPlayer
     * 
     * @param tileEntityRedstoneWireless
     *            The TileEntity
     * @param entityplayer
     *            The Player
     * @return Premature exit
     */
    public boolean beforeIsUseableByPlayer(TileEntityRedstoneWireless tileEntityRedstoneWireless, EntityPlayer entityplayer);

    /**
     * Triggered after default check isUseableByPlayer
     * 
     * @param tileEntityRedstoneWireless
     *            The TileEntity
     * @param entityplayer
     *            The Player
     * @param output
     *            the original result
     * @return The new/original result
     */
    public boolean afterIsUseableByPlayer(TileEntityRedstoneWireless tileEntityRedstoneWireless, EntityPlayer entityplayer, boolean output);

    /**
     * Used to assist with additional NBT Data
     * 
     * @return whether we should handle additional data
     */
    public boolean handlesExtraNBTTags();

    /**
     * Writes data to an NBTTagCompound
     * 
     * @param tileEntityRedstoneWireless
     *            The TileEntity
     * @param nbttagcompound
     *            the NBT data
     */
    public void writeToNBT(TileEntityRedstoneWireless tileEntityRedstoneWireless, NBTTagCompound nbttagcompound);

    /**
     * Reads data into an NBTTagCompound
     * 
     * @param tileEntityRedstoneWireless
     *            The TileEntity
     * @param nbttagcompound
     *            the NBT data
     */
    public void readFromNBT(TileEntityRedstoneWireless tileEntityRedstoneWireless, NBTTagCompound nbttagcompound);

    /**
     * Should we handle additional Inventory information
     * 
     * @return Whether we should or not
     */
    public boolean handleInventory();

    /**
     * Retrieves the stack in slot
     * 
     * @param tileEntityRedstoneWireless
     * @param slot
     * @param itemstack
     * @return
     */
    public ItemStack getStackInSlot(TileEntityRedstoneWireless tileEntityRedstoneWireless, int slot, ItemStack itemstack);

    /**
     * Decreases stack size in a given slot
     * 
     * @param tileEntityRedstoneWireless
     * @param slot
     * @param amount
     * @param itemstack
     * @return
     */
    public ItemStack decrStackSize(TileEntityRedstoneWireless tileEntityRedstoneWireless, int slot, int amount, ItemStack itemstack);

    /**
     * Returns the stack in slot when the container is closed
     * 
     * @param tileEntityRedstoneWireless
     * @param slot
     * @param itemstack
     * @return
     */
    public ItemStack getStackInSlotOnClosing(TileEntityRedstoneWireless tileEntityRedstoneWireless, int slot, ItemStack itemstack);

    /**
     * Sets the slot contents for a given slot
     * 
     * @param tileEntityRedstoneWireless
     * @param slot
     * @param itemstack
     * @return If we should prematurely exit
     */
    public boolean setInventorySlotContents(TileEntityRedstoneWireless tileEntityRedstoneWireless, int slot, ItemStack itemstack);

    /**
     * Ensures that the given stack is valid for a given slot
     * 
     * @param tileEntityRedstoneWireless
     * @param slot
     * @param itemstack
     * @param result
     *            the previous result
     * @return a new result or the existing previous result
     */
    public boolean isStackValidForSlot(TileEntityRedstoneWireless tileEntityRedstoneWireless, int slot, ItemStack itemstack, boolean result);

    /**
     * Called when we remove the TileEntity
     * 
     * @param tileEntityRedstoneWireless
     * @param block
     * @param metadata
     */
    public void onBlockRemoval(TileEntityRedstoneWireless tileEntityRedstoneWireless, Block block, int metadata);
}
