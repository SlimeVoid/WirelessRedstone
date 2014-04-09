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

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.minecraft.world.World;
import net.slimevoid.wirelessredstone.core.WRCore;
import net.slimevoid.wirelessredstone.data.LoggerRedstoneWireless;
import net.slimevoid.wirelessredstone.data.WirelessReadWriteLock;

/**
 * Wireless Redstone Frequency node container.<br>
 * Partitions the wireless nodes into a frequency container and handles business
 * logic.
 * 
 * @author ali4z
 */
public class RedstoneEtherFrequency {
    /**
     * Registered transmitter nodes.
     */
    public Map<RedstoneEtherNode, RedstoneEtherNode> txs;
    private WirelessReadWriteLock                    txLock;
    /**
     * Registered receiver nodes.
     */
    public Map<RedstoneEtherNode, RedstoneEtherNode> rxs;
    private WirelessReadWriteLock                    rxLock;

    /**
     * Constructor.<br>
     * Initializes node registers.
     */
    public RedstoneEtherFrequency() {
        txs = new TreeMap<RedstoneEtherNode, RedstoneEtherNode>();
        txLock = new WirelessReadWriteLock();

        rxs = new TreeMap<RedstoneEtherNode, RedstoneEtherNode>();
        rxLock = new WirelessReadWriteLock();
    }

    /**
     * Gets the frequency broadcasting state based on transmitter states.<br>
     * Removes transmitters from the register if they are not loaded in the
     * world.
     * 
     * @param world
     *            the world object
     * @return Frequency broadcasting state.
     */
    public boolean getState(World world) {
        boolean state = false;
        try {
            List<RedstoneEtherNode> rem = new LinkedList<RedstoneEtherNode>();

            LoggerRedstoneWireless.getInstance("RedstoneEtherFrequency").write(world.isRemote,
                                                                               "getState(world)",
                                                                               LoggerRedstoneWireless.LogLevel.DEBUG);

            // Iterate through Transmitters.
            txLock.readLock(world);
            for (RedstoneEtherNode tx : txs.values()) {
                // Add to remove list if block is not loaded.
                if (!RedstoneEther.getInstance().isLoaded(world,
                                                          tx.x,
                                                          tx.y,
                                                          tx.z)) {
                    LoggerRedstoneWireless.getInstance("RedstoneEtherFrequency").write(world.isRemote,
                                                                                       "getState(world) - "
                                                                                               + tx.toString()
                                                                                               + " not loaded. Removing",
                                                                                       LoggerRedstoneWireless.LogLevel.WARNING);
                    rem.add(tx);
                    continue;
                }
                // State flip if not already flipped and TX is active.
                if (!state && tx.state) {
                    state = true;
                    break;
                }
            }
            txLock.readUnlock();

            // Remove unloaded transmitters.
            for (RedstoneEtherNode tx : rem)
                remTransmitter(world,
                               tx.x,
                               tx.y,
                               tx.z);

        } catch (InterruptedException e) {
            LoggerRedstoneWireless.getInstance("RedstoneEtherFrequency").writeStackTrace(e);
        }

        return state;
    }

    /**
     * Sets the state of a transmitter in the register.<br>
     * Triggers an update to all registered receivers.
     * 
     * @param world
     *            the world object
     * @param i
     *            world X coordinate of transmitter
     * @param j
     *            world Y coordinate of transmitter
     * @param k
     *            world Z coordinate of transmitter
     * @param state
     *            state of transmitter
     */
    public void setTransmitterState(World world, int x, int y, int z, boolean state) {
        try {
            if (!txs.containsKey(new RedstoneEtherNode(x, y, z))) return;

            txLock.readLock(world);
            LoggerRedstoneWireless.getInstance("RedstoneEtherFrequency").write(world.isRemote,
                                                                               "setTransmitterState(world, "
                                                                                       + x
                                                                                       + ", "
                                                                                       + y
                                                                                       + ", "
                                                                                       + z
                                                                                       + ", "
                                                                                       + state
                                                                                       + ")",
                                                                               LoggerRedstoneWireless.LogLevel.DEBUG);
            txs.get(new RedstoneEtherNode(x, y, z)).state = state;
            txLock.readUnlock();

            updateReceivers(world);
        } catch (InterruptedException e) {
            LoggerRedstoneWireless.getInstance("RedstoneEtherFrequency").writeStackTrace(e);
        }
    }

