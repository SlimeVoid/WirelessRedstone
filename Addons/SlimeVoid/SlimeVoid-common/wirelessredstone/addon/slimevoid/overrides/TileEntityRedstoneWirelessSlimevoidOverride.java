package wirelessredstone.addon.slimevoid.overrides;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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

	@Override
	public boolean handlesExtraNBTTags() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void writeToNBT(TileEntityRedstoneWireless tileEntityRedstoneWireless, NBTTagCompound nbttagcompound) {
		// TODO Auto-generated method stub

	}

	@Override
	public void readFromNBT(TileEntityRedstoneWireless tileEntityRedstoneWireless, NBTTagCompound nbttagcompound) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean handleInventory() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ItemStack getStackInSlot(TileEntityRedstoneWireless tileEntityRedstoneWireless, int i, ItemStack itemstack) {
		// TODO Auto-generated method stub
		return itemstack;
	}

	@Override
	public ItemStack decrStackSize(TileEntityRedstoneWireless tileEntityRedstoneWireless, int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(TileEntityRedstoneWireless tileEntityRedstoneWireless, int i, ItemStack itemstack) {
		// TODO Auto-generated method stub
		return itemstack;
	}

	@Override
	public boolean setInventorySlotContents(TileEntityRedstoneWireless tileEntityRedstoneWireless, int slot, ItemStack itemstack) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isStackValidForSlot(TileEntityRedstoneWireless tileEntityRedstoneWireless, int slot, ItemStack itemstack, boolean result) {
		// TODO Auto-generated method stub
		return result;
	}

	@Override
	public void onBlockRemoval(TileEntityRedstoneWireless tileEntityRedstoneWireless, int side, int metadata) {
		// TODO Auto-generated method stub

	}

}
