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
package net.slimevoid.wirelessredstone.ether;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.slimevoid.wirelessredstone.api.IRedstoneEtherOverride;
import net.slimevoid.wirelessredstone.data.LoggerRedstoneWireless;

/**
 * Wireless Redstone Ether.<br>
 * Singleton pattern class.
 * 
 * @author ali4z
 */
public class RedstoneEther {
    private Map<Integer, Map<String, RedstoneEtherFrequency>> ether;
    private List<IRedstoneEtherOverride>                      overrides;
    private String                                            currentWorldName = "";
    private static RedstoneEther                              instance;
    private JFrame                                            gui;

    private RedstoneEther() {
        ether = new HashMap<Integer, Map<String, RedstoneEtherFrequency>>();
        // ether = new HashMap<String, RedstoneEtherFrequency>();
        overrides = new ArrayList<IRedstoneEtherOverride>();
    }

    /**
     * Fetch the Ether singleton instance.
     * 
     * @return Ether instance.
     */
    public static RedstoneEther getInstance() {
        if (instance == null) {
            instance = new RedstoneEther();
        }
        return instance;
    }

    /**
     * Retrieves a ether HashMap for a given world
     * 
     * @param world for hash
     * @return ether instance
     */
    @SuppressWarnings("unchecked")
    private HashMap<String, RedstoneEtherFrequency> getEtherClone(World world) {
        return (HashMap<String, RedstoneEtherFrequency>) ((HashMap<String, RedstoneEtherFrequency>) this.ether.get(world.hashCode())).clone();
    }

    private Map<String, RedstoneEtherFrequency> getEtherSafe(World world) {
        this.checkWorldHash(world);
        return ether.get(world.hashCode());
    }

    private Map<String, RedstoneEtherFrequency> getEtherRaw(World world) {
        return ether.get(world.hashCode());
    }

    /**
     * Adds a Ether override.
     * 
     * @param override
     *            Ether override
     */
    public void addOverride(IRedstoneEtherOverride override) {
        overrides.add(override);
    }

    /**
     * Associate a JFrame GUI to the ether.<br>
     * This will allow the ether to redraw the GUI on changes.
     * 
     * @param gui
     *            JFrame gui.
     */
    public void assGui(JFrame gui) {
        this.gui = gui;
    }

    /**
     * Add a transmitter to the ether on a given frequency.
     * 
     * @param world
     *            the world object
     * @param x
     *            world X coordinate
     * @param y
     *            world Y coordinate
     * @param z
     *            world Z coordinate
     * @param object
     *            frequency
     */
    public synchronized void addTransmitter(World world, int x, int y, int z, Object object) {
        if (world == null) return;

        LoggerRedstoneWireless.getInstance("RedstoneEther").write(world.isRemote,
                                                                  "addTransmitter(world, "
                                                                          + x
                                                                          + ", "
                                                                          + y
                                                                          + ", "
                                                                          + z
                                                                          + ", "
                                                                          + object
                                                                          + ")",
                                                                  LoggerRedstoneWireless.LogLevel.INFO);

        // Run before overrides.
        boolean prematureExit = false;
        for (IRedstoneEtherOverride override : overrides) {
            if (override.beforeAddTransmitter(world,
                                              x,
                                              y,
                                              z,
                                              object)) prematureExit = true;
        }
        // Exit if premature exit was given.
        if (prematureExit) return;

        try {
            // Make sure the frequency and world is set up properly.
            this.checkWorldHash(world);
            if (!this.freqIsset(world,
                                object)) createFreq(world,
                                                    object);

            // Assemble and store node.
            RedstoneEtherNode node = new RedstoneEtherNode(x, y, z);
            node.freq = object;
            this.getEtherRaw(world).get(object).addTransmitter(world,
                                                               node);

            // Repaint GUI.
            if (gui != null) gui.repaint();

        } catch (Exception e) {
            LoggerRedstoneWireless.getInstance("RedstoneEther").writeStackTrace(e);
        }

        // Run after overrides.
        for (IRedstoneEtherOverride override : overrides)
            override.afterAddTransmitter(world,
                                         x,
                                         y,
                                         z,
                                         object);
    }

