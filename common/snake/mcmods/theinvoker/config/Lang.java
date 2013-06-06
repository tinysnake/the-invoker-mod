package snake.mcmods.theinvoker.config;

import snake.mcmods.theinvoker.lib.constants.TIGlobal;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class Lang
{
	public final static String[] LOCALIZE_FILES =
	{ "en_US", "zh_CN" };

	private final static String FILE_DIR = "/mods/" + TIGlobal.MOD_ID + "/lang/";

	public static void loadLocalizedFiles()
	{
		for (String f : LOCALIZE_FILES)
		{
			LanguageRegistry.instance().loadLocalization(FILE_DIR + f + ".txt", f, false);
		}
	}

	public static String getLocalizedStr(String key)
	{
		String val = LanguageRegistry.instance().getStringLocalization(key);
		if (val == null || val == "")
		{
			val = LanguageRegistry.instance().getStringLocalization(key, "en_US");
		}
		return val;
	}
}
