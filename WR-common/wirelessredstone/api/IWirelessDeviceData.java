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
