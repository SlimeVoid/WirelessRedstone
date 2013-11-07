package wirelessredstone.addon.camouflager.core.lib;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;
import cpw.mods.fml.common.FMLCommonHandler;

public class CamouLib {

	public static void setBlockRef(World world, TileEntityRedstoneWireless tRW, ItemStack blockRef) {
		CamouAddonData newData = (CamouAddonData) tRW.getAdditionalData(CoreLib.MOD_ID);
		if (newData == null) {
			newData = new CamouAddonData();
		}
		newData.setBlockRef(blockRef);
		tRW.setAdditionalData(	CoreLib.MOD_ID,
								newData);
		tRW.onInventoryChanged();
		world.markBlockForUpdate(	tRW.xCoord,
									tRW.yCoord,
									tRW.zCoord);
	}

	public static ItemStack getBlockRef(World world, TileEntityRedstoneWireless tRW) {
		CamouAddonData data = (CamouAddonData) tRW.getAdditionalData(CoreLib.MOD_ID);
		if (data != null) {
			return data.getBlockRef();
		} else {
			return null;
		}
	}

	public static Icon getIconForTile(TileEntityRedstoneWireless tRW, int side, Icon output) {
		CamouAddonData data = (CamouAddonData) tRW.getAdditionalData(CoreLib.MOD_ID);
		if (data != null) {
			ItemStack itemstack = data.getBlockRef();
			if (itemstack != null) {
				int blockID = itemstack.itemID;
				int damage = itemstack.getItemDamage();
				return Block.blocksList[blockID].getIcon(	side,
															damage);
			}
		}
		return output;
	}

	public static void writeToNBT(TileEntityRedstoneWireless tileEntityRedstoneWireless, NBTTagCompound nbttagcompound) {
		CamouAddonData data = (CamouAddonData) tileEntityRedstoneWireless.getAdditionalData(CoreLib.MOD_ID);
		if (data == null) {
			data = new CamouAddonData();
		}
		NBTTagCompound addonTag = new NBTTagCompound();
		data.writeToNBT(addonTag);
		nbttagcompound.setCompoundTag(	CoreLib.MOD_ID,
										addonTag);
	}

	public static void readFromNBT(TileEntityRedstoneWireless tileEntityRedstoneWireless, NBTTagCompound nbttagcompound) {
		if (nbttagcompound.hasKey(CoreLib.MOD_ID)) {
			NBTTagCompound addonTag = nbttagcompound.getCompoundTag(CoreLib.MOD_ID);
			CamouAddonData newData = new CamouAddonData();
			newData.readFromNBT(addonTag);
			tileEntityRedstoneWireless.setAdditionalData(	CoreLib.MOD_ID,
															newData);
		} else {
			FMLCommonHandler.instance().getFMLLogger().warning("Could not read Camouflage NBT from tile");
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
			EntityItem item = new EntityItem(world, x + xx, y + yy, z + zz, itemstack);
			item.age = 10;
			world.spawnEntityInWorld(item);
			return;
		}
	}

	public static void prepareTile(TileEntityRedstoneWireless tileentity) {
		CamouAddonData data = (CamouAddonData) tileentity.getAdditionalData(CoreLib.MOD_ID);
		if (data == null) {
			data = new CamouAddonData();
			tileentity.setAdditionalData(	CoreLib.MOD_ID,
											data);
			tileentity.onInventoryChanged();
		}
	}
}
