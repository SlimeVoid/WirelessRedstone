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

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import wirelessredstone.addon.camouflager.core.WirelessCamouflager;
import wirelessredstone.addon.camouflager.core.lib.IconLib;
import wirelessredstone.core.lib.GuiLib;

public class ItemRedstoneWirelessCamouflager extends Item {

	protected Icon[]	iconList;

	@Override
	public void registerIcons(IconRegister iconRegister) {
		this.iconList = new Icon[1];
		this.iconList[0] = iconRegister.registerIcon(IconLib.CAMOUFLAGER);
	}

	public ItemRedstoneWirelessCamouflager(int i) {
		super(i);
		this.setCreativeTab(CreativeTabs.tabRedstone);
		maxStackSize = 1;
		setMaxDamage(64);
	}

	@Override
	// ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World,
	// int par4, int par5, int par6, int par7, float par8, float par9, float
	// par10
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float a, float b, float c) {
		TileEntity tileentity = world.getBlockTileEntity(	i,
															j,
															k);

		if (tileentity != null) {
			entityplayer.openGui(	WirelessCamouflager.instance,
									GuiLib.GUIID_DEVICE,
									world,
									i,
									j,
									k);// PowerConfigurator.openGUI(world,
										// entityplayer, tileentity);
			// TODO :: Open GUI
			itemstack.damageItem(	1,
									entityplayer);
			return true;
		}
		return false;
	}

	public Icon getIconFromDamage(int i) {
		return this.iconList[0];
	}

	public boolean isFull3D() {
		return true;
	}
}
