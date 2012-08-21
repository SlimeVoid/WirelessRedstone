package wirelessredstone.block;

import wirelessredstone.core.WRCore;

public class BlockItemRedstoneWirelessR extends BlockItemRedstoneWireless {

	public BlockItemRedstoneWirelessR(int par1) {
		super(par1);
	}

	@Override
	protected int getRedstoneWirelessItemIndex() {
		return WRCore.spriteRItem;
	}

}
