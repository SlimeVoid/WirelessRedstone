package wirelessredstone.overrides;

import wirelessredstone.api.IRedstoneWirelessData;
import wirelessredstone.network.packets.PacketWirelessTile;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessOverride;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessR;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessT;

public class TileEntityRedstoneWirelessOverrideSMP implements
		TileEntityRedstoneWirelessOverride {
	@Override
	public boolean beforeUpdateEntity(TileEntityRedstoneWireless tileentity) {
		return tileentity.worldObj.isRemote;
	}

	@Override
	public void afterUpdateEntity(TileEntityRedstoneWireless tileentity) {
	}

	@Override
	public boolean beforeHandleData(
			TileEntityRedstoneWireless tileentityredstonewireless,
			IRedstoneWirelessData data) {
		if (data != null && tileentityredstonewireless != null) {
			if (data instanceof PacketWirelessTile) {
				PacketWirelessTile packetData = (PacketWirelessTile) data;
				if (tileentityredstonewireless instanceof TileEntityRedstoneWirelessT) {
					TileEntityRedstoneWirelessT teRWT = (TileEntityRedstoneWirelessT) tileentityredstonewireless;
					teRWT.setFreq(packetData.getFreq().toString());
					teRWT.onInventoryChanged();
					teRWT.worldObj.markBlockNeedsUpdate(packetData.xPosition,
							packetData.yPosition, packetData.zPosition);
				}

				if (tileentityredstonewireless instanceof TileEntityRedstoneWirelessR) {
					TileEntityRedstoneWirelessR teRWR = (TileEntityRedstoneWirelessR) tileentityredstonewireless;
					teRWR.setFreq(packetData.getFreq().toString());
					teRWR.setInDirectlyPowering(packetData
							.getInDirectlyPowering());
					teRWR.setPowerDirections(packetData.getPowerDirections());
					teRWR.onInventoryChanged();
					teRWR.worldObj.markBlockNeedsUpdate(packetData.xPosition,
							packetData.yPosition, packetData.zPosition);
				}
			}
		}
		return false;
	}
}