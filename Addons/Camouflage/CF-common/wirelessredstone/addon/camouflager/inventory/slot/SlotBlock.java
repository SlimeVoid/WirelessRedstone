package wirelessredstone.addon.camouflager.inventory.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class SlotBlock extends Slot {

	public SlotBlock(IInventory inventory, int i, int j, int k) {
		super(inventory, i, j, k);
	}

	@Override
	// isItemValid
	public boolean isItemValid(ItemStack itemstack) {
		return itemstack.getItem() instanceof ItemBlock;
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}

	@Override
	public void onSlotChanged() {
		super.onSlotChanged();
		if (this.inventory instanceof TileEntity) {
			TileEntity tileentity = ((TileEntity) this.inventory);
			tileentity.worldObj.markBlockForUpdate(	tileentity.xCoord,
													tileentity.yCoord,
													tileentity.zCoord);
		}
	}
}
