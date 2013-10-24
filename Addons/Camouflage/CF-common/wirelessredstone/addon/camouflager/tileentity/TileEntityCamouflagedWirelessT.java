package wirelessredstone.addon.camouflager.tileentity;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.Icon;
import wirelessredstone.addon.camouflager.api.ICamouflaged;
import wirelessredstone.addon.camouflager.core.lib.CamouLib;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessT;

public class TileEntityCamouflagedWirelessT extends TileEntityRedstoneWirelessT
		implements ICamouflaged {

	private ItemStack[]	blockRefs;

	public TileEntityCamouflagedWirelessT() {
		super();
		this.blockRefs = new ItemStack[6];
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		NBTTagList items = nbttagcompound.getTagList("BlockRef");
		for (int i = 0; i < items.tagCount(); i++) {
			NBTTagCompound item = (NBTTagCompound) items.tagAt(i);
			int j = item.getByte("Side") & 0xff;
			if (j >= 0 && j < this.blockRefs.length) {
				this.blockRefs[j] = ItemStack.loadItemStackFromNBT(item);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		CamouLib.writeToNBT(this,
							nbttagcompound);
	}

	@Override
	public Icon getIconFromSide(int side) {
		if (this.blockRefs != null && side >= 0 && side < this.blockRefs.length
			&& this.blockRefs[side] != null) {
			ItemStack blockRef = this.blockRefs[side];
			return Block.blocksList[blockRef.itemID].getIcon(	side,
																blockRef.getItemDamage());
		}
		return this.blockType.getBlockTextureFromSide(side);
	}

	@Override
	public void setBlockReference(ItemStack blockRef) {
		if (this.blockRefs != null) {
			for (int i = 0; i < this.blockRefs.length; i++) {
				this.setBlockReferenceForSide(	blockRef,
												i);
			}
		}
	}

	@Override
	public void setBlockReferenceForSide(ItemStack blockRef, int side) {
		if (this.blockRefs != null) {
			this.blockRefs[side] = blockRef;
		}
	}

	@Override
	public ItemStack[] getBlockRefs() {
		return this.blockRefs;
	}
}
