package com.slimevoid.wirelessredstone.device;

import java.util.List;

import com.slimevoid.wirelessredstone.core.WRCore;
import com.slimevoid.wirelessredstone.core.lib.NBTHelper;
import com.slimevoid.wirelessredstone.core.lib.NBTLib;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public abstract class ItemWirelessDevice extends Item {

    protected IIcon[] iconList;

    public ItemWirelessDevice(int itemID) {
        super();
        this.setMaxStackSize(1);
        this.setCreativeTab(WRCore.wirelessRedstone);
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        this.iconList = new IIcon[2];
        this.registerIconList(iconRegister);
    }

    protected abstract void registerIconList(IIconRegister iconRegister);

    @Override
    public IIcon getIconIndex(ItemStack itemstack) {
        return this.getIcon(itemstack,
                            0);
    }

    @Override
    public IIcon getIcon(ItemStack itemstack, int pass) {
        if (!getState(itemstack)) return iconList[0];
        return iconList[1];
    }

    @Override
    public int getMaxItemUseDuration(ItemStack itemstack) {
        return 72000;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack itemstack) {
        return EnumAction.none;
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack itemstack, EntityPlayer entityplayer) {
        this.setState(itemstack,
                      false);
        return this.onDeviceDroppedByPlayer(itemstack,
                                            entityplayer);
    }

    @Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean isHeld) {
        if (this.isValidDevice(itemstack)) {
            if (!itemstack.hasTagCompound()) {
                itemstack.stackTagCompound = new NBTTagCompound();
                this.getFreq(itemstack);
                this.getState(itemstack);
            }
            if (entity != null && entity instanceof EntityLivingBase) {
                EntityLivingBase entitylivingbase = (EntityLivingBase) entity;
                this.onUpdateDevice(itemstack,
                                    world,
                                    entitylivingbase,
                                    i,
                                    isHeld);
                if (!isHeld) {
                    this.setState(itemstack,
                                  false);
                }
            }
        }
    }

    protected abstract void onUpdateDevice(ItemStack itemstack, World world, EntityLivingBase entity, int i, boolean isHeld);

    protected abstract boolean isValidDevice(ItemStack itemstack);

    protected abstract boolean onDeviceDroppedByPlayer(ItemStack itemstack, EntityPlayer entityplayer);

    public boolean getState(ItemStack itemstack) {
        if (this.isValidDevice(itemstack)) {
            return NBTHelper.getBoolean(itemstack,
                                        NBTLib.STATE,
                                        false);
        }
        return false;
    }

    public Object getFreq(ItemStack itemstack) {
        if (this.isValidDevice(itemstack)) {
            return NBTHelper.getString(itemstack,
                                       NBTLib.FREQUENCY,
                                       "0");
        }
        return "0";
    }

    public void setState(ItemStack itemstack, boolean state) {
        if (this.isValidDevice(itemstack)) {
            NBTHelper.setBoolean(itemstack,
                                 NBTLib.STATE,
                                 state);
        }
    }

    public void setFreq(ItemStack itemstack, Object freq) {
        if (this.isValidDevice(itemstack)) {
            NBTHelper.setString(itemstack,
                                NBTLib.FREQUENCY,
                                String.valueOf(freq));
        }
    }

    public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List lines, boolean showAdvancedInfo) {
        lines.add("Frequency [" + this.getFreq(itemstack) + "]");
    }

    @Override
    public boolean getShareTag() {
        return true;
    }

    @Override
    public boolean isFull3D() {
        return true;
    }
}