    /**
     * Add transmitter node to the register.
     * 
     * @param tx
     *            transmitter node
     */
    public void addTransmitter(World world, RedstoneEtherNode tx) {
        try {
            txLock.writeLock(world);
            LoggerRedstoneWireless.getInstance("RedstoneEtherFrequency").write(world.isRemote,
                                                                               "addTransmitter("
                                                                                       + tx.toString()
                                                                                       + ")",
                                                                               LoggerRedstoneWireless.LogLevel.DEBUG);
            txs.put(tx,
                    tx);
            txLock.writeUnlock();
        } catch (InterruptedException e) {
            LoggerRedstoneWireless.getInstance("RedstoneEtherFrequency").writeStackTrace(e);
        }
    }

    /**
     * Add receiver node to the register.
     * 
     * @param rx
     *            receiver node
     */
    public void addReceiver(World world, RedstoneEtherNode rx) {
        try {
            rxLock.writeLock(world);
            LoggerRedstoneWireless.getInstance("RedstoneEtherFrequency").write(world.isRemote,
                                                                               "addTransmitter("
                                                                                       + rx.toString()
                                                                                       + ")",
                                                                               LoggerRedstoneWireless.LogLevel.DEBUG);
            rxs.put(rx,
                    rx);
            rxLock.writeUnlock();
        } catch (InterruptedException e) {
            LoggerRedstoneWireless.getInstance("RedstoneEtherFrequency").writeStackTrace(e);
        }
    }

    /**
     * Remove transmitter from the register.<br>
     * Triggers an update to all registered receivers.
     * 
     * @param world
     *            the world object
     * @param i
     *            world X coordinate of transmitter
     * @param j
     *            world Y coordinate of transmitter
     * @param k
     *            world Z coordinate of transmitter
     */
    public void remTransmitter(World world, int x, int y, int z) {
        try {
            if (!txs.containsKey(new RedstoneEtherNode(x, y, z))) return;

            txLock.writeLock(world);
            LoggerRedstoneWireless.getInstance("RedstoneEtherFrequency").write(world.isRemote,
                                                                               "remTransmitter(world, "
                                                                                       + x
                                                                                       + ", "
                                                                                       + y
                                                                                       + ", "
                                                                                       + z
                                                                                       + ")",
                                                                               LoggerRedstoneWireless.LogLevel.DEBUG);
            txs.remove(new RedstoneEtherNode(x, y, z));
            txLock.writeUnlock();

            updateReceivers(world);
        } catch (InterruptedException e) {
            LoggerRedstoneWireless.getInstance("RedstoneEtherFrequency").writeStackTrace(e);
        }
    }

    /**
     * Removes a receiver from the register.
     * 
     * @param i
     *            world X coordinate of receiver
     * @param j
     *            world Y coordinate of receiver
     * @param k
     *            world Z coordinate of receiver
     */
    public void remReceiver(World world, int x, int y, int z) {
        try {
            if (!rxs.containsKey(new RedstoneEtherNode(x, y, z))) return;

            rxLock.writeLock(world);
            LoggerRedstoneWireless.getInstance("RedstoneEtherFrequency").write(world.isRemote,
                                                                               "remReceiver("
                                                                                       + x
                                                                                       + ", "
                                                                                       + y
                                                                                       + ", "
                                                                                       + z
                                                                                       + ")",
                                                                               LoggerRedstoneWireless.LogLevel.DEBUG);
            rxs.remove(new RedstoneEtherNode(x, y, z));
            rxLock.writeUnlock();
        } catch (InterruptedException e) {
            LoggerRedstoneWireless.getInstance("RedstoneEtherFrequency").writeStackTrace(e);
        }
    }

