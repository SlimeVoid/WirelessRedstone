package wirelessredstone.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;

public class ContainerRedstoneWireless extends Container {

    public IInventory redstoneWireless;

    public ContainerRedstoneWireless(IInventory inventory) {
        super();
        this.redstoneWireless = inventory;
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return true;
    }

}
