package wirelessredstone.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

public class ContainerRedstoneWireless extends Container {

	public TileEntityRedstoneWireless	redstoneWireless;

	public ContainerRedstoneWireless(TileEntity tileentity) {
		super();
		if (tileentity instanceof TileEntityRedstoneWireless) {
			this.redstoneWireless = (TileEntityRedstoneWireless) tileentity;
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}

}
