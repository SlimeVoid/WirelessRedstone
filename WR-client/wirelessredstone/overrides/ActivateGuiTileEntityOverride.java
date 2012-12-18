package wirelessredstone.overrides;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import wirelessredstone.api.IActivateGuiOverride;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.proxy.WRClientProxy;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessR;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessT;

public class ActivateGuiTileEntityOverride implements IActivateGuiOverride {

	@Override
	public boolean beforeOpenGui(World world, EntityPlayer entityplayer, TileEntityRedstoneWireless tileentityredstonewireless) {
		if (tileentityredstonewireless instanceof TileEntityRedstoneWirelessR) {
			WRClientProxy.guiWirelessR
					.assTileEntity(tileentityredstonewireless);
			ModLoader.openGUI(entityplayer, WRClientProxy.guiWirelessR);
			return true;
		}
		if (tileentityredstonewireless instanceof TileEntityRedstoneWirelessT) {
			WRClientProxy.guiWirelessT
					.assTileEntity(tileentityredstonewireless);
			ModLoader.openGUI(entityplayer, WRClientProxy.guiWirelessT);
			return true;
		}
		return false;
	}

	@Override
	public boolean beforeOpenGui(World world, EntityPlayer entityplayer, IWirelessDeviceData devicedata) {
		return false;
	}

}
