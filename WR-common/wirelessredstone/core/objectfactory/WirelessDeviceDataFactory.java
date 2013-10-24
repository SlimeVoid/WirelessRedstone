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
package wirelessredstone.core.objectfactory;

import java.lang.reflect.Constructor;

import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import wirelessredstone.api.IWirelessDeviceData;
import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.device.WirelessDeviceData;

public class WirelessDeviceDataFactory {

	private World		wirelessDeviceDataWorld;
	private Constructor	wirelessDeviceDataConstructor;
	private Class		wirelessDeviceDataClass;
	private String		wirelessDeviceDataIndex;

	public WirelessDeviceDataFactory(World world, Class<? extends IWirelessDeviceData> wirelessData, String index) {
		try {
			this.wirelessDeviceDataWorld = world;
			this.wirelessDeviceDataConstructor = wirelessData.getConstructors()[0];
			this.wirelessDeviceDataClass = wirelessData;
			this.wirelessDeviceDataIndex = index;
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance("WirelessDeviceDataFactory").writeStackTrace(e);
		}
	}

	public WirelessDeviceData getDeviceDataFromClass() {
		try {
			if (this.wirelessDeviceDataClass != null
				&& this.wirelessDeviceDataConstructor != null
				&& !this.wirelessDeviceDataIndex.isEmpty()) {
				Object data = this.wirelessDeviceDataConstructor.newInstance(wirelessDeviceDataIndex);
				return this.getDeviceDataFromInstance(data);
			}
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance("WirelessDeviceDataFactory").writeStackTrace(e);
		}
		return null;
	}

	public WirelessDeviceData getDeviceDataFromInstance(Object object) {
		try {
			if (object != null && this.wirelessDeviceDataClass != null) {
				return (WirelessDeviceData) this.wirelessDeviceDataClass.cast(object);
			}
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance("WirelessDeviceDataFactory").writeStackTrace(e);
		}
		return null;

	}

	@SuppressWarnings("unused")
	public static WirelessDeviceData getDeviceDataFromFactory(World world, Class<? extends IWirelessDeviceData> wirelessData, String index, boolean loadData) {
		WirelessDeviceDataFactory factory = new WirelessDeviceDataFactory(world, wirelessData, index);
		if (factory != null) {
			if (loadData) {
				WorldSavedData data = factory.wirelessDeviceDataWorld.loadItemData(	factory.wirelessDeviceDataClass,
																					factory.wirelessDeviceDataIndex);
				return factory.getDeviceDataFromInstance(data);
			} else {
				return factory.getDeviceDataFromClass();
			}
		}
		return null;
	}
}