    /**
     * Remove a transmitter from the ether.
     * 
     * @param world
     *            the world object
     * @param x
     *            world X coordinate
     * @param y
     *            world Y coordinate
     * @param z
     *            world Z coordinate
     * @param object
     *            frequency
     */
    public synchronized void remTransmitter(World world, int x, int y, int z, Object object) {
        if (world == null) return;

        LoggerRedstoneWireless.getInstance("RedstoneEther").write(world.isRemote,
                                                                  "remTransmitter(world, "
                                                                          + x
                                                                          + ", "
                                                                          + y
                                                                          + ", "
                                                                          + z
                                                                          + ", "
                                                                          + object
                                                                          + ")",
                                                                  LoggerRedstoneWireless.LogLevel.INFO);

        // Run before overrides.
        boolean prematureExit = false;
        for (IRedstoneEtherOverride override : overrides) {
            if (override.beforeRemTransmitter(world,
                                              x,
                                              y,
                                              z,
                                              object)) prematureExit = true;
        }
        // Exit if premature exit was given.
        if (prematureExit) return;

        try {

            // Make sure the frequency and world is set up properly.
            checkWorldHash(world);
            if (freqIsset(world,
                          object)) {
                // Remove the node.
                this.getEtherRaw(world).get(object).remTransmitter(world,
                                                                   x,
                                                                   y,
                                                                   z);
                // Remove the frequency if empty.
                if (this.getEtherRaw(world).get(object).count() == 0) this.getEtherRaw(world).remove(object);
            }

            // Repaint GUI.
            if (gui != null) gui.repaint();
        } catch (Exception e) {
            LoggerRedstoneWireless.getInstance("RedstoneEther").writeStackTrace(e);
        }

        // Run after overrides.
        for (IRedstoneEtherOverride override : overrides)
            override.afterRemTransmitter(world,
                                         x,
                                         y,
                                         z,
                                         object);
    }

    /**
     * Add a receiver to the ether on a given frequency.
     * 
     * @param world
     *            the world object
     * @param x
     *            world X coordinate
     * @param y
     *            world Y coordinate
     * @param z
     *            world Z coordinate
     * @param freq
     *            frequency
     */
    public synchronized void addReceiver(World world, int x, int y, int z, Object freq) {
        if (world == null) return;

        LoggerRedstoneWireless.getInstance("RedstoneEther").write(world.isRemote,
                                                                  "addReceiver(world, "
                                                                          + x
                                                                          + ", "
                                                                          + y
                                                                          + ", "
                                                                          + z
                                                                          + ", "
                                                                          + freq
                                                                          + ")",
                                                                  LoggerRedstoneWireless.LogLevel.INFO);

        // Run before overrides.
        boolean prematureExit = false;
        for (IRedstoneEtherOverride override : overrides) {
            if (override.beforeAddReceiver(world,
                                           x,
                                           y,
                                           z,
                                           freq)) prematureExit = true;
        }
        // Exit if premature exit was given.
        if (prematureExit) return;

        try {

            // Make sure the frequency and world is set up properly.
            checkWorldHash(world);
            if (!freqIsset(world,
                           freq)) createFreq(world,
                                             freq);

            // Assemble and store node.
            RedstoneEtherNode node = new RedstoneEtherNode(x, y, z);
            node.freq = freq;
            this.getEtherRaw(world).get(freq).addReceiver(world,
                                                          node);

            // Repaint GUI.
            if (gui != null) gui.repaint();

        } catch (Exception e) {
            LoggerRedstoneWireless.getInstance("RedstoneEther").writeStackTrace(e);
        }

        // Run after overrides.
        for (IRedstoneEtherOverride override : overrides)
            override.afterAddReceiver(world,
                                      x,
                                      y,
                                      z,
                                      freq);
    }

