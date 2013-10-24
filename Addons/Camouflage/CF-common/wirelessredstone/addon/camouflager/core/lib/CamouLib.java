package wirelessredstone.addon.camouflager.core.lib;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import wirelessredstone.addon.camouflager.api.ICamouflaged;
import wirelessredstone.addon.camouflager.tileentity.TileEntityCamouflagedWirelessR;
import wirelessredstone.addon.camouflager.tileentity.TileEntityCamouflagedWirelessT;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessR;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessT;

public class CamouLib {

	public static TileEntity createCamouflagedTile(TileEntityRedstoneWireless tRW) {
		NBTTagCompound tags = new NBTTagCompound();
		tRW.writeToNBT(tags);
		TileEntityRedstoneWireless camouTile = null;
		if (tRW instanceof TileEntityRedstoneWirelessR) {
			camouTile = new TileEntityCamouflagedWirelessR();
			camouTile.readFromNBT(tags);
		} else if (tRW instanceof TileEntityRedstoneWirelessT) {
			camouTile = new TileEntityCamouflagedWirelessT();
			camouTile.readFromNBT(tags);
		}
		return camouTile;
	}

	public static void writeToNBT(ICamouflaged tRW, NBTTagCompound nbttagcompound) {
		NBTTagList items = new NBTTagList();
		for (int i = 0; i < tRW.getBlockRefs().length; i++) {
			if (tRW.getBlockRefs()[i] != null) {
				NBTTagCompound item = new NBTTagCompound();
				item.setByte(	"Side",
								(byte) i);
				tRW.getBlockRefs()[i].writeToNBT(item);
				items.appendTag(item);
			}
		}
		nbttagcompound.setTag(	"BlockRef",
								items);
	}

	public static void readFromNBT(ICamouflaged tRW, NBTTagCompound nbttagcompound) {
		if (nbttagcompound.hasKey("BlockRef")) {
			NBTTagList items = nbttagcompound.getTagList("BlockRef");
			for (int i = 0; i < items.tagCount(); i++) {
				NBTTagCompound item = (NBTTagCompound) items.tagAt(i);
				int j = item.getByte("Side") & 0xff;
				if (j >= 0 && j < tRW.getBlockRefs().length) {
					tRW.setBlockReferenceForSide(	ItemStack.loadItemStackFromNBT(item),
													j);
				}
			}
		}
	}
}
