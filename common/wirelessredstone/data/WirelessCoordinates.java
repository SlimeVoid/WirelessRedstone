package wirelessredstone.data;

public class WirelessCoordinates implements Comparable<WirelessCoordinates> {
	int x, y, z;

	public WirelessCoordinates(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public WirelessCoordinates(int[] coordinates) {
		if (coordinates.length <= 3 && coordinates.length > 0) {
			this.x = coordinates[0];
			this.y = coordinates[1];
			this.z = coordinates[2];
		}
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getZ() {
		return this.z;
	}

	public void setX(int posX) {
		this.x = posX;
	}

	public void setY(int posY) {
		this.y = posY;
	}

	public void setZ(int posZ) {
		this.z = posZ;
	}

	public int[] getCoordinateArray() {
		int[] coordArray = { 0, 0, 0 };
		if (this != null) {
			coordArray[0] = this.getX();
			coordArray[1] = this.getY();
			coordArray[2] = this.getZ();
			return coordArray;
		} else
			return null;
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
		if (theString != "")
			return theString;
		else
			return "No Coordinates found!";
	}

	@Override
	public int compareTo(WirelessCoordinates arg0) {
		if (arg0.x == this.x) {
			if (arg0.y == this.y) {
				if (arg0.z == this.z)
					return 0;
				else
					return this.z - arg0.z;
			} else
				return this.y - arg0.y;
		} else
			return this.x - arg0.x;
	}
}
