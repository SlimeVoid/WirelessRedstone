package wirelessredstone.api;

import wirelessredstone.data.WirelessCoordinates;

public interface IWirelessDevice {

	void setFreq(String freq);

	String getFreq();

	WirelessCoordinates getCoords();

	void activate();

	void deactivate();
}
