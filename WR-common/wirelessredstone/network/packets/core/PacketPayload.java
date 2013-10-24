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
package wirelessredstone.network.packets.core;

import java.util.Arrays;

/**
 * Payload for data transfer in packets
 * 
 * @author Eurymachus
 * 
 */
public class PacketPayload {
	public static <T> T[] concat(T[] first, T[] second) {
		T[] result = Arrays.copyOf(	first,
									first.length + second.length);
		System.arraycopy(	second,
							0,
							result,
							first.length,
							second.length);
		return result;
	}

	public static int[] concat(int[] first, int[] second) {
		int[] result = Arrays.copyOf(	first,
										first.length + second.length);
		System.arraycopy(	second,
							0,
							result,
							first.length,
							second.length);
		return result;
	}

	public static float[] concat(float[] first, float[] second) {
		float[] result = Arrays.copyOf(	first,
										first.length + second.length);
		System.arraycopy(	second,
							0,
							result,
							first.length,
							second.length);
		return result;
	}

	public static double[] concat(double[] first, double[] second) {
		double[] result = Arrays.copyOf(first,
										first.length + second.length);
		System.arraycopy(	second,
							0,
							result,
							first.length,
							second.length);
		return result;
	}

	public static boolean[] concat(boolean[] first, boolean[] second) {
		boolean[] result = Arrays.copyOf(	first,
											first.length + second.length);
		System.arraycopy(	second,
							0,
							result,
							first.length,
							second.length);
		return result;
	}

	/**
	 * Array of int values
	 */
	private int[]		intPayload;

	/**
	 * Array of float values
	 */
	private float[]		floatPayload;

	/**
	 * Array of String values
	 */
	private String[]	stringPayload;

	/**
	 * Array of double values
	 */
	private double[]	doublePayload;

	/**
	 * Array of boolean values
	 */
	private boolean[]	boolPayload;

	public PacketPayload() {
	}

	/**
	 * Retrieves the intPayload size
	 * 
	 * @return intPayload.length or 0 if null
	 */
	public int getIntSize() {
		if (this.intPayload != null) return this.intPayload.length;
		return 0;
	}

	/**
	 * Retrieves the floatPayload size
	 * 
	 * @return floatPayload.length or 0 if null
	 */
	public int getFloatSize() {
		if (this.floatPayload != null) return this.floatPayload.length;
		return 0;
	}

	/**
	 * Retrieves the doublePayload size
	 * 
	 * @return doublePayload.length or 0 if null
	 */
	public int getDoubleSize() {
		if (this.doublePayload != null) return this.doublePayload.length;
		return 0;
	}

	/**
	 * Retrieves the stringPayload size
	 * 
	 * @return stringPayload.length or 0 if null
	 */
	public int getStringSize() {
		if (this.stringPayload != null) return this.stringPayload.length;
		return 0;
	}

	/**
	 * Retrieves the boolPayload size
	 * 
	 * @return boolPayload.length or 0 if null
	 */
	public int getBoolSize() {
		if (this.boolPayload != null) return this.boolPayload.length;
		return 0;
	}

	/**
	 * Adds a new int value to intPayload
	 * 
	 * @param index
	 *            The index in the array
	 * @param newInt
	 *            The value to be added
	 * @return true if successful or false if unsuccessful
	 */
	public boolean setIntPayload(int index, int newInt) {
		if (this.intPayload != null && index < this.getIntSize()) {
			this.intPayload[index] = newInt;
			return true;
		}
		return false;
	}

	/**
	 * Adds a new float value to floatPayload
	 * 
	 * @param index
	 *            The index in the array
	 * @param newFloat
	 *            The value to be added
	 * @return true if successful or false if unsuccessful
	 */
	public boolean setFloatPayload(int index, float newFloat) {
		if (this.floatPayload != null && index < this.getFloatSize()) {
			this.floatPayload[index] = newFloat;
			return true;
		}
		return false;
	}

	/**
	 * Adds a new double value to doublePayload
	 * 
	 * @param index
	 *            The index in the array
	 * @param newDouble
	 *            The value to be added
	 * @return true if successful or false if unsuccessful
	 */
	public boolean setDoublePayload(int index, double newDouble) {
		if (this.doublePayload != null && index < this.getDoubleSize()) {
			this.doublePayload[index] = newDouble;
			return true;
		}
		return false;
	}

	/**
	 * Adds a new String value to stringPayload
	 * 
	 * @param index
	 *            The index in the array
	 * @param newString
	 *            The value to be added
	 * @return true if successful or false if unsuccessful
	 */
	public boolean setStringPayload(int index, String newString) {
		if (this.stringPayload != null && index < this.getStringSize()) {
			this.stringPayload[index] = newString;
			return true;
		}
		return false;
	}

	/**
	 * Adds a new boolean value to boolPayload
	 * 
	 * @param index
	 *            The index in the array
	 * @param newBool
	 *            The value to be added
	 * @return true if successful or false if unsuccessful
	 */
	public boolean setBoolPayload(int index, boolean newBool) {
		if (this.boolPayload != null && index < this.getBoolSize()) {
			this.boolPayload[index] = newBool;
			return true;
		}
		return false;
	}

