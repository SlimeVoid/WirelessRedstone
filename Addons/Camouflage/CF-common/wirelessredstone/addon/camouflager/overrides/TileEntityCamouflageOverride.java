package wirelessredstone.addon.camouflager.overrides;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import wirelessredstone.addon.camouflager.core.lib.CamouAddonData;
import wirelessredstone.addon.camouflager.core.lib.CamouLib;
import wirelessredstone.addon.camouflager.core.lib.CoreLib;
import wirelessredstone.api.ITileEntityRedstoneWirelessOverride;
import wirelessredstone.api.IWirelessData;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;

public class TileEntityCamouflageOverride implements
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
    public boolean afterIsUseableByPlayer(TileEntityRedstoneWireless tileEntityRedstoneWireless, EntityPlayer entityplayer, boolean output) {
        return output;
    }

    @Override
    public boolean handlesExtraNBTTags() {
        return true;
    }

    @Override
    public void writeToNBT(TileEntityRedstoneWireless tileEntityRedstoneWireless, NBTTagCompound nbttagcompound) {
        CamouLib.writeToNBT(tileEntityRedstoneWireless,
                            nbttagcompound);
    }

    @Override
    public void readFromNBT(TileEntityRedstoneWireless tileEntityRedstoneWireless, NBTTagCompound nbttagcompound) {
        CamouLib.readFromNBT(tileEntityRedstoneWireless,
                             nbttagcompound);
    }

    @Override
    public boolean handleInventory() {
        return true;
    }

    @Override
    public ItemStack getStackInSlot(TileEntityRedstoneWireless tileEntityRedstoneWireless, int i, ItemStack itemstack) {
        CamouAddonData data = (CamouAddonData) tileEntityRedstoneWireless.getAdditionalData(CoreLib.MOD_ID);
        if (data != null) {
            return data.getBlockRef();
        }
        return itemstack;
    }

    @Override
    public ItemStack decrStackSize(TileEntityRedstoneWireless tileEntityRedstoneWireless, int slot, int amount, ItemStack itemstack) {
        if (tileEntityRedstoneWireless.getStackInSlot(slot) == null) {
            return null;
        }
        CamouAddonData data = (CamouAddonData) tileEntityRedstoneWireless.getAdditionalData(CoreLib.MOD_ID);
        if (data == null) {
            return null;
        }
        ItemStack stackcopy;
        if (data.getBlockRef().stackSize <= amount) {
            stackcopy = data.getBlockRef();
            data.setBlockRef(null);
            tileEntityRedstoneWireless.setAdditionalData(CoreLib.MOD_ID,
                                                         data);
            tileEntityRedstoneWireless.onInventoryChanged();
            return stackcopy;
        }
        stackcopy = data.getBlockRef().splitStack(amount);
        if (data.getBlockRef().stackSize == 0) {
            data.setBlockRef(null);
            tileEntityRedstoneWireless.setAdditionalData(CoreLib.MOD_ID,
                                                         data);
        }
        tileEntityRedstoneWireless.onInventoryChanged();
        return stackcopy;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(TileEntityRedstoneWireless tileEntityRedstoneWireless, int i, ItemStack itemstack) {
        return itemstack;
    }

    @Override
    public boolean setInventorySlotContents(TileEntityRedstoneWireless tileEntityRedstoneWireless, int slot, ItemStack itemstack) {
        if (CamouLib.isValidStack(itemstack)) {
            CamouLib.setBlockRef(tileEntityRedstoneWireless.getWorldObj(),
                                 tileEntityRedstoneWireless,
                                 itemstack);
            return true;
        }
        return false;
    }

    @Override
    public boolean isStackValidForSlot(TileEntityRedstoneWireless tileEntityRedstoneWireless, int slot, ItemStack itemstack, boolean result) {
        return slot == 0
               && CamouLib.isValidStack(itemstack)
               && CamouLib.getBlockRef(tileEntityRedstoneWireless.getWorldObj(),
                                       tileEntityRedstoneWireless) == null;
    }

    @Override
    public void onBlockRemoval(TileEntityRedstoneWireless tileEntityRedstoneWireless, int side, int metadata) {
        ItemStack blockRef = CamouLib.getBlockRef(tileEntityRedstoneWireless.getWorldObj(),
                                                  tileEntityRedstoneWireless);
        if (blockRef != null) {
            CamouLib.dropItem(tileEntityRedstoneWireless.worldObj,
                              tileEntityRedstoneWireless.xCoord,
                              tileEntityRedstoneWireless.yCoord,
                              tileEntityRedstoneWireless.zCoord,
                              blockRef);
        }
    }
}
