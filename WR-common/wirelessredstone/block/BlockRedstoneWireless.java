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
package wirelessredstone.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.src.BlockContainer;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import wirelessredstone.api.IBlockRedstoneWirelessOverride;
import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.tileentity.TileEntityRedstoneWireless;

/**
 * Base Wireless Redstone Block.
 * 
 * @author ali4z
 */
public abstract class BlockRedstoneWireless extends BlockContainer {
	/**
	 * A list of overrides.
	 */
	private List<IBlockRedstoneWirelessOverride> overrides;

	/**
	 * Constructor sets the block ID, material and initializes the override
	 * list.
	 * 
	 * @param i Block ID
	 */
	protected BlockRedstoneWireless(int i, float hardness, float resistance) {
		super(i, Material.circuits);
		setHardness(hardness);
		setResistance(resistance);
		setCreativeTab(CreativeTabs.tabRedstone);
		overrides = new ArrayList<IBlockRedstoneWirelessOverride>();
	}

	/**
	 * Adds a Block override.
	 * 
	 * @param override Block override
	 */
	public void addOverride(IBlockRedstoneWirelessOverride override) {
		overrides.add(override);
	}

	/**
	 * Stores state in metadata and marks Block for update.
	 * 
	 * @param world The world object
	 * @param i World X coordinate
	 * @param j World Y coordinate
	 * @param k World Z coordinate
	 * @param state The state to be set
	 */
	public synchronized void setState(World world, int i, int j, int k, boolean state) {
		LoggerRedstoneWireless.getInstance(
				LoggerRedstoneWireless.filterClassName(this.getClass().toString())
		).write(
				world.isRemote,
				"setState(world," + i + "," + j + "," + k + "," + state + ")",
				LoggerRedstoneWireless.LogLevel.DEBUG
		);
		
		int meta = 0;
		if (state)
			meta = 1;

		// Store meta.
		try {
			world.setBlockMetadataWithNotify(i, j, k, meta);
			world.markBlockForRenderUpdate(i, j, k);
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance(
					LoggerRedstoneWireless.filterClassName(this.getClass().toString())
			).writeStackTrace(e);
		}
	}

	/**
	 * Fetches the state from metadata.
	 * 
	 * @param world The world object
	 * @param i World X coordinate
	 * @param j World Y coordinate
	 * @param k World Z coordinate
	 * 
	 * @return The state.
	 */
	public synchronized boolean getState(World world, int i, int j, int k) {
		LoggerRedstoneWireless.getInstance(
				LoggerRedstoneWireless.filterClassName(this.getClass().toString())
		).write(
				world.isRemote,
				"getState(world," + i + "," + j + "," + k + ")",
				LoggerRedstoneWireless.LogLevel.DEBUG
		);
		
		int meta = 0;
		try {
			meta = world.getBlockMetadata(i, j, k);
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance(
					LoggerRedstoneWireless.filterClassName(this
							.getClass()
								.toString())).writeStackTrace(e);
		}

		// Returns true if the last bit in meta is 1.
		return getState(meta);
	}

	/**
	 * Filters a 4-bit metadata integer to a boolean state.
	 * 
	 * @param meta 4-bit metadata
	 * @return Boolean state
	 */
	public static boolean getState(int meta) {
		return (meta & 1) == 1;
	}

	/**
	 * Gets the Block's frequency from it's TileEntity.
	 * 
	 * @param world The world object
	 * @param i World X coordinate
	 * @param j World Y coordinate
	 * @param k World Z coordinate
	 * 
	 * @return Frequency.
	 */
	public String getFreq(World world, int i, int j, int k) {
		LoggerRedstoneWireless.getInstance(
				LoggerRedstoneWireless.filterClassName(this.getClass().toString())
		).write(
				world.isRemote,
				"getFreq(world," + i + "," + j + "," + k + ")",
				LoggerRedstoneWireless.LogLevel.DEBUG
		);
		
		try {
			TileEntity tileentity = world.getBlockTileEntity(i, j, k);
			if (tileentity == null)
				return null;

			if (tileentity instanceof TileEntityRedstoneWireless)
				return ((TileEntityRedstoneWireless) tileentity)
						.getFreq()
							.toString();
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance(
					LoggerRedstoneWireless.filterClassName(this.getClass().toString())
			).writeStackTrace(e);
		}
		return null;
	}

