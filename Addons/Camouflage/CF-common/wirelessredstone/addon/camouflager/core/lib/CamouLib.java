package wirelessredstone.addon.camouflager.core.lib;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;

public class CamouLib {

	public static void setIconForTile(World world, TileEntityRedstoneWireless tRW, ItemStack blockRef) {
		tRW.reference = blockRef;
		tRW.onInventoryChanged();
		world.markBlockForUpdate(	tRW.xCoord,
									tRW.yCoord,
									tRW.zCoord);
	}

	public static Icon getIconForTile(TileEntityRedstoneWireless tRW, int side, Icon output) {
		NBTTagCompound tags = new NBTTagCompound();
		tRW.writeToNBT(tags);
		if (tags.hasKey("BlockRef")) {
			NBTTagCompound stackTag = tags.getCompoundTag("BlockRef");
			ItemStack itemstack = ItemStack.loadItemStackFromNBT(stackTag);
			int blockID = itemstack.itemID;
			int damage = itemstack.getItemDamage();
			return Block.blocksList[blockID].getIcon(	side,
														damage);
		}
		return output;
	}

	public static void writeToNBT(TileEntityRedstoneWireless tileEntityRedstoneWireless, NBTTagCompound nbttagcompound) {
		NBTTagCompound stackTag = new NBTTagCompound();
		ItemStack reference = tileEntityRedstoneWireless.reference;

		if (reference == null) {
			reference = new ItemStack(Block.blockRedstone);
		}

		if (reference != null) {
			reference.writeToNBT(stackTag);
			nbttagcompound.setCompoundTag(	"BlockRef",
											stackTag);
		}
	}

	public static void readFromNBT(TileEntityRedstoneWireless tileEntityRedstoneWireless, NBTTagCompound nbttagcompound) {
		if (nbttagcompound.hasKey("BlockRef")) {
			NBTTagCompound stackTag = nbttagcompound.getCompoundTag("BlockRef");
			tileEntityRedstoneWireless.reference = ItemStack.loadItemStackFromNBT(stackTag);
		}
	}
}
