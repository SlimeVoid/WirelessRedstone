package wirelessredstone.device;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import wirelessredstone.core.WRCore;

public abstract class ItemWirelessDevice extends Item {

	protected Icon[]	iconList;

	public ItemWirelessDevice(int itemID) {
		super(itemID);
		this.setCreativeTab(WRCore.wirelessRedstone);
	}

	@Override
	public void registerIcons(IconRegister iconRegister) {
		this.registerIconList(iconRegister);
	}

	protected abstract void registerIconList(IconRegister iconRegister);

	@Override
	public Icon getIcon(ItemStack itemstack, int pass) {
		if (!getState(itemstack)) return iconList[0];
		return iconList[1];
	}

	@Override
	public boolean onDroppedByPlayer(ItemStack itemstack, EntityPlayer entityplayer) {
		this.setState(	itemstack,
						false);
		this.doDroppedByPlayer(	itemstack,
								entityplayer);
		return super.onDroppedByPlayer(	itemstack,
										entityplayer);
	}

	protected abstract void doDroppedByPlayer(ItemStack itemstack, EntityPlayer entityplayer);

	public abstract boolean getState(ItemStack itemstack);

	public abstract Object getFreq(ItemStack itemstack);

	public abstract void setState(ItemStack itemstack, boolean state);

	public abstract void setFreq(ItemStack itemstack, Object freq);

	@Override
	public boolean getShareTag() {
		return true;
	}

	@Override
	public boolean isFull3D() {
		return true;
	}
}
