package net.minecraft.src.wirelessredstone.addon.remote.smp.overrides;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.WorldSavedData;
import net.minecraft.src.wirelessredstone.addon.remote.WirelessRemote;
import net.minecraft.src.wirelessredstone.overrides.BaseModOverride;
import net.minecraft.src.wirelessredstone.smp.network.PacketHandlerRedstoneWireless;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWirelessR;

public class BaseModOverrideRemoteServer implements BaseModOverride {

	@Override
	public boolean beforeOpenGui(World world, EntityPlayer entityplayer,
			TileEntity tileentity) {
		if (entityplayer.getCurrentEquippedItem() != null
				&& entityplayer.getCurrentEquippedItem().itemID == WirelessRemote.remoteID) {
			if (tileentity instanceof TileEntityRedstoneWirelessR) {
				((TileEntityRedstoneWirelessR) tileentity)
						.setFreq((WirelessRemote.getDeviceData(
								entityplayer.getCurrentEquippedItem(), world,
								entityplayer)).getFreq());
				PacketHandlerRedstoneWireless.PacketHandlerOutput
						.sendEtherTileTo(entityplayer,
								(TileEntityRedstoneWirelessR) tileentity,
								world, 0);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean beforeOpenGui(World world, EntityPlayer entityplayer,
			WorldSavedData data) {
		// TODO Auto-generated method stub
		return false;
	}

}
