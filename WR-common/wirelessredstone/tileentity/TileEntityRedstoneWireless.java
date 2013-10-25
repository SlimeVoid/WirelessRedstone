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
package wirelessredstone.tileentity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import wirelessredstone.api.IRedstoneWirelessData;
import wirelessredstone.api.ITileEntityRedstoneWirelessOverride;
import wirelessredstone.block.BlockRedstoneWireless;
import wirelessredstone.data.LoggerRedstoneWireless;

public abstract class TileEntityRedstoneWireless extends TileEntity implements
		IInventory {
	protected BlockRedstoneWireless								blockRedstoneWireless;
	public boolean												firstTick	= true;
	public String												oldFreq;
	public String												currentFreq;
	protected boolean[]											powerRoute;
	protected boolean[]											indirPower;
	public ItemStack											reference;
	protected static List<ITileEntityRedstoneWirelessOverride>	overrides	= new ArrayList();

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
		ItemStack itemstack = this.reference;
		for (ITileEntityRedstoneWirelessOverride override : overrides) {
			if (override.handleInventory()) {
				itemstack = override.getStackInSlot(this,
													i,
													itemstack);
			}
		}
		return itemstack;
	}

	public String getFreq() {
		return currentFreq;
	}

	public void setFreq(String i) {
		try {
			currentFreq = i;
			updateEntity();
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone: "
												+ this.getClass().toString()).writeStackTrace(e);
		}
	}

	public int getBlockCoord(int i) {
		switch (i) {
		case 0:
			return this.xCoord;
		case 1:
			return this.yCoord;
		case 2:
			return this.zCoord;
		default:
			return 0;
		}
	}

	@Override
	public void updateEntity() {
		boolean prematureExit = false;
		for (ITileEntityRedstoneWirelessOverride override : overrides) {
			if (override.beforeUpdateEntity(this)) prematureExit = true;
		}

		if (!prematureExit) {
			String freq = getFreq().toString();
			if (!oldFreq.equals(freq) || firstTick) {
				blockRedstoneWireless.changeFreq(	worldObj,
													getBlockCoord(0),
													getBlockCoord(1),
													getBlockCoord(2),
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
				itemstack = override.decrStackSize(	this,
													i,
													j);
			}
		}
		return itemstack;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		onInventoryChanged();
	}

	@Override
	public abstract String getInvName();

	public boolean isPoweringDirection(int l) {
		if (l < 6) return powerRoute[l];
		else return false;
	}

	public void flipPowerDirection(int l) {
		if (isPoweringIndirectly(l) && powerRoute[l]) flipIndirectPower(l);
		powerRoute[l] = !powerRoute[l];
		this.onInventoryChanged();
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
	 * @return
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

	public void flipIndirectPower(int l) {
		if (!isPoweringDirection(l) && !indirPower[l]) flipPowerDirection(l);
		indirPower[l] = !indirPower[l];
		this.onInventoryChanged();
		this.notifyNeighbors();
	}

	public boolean isPoweringIndirectly(int l) {
		if (l < 6) return indirPower[l];
		else return false;
	}

	public void flushIndirPower() {
		indirPower = new boolean[6];
		for (int i = 0; i < indirPower.length; i++) {
			indirPower[i] = true;
		}
	}

	private void notifyNeighbors() {
		int i = this.getBlockCoord(0);
		int j = this.getBlockCoord(1);
		int k = this.getBlockCoord(2);
		BlockRedstoneWireless.notifyNeighbors(	this.worldObj,
												i,
												j,
												k);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		try {
			super.readFromNBT(nbttagcompound);

			NBTTagList nbttaglist3 = nbttagcompound.getTagList("Frequency");
			NBTTagCompound nbttagcompound3 = (NBTTagCompound) nbttaglist3.tagAt(0);
			currentFreq = nbttagcompound3.getString("freq");

			NBTTagList nbttaglist1 = nbttagcompound.getTagList("PowerRoute");
			if (nbttaglist1.tagCount() == 6) {
				for (int i = 0; i < nbttaglist1.tagCount(); i++) {
					NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist1.tagAt(i);
					powerRoute[i] = nbttagcompound1.getBoolean("b");
				}
			} else {
				flushPowerRoute();
				writeToNBT(nbttagcompound);
			}

			NBTTagList nbttaglist4 = nbttagcompound.getTagList("IndirPower");
			if (nbttaglist4.tagCount() == 6) {
				for (int i = 0; i < nbttaglist4.tagCount(); i++) {
					NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist4.tagAt(i);
					indirPower[i] = nbttagcompound1.getBoolean("b");
				}
			} else {
				flushIndirPower();
				writeToNBT(nbttagcompound);
			}
			for (ITileEntityRedstoneWirelessOverride override : overrides) {
				if (override.handlesExtraNBTTags()) {
					override.readFromNBT(	this,
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
			nbttagcompound1.setString(	"freq",
										currentFreq.toString());
			nbttaglist3.appendTag(nbttagcompound1);
			nbttagcompound.setTag(	"Frequency",
									nbttaglist3);

			NBTTagList nbttaglist2 = new NBTTagList();
			for (int i = 0; i < powerRoute.length; i++) {
				NBTTagCompound nbttagcompound2 = new NBTTagCompound();
				nbttagcompound2.setBoolean(	"b",
											powerRoute[i]);
				nbttaglist2.appendTag(nbttagcompound2);
			}
			nbttagcompound.setTag(	"PowerRoute",
									nbttaglist2);

			NBTTagList nbttaglist4 = new NBTTagList();
			for (int i = 0; i < indirPower.length; i++) {
				NBTTagCompound nbttagcompound2 = new NBTTagCompound();
				nbttagcompound2.setBoolean(	"b",
											indirPower[i]);
				nbttaglist4.appendTag(nbttagcompound2);
			}
			nbttagcompound.setTag(	"IndirPower",
									nbttaglist4);
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
		LoggerRedstoneWireless.getInstance(LoggerRedstoneWireless.filterClassName(this.getClass().toString())).write(	this.worldObj.isRemote,
																														"isUseableByPlayer("
																																+ entityplayer.username
																																+ ")",
																														LoggerRedstoneWireless.LogLevel.DEBUG);

		// Run before overrides.
		boolean prematureExit = false;
		for (ITileEntityRedstoneWirelessOverride override : overrides) {
			if (override.beforeIsUseableByPlayer(	this,
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
			output = override.afterIsUseableByPlayer(	this,
														entityplayer,
														output);
		}
		return output;

		/**
		 * try { if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) !=
		 * this) { return false; } return entityplayer.getDistanceSq( xCoord +
		 * 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64D; } catch (Exception e) {
		 * LoggerRedstoneWireless.getInstance( "TileEntityRedstoneWireless"
		 * ).writeStackTrace(e); return false; }
		 **/
	}

	private boolean isTileRedstoneWirelessUseable(EntityPlayer entityplayer) {
		if (worldObj.getBlockTileEntity(xCoord,
										yCoord,
										zCoord) != this) {
			return false;
		}
		return entityplayer.getDistanceSq(	xCoord + 0.5D,
											yCoord + 0.5D,
											zCoord + 0.5D) <= 64D;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		ItemStack itemstack = this.reference;
		for (ITileEntityRedstoneWirelessOverride override : overrides) {
			if (override.handleInventory()) {
				itemstack = override.getStackInSlotOnClosing(	this,
																i,
																itemstack);
			}
		}
		return itemstack;
	}

	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
		this.readFromNBT(pkt.customParam1);
		this.onInventoryChanged();
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		this.writeToNBT(nbttagcompound);
		Packet packet = new Packet132TileEntityData(xCoord, yCoord, zCoord, 0, nbttagcompound);
		return packet;
	}

	public void handleData(IRedstoneWirelessData data) {
		boolean prematureExit = false;

		for (ITileEntityRedstoneWirelessOverride override : overrides) {
			if (override.beforeHandleData(	this,
											data)) {
				prematureExit = true;
			}
		}
	}

	@Override
	public boolean isInvNameLocalized() {
		// TODO :: Auto-generated method stub
		return true;
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		// TODO :: Auto-generated method stub
		return false;
	}
}
