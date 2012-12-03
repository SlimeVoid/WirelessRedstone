package wirelessredstone.addon.remote.data;

import java.util.HashMap;
import java.util.TreeMap;

import net.minecraft.src.EntityPlayer;
import wirelessredstone.data.WirelessCoordinates;
import wirelessredstone.data.WirelessDeviceData;

public class WirelessRemoteData extends WirelessDeviceData {

	public static HashMap<EntityPlayer, WirelessRemoteDevice> remoteTransmitters;
	public static TreeMap<WirelessCoordinates, WirelessRemoteDevice> remoteWirelessCoords;

	public WirelessRemoteData(String index) {
		super(index);
	}
}
