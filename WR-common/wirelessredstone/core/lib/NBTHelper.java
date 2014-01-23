package wirelessredstone.core.lib;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTHelper {

	public static String getString(ItemStack itemstack, String key, String defaultValue) {
		if (itemstack != null && itemstack.hasTagCompound()) {
			NBTTagCompound nbttagcompound = itemstack.getTagCompound();
			if (nbttagcompound.hasKey(key)) {
				return nbttagcompound.getString(key);
			} else {
				nbttagcompound.setString(	key,
											defaultValue);
			}
		}
		return defaultValue;
	}

}