    /**
     * Remove a receiver from the ether.
     * 
     * @param world
     *            the world object
     * @param x
     *            world X coordinate
     * @param y
     *            world Y coordinate
     * @param z
     *            world Z coordinate
     * @param oldFreq
     *            frequency
     */
    public synchronized void remReceiver(World world, int x, int y, int z, Object oldFreq) {
        if (world == null) return;

        LoggerRedstoneWireless.getInstance("RedstoneEther").write(world.isRemote,
                                                                  "remReceiver(world, "
                                                                          + x
                                                                          + ", "
                                                                          + y
                                                                          + ", "
                                                                          + z
                                                                          + ", "
                                                                          + oldFreq
                                                                          + ")",
                                                                  LoggerRedstoneWireless.LogLevel.INFO);

        // Run before overrides.
        boolean prematureExit = false;
        for (IRedstoneEtherOverride override : overrides) {
            if (override.beforeRemReceiver(world,
                                           x,
                                           y,
                                           z,
                                           oldFreq)) prematureExit = true;
        }
        // Exit if premature exit was given.
        if (prematureExit) return;

        try {

            // Make sure the frequency and world is set up properly.
            this.checkWorldHash(world);
            if (this.freqIsset(world,
                               oldFreq)) {
                // Remove the node.
                this.getEtherRaw(world).get(oldFreq).remReceiver(world,
                                                                 x,
                                                                 y,
                                                                 z);
                // Remove the frequency if empty.
                if (this.getEtherRaw(world).get(oldFreq).count() == 0) this.getEtherRaw(world).remove(oldFreq);
            }

            // Repaint GUI.
            if (gui != null) gui.repaint();
        } catch (Exception e) {
            LoggerRedstoneWireless.getInstance("RedstoneEther").writeStackTrace(e);
        }

        // Run after overrides.
        for (IRedstoneEtherOverride override : overrides)
            override.afterRemReceiver(world,
                                      x,
                                      y,
                                      z,
                                      oldFreq);
    }

    /**
     * Checks the world HASH value.<br>
     * Flush out the ether if it has changed. (meaning the world has changed.)
     * 
     * @param world
     *            the world object
     */
    private synchronized void checkWorldHash(World world) {
        if (!currentWorldName.equals(world.getWorldInfo().getWorldName())) {
            ether = new HashMap<Integer, Map<String, RedstoneEtherFrequency>>();
            currentWorldName = world.getWorldInfo().getWorldName();
        }

        // Flush ether if world's hashcode differes from the current.
        if (!ether.containsKey(world.hashCode())) {
            ether.put(world.hashCode(),
                      new HashMap<String, RedstoneEtherFrequency>());
        }

        // Repaint GUI.
        if (gui != null) gui.repaint();
    }

    /**
     * Initialize a frequency object on the ether.
     * 
     * @param freq
     *            frequency
     */
    private synchronized void createFreq(World world, Object freq) {
        checkWorldHash(world);
        this.getEtherSafe(world).put(String.valueOf(freq),
                                     new RedstoneEtherFrequency());
    }

    /**
     * Checks if the frequency object is initialized.
     * 
     * @param object
     *            frequency
     * @return Initialization status.
     */
    private synchronized boolean freqIsset(World world, Object object) {
        checkWorldHash(world);
        return this.getEtherRaw(world).containsKey(object);
    }

    /**
     * Get the transmitting state on a frequency.
     * 
     * @param world
     *            the world object
     * @param object
     *            frequency
     * @return Frequency state.
     */
    public synchronized boolean getFreqState(World world, Object object) {
        LoggerRedstoneWireless.getInstance("RedstoneEther").write(world.isRemote,
                                                                  "getFreqState(world, "
                                                                          + object
                                                                          + ")",
                                                                  LoggerRedstoneWireless.LogLevel.DEBUG);

        // Run before overrides.
        boolean prematureExit = false;
        for (IRedstoneEtherOverride override : overrides) {
            if (override.beforeGetFreqState(world,
                                            object)) prematureExit = true;
        }

        // If premature exit was not given, set the initial return state.
        boolean returnState = false;
        if (!prematureExit) {
            if (this.freqIsset(world,
                               object)) returnState = this.getEtherRaw(world).get(object).getState(world);
        }
        boolean out = returnState;

        // Run after overrides.
        for (IRedstoneEtherOverride override : overrides) {
            out = override.afterGetFreqState(world,
                                             object,
                                             out);
        }

        return out;
    }

