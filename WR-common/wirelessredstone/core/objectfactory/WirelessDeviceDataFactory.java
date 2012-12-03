package wirelessredstone.core.objectfactory;

import java.lang.reflect.Constructor;

import net.minecraft.src.World;

import wirelessredstone.data.LoggerRedstoneWireless;
import wirelessredstone.data.WirelessDeviceData;

public class WirelessDeviceDataFactory {

	private World wirelessDeviceDataWorld;
	private Constructor wirelessDeviceDataConstructor;
	private Class wirelessDeviceDataClass;
	private String wirelessDeviceDataIndex;
	
	public WirelessDeviceDataFactory(World world, Class<? extends WirelessDeviceData> wirelessData, String index) {
		try {
			this.wirelessDeviceDataWorld = world;
			this.wirelessDeviceDataConstructor = wirelessData
					.getConstructor(wirelessData);
			this.wirelessDeviceDataClass = wirelessData;
			this.wirelessDeviceDataIndex = index;
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance(
					"WirelessDeviceDataFactory"
			).writeStackTrace(e);
		}
	}
	
	public WirelessDeviceData getDeviceDataFromClass() {
		try {
			if (this.wirelessDeviceDataClass != null && this.wirelessDeviceDataConstructor != null && !this.wirelessDeviceDataIndex.isEmpty()) {
				return this.getDeviceDataFromInstance(wirelessDeviceDataConstructor.newInstance(wirelessDeviceDataIndex));	
			}
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance(
					"WirelessDeviceDataFactory"
			).writeStackTrace(e);
		}
		return null;
	}
	
	public WirelessDeviceData getDeviceDataFromInstance(Object object) {
		try {
			if (object != null && this.wirelessDeviceDataClass != null) {
				return (WirelessDeviceData) this.wirelessDeviceDataClass.cast(object);
			}
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance(
					"WirelessDeviceDataFactory"
			).writeStackTrace(e);
		}
		return null;
		
	}
	
	@SuppressWarnings("unused")
	public static WirelessDeviceData getDeviceDataFromFactory(World world, Class <? extends WirelessDeviceData> wirelessDataClass, String index, boolean loadData) {
		WirelessDeviceDataFactory factory = new WirelessDeviceDataFactory(
			world,
			wirelessDataClass,
			index
		);
		if (factory != null) {
			if (loadData) {
				return factory.getDeviceDataFromInstance(
							factory.wirelessDeviceDataWorld.loadItemData(
								factory.wirelessDeviceDataClass,
								factory.wirelessDeviceDataIndex
							)
						);
			} else {
				return factory.getDeviceDataFromClass();
			}
		}
		return null;
	}
}
