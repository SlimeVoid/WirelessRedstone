package wirelessredstone.addon.camouflager.core.lib;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import wirelessredstone.api.IRedstoneWirelessData;

public class CamouAddonData implements IRedstoneWirelessData {

    private ItemStack blockRef = null;

    public void writeToNBT(NBTTagCompound nbttagcompound) {
        NBTTagCompound stackTag = new NBTTagCompound();
        ItemStack reference = this.blockRef;

        if (reference != null) {
            reference.writeToNBT(stackTag);
        }
        nbttagcompound.setCompoundTag("BlockRef",
                                      stackTag);
    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        if (nbttagcompound.hasKey("BlockRef")) {
            NBTTagCompound stackTag = nbttagcompound.getCompoundTag("BlockRef");
            this.blockRef = ItemStack.loadItemStackFromNBT(stackTag);
        }
    }

    public ItemStack getBlockRef() {
        return this.blockRef;
    }

    public void setBlockRef(ItemStack blockRef) {
        this.blockRef = blockRef;
    }
}
