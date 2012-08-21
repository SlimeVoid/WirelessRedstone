package wirelessredstone.tileentity;

import wirelessredstone.api.IRedstoneWirelessData;

public interface TileEntityRedstoneWirelessOverride {
	public boolean beforeUpdateEntity(TileEntityRedstoneWireless tileentity);

	public void afterUpdateEntity(TileEntityRedstoneWireless tileentity);

	public boolean beforeHandleData(
			TileEntityRedstoneWireless tileEntityRedstoneWireless,
			IRedstoneWirelessData data);
}
