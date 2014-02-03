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

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import wirelessredstone.addon.remote.core.WirelessRemote;
import wirelessredstone.addon.remote.core.lib.IconLib;
import wirelessredstone.addon.remote.core.lib.ItemLib;
import wirelessredstone.addon.remote.network.packets.PacketRemoteCommands;
import wirelessredstone.client.network.handlers.ClientRedstoneEtherPacketHandler;
import wirelessredstone.core.lib.GuiLib;
import wirelessredstone.device.ItemWirelessDevice;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessR;

public class ItemRedstoneWirelessRemote extends ItemWirelessDevice {

    public ItemRedstoneWirelessRemote(int itemID) {
        super(itemID);
    }

    @Override
    protected void registerIconList(IconRegister iconRegister) {
        this.iconList[0] = iconRegister.registerIcon(IconLib.WIRELESS_REMOTE_OFF);
        this.iconList[1] = iconRegister.registerIcon(IconLib.WIRELESS_REMOTE_ON);
    }

    @Override
    protected boolean isValidDevice(ItemStack itemstack) {
        return ItemLib.isWirelessRemote(itemstack);
    }

    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float a, float b, float c) {
        return this.onItemUseFirst(itemstack,
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
        if (entityplayer.isSneaking()) {
            TileEntity tileentity = world.getBlockTileEntity(i,
                                                             j,
                                                             k);
            if (tileentity != null) {
                if (tileentity instanceof TileEntityRedstoneWirelessR) {
                    if (world.isRemote) {
                        ClientRedstoneEtherPacketHandler.sendRedstoneEtherPacket(PacketRemoteCommands.remoteCommands.updateReceiver.toString(),
                                                                                 ((TileEntityRedstoneWirelessR) tileentity).getBlockCoord(0),
                                                                                 ((TileEntityRedstoneWirelessR) tileentity).getBlockCoord(1),
                                                                                 ((TileEntityRedstoneWirelessR) tileentity).getBlockCoord(2),
                                                                                 this.getFreq(itemstack),
                                                                                 false);
                    }
                    return true;
                }
            }

            entityplayer.openGui(WirelessRemote.instance,
                                 GuiLib.GUIID_DEVICE,
                                 world,
                                 (int) Math.round(entityplayer.posX),
                                 (int) Math.round(entityplayer.posY),
                                 (int) Math.round(entityplayer.posZ));
            return false;
        }
        Block block = Block.blocksList[world.getBlockId(i,
                                                        j,
                                                        k)];
        if (!block.onBlockActivated(world,
                                    i,
                                    j,
                                    k,
                                    entityplayer,
                                    l,
                                    a,
                                    b,
                                    c)) {
            this.onItemRightClick(itemstack,
                                  world,
                                  entityplayer);
        }
        return false;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if (!entityplayer.isSneaking()) {
            entityplayer.setItemInUse(itemstack,
                                      this.getMaxItemUseDuration(itemstack));
        } else {
            this.onItemUseFirst(itemstack,
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
    public void onUsingItemTick(ItemStack itemstack, EntityPlayer player, int count) {
        WirelessRemote.proxy.activateRemote(player.getEntityWorld(),
                                            player,
                                            itemstack);
        if (!this.getState(itemstack)) {
            this.setState(itemstack,
                          true);
        }
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityplayer, int itemInUseCount) {
        WirelessRemote.proxy.deactivateRemote(world,
                                              entityplayer,
                                              itemstack);
        this.setState(itemstack,
                      false);
    }

    @Override
    public void onUpdateDevice(ItemStack itemstack, World world, EntityLivingBase entitylivingbase, int i, boolean isHeld) {
        if (!isHeld) {
            WirelessRemote.proxy.deactivateRemote(world,
                                                  entitylivingbase,
                                                  itemstack);
        }
    }

    @Override
    protected boolean onDeviceDroppedByPlayer(ItemStack itemstack, EntityPlayer entityplayer) {
        WirelessRemote.proxy.deactivateRemote(entityplayer.getEntityWorld(),
                                              entityplayer,
                                              itemstack);
        return true;
    }
}
