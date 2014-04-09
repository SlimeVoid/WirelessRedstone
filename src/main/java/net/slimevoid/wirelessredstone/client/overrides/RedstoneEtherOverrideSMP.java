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
package net.slimevoid.wirelessredstone.client.overrides;

import net.minecraft.world.World;
import net.slimevoid.wirelessredstone.api.IRedstoneEtherOverride;

public class RedstoneEtherOverrideSMP implements IRedstoneEtherOverride {

    @Override
    public boolean beforeAddTransmitter(World world, int x, int y, int z, Object freq) {
        return (world == null || world.isRemote);
    }

    @Override
    public void afterAddTransmitter(World world, int x, int y, int z, Object freq) {
    }

    @Override
    public boolean beforeRemTransmitter(World world, int x, int y, int z, Object freq) {
        return (world == null || world.isRemote);
    }

    @Override
    public void afterRemTransmitter(World world, int x, int y, int z, Object freq) {
    }

    @Override
    public boolean beforeSetTransmitterState(World world, int x, int y, int z, Object freq, boolean state) {
        return (world == null || world.isRemote);
    }

    @Override
    public void afterSetTransmitterState(World world, int x, int y, int z, Object freq, boolean state) {
    }

    @Override
    public boolean beforeAddReceiver(World world, int x, int y, int z, Object freq) {
        return (world == null || world.isRemote);
    }

    @Override
    public void afterAddReceiver(World world, int x, int y, int z, Object freq) {
    }

    @Override
    public boolean beforeRemReceiver(World world, int x, int y, int z, Object freq) {
        return (world == null || world.isRemote);
    }

    @Override
    public void afterRemReceiver(World world, int x, int y, int z, Object freq) {
    }

    @Override
    public boolean beforeGetFreqState(World world, Object freq) {
        return (world == null || world.isRemote);
    }

    @Override
    public boolean afterGetFreqState(World world, Object freq, boolean returnState) {
        return returnState;
    }

    @Override
    public boolean beforeIsLoaded(World world, int x, int y, int z) {
        return (world == null || world.isRemote);
    }

    @Override
    public boolean afterIsLoaded(World world, int x, int y, int z, boolean returnState) {
        return returnState;
    }

    @Override
    public int[] beforeGetClosestActiveTransmitter(int x, int y, int z, String freq) {
        return null;
    }

    @Override
    public int[] afterGetClosestActiveTransmitter(int x, int y, int z, String freq, int[] coords) {
        return coords;
    }
}
