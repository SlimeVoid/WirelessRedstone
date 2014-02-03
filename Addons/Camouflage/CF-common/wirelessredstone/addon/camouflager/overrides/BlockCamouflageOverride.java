/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version. This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details. You should have received a copy of the GNU
 * Lesser General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>
 */
package wirelessredstone.addon.camouflager.overrides;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import wirelessredstone.addon.camouflager.core.CamouCore;
import wirelessredstone.addon.camouflager.core.lib.CamouLib;
import wirelessredstone.api.IBlockRedstoneWirelessOverride;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;

public class BlockCamouflageOverride implements IBlockRedstoneWirelessOverride {

    @Override
    public boolean beforeBlockRedstoneWirelessAdded(World world, int i, int j, int k) {
        return (world.isRemote);
    }

    @Override
    public void afterBlockRedstoneWirelessAdded(World world, int i, int j, int k) {
    }

    @Override
    public boolean beforeBlockRedstoneWirelessRemoved(World world, int i, int j, int k, int l, int m) {
        TileEntity tileentity = world.getBlockTileEntity(i,
                                                         j,
                                                         k);
        if (tileentity instanceof TileEntityRedstoneWireless) {
            ((TileEntityRedstoneWireless) tileentity).onBlockRemoval(l,
                                                                     m);
        }
        return (world.isRemote);
    }

    @Override
    public void afterBlockRedstoneWirelessRemoved(World world, int i, int j, int k) {
    }

    @Override
    public boolean beforeBlockRedstoneWirelessActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
        if (entityplayer.getCurrentEquippedItem() != null
            && entityplayer.getCurrentEquippedItem().itemID == CamouCore.itemCamouflager.itemID) {
            return true;
        }

        return false;
    }

    @Override
    public void afterBlockRedstoneWirelessActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
    }

    @Override
    public boolean beforeBlockRedstoneWirelessNeighborChange(World world, int i, int j, int k, int l) {
        return (world.isRemote);
    }

    @Override
    public void afterBlockRedstoneWirelessNeighborChange(World world, int i, int j, int k, int l) {
    }

    @Override
    public boolean beforeUpdateRedstoneWirelessTick(World world, int i, int j, int k, Random random) {
        return (world.isRemote);
    }

    @Override
    public void afterUpdateRedstoneWirelessTick(World world, int i, int j, int k, Random random) {
    }

    @Override
    public boolean shouldOverrideTextureAt(IBlockAccess iblockaccess, int i, int j, int k, int side) {
        TileEntity tileentity = iblockaccess.getBlockTileEntity(i,
                                                                j,
                                                                k);
        if (tileentity != null
            && tileentity instanceof TileEntityRedstoneWireless) {
            return true;
        }
        return false;
    }

    @Override
    public Icon getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int side, Icon output) {
        TileEntity tileentity = iblockaccess.getBlockTileEntity(i,
                                                                j,
                                                                k);
        if (tileentity != null
            && tileentity instanceof TileEntityRedstoneWireless) {
            TileEntityRedstoneWireless tRW = (TileEntityRedstoneWireless) tileentity;
            output = CamouLib.getIconForTile(tRW,
                                             side,
                                             output);
        }
        return output;
    }
}
