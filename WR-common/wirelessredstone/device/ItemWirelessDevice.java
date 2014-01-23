package wirelessredstone.device;

import net.minecraft.item.Item;

public class ItemWirelessDevice extends Item {

	public ItemWirelessDevice(int itemID) {
		super(itemID);
	}

	@Override
	public boolean getShareTag() {
		return true;
	}
}