    /**
     * Triggers an update to all registered receivers.<br>
     * Removes receivers from the register that is not loaded into the world.
     * 
     * @param world
     *            the world object
     */
    private void updateReceivers(World world) {
        try {
            List<RedstoneEtherNode> rem = new LinkedList<RedstoneEtherNode>();
            List<RedstoneEtherNode> update = new LinkedList<RedstoneEtherNode>();

            LoggerRedstoneWireless.getInstance("RedstoneEtherFrequency").write(world.isRemote,
                                                                               "updateReceivers(world)",
                                                                               LoggerRedstoneWireless.LogLevel.DEBUG);

            rxLock.readLock(world);
            // Iterate through Receivers.
            for (RedstoneEtherNode rx : rxs.values()) {
                // Add to remove list if block is not loaded.
                if (!RedstoneEther.getInstance().isLoaded(world,
                                                          rx.x,
                                                          rx.y,
                                                          rx.z)) {
                    LoggerRedstoneWireless.getInstance("RedstoneEtherFrequency").write(world.isRemote,
                                                                                       "updateReceivers(world) "
                                                                                               + rx.toString()
                                                                                               + " not loaded. Removing",
                                                                                       LoggerRedstoneWireless.LogLevel.WARNING);
                    rem.add(rx);
                    continue;
                }

                // Update RX tick.
                WRCore.blockWirelessR.updateTick(world,
                                                 rx.x,
                                                 rx.y,
                                                 rx.z,
                                                 null);
            }
            rxLock.readUnlock();

            // Remove unloaded receivers.
            for (RedstoneEtherNode rx : rem)
                remReceiver(world,
                            rx.x,
                            rx.y,
                            rx.z);
        } catch (InterruptedException e) {
            LoggerRedstoneWireless.getInstance("RedstoneEtherFrequency").writeStackTrace(e);
        }
    }

    /**
     * Total number of all registered nodes.
     * 
     * @return Node count.
     */
    public int count() {
        return rxs.size() + txs.size();
    }

    /**
     * Fetch the coordinate array of the closest ACTIVE transmitter from a given
     * point on the world.
     * 
     * @param i
     *            world X coordinate
     * @param j
     *            world Y coordinate
     * @param k
     *            world Z coordinate
     * @return Closest transmitter coordinate: {X,Y,Z}
     */
    @SuppressWarnings("unchecked")
    public int[] getClosestActiveTransmitter(World world, int x, int y, int z) {
        try {
            int[] pos = new int[3];
            int[] myPos = { x, y, z };
            int[] tmpPos = new int[3];
            boolean first = true;
            float h = 0.0f;

            txLock.readLock(world);
            for (RedstoneEtherNode node : txs.values()) {
                if (node.state) {
                    if (first) {
                        pos = new int[3];
                        pos[0] = node.x;
                        pos[1] = node.y;
                        pos[2] = node.z;
                        h = RedstoneEther.pythagoras(myPos,
                                                     pos);
                        first = false;
                    } else {
                        tmpPos[0] = node.x;
                        tmpPos[1] = node.y;
                        tmpPos[2] = node.z;
                        if (h > RedstoneEther.pythagoras(myPos,
                                                         tmpPos)) {
                            pos[0] = node.x;
                            pos[1] = node.y;
                            pos[2] = node.z;
                        }
                    }
                }
            }
            txLock.readUnlock();

            if (first) return null;
            else return pos;
        } catch (InterruptedException e) {
            LoggerRedstoneWireless.getInstance("RedstoneEtherFrequency").writeStackTrace(e);
        }

        return null;
    }

    /**
     * Fetch the coordinate array of the closest transmitter from a given point
     * on the world.
     * 
     * @param i
     *            world X coordinate
     * @param j
     *            world Y coordinate
     * @param k
     *            world Z coordinate
     * @param freq
     *            frequency
     * @return Closest transmitter coordinate: {X,Y,Z}
     */
    public int[] getClosestTransmitter(World world, int x, int y, int z) {
        try {
            int[] pos = new int[3];
            int[] myPos = { x, y, z };
            int[] tmpPos = new int[3];
            boolean first = true;
            float h = 0.0f;

            txLock.readLock(world);
            for (RedstoneEtherNode node : txs.values()) {
                if (first) {
                    pos = new int[3];
                    pos[0] = node.x;
                    pos[1] = node.y;
                    pos[2] = node.z;
                    h = RedstoneEther.pythagoras(myPos,
                                                 pos);
                    first = false;
                } else {
                    tmpPos[0] = node.x;
                    tmpPos[1] = node.y;
                    tmpPos[2] = node.z;
                    if (h > RedstoneEther.pythagoras(myPos,
                                                     tmpPos)) {
                        pos[0] = node.x;
                        pos[1] = node.y;
                        pos[2] = node.z;
                    }
                }
            }
            txLock.readUnlock();

            if (first) return null;
            else return pos;
        } catch (InterruptedException e) {
            LoggerRedstoneWireless.getInstance("RedstoneEtherFrequency").writeStackTrace(e);
        }
        return null;
    }
}