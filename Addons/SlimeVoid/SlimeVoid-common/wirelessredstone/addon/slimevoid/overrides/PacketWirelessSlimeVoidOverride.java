package wirelessredstone.addon.slimevoid.overrides;

import com.slimevoid.library.util.helpers.SlimevoidHelper;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import wirelessredstone.api.IPacketWirelessOverride;

public class PacketWirelessSlimeVoidOverride implements IPacketWirelessOverride {

    @Override
    public boolean shouldSkipDefault() {
        return false;
    }

    @Override
    public TileEntity getTarget(World world, int xPosition, int yPosition, int zPosition, TileEntity tileentity) {
        if (tileentity == null) {
            return SlimevoidHelper.getBlockTileEntity(world,
                                                      xPosition,
                                                      yPosition,
                                                      zPosition);
        }
        return tileentity;
    }

}
