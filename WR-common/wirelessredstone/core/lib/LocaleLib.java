package wirelessredstone.core.lib;

import cpw.mods.fml.common.registry.LanguageRegistry;

public class LocaleLib {

	private static final String	LANGUAGE_PATH	= "/mods/wirelessredstone/locale/";

	public static String[]		localeFiles		= {
			LANGUAGE_PATH + "de_DE.xml",
			LANGUAGE_PATH + "en_US.xml",
			LANGUAGE_PATH + "nb_NO.xml",
			LANGUAGE_PATH + "nn_NO.xml"		};

	public static void registerLanguages() {
		// For every file specified in the localeFiles class, load them into the
		// Language Registry
		for (String localizationFile : localeFiles) {
			LanguageRegistry.instance().loadLocalization(	localizationFile,
															getLocaleFromFileName(localizationFile),
															isXMLLanguageFile(localizationFile));
		}
		// System.out.println(LanguageRegistry.instance().getStringLocalization("tile.lb.littleblocks.name"));
	}

	/***
	 * Simple test to determine if a specified file name represents a XML file
	 * or not
	 * 
	 * @param fileName
	 *            String representing the file name of the file in question
	 * @return True if the file name represents a XML file, false otherwise
	 */
	public static boolean isXMLLanguageFile(String fileName) {
		return fileName.endsWith(".xml");
	}

	/***
	 * Returns the locale from file name
	 * 
	 * @param fileName
	 *            String representing the file name of the file in question
	 * @return String representation of the locale snipped from the file name
	 */
	public static String getLocaleFromFileName(String fileName) {
		return fileName.substring(	fileName.lastIndexOf('/') + 1,
									fileName.lastIndexOf('.'));
	}

	public static String getLocalizedString(String key) {
		return LanguageRegistry.instance().getStringLocalization(key);
	}

}
