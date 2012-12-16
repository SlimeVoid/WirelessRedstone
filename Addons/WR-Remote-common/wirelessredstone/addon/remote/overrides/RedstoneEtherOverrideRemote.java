package wirelessredstone.addon.remote.overrides;

import wirelessredstone.addon.remote.data.WirelessRemoteDevice;
import wirelessredstone.api.IRedstoneEtherOverride;
import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.data.WirelessCoordinates;
import wirelessredstone.ether.RedstoneEther;
import net.minecraft.src.World;

public class RedstoneEtherOverrideRemote implements IRedstoneEtherOverride {
	@Override
	public boolean beforeAddTransmitter(World world, int i, int j, int k,
			String freq) {
		return false;
	}

	@Override
	public void afterAddTransmitter(World world, int i, int j, int k,
			String freq) {
	}

	@Override
	public boolean beforeRemTransmitter(World world, int i, int j, int k,
			String freq) {
		return false;
	}

	@Override
	public void afterRemTransmitter(World world, int i, int j, int k,
			String freq) {

	}

	@Override
	public boolean beforeSetTransmitterState(World world, int i, int j, int k,
			String freq, boolean state) {
		return false;
	}

	@Override
	public void afterSetTransmitterState(World world, int i, int j, int k,
			String freq, boolean state) {

	}

	@Override
	public boolean beforeAddReceiver(World world, int i, int j, int k,
			String freq) {
		return false;
	}

	@Override
	public void afterAddReceiver(World world, int i, int j, int k, String freq) {
	}

	@Override
	public boolean beforeRemReceiver(World world, int i, int j, int k,
			String freq) {
		return false;
	}

	@Override
	public void afterRemReceiver(World world, int i, int j, int k, String freq) {
	}

	@Override
	public boolean beforeGetFreqState(World world, String freq) {
		return false;
	}

	@Override
	public boolean afterGetFreqState(World world, String freq,
			boolean returnState) {
		return returnState;
	}

	@Override
	public boolean beforeIsLoaded(World world, int i, int j, int k) {
		return false;
	}

	@Override
	public boolean afterIsLoaded(World world, int i, int j, int k,
			boolean returnState) {
		LoggerRedstoneWireless.getInstance("RedstoneEtherOverrideRemote")
				.write(
					world.isRemote,
					"isLoaded(world, " + i + ", " + j + ", " + k + ")",
					LoggerRedstoneWireless.LogLevel.DEBUG);
		int[] a = { i, j, k };
		WirelessCoordinates deviceCoords = new WirelessCoordinates(a);
		if (WirelessRemoteDevice.remoteWirelessCoords.containsKey(deviceCoords)) {
			return true;
		}
		System.out.println("isLoaded(" + world.isRemote + ", " + i + ", " + j + ", " + k + ")");
		System.out.println("" + returnState);
		return returnState;
	}

	@Override
	public int[] beforeGetClosestActiveTransmitter(int i, int j, int k,
			String freq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] afterGetClosestActiveTransmitter(int i, int j, int k,
			String freq, int[] coords) {
		// TODO Auto-generated method stub
		return coords;
	}
}