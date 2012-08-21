package wirelessredstone.network.packets.core;

/**
 * Keeps track of the indices to use when writing data to payload arrays.
 * Internal use only.
 */
public class IndexInPayload {
	public IndexInPayload(int intIndex, int floatIndex, int stringIndex,
			int boolIndex) {
		this.intIndex = intIndex;
		this.floatIndex = floatIndex;
		this.stringIndex = stringIndex;
		this.boolIndex = boolIndex;
	}

	public IndexInPayload(int intIndex, int floatIndex, int stringIndex,
			int boolIndex, int doubleIndex) {
		this(intIndex, floatIndex, stringIndex, boolIndex);
		this.doubleIndex = doubleIndex;
	}

	public int intIndex = 0;
	public int floatIndex = 0;
	public int stringIndex = 0;
	public int boolIndex = 0;
	public int doubleIndex = 0;
}
