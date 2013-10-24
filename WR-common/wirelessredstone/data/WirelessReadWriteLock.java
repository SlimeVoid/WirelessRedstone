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
package wirelessredstone.data;

import net.minecraft.world.World;

/**
 * A standard Multiple Readers / Single Writer lock.
 * 
 * @author ali4z
 * 
 */
public class WirelessReadWriteLock {
	private int	readers		= 0;
	private int	writers		= 0;
	private int	writeReq	= 0;

	/**
	 * Register a read lock position.<br>
	 * Waits if no position is available.
	 * 
	 * @throws InterruptedException
	 *             if any thread interrupted the current thread before or while
	 *             the current thread was waiting for a notification. The
	 *             interrupted status of the current thread is cleared when this
	 *             exception is thrown.
	 */
	public synchronized void readLock(World world) throws InterruptedException {
		while (writers > 0 || writeReq > 0) {
			LoggerRedstoneWireless.getInstance("WirelessReadWriteLock").write(	world.isRemote,
																				"readLock() - waiting",
																				LoggerRedstoneWireless.LogLevel.INFO);
			wait();
		}
		readers++;
	}

	/**
	 * Release a read lock position.
	 */
	public synchronized void readUnlock() {
		readers--;
		notifyAll();
	}

	/**
	 * Register a write lock position.<br>
	 * Waits if no position is available.
	 * 
	 * @throws InterruptedException
	 *             if any thread interrupted the current thread before or while
	 *             the current thread was waiting for a notification. The
	 *             interrupted status of the current thread is cleared when this
	 *             exception is thrown.
	 */
	public synchronized void writeLock(World world) throws InterruptedException {
		writeReq++;
		while (readers > 0 || writers > 0) {
			LoggerRedstoneWireless.getInstance("WirelessReadWriteLock").write(	world.isRemote,
																				"writeLock() - waiting",
																				LoggerRedstoneWireless.LogLevel.INFO);
			wait();
		}
		writers++;
		writeReq--;
	}

	/**
	 * Release a write lock position.
	 */
	public synchronized void writeUnlock() {
		writers--;
		notifyAll();
	}
}