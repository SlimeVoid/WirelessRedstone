package wirelessredstone.core.lib;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;

public class BlockLib {

    public static final String WIRELESS_PREFIX          = "wirelessredstone.";

    public static final String WIRELESS_TRANSMITTER     = WIRELESS_PREFIX
                                                          + "transmitter";
    public static final String WIRELESS_RECEIVER        = WIRELESS_PREFIX
                                                          + "receiver";

    public static final String WIRELESS_RECEIVER_ALT    = "Wireless Receiver";
    public static final String WIRELESS_TRANSMITTER_ALT = "Wireless Transmitter";

    public static TileEntityRedstoneWireless getBlockTileEntity(World world, int x, int y, int z) {
        TileEntity tileentity = world.getBlockTileEntity(x,
                                                         y,
                                                         z);
        if (tileentity != null
            && tileentity instanceof TileEntityRedstoneWireless) {
            return (TileEntityRedstoneWireless) tileentity;
        }
        return null;
    }

}