	/**
	 * Retrieves an int value stored in intPayload
	 * 
	 * @param index
	 *            The index in the array
	 * @return intPayload[index] or 0 if null
	 */
	public int getIntPayload(int index) {
		if (this.intPayload != null && index < this.getIntSize()) return this.intPayload[index];
		return 0;
	}

	/**
	 * Retrieves a float value stored in floatPayload
	 * 
	 * @param index
	 *            The index in the array
	 * @return floatPayload[index] or 0 if null
	 */
	public float getFloatPayload(int index) {
		if (this.floatPayload != null && index < this.getFloatSize()) return this.floatPayload[index];
		return 0;
	}

	/**
	 * Retrieves a double value stored in doublePayload
	 * 
	 * @param index
	 *            The index in the array
	 * @return doublePayload[index] or 0 if null
	 */
	public double getDoublePayload(int index) {
		if (this.doublePayload != null && index < this.getDoubleSize()) return this.doublePayload[index];
		return 0;
	}

	/**
	 * Retrieves a String value stored in stringPayload
	 * 
	 * @param index
	 *            The index in the array
	 * @return stringPayload[index] or "null" if null
	 */
	public String getStringPayload(int index) {
		if (this.stringPayload != null && index < this.getStringSize()
			&& this.stringPayload[index] != null) return this.stringPayload[index];
		return "null";
	}

	/**
	 * Retrieves a boolean value stored in boolPayload
	 * 
	 * @param index
	 *            The index in the array
	 * @return boolPayload[index] or false if null
	 */
	public boolean getBoolPayload(int index) {
		if (this.boolPayload != null && index < this.getBoolSize()) return this.boolPayload[index];
		return false;
	}

	/**
	 * Constructor Create a new PacketPayload
	 * 
	 * @param intSize
	 *            The size of the new intPayload array
	 * @param floatSize
	 *            The size of the new floatPayload array
	 * @param stringSize
	 *            The size of the new stringPayload array
	 * @param boolSize
	 *            The size of the new boolPayload array
	 */
	public PacketPayload(int intSize, int floatSize, int stringSize, int boolSize) {
		this.intPayload = new int[intSize];
		this.floatPayload = new float[floatSize];
		this.stringPayload = new String[stringSize];
		this.boolPayload = new boolean[boolSize];
	}

	/**
	 * Constructor Create a new PacketPayload
	 * 
	 * @param intSize
	 *            The size of the new intPayload array
	 * @param floatSize
	 *            The size of the new floatPayload array
	 * @param stringSize
	 *            The size of the new stringPayload array
	 * @param boolSize
	 *            The size of the new boolPayload array
	 * @param doubleSize
	 *            The size of the new doublePayload array
	 */
	public PacketPayload(int intSize, int floatSize, int stringSize, int boolSize, int doubleSize) {
		this(intSize, floatSize, stringSize, boolSize);
		this.doublePayload = new double[doubleSize];
	}

	public void append(PacketPayload other) {
		if (other == null) return;

		if (other.intPayload.length > 0) this.intPayload = concat(	this.intPayload,
																	other.intPayload);
		if (other.floatPayload.length > 0) this.floatPayload = concat(	this.floatPayload,
																		other.floatPayload);
		if (other.doublePayload.length > 0) this.doublePayload = concat(this.doublePayload,
																		other.doublePayload);
		if (other.stringPayload.length > 0) this.stringPayload = concat(this.stringPayload,
																		other.stringPayload);
		if (other.boolPayload.length > 0) this.boolPayload = concat(this.boolPayload,
																	other.boolPayload);
	}

	public void append(int[] other) {
		if (other == null || other.length < 0) return;

		this.intPayload = concat(	this.intPayload,
									other);
	}

	public void splitTail(IndexInPayload index) {
		PacketPayload payload = new PacketPayload(intPayload.length
													- index.intIndex, floatPayload.length
																		- index.floatIndex, stringPayload.length
																							- index.stringIndex, boolPayload.length
																													- index.boolIndex);

		if (intPayload.length > 0) System.arraycopy(intPayload,
													index.intIndex,
													payload.intPayload,
													0,
													payload.intPayload.length);
		if (floatPayload.length > 0) System.arraycopy(	floatPayload,
														index.floatIndex,
														payload.floatPayload,
														0,
														payload.floatPayload.length);
		if (doublePayload.length > 0) System.arraycopy(	doublePayload,
														index.doubleIndex,
														payload.doublePayload,
														0,
														payload.doublePayload.length);
		if (stringPayload.length > 0) System.arraycopy(	stringPayload,
														index.stringIndex,
														payload.stringPayload,
														0,
														payload.stringPayload.length);
		if (boolPayload.length > 0) System.arraycopy(	boolPayload,
														index.boolIndex,
														payload.boolPayload,
														0,
														payload.boolPayload.length);
	}

	public void addIntValue(int newValue) {
		int[] newInt = new int[1];
		newInt[0] = newValue;
		append(newInt);
	}
}
