package wirelessredstone.overrides;

import net.minecraft.world.World;
import wirelessredstone.api.IRedstoneEtherOverride;

public class RedstoneEtherOverrideSMP implements IRedstoneEtherOverride {

	@Override
	public boolean beforeAddTransmitter(World world, int i, int j, int k,
			String freq) {
		return (world == null || world.isRemote);
	}

	@Override
	public void afterAddTransmitter(World world, int i, int j, int k,
			String freq) {
	}

	@Override
	public boolean beforeRemTransmitter(World world, int i, int j, int k,
			String freq) {
		return (world == null || world.isRemote);
	}

	@Override
	public void afterRemTransmitter(World world, int i, int j, int k,
			String freq) {
	}

	@Override
	public boolean beforeSetTransmitterState(World world, int i, int j, int k,
			String freq, boolean state) {
		return (world == null || world.isRemote);
	}

	@Override
	public void afterSetTransmitterState(World world, int i, int j, int k,
			String freq, boolean state) {
	}

	@Override
	public boolean beforeAddReceiver(World world, int i, int j, int k,
			String freq) {
		return (world == null || world.isRemote);
	}

	@Override
	public void afterAddReceiver(World world, int i, int j, int k, String freq) {
	}

	@Override
	public boolean beforeRemReceiver(World world, int i, int j, int k,
			String freq) {
		return (world == null || world.isRemote);
	}

	@Override
	public void afterRemReceiver(World world, int i, int j, int k, String freq) {
	}

	@Override
	public boolean beforeGetFreqState(World world, String freq) {
		return (world == null || world.isRemote);
	}

	@Override
	public boolean afterGetFreqState(World world, String freq,
			boolean returnState) {
		return returnState;
	}

	@Override
	public boolean beforeIsLoaded(World world, int i, int j, int k) {
		return (world == null || world.isRemote);
	}

	@Override
	public boolean afterIsLoaded(World world, int i, int j, int k,
			boolean returnState) {
		return returnState;
	}

	@Override
	public int[] beforeGetClosestActiveTransmitter(int i, int j, int k,
			String freq) {
		return null;
	}

	@Override
	public int[] afterGetClosestActiveTransmitter(int i, int j, int k,
			String freq, int[] coords) {
		return coords;
	}
}
