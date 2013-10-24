package wirelessredstone.addon.camouflager.api;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public interface ICamouflaged extends IInventory {

	public ItemStack[] getBlockRefs();

	public Icon getIconFromSide(int side);

	public void setBlockReference(ItemStack blockRef);

	public void setBlockReferenceForSide(ItemStack blockRef, int side);

}
