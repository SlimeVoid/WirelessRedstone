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

import wirelessredstone.data.WirelessCoordinates;

/**
 * Wireless device.
 * 
 * @author Eurymachus
 * 
 */
public interface IWirelessDevice {

	/**
	 * Set the frequency of the wireless device.
	 * 
	 * @param freq Frequency
	 */
	void setFreq(String freq);

	/**
	 * Get the frequency of the wireless device.
	 * 
	 * @return Frequency
	 */
	String getFreq();

	/**
	 * Get the wireless device's coords.
	 * 
	 * @return Coordinates
	 */
	WirelessCoordinates getCoords();

	/**
	 * Activate the wireless device.
	 */
	void activate();

	/**
	 * Deactivate the wireless device.
	 */
	void deactivate();
}
