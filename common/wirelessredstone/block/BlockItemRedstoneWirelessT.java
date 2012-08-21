package wirelessredstone.block;

import wirelessredstone.core.WRCore;

public class BlockItemRedstoneWirelessT extends BlockItemRedstoneWireless {

	public BlockItemRedstoneWirelessT(int par1) {
		super(par1);
	}

	@Override
	protected int getRedstoneWirelessItemIndex() {
		return WRCore.spriteTItem;
	}

}
