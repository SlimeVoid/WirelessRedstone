package wirelessredstone.addon.slimevoid.core;

import wirelessredstone.addon.slimevoid.overrides.PacketWirelessSlimeVoidOverride;
import wirelessredstone.addon.slimevoid.overrides.TileEntityRedstoneWirelessSlimevoidOverride;
import wirelessredstone.network.packets.PacketWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;

public class WSlimeCore {

	/**
	 * Fires off all the canons.<br>
	 * Loads configurations and initializes objects. Loads ModLoader related
	 * stuff.
	 */
	public static boolean initialize() {
		addOverrides();
		return true;
	}
	
	private static void addOverrides() {
		TileEntityRedstoneWirelessSlimevoidOverride tileOverride = new TileEntityRedstoneWirelessSlimevoidOverride();
		TileEntityRedstoneWireless.addOverride(tileOverride);
		PacketWirelessSlimeVoidOverride packetOverride = new PacketWirelessSlimeVoidOverride();
		PacketWireless.addOverride(packetOverride);
	}
}
