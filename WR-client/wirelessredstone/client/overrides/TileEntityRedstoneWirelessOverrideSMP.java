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
package wirelessredstone.client.overrides;

import net.minecraft.entity.player.EntityPlayer;
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
		return tileentity.worldObj.isRemote;
	}

	@Override
	public void afterUpdateEntity(TileEntityRedstoneWireless tileentity) {
	}

	@Override
	public boolean beforeHandleData(TileEntityRedstoneWireless tileentityredstonewireless, IRedstoneWirelessData data) {
		if (data != null && tileentityredstonewireless != null) {
			if (data instanceof PacketWirelessTile) {
				PacketWirelessTile packetData = (PacketWirelessTile) data;
				if (tileentityredstonewireless instanceof TileEntityRedstoneWirelessT) {
					TileEntityRedstoneWirelessT teRWT = (TileEntityRedstoneWirelessT) tileentityredstonewireless;
					teRWT.setFreq(packetData.getFreq().toString());
					teRWT.onInventoryChanged();
					teRWT.worldObj.markBlockForRenderUpdate(packetData.xPosition,
															packetData.yPosition,
															packetData.zPosition);
				}

				if (tileentityredstonewireless instanceof TileEntityRedstoneWirelessR) {
					TileEntityRedstoneWirelessR teRWR = (TileEntityRedstoneWirelessR) tileentityredstonewireless;
					teRWR.setFreq(packetData.getFreq().toString());
					teRWR.setInDirectlyPowering(packetData.getInDirectlyPowering());
					teRWR.setPowerDirections(packetData.getPowerDirections());
					teRWR.onInventoryChanged();
					teRWR.worldObj.markBlockForRenderUpdate(packetData.xPosition,
															packetData.yPosition,
															packetData.zPosition);
				}
			}
		}
		return false;
	}

	@Override
	public boolean beforeIsUseableByPlayer(TileEntityRedstoneWireless tileEntityRedstoneWireless, EntityPlayer entityplayer) {
		return false;
	}

	@Override
	public boolean afterIsUseableByPlayer(TileEntityRedstoneWireless tileEntityRedstoneWireless, EntityPlayer entityplayer, boolean returnState) {
		return returnState;
	}
}