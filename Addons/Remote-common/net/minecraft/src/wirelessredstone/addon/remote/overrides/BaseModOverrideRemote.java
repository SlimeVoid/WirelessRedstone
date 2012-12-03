package net.minecraft.src.wirelessredstone.addon.remote.overrides;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.WorldSavedData;
import net.minecraft.src.wirelessredstone.addon.remote.WirelessRemote;
import net.minecraft.src.wirelessredstone.overrides.BaseModOverride;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWirelessR;

public class BaseModOverrideRemote implements BaseModOverride {

	@Override
	public boolean beforeOpenGui(World world, EntityPlayer entityplayer,
			TileEntity tileentity) {
		if (entityplayer.getCurrentEquippedItem() != null
				&& entityplayer.getCurrentEquippedItem().itemID == WirelessRemote.remoteID) {
			if (tileentity instanceof TileEntityRedstoneWirelessR) {
				((TileEntityRedstoneWirelessR) tileentity)
						.setFreq((WirelessRemote.getDeviceData(
								entityplayer.getCurrentEquippedItem(), world,
								entityplayer).getFreq()));
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean beforeOpenGui(World world, EntityPlayer entityplayer,
			WorldSavedData data) {
		return false;
	}

}
