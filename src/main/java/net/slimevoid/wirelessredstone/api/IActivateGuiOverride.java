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
package net.slimevoid.wirelessredstone.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.slimevoid.wirelessredstone.tileentity.TileEntityRedstoneWireless;

/**
 * Base Mod override.<br>
 * Used for injecting code into Client side BaseMod code.<br>
 * Useful for addons that changes the mechanics of existing Base code.<br>
 * NOTE: All methods must be implemented, content is optional.
 * 
 * Used in WRClientProxy
 * 
 * @author Eurymachus
 * 
 */
public interface IActivateGuiOverride {
    /**
     * Called before a GUI is opened. Block specific.
     * 
     * @param world
     *            The world object
     * @param entityplayer
     *            The player opening the GUI
     * @param tileentityredstonewireless
     *            Block's TileEntity.
     * @return Exits prematurely if true, skipping existing code.
     */
    public boolean beforeOpenGui(World world, EntityPlayer entityplayer, TileEntityRedstoneWireless tileentityredstonewireless);

    /**
     * Called before a GUI is opened. Item specific.
     * 
     * @param world
     *            The world object
     * @param entityplayer
     *            The player opening the GUI
     * @param data
     *            The world saved data.
     * @return Exits prematurely if true, skipping existing code.
     */
    public boolean beforeOpenGui(World world, EntityPlayer entityplayer, IWirelessDevice wirelessDevice);
}