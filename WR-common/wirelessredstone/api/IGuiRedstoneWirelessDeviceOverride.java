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

/**
 * GUI override.<br>
 * Used for injecting code into existing Wireless Device GUI screens.<br>
 * Useful for addons that changes the mechanics of existing GUIs.<br>
 * NOTE: All methods must be implemented, content is optional.
 * 
 * @author Eurymachus
 * 
 */
public interface IGuiRedstoneWirelessDeviceOverride extends
		IGuiRedstoneWirelessOverride {
	/**
	 * Triggers before the frequency is changed.
	 * 
	 * @param wirelessDeviceData
	 *            WirelessDeviceData
	 * @param oldFreq
	 *            Old frequency
	 * @param newFreq
	 *            New frequency
	 * @return Exits prematurely if true, skipping existing code.
	 */
	public boolean beforeFrequencyChange(IWirelessDeviceData wirelessDeviceData, Object oldFreq, Object newFreq);
}
