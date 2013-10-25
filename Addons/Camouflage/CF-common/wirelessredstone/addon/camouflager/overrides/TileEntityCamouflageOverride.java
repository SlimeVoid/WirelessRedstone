package wirelessredstone.addon.camouflager.overrides;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import wirelessredstone.addon.camouflager.core.lib.CamouLib;
import wirelessredstone.api.IRedstoneWirelessData;
import wirelessredstone.api.ITileEntityRedstoneWirelessOverride;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;

public class TileEntityCamouflageOverride implements
		ITileEntityRedstoneWirelessOverride {

	@Override
	public boolean beforeUpdateEntity(TileEntityRedstoneWireless tileentity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void afterUpdateEntity(TileEntityRedstoneWireless tileentity) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean beforeHandleData(TileEntityRedstoneWireless tileEntityRedstoneWireless, IRedstoneWirelessData data) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean beforeIsUseableByPlayer(TileEntityRedstoneWireless tileEntityRedstoneWireless, EntityPlayer entityplayer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean afterIsUseableByPlayer(TileEntityRedstoneWireless tileEntityRedstoneWireless, EntityPlayer entityplayer, boolean output) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean handlesExtraNBTTags() {
		return true;
	}

	@Override
	public void writeToNBT(TileEntityRedstoneWireless tileEntityRedstoneWireless, NBTTagCompound nbttagcompound) {
		CamouLib.writeToNBT(tileEntityRedstoneWireless,
							nbttagcompound);
	}

	@Override
	public void readFromNBT(TileEntityRedstoneWireless tileEntityRedstoneWireless, NBTTagCompound nbttagcompound) {
		CamouLib.readFromNBT(	tileEntityRedstoneWireless,
								nbttagcompound);
	}

	@Override
	public boolean handleInventory() {
		return true;
	}

	@Override
	public ItemStack getStackInSlot(TileEntityRedstoneWireless tileEntityRedstoneWireless, int i, ItemStack itemstack) {
		return itemstack;
	}

	@Override
	public ItemStack decrStackSize(TileEntityRedstoneWireless tileEntityRedstoneWireless, int i, int j) {
		// TODO :: Decrease Stack Size
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(TileEntityRedstoneWireless tileEntityRedstoneWireless, int i, ItemStack itemstack) {
		return itemstack;
	}
}