	/**
	 * Changes the frequency.
	 * 
	 * @param world The world object
	 * @param i World X coordinate
	 * @param j World Y coordinate
	 * @param k World Z coordinate
	 * @param oldFreq Old frequency to change from.
	 * @param freq New frequency to change to.
	 */
	public abstract void changeFreq(World world, int i, int j, int k, String oldFreq, String freq);

	/**
	 * Triggers when the Block is added to the world.<br>
	 * - Create and set the Block's TileEntity.<br>
	 * - Triggers onBlockRedstoneWirelessAdded<br>
	 * - Runs all override afterBlockRedstoneWirelessAdded
	 * 
	 * @param world The world object
	 * @param i World X coordinate
	 * @param j World Y coordinate
	 * @param k World Z coordinate
	 */
	@Override
	public void onBlockAdded(World world, int i, int j, int k) {
		LoggerRedstoneWireless.getInstance(
				LoggerRedstoneWireless.filterClassName(this.getClass().toString())
		).write(
				world.isRemote,
				"onBlockAdded(world," + i + "," + j + "," + k + ")",
				LoggerRedstoneWireless.LogLevel.DEBUG
		);

		// Run overrides.
		boolean prematureExit = false;
		for (IBlockRedstoneWirelessOverride override : overrides) {
			if (override.beforeBlockRedstoneWirelessAdded(world, i, j, k))
				prematureExit = true;
		}
		if (prematureExit)
			return;

		try {
			TileEntityRedstoneWireless entity = (TileEntityRedstoneWireless) createNewTileEntity(world);
			world.setBlockTileEntity(i, j, k, entity);

			onBlockRedstoneWirelessAdded(world, i, j, k);
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance(
					LoggerRedstoneWireless.filterClassName(this.getClass().toString())
			).writeStackTrace(e);
		}

		// Run overrides.
		for (IBlockRedstoneWirelessOverride override : overrides)
			override.afterBlockRedstoneWirelessAdded(world, i, j, k);
	}

	/**
	 * Is triggered after the Block and TileEntity is added to the world.
	 * 
	 * @param world The world object
	 * @param i World X coordinate
	 * @param j World Y coordinate
	 * @param k World Z coordinate
	 */
	protected abstract void onBlockRedstoneWirelessAdded(World world, int i, int j, int k);

	/**
	 * Triggers when the Block is removed from the world.<br>
	 * - Triggers onBlockRedstoneWirelessRemoved<br>
	 * - Runs all override afterBlockRedstoneWirelessRemoved<br>
	 * - Remove the TileEntity from the world
	 * 
	 * @param world The world object
	 * @param i World X coordinate
	 * @param j World Y coordinate
	 * @param k World Z coordinate
	 */
	@Override
	public void breakBlock(World world, int i, int j, int k, int l, int m) {
		LoggerRedstoneWireless.getInstance(
				LoggerRedstoneWireless.filterClassName(this.getClass().toString())
		).write(
				world.isRemote,
				"onBlockRemoval(world," + i + "," + j + "," + k + ")",
				LoggerRedstoneWireless.LogLevel.DEBUG
		);

		// Run overrides.
		boolean prematureExit = false;
		for (IBlockRedstoneWirelessOverride override : overrides) {
			if (override.beforeBlockRedstoneWirelessRemoved(world, i, j, k))
				prematureExit = true;
		}
		if (prematureExit)
			return;

		try {
			onBlockRedstoneWirelessRemoved(world, i, j, k);

			world.removeBlockTileEntity(i, j, k);
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance(
					LoggerRedstoneWireless.filterClassName(this.getClass().toString())
			).writeStackTrace(e);
		}

		// Run overrides.
		for (IBlockRedstoneWirelessOverride override : overrides)
			override.afterBlockRedstoneWirelessRemoved(world, i, j, k);
	}

	/**
	 * Is triggered after the Block is removed from the world and before the
	 * TileEntity is removed.
	 * 
	 * @param world The world object
	 * @param i World X coordinate
	 * @param j World Y coordinate
	 * @param k World Z coordinate
	 */
	protected abstract void onBlockRedstoneWirelessRemoved(World world, int i, int j, int k);

	/**
	 * Returns the Block ID to be dropped on breaking the Block.
	 * 
	 * @param i Direction
	 * @param random Randomization object
	 * @param i1 Fortune modifier
	 * @return Block ID.
	 */
	@Override
	public int idDropped(int i, Random random, int i1) {
		return this.blockID;
	}

