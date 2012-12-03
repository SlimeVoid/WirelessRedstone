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
package wirelessredstone.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import wirelessredstone.core.WRCore;

/**
 * Configuration store.<br>
 * Singleton pattern class.
 * 
 * @author ali4z
 */
public class ConfigStoreRedstoneWireless {
	private static ConfigStoreRedstoneWireless instance;
	private Map<String, String> conf;
	private Properties prop;
	private File file;
	private String name;

	/**
	 * Constructor.<br>
	 * Initializes variables and loads properties from configuration file.
	 */
	private ConfigStoreRedstoneWireless() {
		prop = new Properties();
		file = new File(
				WRCore.proxy.getMinecraftDir()+
				File.separator+
				"wirelessRedstone.properties"
		);
		name = "WirelessRedstone";
		loadProperties();
	}

	/**
	 * Returns the configuration store singleton instance.
	 * 
	 * @param name Configuration domain name.
	 * @return Configuration store instance.
	 */
	public static ConfigStoreRedstoneWireless getInstance(String name) {
		if (!name.equals("WirelessRedstone"))
			name = "WirelessRedstone." + name;

		if (instance == null) {
			instance = new ConfigStoreRedstoneWireless();
			if (
					!LoggerRedstoneWireless.getInstance(
							name
					).setFilterLevel(
							(String) instance.get(
									"Log.Level",
									String.class,
									LoggerRedstoneWireless.LogLevel.INFO.name()
							)
					)
			) {
				LoggerRedstoneWireless.getInstance(
						name
				).write(
						true,
						"Unable to parse Log.Level. Valid settings are ERROR,WARNING,INFO,DEBUG.",
						LoggerRedstoneWireless.LogLevel.WARNING
				);
			}
		}

		instance.name = name;

		return instance;
	}

	/**
	 * Get a configuration variable.<br>
	 * <i>Throws an IllegalArgumentException if the defValue is not of a valid
	 * type.<br>
	 * Valid types are: Boolean, Integer, String, Double, Float, Character,
	 * Long</i>
	 * 
	 * @param attr configuration attribute name
	 * @param type configuration attribute type
	 * @param defValue default attribute value
	 * 
	 * @return Configuration attribute value.
	 * @throws IllegalArgumentException defValue was not a valid type.
	 */
	public Object get(String attr, Class type, Object defValue) throws IllegalArgumentException {
		try {
			if (conf.containsKey(name + "." + attr)) {
				String value = conf.get(name + "." + attr);
				
				LoggerRedstoneWireless.getInstance(
						name
				).write(
						true,
						attr + "=" + value,
						LoggerRedstoneWireless.LogLevel.DEBUG
				);

				if (type == Boolean.class)
					return Boolean.parseBoolean(value);
				else if (type == Integer.class)
					return Integer.parseInt(value);
				else if (type == String.class)
					return value;
				else if (type == Double.class)
					return Double.parseDouble(value);
				else if (type == Float.class)
					return Float.parseFloat(value);
				else if (type == Character.class)
					return value.toCharArray()[0];
				else if (type == Long.class)
					return Long.parseLong(value);
				else
					throw new IllegalArgumentException(type.toString());
			} else {
				LoggerRedstoneWireless.getInstance(
						name
				).write(
						true,
						attr + " not found, restoring to default - " + defValue.toString(),
						LoggerRedstoneWireless.LogLevel.WARNING
				);
				
				conf.put(name + "." + attr, defValue.toString());
				saveProperties();
				return defValue;
			}
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance(
					name
			).writeStackTrace(e);
			
			LoggerRedstoneWireless.getInstance(
					name
			).write(
					true,
					"Problem with " + attr + ", restoring to default - " + defValue.toString(),
					LoggerRedstoneWireless.LogLevel.WARNING
			);
			
			conf.put(name + "." + attr, defValue.toString());
			saveProperties();
			return defValue;
		}
	}

	/**
	 * Loads the properties file.
	 */
	private void loadProperties() {
		conf = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
		LoggerRedstoneWireless.getInstance(
				name
		).write(
				true,
				"Loading Properties.",
				LoggerRedstoneWireless.LogLevel.INFO
		);
		
		File fullPath = file;

		try {
			if (fullPath.canRead()) {
				synchronized (prop) {
					prop.load(new FileInputStream(fullPath));

					Set<Entry<Object, Object>> propSet = prop.entrySet();
					for (Entry<Object, Object> propEntry : propSet) {
						conf.put(propEntry.getKey().toString(), propEntry
								.getValue()
									.toString());
					}
				}
			} else if (!fullPath.exists()) {
				LoggerRedstoneWireless.getInstance(
						name
				).write(
						true,
						"Properties file not found, creating.",
						LoggerRedstoneWireless.LogLevel.INFO
				);
				if (saveProperties())
					 loadProperties();
			} else {
				throw new IOException(
						name + ": Unable to handle Properties file!");
			}
		} catch (FileNotFoundException e) {
			LoggerRedstoneWireless.getInstance(
					name
			).writeStackTrace(e);
		} catch (IOException e) {
			LoggerRedstoneWireless.getInstance(
					name
			).writeStackTrace(e);
		}
	}

	/**
	 * Saves the properties file.
	 * 
	 * @return Success state.
	 */
	private boolean saveProperties() {
		File fullPath = file;

		try {
			if (!fullPath.exists()) {
				fullPath.createNewFile();
			}
			if (fullPath.canWrite()) {
				synchronized (prop) {
					prop.load(new FileInputStream(fullPath));
					LoggerRedstoneWireless.getInstance(
							name
					).write(
							true,
							"Saving Properties.",
							LoggerRedstoneWireless.LogLevel.INFO
					);

					for (String c : conf.keySet()) {
						prop.setProperty(c, conf.get(c));
					}

					prop.store(
							new FileOutputStream(fullPath),
							"WirelessRedstone Properties");

					return true;
				}
			} else {
				throw new IOException(
						name + ": Unable to handle Properties file!");
			}
		} catch (FileNotFoundException e) {
			LoggerRedstoneWireless.getInstance(
					name
			).writeStackTrace(e);
		} catch (IOException e) {
			LoggerRedstoneWireless.getInstance(
					name
			).writeStackTrace(e);
		}
		return false;
	}
}
