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

/**
 * A set of coordinates.
 * 
 * @author Eurymachus
 * 
 */
public class WirelessCoordinates implements Comparable<WirelessCoordinates> {
	int	x, y, z;

	public WirelessCoordinates(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Constructor with integer array as parameter.<br>
	 * 0 = x, 1 = y and 2 = z.
	 * 
	 * @param coordinates
	 */
	public WirelessCoordinates(int[] coordinates) {
		if (coordinates.length <= 3 && coordinates.length > 0) {
			this.x = coordinates[0];
			this.y = coordinates[1];
			this.z = coordinates[2];
		}
	}

	/**
	 * Getter for the X coordinate
	 * 
	 * @return The X coordinate.
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Getter for the Y coordinate
	 * 
	 * @return The Y coordinate.
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * Getter for the Z coordinate
	 * 
	 * @return The Z coordinate.
	 */
	public int getZ() {
		return this.z;
	}

	/**
	 * Setter for the X coordinate.
	 * 
	 * @param posX
	 *            The X coordinate.
	 */
	public void setX(int posX) {
		this.x = posX;
	}

	/**
	 * Setter for the Y coordinate.
	 * 
	 * @param posY
	 *            The Y coordinate.
	 */
	public void setY(int posY) {
		this.y = posY;
	}

	/**
	 * Setter for the Z coordinate.
	 * 
	 * @param posZ
	 *            The Z coordinate.
	 */
	public void setZ(int posZ) {
		this.z = posZ;
	}

	/**
	 * Fetches a coordinate integer array.<br>
	 * 0 = x, 1 = y and 2 = z.
	 * 
	 * @return coorinate integer array.
	 */
	public int[] getCoordinateArray() {
		int[] coordArray = { 0, 0, 0 };
		if (this != null) {
			coordArray[0] = this.getX();
			coordArray[1] = this.getY();
			coordArray[2] = this.getZ();
			return coordArray;
		} else return null;
	}

	@Override
	public String toString() {
		String theString = "";
		if (this != null) {
			if (Integer.toString(this.x) != null) {
				theString += "[X:" + this.x + "]";
				if (Integer.toString(this.y) != null) {
					theString += "[Y:" + this.y + "]";
					if (Integer.toString(this.z) != null) {
						theString += "[Z:" + this.z + "]";
					}
				}
			}
		}
		if (theString != "") return theString;
		else return "No Coordinates found!";
	}

	@Override
	public int compareTo(WirelessCoordinates arg0) {
		if (arg0.x == this.x) {
			if (arg0.y == this.y) {
				if (arg0.z == this.z) return 0;
				else return this.z - arg0.z;
			} else return this.y - arg0.y;
		} else return this.x - arg0.x;
	}
}