	/**
	 * Triggers when the Block is activated/right clicked.<br>
	 * - Runs all override beforeBlockRedstoneWirelessActivated, returns false
	 * if premature exit.<br>
	 * - Triggers onBlockRedstoneWirelessActivated<br>
	 * - Runs all override afterBlockRedstoneWirelessActivated
	 * 
	 * @param world The world object
	 * @param i World X coordinate
	 * @param j World Y coordinate
	 * @param k World Z coordinate
	 * @param entityplayer The player that activated it
	 * 
	 * @return Activation status.
	 */
	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int q, float a, float b, float c) {
		LoggerRedstoneWireless.getInstance(
				LoggerRedstoneWireless.filterClassName(this.getClass().toString())
		).write(
				world.isRemote,
				"blockActivated(world," + i + "," + j + "," + k + ")",
				LoggerRedstoneWireless.LogLevel.DEBUG
		);

		boolean prematureExit = false;
		// Run overrides.
		for (IBlockRedstoneWirelessOverride override : overrides) {
			if (override.beforeBlockRedstoneWirelessActivated(
					world,
					i,
					j,
					k,
					entityplayer))
				prematureExit = true;
		}
		if (prematureExit)
			return false;

		boolean output = false;
		try {
			output = onBlockRedstoneWirelessActivated(
					world,
					i,
					j,
					k,
					entityplayer);
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance(
					LoggerRedstoneWireless.filterClassName(this.getClass().toString())
			).writeStackTrace(e);
			return false;
		}

		// Run overrides.
		for (IBlockRedstoneWirelessOverride override : overrides) {
			override.afterBlockRedstoneWirelessActivated(
					world,
					i,
					j,
					k,
					entityplayer);
		}
		return output;
	}

	/**
	 * Is triggered on Block activation unless overrides exits prematurely.
	 * 
	 * @param world The world object
	 * @param i World X coordinate
	 * @param j World Y coordinate
	 * @param k World Z coordinate
	 * @param entityplayer The player that activated it
	 * 
	 * @return Activation status.
	 */
	protected abstract boolean onBlockRedstoneWirelessActivated(World world, int i, int j, int k, EntityPlayer entityplayer);

	/**
	 * Triggers when the Block's neighboring Block changes.<br>
	 * - Triggers onBlockRedstoneWirelessNeighborChange<br>
	 * - Runs all override afterBlockRedstoneWirelessNeighborChange
	 * 
	 * @param world The world object
	 * @param i World X coordinate
	 * @param j World Y coordinate
	 * @param k World Z coordinate
	 * @param l Neighbor Block ID
	 */
	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
		LoggerRedstoneWireless.getInstance(
				LoggerRedstoneWireless.filterClassName(this.getClass().toString())
		).write(
				world.isRemote,
				"onNeighborBlockChange(world," + i + "," + j + "," + k + "," + l + ")",
				LoggerRedstoneWireless.LogLevel.DEBUG
		);

		boolean prematureExit = false;
		// Run overrides.
		for (IBlockRedstoneWirelessOverride override : overrides) {
			if (override.beforeBlockRedstoneWirelessNeighborChange(
					world,
					i,
					j,
					k,
					l))
				prematureExit = true;
		}
		if (prematureExit)
			return;

		try {
			onBlockRedstoneWirelessNeighborChange(world, i, j, k, l);

		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance(
					LoggerRedstoneWireless.filterClassName(this.getClass().toString())
			).writeStackTrace(e);
		}

		// Run overrides.
		for (IBlockRedstoneWirelessOverride override : overrides) {
			override
					.afterBlockRedstoneWirelessNeighborChange(world, i, j, k, l);
		}
	}

	/**
	 * Is triggered on Block's neighboring Block change.
	 * 
	 * @param world The world object
	 * @param i World X coordinate
	 * @param j World Y coordinate
	 * @param k World Z coordinate
	 * @param l Direction
	 */
	protected abstract void onBlockRedstoneWirelessNeighborChange(World world, int i, int j, int k, int l);

	/**
	 * Checks whether or not the Block is an Opaque Cube.
	 * 
	 * @return false
	 */
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	/**
	 * Checks whether or not the Block has the potential to provide redstone
	 * power.
	 * 
	 * @return true
	 */
	@Override
	public boolean canProvidePower() {
		return true;
	}

	/**
	 * Fetch the Block texture ID at a given side.<br>
	 * Queries getBlockRedstoneWirelessTexture
	 * 
	 * @param iblockaccess Block accessing interface
	 * @param i World X coordinate
	 * @param j World Y coordinate
	 * @param k World Z coordinate
	 * @param l Direction
	 * 
	 * @return Block texture ID
	 */
	@Override
	public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		try {
			return getBlockRedstoneWirelessTexture(iblockaccess, i, j, k, l);
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance(
					LoggerRedstoneWireless.filterClassName(this.getClass().toString())
			).writeStackTrace(e);
			return 0;
		}
	}

	/**
	 * Fetch the Block texture ID at a given side.
	 * 
	 * @param iblockaccess Block accessing interface
	 * @param i World X coordinate
	 * @param j World Y coordinate
	 * @param k World Z coordinate
	 * @param l Direction
	 * 
	 * @return Block texture ID
	 */
	protected abstract int getBlockRedstoneWirelessTexture(IBlockAccess iblockaccess, int i, int j, int k, int l);

	/**
	 * Fetch the Block texture ID at a given side.<br>
	 * Queries getBlockRedstoneWirelessTextureFromSide<br>
	 * NOTE: This is only called for Blocks in inventory or held.
	 * 
	 * @param i Direction
	 * 
	 * @return Block texture ID
	 */
	@Override
	public int getBlockTextureFromSide(int i) {
		try {
			return getBlockRedstoneWirelessTextureFromSide(i);
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance(
					LoggerRedstoneWireless.filterClassName(this.getClass().toString())
			).writeStackTrace(e);
			return 0;
		}
	}

	/**
	 * Fetch the Block texture ID at a given side.<br>
	 * 
	 * @param i Direction
	 * 
	 * @return Block texture ID
	 */
	protected abstract int getBlockRedstoneWirelessTextureFromSide(int l);

	/**
	 * Fetches a new Block TileEntity.<br>
	 * Queries getBlockRedstoneWirelessEntity
	 * 
	 * @return The TileEntity.
	 */
	@Override
	public TileEntity createNewTileEntity(World world) {
		return getBlockRedstoneWirelessEntity();
	}

	/**
	 * Fetches a new Wireless Block TileEntity.
	 * 
	 * @return The Wireless TileEntity.
	 */
	protected abstract TileEntityRedstoneWireless getBlockRedstoneWirelessEntity();

	/**
	 * Tells the world object to notify all surrounding Blocks of a change.
	 * 
	 * @param world The world object
	 * @param i World X coordinate
	 * @param j World Y coordinate
	 * @param k World Z coordinate
	 */
	public static void notifyNeighbors(World world, int i, int j, int k) {
		LoggerRedstoneWireless.getInstance(
				"BlockRedstoneWireless"
		).write(
				world.isRemote,
				"notifyNeighbors(world," + i + "," + j + "," + k + ")",
				LoggerRedstoneWireless.LogLevel.DEBUG
		);
		
		world.notifyBlocksOfNeighborChange(i, j, k, 0);
		world.notifyBlocksOfNeighborChange(i - 1, j, k, 0);
		world.notifyBlocksOfNeighborChange(i + 1, j, k, 0);
		world.notifyBlocksOfNeighborChange(i, j - 1, k, 0);
		world.notifyBlocksOfNeighborChange(i, j + 1, k, 0);
		world.notifyBlocksOfNeighborChange(i, j, k - 1, 0);
		world.notifyBlocksOfNeighborChange(i, j, k + 1, 0);
	}

	/**
	 * Overridden to do nothing at all.
	 */
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
	}

	/**
	 * Triggered on a Block update tick.<br>
	 * - Runs all override beforeUpdateRedstoneWirelessTick, exits if premature
	 * exit.<br>
	 * - Triggers updateRedstoneWirelessTick
	 * 
	 * @param world The world object
	 * @param i World X coordinate
	 * @param j World Y coordinate
	 * @param k World Z coordinate
	 * @param random Randomization object
	 */
	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		LoggerRedstoneWireless.getInstance(
				LoggerRedstoneWireless.filterClassName(this.getClass().toString())
		).write(
				world.isRemote,
				"updateTick(world," + i + "," + j + "," + k + ")",
				LoggerRedstoneWireless.LogLevel.DEBUG
		);

		boolean prematureExit = false;
		for (IBlockRedstoneWirelessOverride override : overrides) {
			if (override.beforeUpdateRedstoneWirelessTick(
					world,
					i,
					j,
					k,
					random))
				prematureExit = true;
		}

		if (prematureExit)
			return;

		try {
			updateRedstoneWirelessTick(world, i, j, k, random);
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance(
					LoggerRedstoneWireless.filterClassName(this.getClass().toString())
			).writeStackTrace(e);
		}

		// Run overrides.
		for (IBlockRedstoneWirelessOverride override : overrides) {
			override.afterUpdateRedstoneWirelessTick(world, i, j, k, random);
		}
	}

	/**
	 * Triggered on a Block update tick.<br>
	 * 
	 * @param world The world object
	 * @param i World X coordinate
	 * @param j World Y coordinate
	 * @param k World Z coordinate
	 * @param random Randomization object
	 */
	protected abstract void updateRedstoneWirelessTick(World world, int i, int j, int k, Random random);

	/**
	 * Checks whether or not the Block is directly emitting power to a
	 * direction.<br>
	 * - Queries isRedstoneWirelessPoweringTo if the access interface is a World
	 * object.
	 * 
	 * @param iblockaccess Block accessing interface
	 * @param i World X coordinate
	 * @param j World Y coordinate
	 * @param k World Z coordinate
	 * @param l Direction
	 * @return Powering state.
	 */
	@Override
	public boolean isProvidingStrongPower(IBlockAccess iblockaccess, int i, int j, int k, int l) {

		try {
			if (iblockaccess instanceof World) {
				LoggerRedstoneWireless.getInstance(
						LoggerRedstoneWireless.filterClassName(this.getClass().toString())
				).write(
						((World) iblockaccess).isRemote,
						"isPoweringTo(iblockaccess," + i + "," + j + "," + k + "," + l + ")",
						LoggerRedstoneWireless.LogLevel.DEBUG
				);
				return isRedstoneWirelessPoweringTo(
						(World) iblockaccess,
						i,
						j,
						k,
						l);
			}
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance(
					LoggerRedstoneWireless.filterClassName(this.getClass().toString())
			).writeStackTrace(e);
		}
		return false;
	}

	/**
	 * Checks whether or not the Block is directly emitting power to a
	 * direction.<br>
	 * 
	 * @param world The world object
	 * @param i World X coordinate
	 * @param j World Y coordinate
	 * @param k World Z coordinate
	 * @param l Direction
	 * 
	 * @return Powering state.
	 */
	protected abstract boolean isRedstoneWirelessPoweringTo(World world, int i, int j, int k, int l);

	/**
	 * Checks whether or not the Block is indirectly emitting power to a
	 * direction.<br>
	 * - Queries isRedstoneWirelessIndirectlyPoweringTo.
	 * 
	 * @param world The world object
	 * @param i World X coordinate
	 * @param j World Y coordinate
	 * @param k World Z coordinate
	 * @param l Direction
	 * 
	 * @return Powering state.
	 */
	@Override
	public boolean isProvidingWeakPower(IBlockAccess world, int i, int j, int k, int l) {
		if ( world instanceof World ) {
			LoggerRedstoneWireless
			.getInstance(
					LoggerRedstoneWireless.filterClassName(this.getClass().toString())
			).write(
						((World)world).isRemote,
						"isIndirectlyPoweringTo(world," + i + "," + j + "," + k + "," + l + ")",
						LoggerRedstoneWireless.LogLevel.DEBUG
			);
			try {
				return isRedstoneWirelessIndirectlyPoweringTo((World)world, i, j, k, l);
			} catch (Exception e) {
				LoggerRedstoneWireless.getInstance(
						LoggerRedstoneWireless.filterClassName(this.getClass().toString())
				).writeStackTrace(e);
			}
		}
		return false;
	}

	/**
	 * Checks whether or not the Block is indirectly emitting power to a
	 * direction.
	 * 
	 * @param world The world object
	 * @param i World X coordinate
	 * @param j World Y coordinate
	 * @param k World Z coordinate
	 * @param l Direction
	 * 
	 * @return Powering state.
	 */
	protected abstract boolean isRedstoneWirelessIndirectlyPoweringTo(World world, int i, int j, int k, int l);

	@Override
	public String getTextureFile() {
		return "/WirelessSprites/terrain.png";
	}
}
