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
package wirelessredstone.block;

import java.util.Random;

import wirelessredstone.core.WRCore;
import wirelessredstone.ether.RedstoneEther;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWirelessT;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

/**
 * Redstone Wireless Transmitter
 * 
 * @author ali4z
 * 
 */
public class BlockRedstoneWirelessT extends BlockRedstoneWireless {
	/**
	 * Constructor.<br>
	 * Sets the Block ID.
	 * 
	 * @param i
	 *            Block ID
	 */
	public BlockRedstoneWirelessT(int i, float hardness, float resistance) {
		super(i, hardness, resistance);
		setStepSound(Block.soundMetalFootstep);
	}

	/**
	 * Stores state in metadata and marks Block for update.<br>
	 * - Sets the transmitter state in the Ether.
	 */
	@Override
	public void setState(World world, int i, int j, int k, boolean state) {
		super.setState(world, i, j, k, state);

		String freq = getFreq(world, i, j, k);
		RedstoneEther.getInstance().setTransmitterState(world, i, j, k, freq,
				state);
	}

	/**
	 * Changes the frequency.<br>
	 * - Remove transmitter from old frequency.<br>
	 * - Add transmitter to new frequency.<br>
	 * - Set transmitter state in the Ether.
	 */
	@Override
	public void changeFreq(World world, int i, int j, int k, String oldFreq,
			String freq) {
		// Remove transmitter from current frequency on the ether.
		RedstoneEther.getInstance().remTransmitter(world, i, j, k, oldFreq);

		// Add transmitter to the ether on new frequency.
		RedstoneEther.getInstance().addTransmitter(world, i, j, k, freq);
		RedstoneEther.getInstance().setTransmitterState(world, i, j, k, freq,
				getState(world, i, j, k));
	}

	/**
	 * Is triggered after the Block and TileEntity is added to the world.<br>
	 * - Add transmitter to ether.<br>
	 * - Notify self of neighbor change. (forcing transmitter to check for
	 * neighboring power sources)
	 */
	@Override
	protected void onBlockRedstoneWirelessAdded(World world, int i, int j, int k) {
		RedstoneEther.getInstance().addTransmitter(world, i, j, k,
				getFreq(world, i, j, k));

		onBlockRedstoneWirelessNeighborChange(world, i, j, k,
				Block.redstoneWire.blockID);
	}

	/**
	 * Is triggered after the Block is removed from the world and before the
	 * TileEntity is removed.<br>
	 * - Remove transmitter from ether.
	 */
	@Override
	protected void onBlockRedstoneWirelessRemoved(World world, int i, int j,
			int k) {
		RedstoneEther.getInstance().remTransmitter(world, i, j, k,
				getFreq(world, i, j, k));
	}

	/**
	 * Is triggered on Block activation unless overrides exits prematurely.<br>
	 * - Associates the TileEntity with the GUI. - Opens the GUI.
	 */
	@Override
	protected boolean onBlockRedstoneWirelessActivated(World world, int i,
			int j, int k, EntityPlayer entityplayer) {
		TileEntity tileentity = world.getBlockTileEntity(i, j, k);

		if (tileentity != null)
			WRCore.proxy.openGUI(world, entityplayer, tileentity);

		return true;
	}

	/**
	 * Is triggered on Block's neighboring Block change.<br>
	 * - Exit if the neighboring Block is not of the same type. - If Block is
	 * getting powered directly or indirectly, and the power is not already on,
	 * set the power on. - If Block is not getting powered directly or
	 * indirectly, and the power is already on, set the power off.
	 */
	@Override
	protected void onBlockRedstoneWirelessNeighborChange(World world, int i,
			int j, int k, int l) {
		if (l == this.blockID)
			return;

		// It was not removed, can provide power and is indirectly getting
		// powered.
		if (l > 0
				&& !getState(world, i, j, k)
				&& (world.isBlockGettingPowered(i, j, k) || world
						.isBlockIndirectlyGettingPowered(i, j, k)))
			setState(world, i, j, k, true);
		// There are no powering entities, state is deactivated.
		else if (getState(world, i, j, k)
				&& !(world.isBlockGettingPowered(i, j, k) || world
						.isBlockIndirectlyGettingPowered(i, j, k)))
			setState(world, i, j, k, false);
	}

	@Override
	protected int getBlockRedstoneWirelessTexture(IBlockAccess iblockaccess,
			int i, int j, int k, int l) {
		if (getState(iblockaccess.getBlockMetadata(i, j, k))) {
			if (l == 0 || l == 1) {
				return WRCore.spriteTopOn;
			}
			return WRCore.spriteTOn;
		} else {
			return getBlockRedstoneWirelessTextureFromSide(l);
		}
	}

	@Override
	protected int getBlockRedstoneWirelessTextureFromSide(int l) {
		if (l == 0 || l == 1) {
			return WRCore.spriteTopOff;
		}
		return WRCore.spriteTOff;
	}

	@Override
	protected TileEntityRedstoneWireless getBlockRedstoneWirelessEntity() {
		return new TileEntityRedstoneWirelessT();
	}

	/**
	 * Does nothing.
	 */
	@Override
	protected void updateRedstoneWirelessTick(World world, int i, int j, int k,
			Random random) {
	}

	/**
	 * Always returns false.<br>
	 * Transmitters never emitt power.
	 */
	@Override
	protected boolean isRedstoneWirelessPoweringTo(World world, int i, int j,
			int k, int l) {
		return false;
	}

	/**
	 * Always returns false.<br>
	 * Transmitters never emitt power.
	 */
	@Override
	protected boolean isRedstoneWirelessIndirectlyPoweringTo(World world,
			int i, int j, int k, int l) {
		return false;
	}
}
