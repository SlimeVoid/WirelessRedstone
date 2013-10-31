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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import wirelessredstone.addon.remote.core.WRemoteCore;
import wirelessredstone.addon.remote.core.lib.IconLib;
import wirelessredstone.addon.remote.data.WirelessRemoteData;
import wirelessredstone.client.network.handlers.ClientRedstoneEtherPacketHandler;
import wirelessredstone.core.WRCore;
import wirelessredstone.device.WirelessDeviceData;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessR;
import cpw.mods.fml.client.FMLClientHandler;

public class ItemRedstoneWirelessRemote extends Item {

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

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float a, float b, float c) {
		return onItemUseFirst(	itemstack,
								entityplayer,
								world,
								i,
								j,
								k,
								l,
								a,
								b,
								c);
	}

	@Override
	public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float a, float b, float c) {
		WirelessRemoteData remote = (WirelessRemoteData) WirelessDeviceData.getDeviceData(	WirelessRemoteData.class,
																							"Wireless Remote",
																							itemstack,
																							world,
																							entityplayer);
		if (entityplayer.isSneaking()) {
			TileEntity tileentity = world.getBlockTileEntity(	i,
																j,
																k);
			if (tileentity != null) {
				if (tileentity instanceof TileEntityRedstoneWirelessR) {
					if (world.isRemote) {
						ClientRedstoneEtherPacketHandler.sendRedstoneEtherPacket(	"updateReceiver",
																					((TileEntityRedstoneWirelessR) tileentity).getBlockCoord(0),
																					((TileEntityRedstoneWirelessR) tileentity).getBlockCoord(1),
																					((TileEntityRedstoneWirelessR) tileentity).getBlockCoord(2),
																					0,
																					false);
					}
					return true;
				}
			}
			WRemoteCore.proxy.activateGUI(	world,
											entityplayer,
											remote);
			return false;
		}
		this.onItemRightClick(	itemstack,
								world,
								entityplayer);
		return false;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (!entityplayer.isSneaking()) {
			entityplayer.setItemInUse(	itemstack,
										72000);
			WRemoteCore.proxy.activateRemote(	world,
												entityplayer);
		} else {
			onItemUseFirst(	itemstack,
							entityplayer,
							world,
							(int) Math.round(entityplayer.posX),
							(int) Math.round(entityplayer.posY),
							(int) Math.round(entityplayer.posZ),
							0,
							0,
							0,
							0);
		}
		return itemstack;
	}

	@Override
	public boolean isFull3D() {
		return true;
	}

	@Override
	public Icon getIconFromDamage(int i) {
		String index = this.getUnlocalizedName() + "[" + i + "]";
		WirelessRemoteData data = (WirelessRemoteData) FMLClientHandler.instance().getClient().theWorld.loadItemData(	WirelessRemoteData.class,
																														index);
		if (data == null || !data.getDeviceState()) return iconList[0];
		return iconList[1];
	}

	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean isHeld) {
		if (entity instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) entity;

			WirelessRemoteData data = (WirelessRemoteData) WirelessDeviceData.getDeviceData(WirelessRemoteData.class,
																							"Wireless Remote",
																							itemstack,
																							world,
																							entityplayer);
			String freq = data.getDeviceFreq();
			if (!isHeld
				|| (!WRemoteCore.proxy.isRemoteOn(	world,
													entityplayer,
													freq) && !WRemoteCore.proxy.deactivateRemote(	world,
																									entityplayer))) {
			}
		}
	}

	@Override
	public void onCreated(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		itemstack.setItemDamage(world.getUniqueDataId(this.getUnlocalizedName()));
		WirelessRemoteData data = (WirelessRemoteData) WirelessDeviceData.getDeviceData(WirelessRemoteData.class,
																						"Wireless Remote",
																						itemstack,
																						world,
																						entityplayer);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.bow;
	}
}
