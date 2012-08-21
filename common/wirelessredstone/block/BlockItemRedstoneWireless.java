package wirelessredstone.block;

import net.minecraft.src.ItemBlock;

public abstract class BlockItemRedstoneWireless extends ItemBlock {

	public BlockItemRedstoneWireless(int par1) {
		super(par1);
		setIconIndex(getRedstoneWirelessItemIndex());
	}

	protected abstract int getRedstoneWirelessItemIndex();
}
