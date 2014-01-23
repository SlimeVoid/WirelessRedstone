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

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;
import wirelessredstone.data.WirelessCoordinates;

/**
 * Wireless device.
 * 
 * @author Eurymachus
 * 
 */
public interface IWirelessDevice extends IInventory {

	/**
	 * Set the owner.
	 * 
	 * @param a
	 *            living entity
	 */
	void setOwner(EntityLiving entity);

	/**
	 * Get the owner of the device instance
	 * 
	 * @return a living entity
	 */
	EntityLivingBase getOwner();

	/**
	 * Set the frequency of the wireless device.
	 * 
	 * @param freq
	 *            Frequency
	 */
	void setFreq(String freq);

	/**
	 * Get the frequency of the wireless device.
	 * 
	 * @return Frequency
	 */
	String getFreq();

	/**
	 * Get the device coordinates.
	 * 
	 * @return Device coordinates.
	 */
	WirelessCoordinates getCoords();

	/**
	 * Set the device's coordinates.
	 * 
	 * @param coords
	 *            Device Coordinates.
	 */
	void setCoords(WirelessCoordinates coords);

	/**
	 * Set the device's coordinates.
	 * 
	 * @param x
	 *            the xPosition.
	 * @param y
	 *            the yPosition.
	 * @param z
	 *            the zPosition
	 */
	void setCoords(int x, int y, int z);

	/**
	 * Activate the wireless device.
	 */
	void activate(World world, Entity entity);

	/**
	 * Deactivate the wireless device.
	 */
	void deactivate(World world, Entity entity, boolean isForced);

	/**
	 * Perform the activate command
	 * 
	 */
	void doActivateCommand();

	/**
	 * Perform the deactivate command
	 * 
	 */
	void doDeactivateCommand();

	/**
	 * Return the world to which the device belongs
	 * 
	 * @return the world
	 */
	World getWorld();

	/**
	 * Used to check if the device is being held
	 * 
	 * @return true if the device is being held
	 */
	boolean isBeingHeld();
}
