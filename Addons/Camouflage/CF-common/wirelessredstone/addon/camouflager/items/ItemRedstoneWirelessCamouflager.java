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
package wirelessredstone.addon.camouflager.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import wirelessredstone.addon.camouflager.core.WirelessCamouflager;
import wirelessredstone.core.WRCore;
import wirelessredstone.core.lib.GuiLib;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;

public class ItemRedstoneWirelessCamouflager extends Item {

    public ItemRedstoneWirelessCamouflager(int i) {
        super(i);
        setCreativeTab(WRCore.wirelessRedstone);
        maxStackSize = 1;
        setMaxDamage(64);
    }

    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float a, float b, float c) {
        TileEntity tileentity = world.getBlockTileEntity(i,
                                                         j,
                                                         k);

        if (tileentity != null
            && tileentity instanceof TileEntityRedstoneWireless) {
            entityplayer.openGui(WirelessCamouflager.instance,
                                 GuiLib.GUIID_DEVICE,
                                 world,
                                 i,
                                 j,
                                 k);
            itemstack.damageItem(1,
                                 entityplayer);
            return true;
        }
        return false;
    }

    @Override
    public boolean isFull3D() {
        return true;
    }
}
