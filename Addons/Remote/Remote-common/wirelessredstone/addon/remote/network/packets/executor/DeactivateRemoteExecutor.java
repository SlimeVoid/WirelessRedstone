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
package wirelessredstone.addon.remote.network.packets.executor;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import wirelessredstone.addon.remote.data.WirelessRemoteData;
import wirelessredstone.addon.remote.data.WirelessRemoteDevice;
import wirelessredstone.api.IWirelessDevice;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.network.packets.executor.DevicePacketDeactivateExecutor;

public class DeactivateRemoteExecutor extends DevicePacketDeactivateExecutor {

	@Override
	protected IWirelessDevice getDevice(World world, EntityLivingBase entityliving, IWirelessDeviceData deviceData) {
		return new WirelessRemoteDevice(world, entityliving, deviceData);
	}

	@Override
	protected Class<? extends IWirelessDeviceData> getDeviceDataClass() {
		return WirelessRemoteData.class;
	}
}
