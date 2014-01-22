package wirelessredstone.addon.remote.core.lib;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTLib {

	public static final String	FREQUENCY	= "Freq";
	public static final String	STATE		= "State";

	public static String getDeviceFreq(ItemStack itemstack) {
		if (itemstack.hasTagCompound()) {
			NBTTagCompound stackCompound = itemstack.getTagCompound();
			if (stackCompound.hasKey(FREQUENCY)) {
				return stackCompound.getString(FREQUENCY);
			}
		}
		return "0";
	}

	public static boolean getDeviceState(ItemStack itemstack) {
		if (itemstack.hasTagCompound()) {
			NBTTagCompound stackCompound = itemstack.getTagCompound();
			if (stackCompound.hasKey(STATE)) {
				return stackCompound.getBoolean(STATE);
			}
		}
		return false;
	}

}
