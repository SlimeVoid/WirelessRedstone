package wirelessredstone.overrides;

import wirelessredstone.api.IRedstoneWirelessData;
import wirelessredstone.api.ITileEntityRedstoneWirelessOverride;
import wirelessredstone.network.packets.PacketWirelessTile;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessR;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessT;

public class TileEntityRedstoneWirelessOverrideSMP implements
		ITileEntityRedstoneWirelessOverride {
	@Override
	public boolean beforeUpdateEntity(TileEntityRedstoneWireless tileentity) {
		return false;//tileentity.worldObj.isRemote;
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
					teRWT.setFreq(packetData.getDeviceFreq().toString());
					teRWT.onInventoryChanged();
					teRWT.worldObj.markBlockForRenderUpdate(packetData.xPosition,
							packetData.yPosition, packetData.zPosition);
				}

				if (tileentityredstonewireless instanceof TileEntityRedstoneWirelessR) {
					TileEntityRedstoneWirelessR teRWR = (TileEntityRedstoneWirelessR) tileentityredstonewireless;
					teRWR.setFreq(packetData.getDeviceFreq().toString());
					teRWR.setInDirectlyPowering(packetData
							.getInDirectlyPowering());
					teRWR.setPowerDirections(packetData.getPowerDirections());
					teRWR.onInventoryChanged();
					teRWR.worldObj.markBlockForRenderUpdate(packetData.xPosition,
							packetData.yPosition, packetData.zPosition);
				}
			}
		}
		return false;
	}
}