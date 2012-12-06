/*    
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package wirelessredstone.addon.remote.items;

import wirelessredstone.addon.remote.core.WRemoteCore;
import wirelessredstone.addon.remote.data.WirelessRemoteData;
import wirelessredstone.data.WirelessDeviceData;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessR;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class ItemRedstoneWirelessRemote extends Item {
	
	public ItemRedstoneWirelessRemote(int i) {
		super(i);
		maxStackSize = 1;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer,
			World world, int i, int j, int k, int l, float a, float b, float c) {
		WirelessRemoteData remote = (WirelessRemoteData) WirelessDeviceData.getDeviceData(WirelessRemoteData.class, "Wireless Remote", itemstack,
				world, entityplayer);
		if (entityplayer.isSneaking()) {
			//WirelessRemote.openGUI(world, entityplayer, remote);
			return true;
		} else {
			TileEntity tileentity = world.getBlockTileEntity(i, j, k);
			if (tileentity != null) {
				if (tileentity instanceof TileEntityRedstoneWirelessR) {
					return true;
				}
			}
		}
		this.onItemRightClick(itemstack, world, entityplayer);
		return false;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world,
			EntityPlayer entityplayer) {
		if (!entityplayer.isSneaking()) {
			//WirelessRemote.activateRemote(world, entityplayer);
		} else {
			onItemUse(itemstack, entityplayer, world,
					(int) Math.round(entityplayer.posX),
					(int) Math.round(entityplayer.posY),
					(int) Math.round(entityplayer.posZ), 0, 0, 0, 0);
		}
		return itemstack;
	}

	public boolean isFull3D() {
		return true;
	}

	public int getIconFromDamage(int i) {
		return WRemoteCore.getIconFromDamage(this.getItemName(), i);
	}

	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity,
			int i, boolean isHeld) {
		if (entity instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) entity;
			WirelessRemoteData data = (WirelessRemoteData) WirelessDeviceData.getDeviceData(WirelessRemoteData.class, "Wireless Remote", itemstack,
					world, entityplayer);
			String freq = data.getFreq();
			//if (!isHeld || !WirelessRemote.isRemoteOn(entityplayer, freq)
			//		&& !WirelessRemote.deactivateRemote(world, entityplayer)) {
			//}
		}
	}

	@Override
	public void onCreated(ItemStack itemstack, World world,
			EntityPlayer entityplayer) {
		itemstack.setItemDamage(world.getUniqueDataId(this.getItemName()));
		WirelessRemoteData data = (WirelessRemoteData)WirelessDeviceData.getDeviceData(WirelessRemoteData.class, "Wireless Remote", itemstack, world, entityplayer);
	}
	
	@Override
	public String getTextureFile() {
		return "/WirelessSprites/Remote/items.png";
	}
}
