package wirelessredstone.addon.slimevoid.overrides;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimevoidlib.util.helpers.SlimevoidHelper;
import wirelessredstone.api.ITileEntityRedstoneWirelessOverride;
import wirelessredstone.api.IWirelessData;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;

public class TileEntityRedstoneWirelessSlimevoidOverride implements
        ITileEntityRedstoneWirelessOverride {

    @Override
    public boolean beforeUpdateEntity(TileEntityRedstoneWireless tileentity) {
        return false;
    }

    @Override
    public void afterUpdateEntity(TileEntityRedstoneWireless tileentity) {
    }

    @Override
    public boolean beforeHandleData(TileEntityRedstoneWireless tileEntityRedstoneWireless, IWirelessData data) {
        return false;
    }

    @Override
    public boolean beforeIsUseableByPlayer(TileEntityRedstoneWireless tileEntityRedstoneWireless, EntityPlayer entityplayer) {
        return false;
    }

    @Override
    public boolean afterIsUseableByPlayer(TileEntityRedstoneWireless tile, EntityPlayer entityplayer, boolean output) {
        return SlimevoidHelper.isUseableByPlayer(tile.worldObj,
                                                 entityplayer,
                                                 tile.xCoord,
                                                 tile.yCoord,
                                                 tile.zCoord,
                                                 0.5D,
                                                 0.5D,
                                                 0.5D,
                                                 64D);
    }

    @Override
    public boolean handlesExtraNBTTags() {
        return false;
    }

    @Override
    public void writeToNBT(TileEntityRedstoneWireless tileEntityRedstoneWireless, NBTTagCompound nbttagcompound) {
    }

    @Override
    public void readFromNBT(TileEntityRedstoneWireless tileEntityRedstoneWireless, NBTTagCompound nbttagcompound) {
    }

    @Override
    public boolean handleInventory() {
        return false;
    }

    @Override
    public ItemStack getStackInSlot(TileEntityRedstoneWireless tileEntityRedstoneWireless, int i, ItemStack itemstack) {
        return itemstack;
    }

    @Override
    public ItemStack decrStackSize(TileEntityRedstoneWireless tileEntityRedstoneWireless, int i, int j, ItemStack itemstack) {
        return itemstack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(TileEntityRedstoneWireless tileEntityRedstoneWireless, int i, ItemStack itemstack) {
        return itemstack;
    }

    @Override
    public boolean setInventorySlotContents(TileEntityRedstoneWireless tileEntityRedstoneWireless, int slot, ItemStack itemstack) {
        return false;
    }

    @Override
    public boolean isStackValidForSlot(TileEntityRedstoneWireless tileEntityRedstoneWireless, int slot, ItemStack itemstack, boolean result) {
        return result;
    }

    @Override
    public void onBlockRemoval(TileEntityRedstoneWireless tileEntityRedstoneWireless, int side, int metadata) {
    }

}
