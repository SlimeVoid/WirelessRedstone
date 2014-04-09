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
     * @param x
     *            Transmitter's World X coordinate
     * @param y
     *            Transmitter's World Y coordinate
     * @param z
     *            Transmitter's World Z coordinate
     * @param object
     *            Frequency.
     * 
     * @return Exits prematurely if true, skipping existing code.
     */
    public boolean beforeAddTransmitter(World world, int x, int y, int z, Object object);

    /**
     * Triggered after a Transmitter is added to the Ether.
     * 
     * @param world
     *            The world object
     * @param x
     *            Transmitter's World X coordinate
     * @param y
     *            Transmitter's World Y coordinate
     * @param z
     *            Transmitter's World Z coordinate
     * @param object
     *            Frequency.
     */
    public void afterAddTransmitter(World world, int x, int y, int z, Object object);

    /**
     * Triggered before a Transmitter is removed to the Ether.
     * 
     * @param world
     *            The world object
     * @param x
     *            Transmitter's World X coordinate
     * @param y
     *            Transmitter's World Y coordinate
     * @param z
     *            Transmitter's World Z coordinate
     * @param object
     *            Frequency.
     * 
     * @return Exits prematurely if true, skipping existing code.
     */
    public boolean beforeRemTransmitter(World world, int x, int y, int z, Object object);

    /**
     * Triggered after a Transmitter is removed to the Ether.
     * 
     * @param world
     *            The world object
     * @param x
     *            Transmitter's World X coordinate
     * @param y
     *            Transmitter's World Y coordinate
     * @param z
     *            Transmitter's World Z coordinate
     * @param object
     *            Frequency.
     */
    public void afterRemTransmitter(World world, int x, int y, int z, Object object);

    /**
     * Triggered before a Transmitter's state is changed in the Ether.
     * 
     * @param world
     *            The world object
     * @param x
     *            Transmitter's World X coordinate
     * @param y
     *            Transmitter's World Y coordinate
     * @param z
     *            Transmitter's World Z coordinate
     * @param object
     *            Frequency.
     * @param state
     *            Transmitter's state to set.
     * 
     * @return Exits prematurely if true, skipping existing code.
     */
    public boolean beforeSetTransmitterState(World world, int x, int y, int z, Object object, boolean state);

    /**
     * Triggered after a Transmitter's state is changed in the Ether.
     * 
     * @param world
     *            The world object
     * @param x
     *            Transmitter's World X coordinate
     * @param y
     *            Transmitter's World Y coordinate
     * @param z
     *            Transmitter's World Z coordinate
     * @param object
     *            Frequency.
     * @param state
     *            Transmitter's state to set.
     */
    public void afterSetTransmitterState(World world, int x, int y, int z, Object object, boolean state);

    /**
     * Triggered before a Receiver is added to the Ether.
     * 
     * @param world
     *            The world object
     * @param x
     *            Receiver's World X coordinate
     * @param y
     *            Receiver's World Y coordinate
     * @param z
     *            Receiver's World Z coordinate
     * @param freq
     *            Frequency.
     * 
     * @return Exits prematurely if true, skipping existing code.
     */
    public boolean beforeAddReceiver(World world, int x, int y, int z, Object freq);

    /**
     * Triggered after a Receiver is added to the Ether.
     * 
     * @param world
     *            The world object
     * @param x
     *            Receiver's World X coordinate
     * @param y
     *            Receiver's World Y coordinate
     * @param z
     *            Receiver's World Z coordinate
     * @param freq
     *            Frequency.
     */
    public void afterAddReceiver(World world, int x, int y, int z, Object freq);

    /**
     * Triggered before a Receiver is removed to the Ether.
     * 
     * @param world
     *            The world object
     * @param x
     *            Receiver's World X coordinate
     * @param y
     *            Receiver's World Y coordinate
     * @param z
     *            Receiver's World Z coordinate
     * @param oldFreq
     *            Frequency.
     * 
     * @return Exits prematurely if true, skipping existing code.
     */
    public boolean beforeRemReceiver(World world, int x, int y, int z, Object oldFreq);

    /**
     * Triggered after a Receiver is removed to the Ether.
     * 
     * @param world
     *            The world object
     * @param x
     *            Receiver's World X coordinate
     * @param y
     *            Receiver's World Y coordinate
     * @param z
     *            Receiver's World Z coordinate
     * @param oldFreq
     *            Frequency.
     */
    public void afterRemReceiver(World world, int x, int y, int z, Object oldFreq);

    /**
     * Triggers before the frequencies state is fetched.
     * 
     * @param world
     *            The world object
     * @param object
     *            Frequency.
     * 
     * @return Exits prematurely if true, skipping existing code, and moves on
     *         to after.
     */
    public boolean beforeGetFreqState(World world, Object object);

    /**
     * Triggers after the frequencies state is fetched.
     * 
     * @param world
     *            The world object
     * @param object
     *            Frequency.
     * @param returnState
     *            The initial state to be returned.
     * 
     * @return The state to be returned. Return returnState if no changes is to
     *         be made!
     */
    public boolean afterGetFreqState(World world, Object object, boolean returnState);

    /**
     * Triggers before isLoaded is checked.
     * 
     * @param world
     *            The world object
     * @param x
     *            Receiver's World X coordinate
     * @param y
     *            Receiver's World Y coordinate
     * @param z
     *            Receiver's World Z coordinate
     * 
     * @return Exits prematurely if true, skipping existing code, and moves on
     *         to after.
     */
    public boolean beforeIsLoaded(World world, int x, int y, int z);

    /**
     * Triggers after isLoaded is checked.
     * 
     * @param world
     *            The world object
     * @param x
     *            Receiver's World X coordinate
     * @param y
     *            Receiver's World Y coordinate
     * @param z
     *            Receiver's World Z coordinate
     * @param returnState
     * 
     * @return The state to be returned. Return returnState if no changes is to
     *         be made!
     */
    public boolean afterIsLoaded(World world, int x, int y, int z, boolean returnState);

    /**
     * Triggers before getClosestActiveTransmitter. Coords are in format {x, y,
     * z}
     * 
     * @param x
     *            Receiver's World X coordinate
     * @param y
     *            Receiver's World Y coordinate
     * @param z
     *            Receiver's World Z coordinate
     * @param freq
     *            Frequency
     * @return Coords
     */
    public int[] beforeGetClosestActiveTransmitter(int x, int y, int z, String freq);

    /**
     * Triggers after getClosestActiveTransmitter. Coords are in format {x, y,
     * z}
     * 
     * @param x
     *            Receiver's World X coordinate
     * @param y
     *            Receiver's World Y coordinate
     * @param z
     *            Receiver's World Z coordinate
     * @param freq
     *            Frequency
     * @param coords
     *            The previous coordinates
     * @return Coords
     */
    public int[] afterGetClosestActiveTransmitter(int x, int y, int z, String freq, int[] coords);
}
