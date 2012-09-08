package wirelessredstone.api;

import wirelessredstone.tileentity.TileEntityRedstoneWireless;

public interface ITileEntityRedstoneWirelessOverride {
	public boolean beforeUpdateEntity(TileEntityRedstoneWireless tileentity);

	public void afterUpdateEntity(TileEntityRedstoneWireless tileentity);

	public boolean beforeHandleData(
			TileEntityRedstoneWireless tileEntityRedstoneWireless,
			IRedstoneWirelessData data);
}
