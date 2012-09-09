package wirelessredstone.proxy;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import wirelessredstone.api.ICommonProxy;
import wirelessredstone.core.WRCore;
import wirelessredstone.ether.RedstoneEther;
import wirelessredstone.network.handlers.ServerGuiPacketHandler;
import wirelessredstone.overrides.BaseModOverrideSMP;
import wirelessredstone.overrides.BlockRedstoneWirelessOverrideSMP;
import wirelessredstone.overrides.GuiRedstoneWirelessInventoryOverrideSMP;
import wirelessredstone.overrides.RedstoneEtherOverrideSMP;
import wirelessredstone.overrides.RedstoneEtherOverrideSSP;
import wirelessredstone.overrides.TileEntityRedstoneWirelessOverrideSMP;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;

public class WRCommonProxy implements ICommonProxy {

	@Override
	public void registerRenderInformation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMinecraftDir() {
		// TODO Auto-generated method stub
		return ".";
	}

	@Override
	public void registerTileEntitySpecialRenderer(
			Class<? extends TileEntity> clazz) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addOverrides() {
	}

	@Override
	public void openGUI(World world, EntityPlayer entityplayer,
			TileEntity tileentity) {
		if (!world.isRemote) {
			if (tileentity instanceof TileEntityRedstoneWireless) {
				ServerGuiPacketHandler.sendGuiPacketTo((EntityPlayerMP) entityplayer, (TileEntityRedstoneWireless)tileentity);
			}
		}
	}

	@Override
	public void activateGUI(World world, EntityPlayer entityplayer,
			TileEntity tileentity) {
	}

	@Override
	public World getWorld() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntityPlayer getPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init() {		
	}
}