    /**
     * Set the state of a transmitter on a given frequency.
     * 
     * @param world
     *            the world object
     * @param x
     *            world X coordinate
     * @param y
     *            world Y coordinate
     * @param z
     *            world Z coordinate
     * @param freq
     *            frequency
     * @param state
     *            transmitter state
     */
    public synchronized void setTransmitterState(World world, int x, int y, int z, Object freq, boolean state) {
        if (world == null) return;
        LoggerRedstoneWireless.getInstance("RedstoneEther").write(world.isRemote,
                                                                  "setTransmitterState(world, "
                                                                          + x
                                                                          + ", "
                                                                          + y
                                                                          + ", "
                                                                          + z
                                                                          + ", "
                                                                          + freq
                                                                          + ", "
                                                                          + state
                                                                          + ")",
                                                                  LoggerRedstoneWireless.LogLevel.INFO);

        // Run before overrides.
        boolean prematureExit = false;
        for (IRedstoneEtherOverride override : overrides) {
            if (override.beforeSetTransmitterState(world,
                                                   x,
                                                   y,
                                                   z,
                                                   freq,
                                                   state)) prematureExit = true;
        }
        // Exit if premature exit was given.
        if (prematureExit) return;

        try {

            // Set the transmitter state if frequency exists.
            if (this.freqIsset(world,
                               freq)) this.getEtherRaw(world).get(freq).setTransmitterState(world,
                                                                                            x,
                                                                                            y,
                                                                                            z,
                                                                                            state);

            // Repaint GUI.
            if (gui != null) gui.repaint();
        } catch (Exception e) {
            LoggerRedstoneWireless.getInstance("RedstoneEther").writeStackTrace(e);
        }

        // Run after overrides.
        for (IRedstoneEtherOverride override : overrides)
            override.afterSetTransmitterState(world,
                                              x,
                                              y,
                                              z,
                                              freq,
                                              state);
    }

    /**
     * Fetch the coordinate array of the closest ACTIVE transmitter from a given
     * point on the world and on a given frequency.
     * 
     * @param x
     *            world X coordinate
     * @param y
     *            world Y coordinate
     * @param z
     *            world Z coordinate
     * @param freq
     *            frequency
     * @return Closest transmitter coordinate: {X,Y,Z}
     */
    public synchronized int[] getClosestActiveTransmitter(World world, int x, int y, int z, String freq) {
        int[] coords = null;
        // Run before overrides.
        for (IRedstoneEtherOverride override : overrides) {
            coords = override.beforeGetClosestActiveTransmitter(x,
                                                                y,
                                                                z,
                                                                freq);
        }

        // Continue as usual if coords were not given by overrides.
        if (coords == null) {
            try {
                // Fetch the closest TX from the frequency.
                if (this.freqIsset(world,
                                   freq)) coords = this.getEtherRaw(world).get(freq).getClosestActiveTransmitter(world,
                                                                                                                 x,
                                                                                                                 y,
                                                                                                                 z);
            } catch (Exception e) {
                LoggerRedstoneWireless.getInstance("RedstoneEther").writeStackTrace(e);
            }
        }

        // Run after overrides.
        for (IRedstoneEtherOverride override : overrides) {
            coords = override.afterGetClosestActiveTransmitter(x,
                                                               y,
                                                               z,
                                                               freq,
                                                               coords);
        }

        return coords;
    }

    /**
     * Fetch the coordinate array of the closest transmitter from a given point
     * on the world and on a given frequency.
     * 
     * @param x
     *            world X coordinate
     * @param y
     *            world Y coordinate
     * @param z
     *            world Z coordinate
     * @param freq
     *            frequency
     * @return Closest transmitter coordinate: {X,Y,Z}
     */
    public synchronized int[] getClosestTransmitter(World world, int x, int y, int z, String freq) {
        if (this.freqIsset(world,
                           freq)) return this.getEtherRaw(world).get(freq).getClosestTransmitter(world,
                                                                                                 x,
                                                                                                 y,
                                                                                                 z);
        else return null;
    }

    /**
     * Fetch the state of a given frequency from the ether regardless of
     * location
     * 
     * @param world
     *            current World
     * @param freq
     *            frequency
     * @return State of the given frequency
     */
    public synchronized boolean isFrequencyActive(World world, String freq) {
        return this.getEtherRaw(world).get(freq).getState(world);
    }

    /**
     * Get the hypotenuse between two points by using pythagoras' theorem.<br>
     * IE, returns the distance between two points.<br>
     * If one point has deeper dimension than the other; the shortest one will
     * be used.<br>
     * IE: {x,y,z}, {x,y}: Only {x,y} is used, ignoring the z.
     * 
     * @param a
     *            point A: {x,y,z} or {x,y}
     * @param b
     *            point B: {x,y,z} or {x,y}
     * @return Length between the two points.
     */
    public static float pythagoras(int[] a, int[] b) {
        double x = 0;
        if (a.length <= b.length) {
            for (int n = 0; n < a.length; n++) {
                x += Math.pow((a[n] - b[n]),
                              2);
            }
        } else {
            for (int n = 0; n < b.length; n++) {
                x += Math.pow((a[n] - b[n]),
                              2);
            }
        }
        return (float) Math.sqrt(x);
    }

