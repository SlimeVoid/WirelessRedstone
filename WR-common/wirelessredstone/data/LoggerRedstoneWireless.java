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
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import wirelessredstone.core.WirelessRedstone;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

/**
 * Wireless Redstone logging engine.<br>
 * Singleton pattern class.
 * 
 * @author ali4z
 */
public class LoggerRedstoneWireless {
	private static LoggerRedstoneWireless	instance;
	private String							name;
	private LoggerRedstoneWirelessWriter	writer;
	private LogLevel						filterLevel;

	/**
	 * Log level.<br>
	 * All messages are DEBUG or higher.<br>
	 * Stack trace errors are ERROR messages.
	 * 
	 * @author ali4z
	 */
	public static enum LogLevel {
		DEBUG,
		INFO,
		WARNING,
		ERROR
	}

	private LoggerRedstoneWireless() {
		name = "WirelessRedstone";
	}

	/**
	 * Gets the logger singleton instance.
	 * 
	 * @param name
	 *            Logger domain name.
	 * @return Logger instance.
	 */
	public static LoggerRedstoneWireless getInstance(String name) {
		if (instance == null) instance = new LoggerRedstoneWireless();

		instance.name = name;

		return instance;
	}

	/**
	 * Sets the filtering level based on a string.<br>
	 * "DEBUG","INFO","WARNING","ERROR" are valid strings.
	 * 
	 * @param f
	 *            log level string
	 * @return true if string valid was valid. Defaults to INFO if not.
	 */
	public boolean setFilterLevel(String f) {
		if (f.equals(LogLevel.DEBUG.name())) {
			filterLevel = LogLevel.DEBUG;
			return true;
		} else if (f.equals(LogLevel.INFO.name())) {
			filterLevel = LogLevel.INFO;
			return true;
		} else if (f.equals(LogLevel.WARNING.name())) {
			filterLevel = LogLevel.WARNING;
			return true;
		} else if (f.equals(LogLevel.ERROR.name())) {
			filterLevel = LogLevel.ERROR;
			return true;
		}

		filterLevel = LogLevel.INFO;
		return false;
	}

	private boolean filter(LogLevel lvl) {
		if (filterLevel == LogLevel.DEBUG) return true;
		else if (filterLevel == LogLevel.INFO) {
			if (lvl == LogLevel.DEBUG) return false;
			else return true;
		} else if (filterLevel == LogLevel.WARNING) {
			if (lvl == LogLevel.DEBUG || lvl == LogLevel.INFO) return false;
			else return true;
		} else if (filterLevel == LogLevel.ERROR) {
			if (lvl == LogLevel.ERROR) return true;
			else return false;
		} else return true;
	}

	/**
	 * Write a message to the logger.
	 * 
	 * @param msg
	 *            message text
	 * @param lvl
	 *            message level
	 */
	public void write(boolean isRemote, String msg, LogLevel lvl) {
		String name = this.name;
		if (filter(lvl)) {
			if (writer == null) writer = new LoggerRedstoneWirelessWriter();

			StringBuilder trace = new StringBuilder();
			try {
				throw new Exception();
			} catch (Exception e) {
				StackTraceElement[] c = e.getStackTrace();
				int min = Math.min(	3,
									c.length - 1);
				for (int i = min; i >= 1; i--) {
					trace.append(filterClassName(c[i].getClassName()) + "."
									+ c[i].getMethodName());
					if (i > 1) trace.append("->");
				}
			}

			writer.write(lvl.name() + ":" + getSide(isRemote) + ":" + name
							+ ":" + msg + ":" + trace);
		}
	}

	private String getSide(boolean isRemote) {
		if (!isRemote && FMLCommonHandler.instance().getSide() == Side.CLIENT) return "ISERVER";
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) return "CLIENT";
		if (FMLCommonHandler.instance().getSide() == Side.SERVER) return "SERVER";

		return "UNKNOWN";
	}

	/**
	 * Write an exception stack trace to the logger.
	 * 
	 * @param e
	 *            exception
	 */
	public void writeStackTrace(Exception e) {
		if (writer == null) writer = new LoggerRedstoneWirelessWriter();

		writer.writeStackTrace(e);
		FMLCommonHandler.instance().raiseException(	e,
													e.getMessage(),
													true);
	}

	/**
	 * Logger file handler.
	 * 
	 * @author ali4z
	 */
	private class LoggerRedstoneWirelessWriter {
		private File		file;
		private FileWriter	fstream;
		private PrintWriter	out;

		public LoggerRedstoneWirelessWriter() {
			try {
				file = new File(WirelessRedstone.proxy.getMinecraftDir()
								+ File.separator + "wirelessRedstone.log");
				fstream = new FileWriter(file);
				out = new PrintWriter(fstream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void finalize() {
			try {
				out.close();
				fstream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void write(String msg) {
			out.write("\n" + System.currentTimeMillis() + ":" + msg);
			out.flush();
		}

		public void writeStackTrace(Exception e) {
			out.write("\n" + System.currentTimeMillis() + ":");
			out.flush();
			e.printStackTrace(out);
			out.flush();
			e.printStackTrace();
		}
	}

	/**
	 * Find the class name from a string.<br>
	 * Returns the string beyond the last period ".".
	 * 
	 * @param name
	 *            class name
	 * @return Filtered class name.
	 */

	public static String filterClassName(String name) {
		return name.substring(name.lastIndexOf(".") + 1);
	}
}
