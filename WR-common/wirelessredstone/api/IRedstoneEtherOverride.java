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

import net.minecraft.world.World;

/**
 * Redstone Ether override.<br>
 * Used for injecting code into the Redstone Ether.<br>
 * Useful for addons that changes the mechanics of the ether.<br>
 * NOTE: All methods must be implemented, content is optional.
 * 
 * @author Eurymachus
 * 
 */
public interface IRedstoneEtherOverride {
	/**
	 * Triggered before a Transmitter is added to the Ether.
	 * 
	 * @param world
	 *            The world object
	 * @param i
	 *            Transmitter's World X coordinate
	 * @param j
	 *            Transmitter's World Y coordinate
	 * @param k
	 *            Transmitter's World Z coordinate
	 * @param freq
	 *            Frequency.
	 * 
	 * @return Exits prematurely if true, skipping existing code.
	 */
	public boolean beforeAddTransmitter(World world, int i, int j, int k, String freq);

	/**
	 * Triggered after a Transmitter is added to the Ether.
	 * 
	 * @param world
	 *            The world object
	 * @param i
	 *            Transmitter's World X coordinate
	 * @param j
	 *            Transmitter's World Y coordinate
	 * @param k
	 *            Transmitter's World Z coordinate
	 * @param freq
	 *            Frequency.
	 */
	public void afterAddTransmitter(World world, int i, int j, int k, String freq);

	/**
	 * Triggered before a Transmitter is removed to the Ether.
	 * 
	 * @param world
	 *            The world object
	 * @param i
	 *            Transmitter's World X coordinate
	 * @param j
	 *            Transmitter's World Y coordinate
	 * @param k
	 *            Transmitter's World Z coordinate
	 * @param freq
	 *            Frequency.
	 * 
	 * @return Exits prematurely if true, skipping existing code.
	 */
	public boolean beforeRemTransmitter(World world, int i, int j, int k, String freq);

	/**
	 * Triggered after a Transmitter is removed to the Ether.
	 * 
	 * @param world
	 *            The world object
	 * @param i
	 *            Transmitter's World X coordinate
	 * @param j
	 *            Transmitter's World Y coordinate
	 * @param k
	 *            Transmitter's World Z coordinate
	 * @param freq
	 *            Frequency.
	 */
	public void afterRemTransmitter(World world, int i, int j, int k, String freq);

	/**
	 * Triggered before a Transmitter's state is changed in the Ether.
	 * 
	 * @param world
	 *            The world object
	 * @param i
	 *            Transmitter's World X coordinate
	 * @param j
	 *            Transmitter's World Y coordinate
	 * @param k
	 *            Transmitter's World Z coordinate
	 * @param freq
	 *            Frequency.
	 * @param state
	 *            Transmitter's state to set.
	 * 
	 * @return Exits prematurely if true, skipping existing code.
	 */
	public boolean beforeSetTransmitterState(World world, int i, int j, int k, String freq, boolean state);

	/**
	 * Triggered after a Transmitter's state is changed in the Ether.
	 * 
	 * @param world
	 *            The world object
	 * @param i
	 *            Transmitter's World X coordinate
	 * @param j
	 *            Transmitter's World Y coordinate
	 * @param k
	 *            Transmitter's World Z coordinate
	 * @param freq
	 *            Frequency.
	 * @param state
	 *            Transmitter's state to set.
	 */
	public void afterSetTransmitterState(World world, int i, int j, int k, String freq, boolean state);

	/**
	 * Triggered before a Receiver is added to the Ether.
	 * 
	 * @param world
	 *            The world object
	 * @param i
	 *            Receiver's World X coordinate
	 * @param j
	 *            Receiver's World Y coordinate
	 * @param k
	 *            Receiver's World Z coordinate
	 * @param freq
	 *            Frequency.
	 * 
	 * @return Exits prematurely if true, skipping existing code.
	 */
	public boolean beforeAddReceiver(World world, int i, int j, int k, String freq);