    /**
     * Fetches all receiver nodes on the ether.
     * 
     * @return receiver nodes.
     */
    public synchronized List<RedstoneEtherNode> getRXNodes(World world) {
        List<RedstoneEtherNode> list = new LinkedList<RedstoneEtherNode>();
        try {
            // Make a clone of the ether to prevent concurrency.
            HashMap<String, RedstoneEtherFrequency> etherClone = this.getEtherClone(world);

            // Add all RX nodes to the list.
            for (RedstoneEtherFrequency freq : etherClone.values()) {
                list.addAll(freq.rxs.values());
            }
        } catch (Exception e) {
            LoggerRedstoneWireless.getInstance("RedstoneEther").writeStackTrace(e);
        }
        return list;
    }

    /**
     * Fetches all transmitter nodes on the ether.
     * 
     * @return receiver nodes.
     */
    public synchronized List<RedstoneEtherNode> getTXNodes(World world) {
        List<RedstoneEtherNode> list = new LinkedList<RedstoneEtherNode>();
        try {
            // Make a clone of the ether to prevent concurrency.
            HashMap<String, RedstoneEtherFrequency> etherClone = this.getEtherClone(world);

            // Add all TX nodes to the list.
            for (RedstoneEtherFrequency freq : etherClone.values()) {
                list.addAll(freq.txs.values());
            }
        } catch (Exception e) {
            LoggerRedstoneWireless.getInstance("RedstoneEther").writeStackTrace(e);
        }
        return list;
    }

    /**
     * Gets a map of all loaded frequencies and the number of nodes on each for
     * the specified world.
     * 
     * @return Loaded frequencies: [freq=>nodeCount, ...]
     */
    public synchronized Map<String, Integer> getLoadedFrequenciesForWorld(World world) {
        Map<String, Integer> list = new HashMap<String, Integer>();
        try {
            // Make a clone of the ether to prevent concurrency.
            HashMap<String, RedstoneEtherFrequency> etherClone = (HashMap<String, RedstoneEtherFrequency>) this.getEtherClone(world);

            // Add all counters for each frequency to the list.
            for (String freq : etherClone.keySet()) {
                if (list.containsKey(freq)) list.put(freq,
                                                     list.get(freq)
                                                             + etherClone.get(freq).count());
                else list.put(freq,
                              etherClone.get(freq).count());
            }
        } catch (Exception e) {
            LoggerRedstoneWireless.getInstance("RedstoneEther").writeStackTrace(e);
        }
        return list;
    }

    /**
     * Checks if a block is loaded on the world.
     * 
     * @param world
     *            the world object
     * @param x
     *            world X coordinate
     * @param y
     *            world Y coordinate
     * @param z
     *            world Z coordinate
     * @return false if the block is not loaded, true if it is.
     */
    public synchronized boolean isLoaded(World world, int x, int y, int z) {
    	BlockPos pos = new BlockPos(x, y, z);
        if (world == null) return false;

        LoggerRedstoneWireless.getInstance("RedstoneEther").write(world.isRemote,
                                                                  "isLoaded(world, "
                                                                          + x
                                                                          + ", "
                                                                          + y
                                                                          + ", "
                                                                          + z
                                                                          + ") ["
                                                                          + (world.getBlockState(pos).getBlock() != Blocks.air)
                                                                          + "&"
                                                                          + (world.getTileEntity(pos) != null)
                                                                          + "]",
                                                                  LoggerRedstoneWireless.LogLevel.DEBUG);
        // Run before overrides.
        boolean prematureExit = false;
        for (IRedstoneEtherOverride override : overrides) {
            if (override.beforeIsLoaded(world,
                                        x,
                                        y,
                                        z)) prematureExit = true;
        }

        // Check if blockId and tile is set if premature exit was not called.
        boolean returnState = false;
        if (!prematureExit) {
            returnState = world.getBlockState(pos).getBlock() != Blocks.air
                          && world.getTileEntity(pos) != null;
        }
        boolean out = returnState;

        // Run after overrides.
        for (IRedstoneEtherOverride override : overrides) {
            out = override.afterIsLoaded(world,
                                         x,
                                         y,
                                         z,
                                         out);
        }

        return out;
    }
}
