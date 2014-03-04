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
package wirelessredstone.client.overrides;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import wirelessredstone.api.ITileEntityRedstoneWirelessOverride;
import wirelessredstone.api.IWirelessData;
import wirelessredstone.network.packets.PacketWirelessTile;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessR;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessT;

public class TileEntityRedstoneWirelessOverrideSMP implements
        ITileEntityRedstoneWirelessOverride {
    @Override
    public boolean beforeUpdateEntity(TileEntityRedstoneWireless tileentity) {
        return tileentity.getWorldObj().isRemote;
    }

    @Override
    public void afterUpdateEntity(TileEntityRedstoneWireless tileentity) {
    }

    @Override
    public boolean beforeHandleData(TileEntityRedstoneWireless tileentityredstonewireless, IWirelessData data) {
        if (data != null && tileentityredstonewireless != null) {
            if (data instanceof PacketWirelessTile) {
                PacketWirelessTile packetData = (PacketWirelessTile) data;
                if (tileentityredstonewireless instanceof TileEntityRedstoneWirelessT) {
                    TileEntityRedstoneWirelessT teRWT = (TileEntityRedstoneWirelessT) tileentityredstonewireless;
                    teRWT.setFreq(packetData.getFreq().toString());
                    teRWT.markDirty();
                    teRWT.getWorldObj().markBlockForUpdate(packetData.xPosition,
                                                           packetData.yPosition,
                                                           packetData.zPosition);
                }

                if (tileentityredstonewireless instanceof TileEntityRedstoneWirelessR) {
                    TileEntityRedstoneWirelessR teRWR = (TileEntityRedstoneWirelessR) tileentityredstonewireless;
                    teRWR.setFreq(packetData.getFreq().toString());
                    teRWR.setInDirectlyPowering(packetData.getInDirectlyPowering());
                    teRWR.setPowerDirections(packetData.getPowerDirections());
                    teRWR.markDirty();
                    teRWR.getWorldObj().markBlockForUpdate(packetData.xPosition,
                                                           packetData.yPosition,
                                                           packetData.zPosition);
                }
            }
        }
        return false;
    }

    @Override
    public boolean beforeIsUseableByPlayer(TileEntityRedstoneWireless tileEntityRedstoneWireless, EntityPlayer entityplayer) {
        return false;
    }

    @Override
    public boolean afterIsUseableByPlayer(TileEntityRedstoneWireless tileEntityRedstoneWireless, EntityPlayer entityplayer, boolean returnState) {
        return returnState;
    }

    @Override
    public boolean handlesExtraNBTTags() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void writeToNBT(TileEntityRedstoneWireless tRW, NBTTagCompound nbttagcompound) {
    }

    @Override
    public void readFromNBT(TileEntityRedstoneWireless tileEntityRedstoneWireless, NBTTagCompound nbttagcompound) {
    }

    @Override
    public boolean handleInventory() {
        return false;
    }

    @Override
    public ItemStack getStackInSlot(TileEntityRedstoneWireless tileEntityRedstoneWireless, int i, ItemStack itemstack) {
        return itemstack;
    }

    @Override
    public ItemStack decrStackSize(TileEntityRedstoneWireless tileEntityRedstoneWireless, int i, int j, ItemStack itemstack) {
        return itemstack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(TileEntityRedstoneWireless tileEntityRedstoneWireless, int i, ItemStack itemstack) {
        return itemstack;
    }

    @Override
    public boolean setInventorySlotContents(TileEntityRedstoneWireless tileEntityRedstoneWireless, int slot, ItemStack itemstack) {
        return false;
    }

    @Override
    public boolean isStackValidForSlot(TileEntityRedstoneWireless tileEntityRedstoneWireless, int slot, ItemStack itemstack, boolean result) {
        return result;
    }

    @Override
    public void onBlockRemoval(TileEntityRedstoneWireless tileEntityRedstoneWireless, Block block, int metadata) {
    }
}