package wirelessredstone.overrides;

import java.util.Random;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import wirelessredstone.api.IBlockRedstoneWirelessOverride;

public class BlockRedstoneWirelessOverrideSMP implements
		IBlockRedstoneWirelessOverride {

	@Override
	public boolean beforeBlockRedstoneWirelessAdded(World world, int i, int j,
			int k) {
		return (world.isRemote);
	}

	@Override
	public void afterBlockRedstoneWirelessAdded(World world, int i, int j, int k) {
	}

	@Override
	public boolean beforeBlockRedstoneWirelessRemoved(World world, int i,
			int j, int k) {
		return (world.isRemote);
	}

	@Override
	public void afterBlockRedstoneWirelessRemoved(World world, int i, int j,
			int k) {
	}

	@Override
	public boolean beforeBlockRedstoneWirelessActivated(World world, int i,
			int j, int k, EntityPlayer entityplayer) {
		return (world.isRemote);
	}

	@Override
	public void afterBlockRedstoneWirelessActivated(World world, int i, int j,
			int k, EntityPlayer entityplayer) {
	}

	@Override
	public boolean beforeBlockRedstoneWirelessNeighborChange(World world,
			int i, int j, int k, int l) {
		return (world.isRemote);
	}

	@Override
	public void afterBlockRedstoneWirelessNeighborChange(World world, int i,
			int j, int k, int l) {
	}

	@Override
	public boolean beforeUpdateRedstoneWirelessTick(World world, int i, int j,
			int k, Random random) {
		return (world.isRemote);
	}

	@Override
	public void afterUpdateRedstoneWirelessTick(World world, int i, int j,
			int k, Random random) {
	}
}
