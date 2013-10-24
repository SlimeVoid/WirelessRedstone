package wirelessredstone.addon.slimevoid.overrides;

import net.minecraft.entity.player.EntityPlayer;
import slimevoidlib.util.helpers.SlimevoidHelper;
import wirelessredstone.api.IRedstoneWirelessData;
import wirelessredstone.api.ITileEntityRedstoneWirelessOverride;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;

public class TileEntityRedstoneWirelessSlimevoidOverride implements
		ITileEntityRedstoneWirelessOverride {

	@Override
	public boolean beforeUpdateEntity(TileEntityRedstoneWireless tileentity) {
		// TODO :: Auto-generated method stub
		return false;
	}

	@Override
	public void afterUpdateEntity(TileEntityRedstoneWireless tileentity) {
		// TODO :: Auto-generated method stub

	}

	@Override
	public boolean beforeHandleData(TileEntityRedstoneWireless tileEntityRedstoneWireless, IRedstoneWirelessData data) {
		// TODO :: Auto-generated method stub
		return false;
	}

	@Override
	public boolean beforeIsUseableByPlayer(TileEntityRedstoneWireless tileEntityRedstoneWireless, EntityPlayer entityplayer) {
		// TODO :: Auto-generated method stub
		return false;
	}

	@Override
	public boolean afterIsUseableByPlayer(TileEntityRedstoneWireless tile, EntityPlayer entityplayer, boolean output) {
		System.out.println("isUseable: " + output);
		return SlimevoidHelper.isUseableByPlayer(	tile.worldObj,
													entityplayer,
													tile.xCoord,
													tile.yCoord,
													tile.zCoord,
													0.5D,
													0.5D,
													0.5D,
													64D);
	}

}
