/*    
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package wirelessredstone.ether;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.minecraft.src.World;
import wirelessredstone.core.WRCore;
import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.data.WirelessReadWriteLock;

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
	private WirelessReadWriteLock txLock;
	/**
	 * Registered receiver nodes.
	 */
	public Map<RedstoneEtherNode, RedstoneEtherNode> rxs;
	private WirelessReadWriteLock rxLock;

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

			LoggerRedstoneWireless.getInstance("RedstoneEtherFrequency").write(
					"getState(world)", LoggerRedstoneWireless.LogLevel.DEBUG);

			// Iterate through Transmitters.
			txLock.readLock();
			for (RedstoneEtherNode tx : txs.values()) {
				// Add to remove list if block is not loaded.
				if (!RedstoneEther.getInstance().isLoaded(world, tx.i, tx.j,
						tx.k)) {
					LoggerRedstoneWireless
							.getInstance("RedstoneEtherFrequency").write(
									"getState(world): " + tx
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
				remTransmitter(world, tx.i, tx.j, tx.k);

		} catch (InterruptedException e) {
			LoggerRedstoneWireless.getInstance(
					"WirelessRedstone: " + this.getClass().toString())
					.writeStackTrace(e);
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
	public void setTransmitterState(World world, int i, int j, int k,
			boolean state) {
		try {
			if (!txs.containsKey(new RedstoneEtherNode(i, j, k)))
				return;

			txLock.readLock();
			LoggerRedstoneWireless.getInstance("RedstoneEtherFrequency").write(
					"setTransmitterState(world, " + i + ", " + j + ", " + k
							+ ", " + state + ")",
					LoggerRedstoneWireless.LogLevel.DEBUG);
			txs.get(new RedstoneEtherNode(i, j, k)).state = state;
			txLock.readUnlock();

			updateReceivers(world);
		} catch (InterruptedException e) {
			LoggerRedstoneWireless.getInstance(
					"WirelessRedstone: " + this.getClass().toString())
					.writeStackTrace(e);
		}
	}

	/**
	 * Add transmitter node to the register.
	 * 
	 * @param tx
	 *            transmitter node
	 */
	public void addTransmitter(RedstoneEtherNode tx) {
		try {
			txLock.writeLock();
			LoggerRedstoneWireless.getInstance("RedstoneEtherFrequency").write(
					"addTransmitter(" + tx.toString() + ")",
					LoggerRedstoneWireless.LogLevel.DEBUG);
			txs.put(tx, tx);
			txLock.writeUnlock();
		} catch (InterruptedException e) {
			LoggerRedstoneWireless.getInstance(
					"WirelessRedstone: " + this.getClass().toString())
					.writeStackTrace(e);
		}
	}

	/**
	 * Add receiver node to the register.
	 * 
	 * @param rx
	 *            receiver node
	 */
	public void addReceiver(RedstoneEtherNode rx) {
		try {
			rxLock.writeLock();
			LoggerRedstoneWireless.getInstance("RedstoneEtherFrequency").write(
					"addTransmitter(" + rx.toString() + ")",
					LoggerRedstoneWireless.LogLevel.DEBUG);
			rxs.put(rx, rx);
			rxLock.writeUnlock();
		} catch (InterruptedException e) {
			LoggerRedstoneWireless.getInstance(
					"WirelessRedstone: " + this.getClass().toString())
					.writeStackTrace(e);
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
	public void remTransmitter(World world, int i, int j, int k) {
		try {
			if (!txs.containsKey(new RedstoneEtherNode(i, j, k)))
				return;

			txLock.writeLock();
			LoggerRedstoneWireless.getInstance("RedstoneEtherFrequency").write(
					"remTransmitter(world, " + i + ", " + j + ", " + k + ")",
					LoggerRedstoneWireless.LogLevel.DEBUG);
			txs.remove(new RedstoneEtherNode(i, j, k));
			txLock.writeUnlock();

			updateReceivers(world);
		} catch (InterruptedException e) {
			LoggerRedstoneWireless.getInstance(
					"WirelessRedstone: " + this.getClass().toString())
					.writeStackTrace(e);
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
	public void remReceiver(int i, int j, int k) {
		try {
			if (!rxs.containsKey(new RedstoneEtherNode(i, j, k)))
				return;

			rxLock.writeLock();
			LoggerRedstoneWireless.getInstance("RedstoneEtherFrequency").write(
					"remReceiver(" + i + ", " + j + ", " + k + ")",
					LoggerRedstoneWireless.LogLevel.DEBUG);
			rxs.remove(new RedstoneEtherNode(i, j, k));
			rxLock.writeUnlock();
		} catch (InterruptedException e) {
			LoggerRedstoneWireless.getInstance(
					"WirelessRedstone: " + this.getClass().toString())
					.writeStackTrace(e);
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

			LoggerRedstoneWireless.getInstance("RedstoneEtherFrequency").write(
					"updateReceivers(world)",
					LoggerRedstoneWireless.LogLevel.DEBUG);

			rxLock.readLock();
			// Iterate through Receivers.
			for (RedstoneEtherNode rx : rxs.values()) {
				// Add to remove list if block is not loaded.
				if (!RedstoneEther.getInstance().isLoaded(world, rx.i, rx.j,
						rx.k)) {
					LoggerRedstoneWireless
							.getInstance("RedstoneEtherFrequency").write(
									"updateReceivers(world): " + rx
											+ " not loaded. Removing",
									LoggerRedstoneWireless.LogLevel.WARNING);
					rem.add(rx);
					continue;
				}

				// Update RX tick.
				WRCore.blockWirelessR.updateTick(world, rx.i, rx.j,
						rx.k, null);
			}
			rxLock.readUnlock();

			// Remove unloaded receivers.
			for (RedstoneEtherNode rx : rem)
				remReceiver(rx.i, rx.j, rx.k);
		} catch (InterruptedException e) {
			LoggerRedstoneWireless.getInstance(
					"WirelessRedstone: " + this.getClass().toString())
					.writeStackTrace(e);
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
	public int[] getClosestActiveTransmitter(int i, int j, int k) {
		try {
			int[] pos = new int[3];
			int[] myPos = { i, j, k };
			int[] tmpPos = new int[3];
			boolean first = true;
			float h = 0.0f;

			txLock.readLock();
			for (RedstoneEtherNode node : txs.values()) {
				if (node.state) {
					if (first) {
						pos = new int[3];
						pos[0] = node.i;
						pos[1] = node.j;
						pos[2] = node.k;
						h = RedstoneEther.pythagoras(myPos, pos);
						first = false;
					} else {
						tmpPos[0] = node.i;
						tmpPos[1] = node.j;
						tmpPos[2] = node.k;
						if (h > RedstoneEther.pythagoras(myPos, tmpPos)) {
							pos[0] = node.i;
							pos[1] = node.j;
							pos[2] = node.k;
						}
					}
				}
			}
			txLock.readUnlock();

			if (first)
				return null;
			else
				return pos;
		} catch (InterruptedException e) {
			LoggerRedstoneWireless.getInstance(
					"WirelessRedstone: " + this.getClass().toString())
					.writeStackTrace(e);
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
	public int[] getClosestTransmitter(int i, int j, int k) {
		try {
			int[] pos = new int[3];
			int[] myPos = { i, j, k };
			int[] tmpPos = new int[3];
			boolean first = true;
			float h = 0.0f;

			txLock.readLock();
			for (RedstoneEtherNode node : txs.values()) {
				if (first) {
					pos = new int[3];
					pos[0] = node.i;
					pos[1] = node.j;
					pos[2] = node.k;
					h = RedstoneEther.pythagoras(myPos, pos);
					first = false;
				} else {
					tmpPos[0] = node.i;
					tmpPos[1] = node.j;
					tmpPos[2] = node.k;
					if (h > RedstoneEther.pythagoras(myPos, tmpPos)) {
						pos[0] = node.i;
						pos[1] = node.j;
						pos[2] = node.k;
					}
				}
			}
			txLock.readUnlock();

			if (first)
				return null;
			else
				return pos;
		} catch (InterruptedException e) {
			LoggerRedstoneWireless.getInstance(
					"WirelessRedstone: " + this.getClass().toString())
					.writeStackTrace(e);
		}
		return null;
	}
}