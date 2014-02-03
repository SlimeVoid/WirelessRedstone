package wirelessredstone.addon.remote.inventory;

import net.minecraft.entity.player.EntityPlayer;
import wirelessredstone.api.IWirelessDevice;
import wirelessredstone.inventory.ContainerRedstoneDevice;

public class ContainerWirelessRemote extends ContainerRedstoneDevice {

    public ContainerWirelessRemote(IWirelessDevice device) {
        super(device);
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return true;
    }

}
