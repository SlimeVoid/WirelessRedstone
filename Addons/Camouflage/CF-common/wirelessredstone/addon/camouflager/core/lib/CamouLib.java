package wirelessredstone.addon.camouflager.core.lib;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
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

	public static boolean isBlock(ItemStack itemstack) {
		return itemstack != null && itemstack.getItem() != null
				&& itemstack.getItem() instanceof ItemBlock;
	}

	public static void dropItem(World world, int x, int y, int z, ItemStack itemstack) {
		if (world.isRemote) {
			return;
		} else {
			double d = 0.69999999999999996D;
			double xx = world.rand.nextFloat() * d + (1.0D - d) * 0.5D;
			double yy = world.rand.nextFloat() * d + (1.0D - d) * 0.5D;
			double zz = world.rand.nextFloat() * d + (1.0D - d) * 0.5D;
			EntityItem item = new EntityItem(world, x + xx, y
																		+ yy, z
																				+ zz, itemstack);
			item.age = 10;
			world.spawnEntityInWorld(item);
			return;
		}
	}
}