	/**
	 * Triggered after a Receiver is added to the Ether.
	 * 
	 * @param world
	 *            The world object
	 * @param i
	 *            Receiver's World X coordinate
	 * @param j
	 *            Receiver's World Y coordinate
	 * @param k
	 *            Receiver's World Z coordinate
	 * @param freq
	 *            Frequency.
	 */
	public void afterAddReceiver(World world, int i, int j, int k, String freq);

	/**
	 * Triggered before a Receiver is removed to the Ether.
	 * 
	 * @param world
	 *            The world object
	 * @param i
	 *            Receiver's World X coordinate
	 * @param j
	 *            Receiver's World Y coordinate
	 * @param k
	 *            Receiver's World Z coordinate
	 * @param freq
	 *            Frequency.
	 * 
	 * @return Exits prematurely if true, skipping existing code.
	 */
	public boolean beforeRemReceiver(World world, int i, int j, int k, String freq);

	/**
	 * Triggered after a Receiver is removed to the Ether.
	 * 
	 * @param world
	 *            The world object
	 * @param i
	 *            Receiver's World X coordinate
	 * @param j
	 *            Receiver's World Y coordinate
	 * @param k
	 *            Receiver's World Z coordinate
	 * @param freq
	 *            Frequency.
	 */
	public void afterRemReceiver(World world, int i, int j, int k, String freq);

	/**
	 * Triggers before the frequencies state is fetched.
	 * 
	 * @param world
	 *            The world object
	 * @param freq
	 *            Frequency.
	 * 
	 * @return Exits prematurely if true, skipping existing code, and moves on
	 *         to after.
	 */
	public boolean beforeGetFreqState(World world, String freq);

	/**
	 * Triggers after the frequencies state is fetched.
	 * 
	 * @param world
	 *            The world object
	 * @param freq
	 *            Frequency.
	 * @param returnState
	 *            The initial state to be returned.
	 * 
	 * @return The state to be returned. Return returnState if no changes is to
	 *         be made!
	 */
	public boolean afterGetFreqState(World world, String freq, boolean returnState);

	/**
	 * Triggers before isLoaded is checked.
	 * 
	 * @param world
	 *            The world object
	 * @param i
	 *            Receiver's World X coordinate
	 * @param j
	 *            Receiver's World Y coordinate
	 * @param k
	 *            Receiver's World Z coordinate
	 * 
	 * @return Exits prematurely if true, skipping existing code, and moves on
	 *         to after.
	 */
	public boolean beforeIsLoaded(World world, int i, int j, int k);

	/**
	 * Triggers after isLoaded is checked.
	 * 
	 * @param world
	 *            The world object
	 * @param i
	 *            Receiver's World X coordinate
	 * @param j
	 *            Receiver's World Y coordinate
	 * @param k
	 *            Receiver's World Z coordinate
	 * @param returnState
	 * 
	 * @return The state to be returned. Return returnState if no changes is to
	 *         be made!
	 */
	public boolean afterIsLoaded(World world, int i, int j, int k, boolean returnState);

	/**
	 * Triggers before getClosestActiveTransmitter. Coords are in format {x, y,
	 * z}
	 * 
	 * @param i
	 *            Receiver's World X coordinate
	 * @param j
	 *            Receiver's World Y coordinate
	 * @param k
	 *            Receiver's World Z coordinate
	 * @param freq
	 *            Frequency
	 * @return Coords
	 */
	public int[] beforeGetClosestActiveTransmitter(int i, int j, int k, String freq);

	/**
	 * Triggers after getClosestActiveTransmitter. Coords are in format {x, y,
	 * z}
	 * 
	 * @param i
	 *            Receiver's World X coordinate
	 * @param j
	 *            Receiver's World Y coordinate
	 * @param k
	 *            Receiver's World Z coordinate
	 * @param freq
	 *            Frequency
	 * @param coords
	 *            The previous coordinates
	 * @return Coords
	 */
	public int[] afterGetClosestActiveTransmitter(int i, int j, int k, String freq, int[] coords);
}
