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
package wirelessredstone.addon.remote.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import wirelessredstone.addon.remote.core.WirelessRemote;
import wirelessredstone.addon.remote.core.lib.IconLib;
import wirelessredstone.addon.remote.core.lib.ItemLib;
import wirelessredstone.core.WRCore;
import wirelessredstone.core.lib.GuiLib;
import wirelessredstone.core.lib.NBTHelper;
import wirelessredstone.core.lib.NBTLib;
import wirelessredstone.device.ItemWirelessDevice;

public class ItemRedstoneWirelessRemote extends ItemWirelessDevice {

	protected Icon[]	iconList;

	@Override
	public void registerIcons(IconRegister iconRegister) {
		iconList = new Icon[2];
		iconList[0] = iconRegister.registerIcon(IconLib.WIRELESS_REMOTE_OFF);
		iconList[1] = iconRegister.registerIcon(IconLib.WIRELESS_REMOTE_ON);
	}

	public ItemRedstoneWirelessRemote(int i) {
		super(i);
		setCreativeTab(WRCore.wirelessRedstone);
		maxStackSize = 1;
	}

	//
	// @Override
	// public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer,
	// World world, int i, int j, int k, int l, float a, float b, float c) {
	// return true;
	// }
	//
	// @Override
	// public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer
	// entityplayer, World world, int i, int j, int k, int l, float a, float b,
	// float c) {
	// if (entityplayer.isSneaking()) {
	// TileEntity tileentity = world.getBlockTileEntity( i,
	// j,
	// k);
	// if (tileentity != null) {
	// if (tileentity instanceof TileEntityRedstoneWirelessR) {
	// if (world.isRemote) {
	// ClientRedstoneEtherPacketHandler.sendRedstoneEtherPacket(
	// "updateReceiver",
	// ((TileEntityRedstoneWirelessR) tileentity).getBlockCoord(0),
	// ((TileEntityRedstoneWirelessR) tileentity).getBlockCoord(1),
	// ((TileEntityRedstoneWirelessR) tileentity).getBlockCoord(2),
	// 0,
	// false);
	// }
	// return true;
	// }
	// }
	// } else {
	// // WirelessRemote.proxy.activateRemote(world,
	// // entityplayer);
	// }
	// return false;
	// }

	@Override
	public int getMaxItemUseDuration(ItemStack itemstack) {
		return 32000;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.none;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (entityplayer.isSneaking()) {
			entityplayer.openGui(	WirelessRemote.instance,
									GuiLib.GUIID_DEVICE,
									world,
									(int) Math.floor(entityplayer.posX),
									(int) Math.floor(entityplayer.posY),
									(int) Math.floor(entityplayer.posZ));
		} else {
			entityplayer.setItemInUse(	itemstack,
										this.getMaxItemUseDuration(itemstack));
		}
		return itemstack;
	}

	@Override
	public void onUsingItemTick(ItemStack itemstack, EntityPlayer player, int count) {
		WirelessRemote.proxy.activateRemote(player.getEntityWorld(),
											player,
											itemstack);
		if (!player.worldObj.isRemote) {
			if (!getState(itemstack)) {
				setState(	itemstack,
							true);
			}
		}
	}

	public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityplayer, int itemInUseCount) {
		WirelessRemote.proxy.deactivateRemote(	world,
												entityplayer,
												itemstack);
		if (!world.isRemote) {
			setState(	itemstack,
						false);
		}
	}

	@Override
	public boolean isFull3D() {
		return true;
	}

	@Override
	public Icon getIcon(ItemStack itemstack, int pass) {
		if (!getState(itemstack)) return iconList[0];
		return iconList[1];
	}

	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean isHeld) {
		if (ItemLib.isWirelessRemote(itemstack)) {
			if (!itemstack.hasTagCompound()) {
				itemstack.stackTagCompound = new NBTTagCompound();
				getFreq(itemstack);
				getState(itemstack);
			}
		}
		if (entity instanceof EntityLivingBase) {
			EntityLivingBase entityliving = (EntityLivingBase) entity;
			String freq = getFreq(itemstack);
			if (!isHeld) {
				WirelessRemote.proxy.deactivateRemote(	world,
														entityliving,
														itemstack);
				if (!world.isRemote) {
					setState(	itemstack,
								false);
				}
			}
		}
	}

	public static String getFreq(ItemStack itemstack) {
		if (ItemLib.isWirelessRemote(itemstack)) {
			return NBTHelper.getString(	itemstack,
										NBTLib.FREQUENCY,
										"0");
		}
		return "0";
	}

	public static boolean getState(ItemStack itemstack) {
		if (ItemLib.isWirelessRemote(itemstack)) {
			return NBTHelper.getBoolean(itemstack,
										NBTLib.STATE,
										false);
		}
		return false;
	}

	public static void setFreq(ItemStack itemstack, Object freq) {
		if (ItemLib.isWirelessRemote(itemstack)) {
			NBTHelper.setString(itemstack,
								NBTLib.FREQUENCY,
								String.valueOf(freq));
		}
	}

	public static void setState(ItemStack itemstack, boolean state) {
		if (ItemLib.isWirelessRemote(itemstack)) {
			NBTHelper.setBoolean(	itemstack,
									NBTLib.STATE,
									state);
		}
	}
}
