package net.slimevoid.wirelessredstone.core.lib;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTHelper {

    public static String getString(ItemStack itemstack, String key, String defaultValue) {
        if (itemstack != null) {
            if (!itemstack.hasTagCompound()
                || !itemstack.getTagCompound().hasKey(key)) {
                setString(itemstack,
                          key,
                          defaultValue);
            }
            NBTTagCompound nbttagcompound = itemstack.getTagCompound();
            return nbttagcompound.getString(key);
        }
        return defaultValue;
    }

    public static boolean getBoolean(ItemStack itemstack, String key, boolean defaultValue) {
        if (itemstack != null) {
            if (!itemstack.hasTagCompound()
                || !itemstack.getTagCompound().hasKey(key)) {
                setBoolean(itemstack,
                           key,
                           defaultValue);
            }
            NBTTagCompound nbttagcompound = itemstack.getTagCompound();
            return nbttagcompound.getBoolean(key);
        }
        return defaultValue;
    }

    public static void setString(ItemStack itemstack, String key, String value) {
        if (itemstack != null) {
            if (!itemstack.hasTagCompound()) {
                itemstack.setTagCompound(new NBTTagCompound());
            }
            itemstack.getTagCompound().setString(key,
                                                 value);
        }
    }

    public static void setBoolean(ItemStack itemstack, String key, boolean value) {
        if (itemstack != null) {
            if (!itemstack.hasTagCompound()) {
                itemstack.setTagCompound(new NBTTagCompound());
            }
            itemstack.getTagCompound().setBoolean(key,
                                                  value);
        }
    }
}
