package wirelessredstone.data;

public class WirelessReadWriteLock {
	private int readers = 0;
	private int writers = 0;
	private int writeReq = 0;

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
	public synchronized void readLock() throws InterruptedException {
		while (writers > 0 || writeReq > 0) {
			LoggerRedstoneWireless.getInstance("RedstoneEtherFrequency").write(
					"readLock", LoggerRedstoneWireless.LogLevel.INFO);
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
	public synchronized void writeLock() throws InterruptedException {
		writeReq++;
		while (readers > 0 || writers > 0) {
			LoggerRedstoneWireless.getInstance("RedstoneEtherFrequency").write(
					"writeLock", LoggerRedstoneWireless.LogLevel.INFO);
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