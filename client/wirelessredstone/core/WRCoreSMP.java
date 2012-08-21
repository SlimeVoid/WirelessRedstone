package wirelessredstone.core;

import wirelessredstone.ether.RedstoneEther;
import wirelessredstone.overrides.BaseModOverrideSMP;
import wirelessredstone.overrides.BlockRedstoneWirelessOverrideSMP;
import wirelessredstone.overrides.GuiRedstoneWirelessInventoryOverrideSMP;
import wirelessredstone.overrides.RedstoneEtherOverrideSMP;
import wirelessredstone.overrides.TileEntityRedstoneWirelessOverrideSMP;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;

/**
 * WirelessRedstoneSMP class
 * 
 * To allow abstraction from the BaseModSMP code
 * 
 * @author Eurymachus
 * 
 */
public class WRCoreSMP {
	public static boolean isLoaded = false;

	public static boolean initialize() {

		GuiRedstoneWirelessInventoryOverrideSMP GUIOverride = new GuiRedstoneWirelessInventoryOverrideSMP();
		WRCore.addGuiOverrideToReceiver(GUIOverride);
		WRCore.addGuiOverrideToTransmitter(GUIOverride);

		BlockRedstoneWirelessOverrideSMP blockOverride = new BlockRedstoneWirelessOverrideSMP();
		WRCore.addOverrideToReceiver(blockOverride);
		WRCore.addOverrideToTransmitter(blockOverride);

		TileEntityRedstoneWirelessOverrideSMP tileOverride = new TileEntityRedstoneWirelessOverrideSMP();
		TileEntityRedstoneWireless.addOverride(tileOverride);

		RedstoneEtherOverrideSMP etherOverrideSMP = new RedstoneEtherOverrideSMP();
		RedstoneEther.getInstance().addOverride(etherOverrideSMP);

		BaseModOverrideSMP baseModOverride = new BaseModOverrideSMP();
		WRCore.addOverride(baseModOverride);
		return true;
	}
}
