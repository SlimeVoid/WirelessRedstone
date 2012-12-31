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
package wirelessredstone.api;

public interface IWirelessDeviceData {

	/**
	 * Set the device's ID.
	 * 
	 * @param id Device ID.
	 */
	public void setDeviceID(int id);

	/**
	 * Set the device type.
	 * 
	 * @param type Device type.
	 * 		e.g. "item.wirelessredstone.remote"
	 */
	public void setDeviceType(String type);

	/**
	 * Set the device's name.
	 * 
	 * @param name Device name.
	 */
	public void setDeviceName(String name);

	/**
	 * Set the device's dimension based on world's worldType.
	 * 
	 * @param dimensionID The world dimensionID.
	 */
	public void setDeviceDimension(int dimensionID);

	/**
	 * Set the device's frequency.
	 * 
	 * @param freq Device frequency.
	 * @return 
	 */
	public void setDeviceFreq(String freq);

	/**
	 * Set the device's state.
	 * 
	 * @param state Device state.
	 * @return 
	 */
	public void setDeviceState(boolean state);

	/**
	 * Get the device type.
	 * 
	 * @return Device type.
	 */
	public String getDeviceType();

	/**
	 * Get the device ID.
	 * 
	 * @return Device ID.
	 */
	public int getDeviceID();

	/**
	 * Get the device name.
	 * 
	 * @return Device name.
	 */
	public String getDeviceName();

	/**
	 * Get the device's dimension<br>
	 * 0 for normal world -1 for hell
	 * 
	 * @return Device dimension.
	 */
	public int getDeviceDimension();

	/**
	 * Get the device's frequency.
	 * 
	 * @return Device frequency.
	 */
	public String getDeviceFreq();

	/**
	 * Get the device's state.
	 * 
	 * @return Device state.
	 */
	public boolean getDeviceState();
}
