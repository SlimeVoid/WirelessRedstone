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
package net.slimevoid.wirelessredstone.tileentity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.slimevoid.wirelessredstone.api.IRedstoneWirelessData;
import net.slimevoid.wirelessredstone.api.ITileEntityRedstoneWirelessOverride;
import net.slimevoid.wirelessredstone.api.IWirelessData;
import net.slimevoid.wirelessredstone.block.BlockRedstoneWireless;
import net.slimevoid.wirelessredstone.data.LoggerRedstoneWireless;

public abstract class TileEntityRedstoneWireless extends TileEntity implements
        IInventory, IUpdatePlayerListBox {
    protected BlockRedstoneWireless                            blockRedstoneWireless;
    //private boolean                                            state     = false;
    public boolean                                             firstTick = true;
    public Object                                              oldFreq;
    public Object                                              currentFreq;
    protected boolean[]                                        powerRoute;
    protected boolean[]                                        indirPower;
    public HashMap<String, IRedstoneWirelessData>              tileData  = new HashMap<String, IRedstoneWirelessData>();
    protected static List<ITileEntityRedstoneWirelessOverride> overrides = new ArrayList<ITileEntityRedstoneWirelessOverride>();

    public TileEntityRedstoneWireless(BlockRedstoneWireless block) {
        firstTick = true;
        oldFreq = "";
        currentFreq = "0";
        firstTick = false;
        blockRedstoneWireless = block;
        flushPowerRoute();
        flushIndirPower();
    }

    public static void addOverride(ITileEntityRedstoneWirelessOverride override) {
        overrides.add(override);
    }

    @Override
    public int getSizeInventory() {
        return 0;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return this.getRedstoneWirelessStackInSlot(i);
    }

    protected ItemStack getRedstoneWirelessStackInSlot(int i) {
        ItemStack itemstack = null;
        for (ITileEntityRedstoneWirelessOverride override : overrides) {
            if (override.handleInventory()) {
                itemstack = override.getStackInSlot(this,
                                                    i,
                                                    itemstack);
            }
        }
        return itemstack;
    }

    public Object getFreq() {
        return currentFreq;
    }

    public void setFreq(Object freq) {
        try {
            currentFreq = freq;
            update();
        } catch (Exception e) {
            LoggerRedstoneWireless.getInstance("WirelessRedstone: "
                                               + this.getClass().toString()).writeStackTrace(e);
        }
    }

    public int getBlockCoord(int i) {
        switch (i) {
        case 0:
            return this.pos.getX();
        case 1:
            return this.pos.getY();
        case 2:
            return this.pos.getZ();
        default:
            return 0;
        }
    }

    @Override
    public void update() {
        boolean prematureExit = false;
        for (ITileEntityRedstoneWirelessOverride override : overrides) {
            if (override.beforeUpdateEntity(this)) prematureExit = true;
        }

        if (!prematureExit) {
            String freq = getFreq().toString();
            if (!oldFreq.equals(freq) || firstTick) {
                blockRedstoneWireless.changeFreq(worldObj,
                                                 pos,
                                                 oldFreq,
                                                 freq);
                oldFreq = freq;
                if (firstTick) firstTick = false;
            }
            onUpdateEntity();
        }

        for (ITileEntityRedstoneWirelessOverride override : overrides) {
            override.afterUpdateEntity(this);
        }
    }

    protected abstract void onUpdateEntity();

    @Override
    public ItemStack decrStackSize(int i, int j) {
        ItemStack itemstack = null;
        for (ITileEntityRedstoneWirelessOverride override : overrides) {
            if (override.handleInventory()) {
                itemstack = override.decrStackSize(this,
                                                   i,
                                                   j,
                                                   itemstack);
            }
        }
        return itemstack;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        this.setRedstoneWirelessSlotContents(i,
                                             itemstack);
    }

    protected void setRedstoneWirelessSlotContents(int slot, ItemStack itemstack) {
        boolean prematureExit = false;
        for (ITileEntityRedstoneWirelessOverride override : overrides) {
            if (override.handleInventory()) {
                if (override.setInventorySlotContents(this,
                                                      slot,
                                                      itemstack)) {
                    prematureExit = true;
                }
            }
        }
    }

    @Override
    public abstract String getName();

    public boolean isPoweringDirection(EnumFacing side) {
        return side.getIndex() < 6 && powerRoute[side.getIndex()];
    }

    public void flipPowerDirection(EnumFacing side) {
        if (isPoweringIndirectly(side) && powerRoute[side.getIndex()]) flipIndirectPower(side);
        powerRoute[side.getIndex()] = !powerRoute[side.getIndex()];
        this.markDirty();
        this.notifyNeighbors();
    }

    public void flushPowerRoute() {
        powerRoute = new boolean[6];
        for (int i = 0; i < powerRoute.length; i++) {
            powerRoute[i] = true;
        }
    }

    /**
     * Retrieves the directions the Wireless Tile Entity is powering
     * 
     * @return powerRoute
     */
    public boolean[] getPowerDirections() {
        return powerRoute;
    }

    /**
     * Retrieves the directions the Wireless Tile Entity is indirectly powering
     * 
     * @return Indirect Powering Sides
     */
    public boolean[] getInDirectlyPowering() {
        return indirPower;
    }

    /**
     * Sets the directions the Wireless Tile Entity is powering
     * 
     * @param dir
     *            is the new set of directions
     */
    public void setPowerDirections(boolean[] dir) {
        try {
            powerRoute = dir;
        } catch (Exception e) {
            LoggerRedstoneWireless.getInstance("TileEntityRedstoneWireless").writeStackTrace(e);
        }
    }

    /**
     * Sets the directions the Wireless Tile Entity is powering
     * 
     * @param indir
     *            is the new set of directions
     */
    public void setInDirectlyPowering(boolean[] indir) {
        try {
            indirPower = indir;
        } catch (Exception e) {
            LoggerRedstoneWireless.getInstance("TileEntityRedstoneWireless").writeStackTrace(e);
        }
    }

    public void flipIndirectPower(EnumFacing side) {
        if (!isPoweringDirection(side) && !indirPower[side.getIndex()]) flipPowerDirection(side);
        indirPower[side.getIndex()] = !indirPower[side.getIndex()];
        this.markDirty();
        this.notifyNeighbors();
    }

    public boolean isPoweringIndirectly(EnumFacing side) {
        return side.getIndex() < 6 && indirPower[side.getIndex()];
    }

    public void flushIndirPower() {
        indirPower = new boolean[6];
        for (int i = 0; i < indirPower.length; i++) {
            indirPower[i] = true;
        }
    }

    private void notifyNeighbors() {
        BlockRedstoneWireless.notifyNeighbors(this.worldObj,
                                              this.pos,
                                              this.getBlockType());
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        try {
            super.readFromNBT(nbttagcompound);

            NBTTagList nbttaglist3 = nbttagcompound.getTagList("Frequency",
                                                               10);
            NBTTagCompound nbttagcompound3 = nbttaglist3.getCompoundTagAt(0);
            currentFreq = nbttagcompound3.getString("freq");

            NBTTagList nbttaglist1 = nbttagcompound.getTagList("PowerRoute",
                                                               10);
            if (nbttaglist1.tagCount() == 6) {
                for (int i = 0; i < nbttaglist1.tagCount(); i++) {
                    NBTTagCompound nbttagcompound1 = nbttaglist1.getCompoundTagAt(i);
                    powerRoute[i] = nbttagcompound1.getBoolean("b");
                }
            } else {
                flushPowerRoute();
                writeToNBT(nbttagcompound);
            }

            NBTTagList nbttaglist4 = nbttagcompound.getTagList("IndirPower",
                                                               10);
            if (nbttaglist4.tagCount() == 6) {
                for (int i = 0; i < nbttaglist4.tagCount(); i++) {
                    NBTTagCompound nbttagcompound1 = nbttaglist4.getCompoundTagAt(i);
                    indirPower[i] = nbttagcompound1.getBoolean("b");
                }
            } else {
                flushIndirPower();
                writeToNBT(nbttagcompound);
            }
            //state = nbttagcompound.getBoolean("State");
            for (ITileEntityRedstoneWirelessOverride override : overrides) {
                if (override.handlesExtraNBTTags()) {
                    override.readFromNBT(this,
                                         nbttagcompound);
                }
            }

        } catch (Exception e) {
            LoggerRedstoneWireless.getInstance("TileEntityRedstoneWireless").writeStackTrace(e);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        try {
            super.writeToNBT(nbttagcompound);

            NBTTagList nbttaglist3 = new NBTTagList();
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
            nbttagcompound1.setString("freq",
                                      currentFreq.toString());
            nbttaglist3.appendTag(nbttagcompound1);
            nbttagcompound.setTag("Frequency",
                                  nbttaglist3);

            NBTTagList nbttaglist2 = new NBTTagList();
            for (boolean route : powerRoute) {
                NBTTagCompound nbttagcompound2 = new NBTTagCompound();
                nbttagcompound2.setBoolean("b",
                                           route);
                nbttaglist2.appendTag(nbttagcompound2);
            }
            nbttagcompound.setTag("PowerRoute",
                                  nbttaglist2);

            NBTTagList nbttaglist4 = new NBTTagList();
            for (boolean power : indirPower) {
                NBTTagCompound nbttagcompound2 = new NBTTagCompound();
                nbttagcompound2.setBoolean("b",
                                           power);
                nbttaglist4.appendTag(nbttagcompound2);
            }
            nbttagcompound.setTag("IndirPower",
                                  nbttaglist4);
            //nbttagcompound.setBoolean("State",
            //                          state);
            for (ITileEntityRedstoneWirelessOverride override : overrides) {
                if (override.handlesExtraNBTTags()) {
                    override.writeToNBT(this,
                                        nbttagcompound);
                }
            }

        } catch (Exception e) {
            LoggerRedstoneWireless.getInstance("TileEntityRedstoneWireless").writeStackTrace(e);
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        LoggerRedstoneWireless.getInstance(LoggerRedstoneWireless.filterClassName(this.getClass().toString())).write(this.worldObj.isRemote,
                                                                                                                     "isUseableByPlayer("
                                                                                                                             + entityplayer.getDisplayName()
                                                                                                                             + ")",
                                                                                                                     LoggerRedstoneWireless.LogLevel.DEBUG);

        // Run before overrides.
        boolean prematureExit = false;
        for (ITileEntityRedstoneWirelessOverride override : overrides) {
            if (override.beforeIsUseableByPlayer(this,
                                                 entityplayer)) prematureExit = true;
        }

        // If premature exit was not given, set the initial return state.
        boolean returnState = false;
        if (!prematureExit) {
            try {
                returnState = this.isTileRedstoneWirelessUseable(entityplayer);
            } catch (Exception e) {
                LoggerRedstoneWireless.getInstance(LoggerRedstoneWireless.filterClassName(this.getClass().toString())).writeStackTrace(e);
                return false;
            }
        }

        boolean output = returnState;
        // Run overrides.
        for (ITileEntityRedstoneWirelessOverride override : overrides) {
            output = override.afterIsUseableByPlayer(this,
                                                     entityplayer,
                                                     output);
        }
        return output;

    }

    private boolean isTileRedstoneWirelessUseable(EntityPlayer entityplayer) {
        return worldObj.getTileEntity(this.pos) == this &&
                entityplayer.getDistanceSq(this.pos.getX() + 0.5D,
                                          this.pos.getY() + 0.5D,
                                          this.pos.getZ() + 0.5D) <= 64D;
    }

    @Override
    public void openInventory(EntityPlayer entityplayer) {
    }

    @Override
    public void closeInventory(EntityPlayer entityplayer) {
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        ItemStack itemstack = null;
        for (ITileEntityRedstoneWirelessOverride override : overrides) {
            if (override.handleInventory()) {
                itemstack = override.getStackInSlotOnClosing(this,
                                                             i,
                                                             itemstack);
            }
        }
        return itemstack;
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound());
        this.markDirty();
        this.getWorld().markBlockForUpdate(this.pos);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(this.pos, 0, nbttagcompound);
    }

    public void handleData(IWirelessData data) {
        boolean prematureExit = false;

        for (ITileEntityRedstoneWirelessOverride override : overrides) {
            if (override.beforeHandleData(this,
                                          data)) {
                prematureExit = true;
            }
        }
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    protected boolean isRedstoneWirelessStackValidForSlot(int slot, ItemStack itemstack) {
        boolean result = false;
        for (ITileEntityRedstoneWirelessOverride override : overrides) {
            if (override.handleInventory()) {
                result = override.isStackValidForSlot(this,
                                                      slot,
                                                      itemstack,
                                                      result);
            }
        }
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
        return this.isRedstoneWirelessStackValidForSlot(slot,
                                                        itemstack);
    }

    public void onBlockRemoval(Block block, int metadata) {
        for (ITileEntityRedstoneWirelessOverride override : overrides) {
            override.onBlockRemoval(this,
                                    block,
                                    metadata);
        }
    }

    public void setAdditionalData(String dataID, IRedstoneWirelessData data) {
        this.tileData.put(dataID,
                          data);
    }

    public IRedstoneWirelessData getAdditionalData(String dataID) {
        if (this.tileData.containsKey(dataID)) {
            return this.tileData.get(dataID);
        } else {
            return null;
        }
    }

    /*public void setState(boolean state) {
        this.state = state;
    }*/

    /*public boolean getState() {
        return this.state;
    }*/
    
    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
    	return !oldState.getBlock().isAssociatedBlock(oldState.getBlock());
    }

	@Override
	public int getField(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFieldCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IChatComponent getDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}
}
