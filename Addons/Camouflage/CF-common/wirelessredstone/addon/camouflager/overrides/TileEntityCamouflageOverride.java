package wirelessredstone.addon.camouflager.overrides;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import wirelessredstone.addon.camouflager.core.lib.CamouAddonData;
import wirelessredstone.addon.camouflager.core.lib.CamouLib;
import wirelessredstone.addon.camouflager.core.lib.CoreLib;
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
		CamouAddonData data = (CamouAddonData) tileEntityRedstoneWireless.getAdditionalData(CoreLib.MOD_ID);
		if (data != null) {
			return data.getBlockRef();
		}
		return itemstack;
	}

	@Override
	public ItemStack decrStackSize(TileEntityRedstoneWireless tileEntityRedstoneWireless, int slot, int amount) {
		if (tileEntityRedstoneWireless.getStackInSlot(slot) == null) {
			return null;
		}
		CamouAddonData data = (CamouAddonData) tileEntityRedstoneWireless.getAdditionalData(CoreLib.MOD_ID);
		if (data == null) {
			return null;
		}
		ItemStack itemstack;
		if (data.getBlockRef().stackSize <= amount) {
			itemstack = data.getBlockRef();
			data.setBlockRef(null);
			tileEntityRedstoneWireless.setAdditionalData(	CoreLib.MOD_ID,
															data);
			tileEntityRedstoneWireless.onInventoryChanged();
			return itemstack;
		}
		itemstack = data.getBlockRef().splitStack(amount);
		if (data.getBlockRef().stackSize == 0) {
			data.setBlockRef(null);
			tileEntityRedstoneWireless.setAdditionalData(	CoreLib.MOD_ID,
															data);
		}
		tileEntityRedstoneWireless.onInventoryChanged();
		return itemstack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(TileEntityRedstoneWireless tileEntityRedstoneWireless, int i, ItemStack itemstack) {
		return itemstack;
	}

	@Override
	public boolean setInventorySlotContents(TileEntityRedstoneWireless tileEntityRedstoneWireless, int slot, ItemStack itemstack) {
		CamouLib.setBlockRef(	tileEntityRedstoneWireless.getWorldObj(),
								tileEntityRedstoneWireless,
								itemstack);
		return true;
	}

	@Override
	public boolean isStackValidForSlot(TileEntityRedstoneWireless tileEntityRedstoneWireless, int slot, ItemStack itemstack, boolean result) {
		return CamouLib.isBlock(itemstack)
				&& CamouLib.getBlockRef(tileEntityRedstoneWireless.getWorldObj(),
										tileEntityRedstoneWireless) == null;
	}

	@Override
	public void onBlockRemoval(TileEntityRedstoneWireless tileEntityRedstoneWireless, int side, int metadata) {
		ItemStack blockRef = CamouLib.getBlockRef(	tileEntityRedstoneWireless.getWorldObj(),
													tileEntityRedstoneWireless);
		if (blockRef != null) {
			CamouLib.dropItem(	tileEntityRedstoneWireless.worldObj,
								tileEntityRedstoneWireless.xCoord,
								tileEntityRedstoneWireless.yCoord,
								tileEntityRedstoneWireless.zCoord,
								blockRef);
		}
	}
}
