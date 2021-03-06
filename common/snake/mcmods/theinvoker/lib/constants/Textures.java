package snake.mcmods.theinvoker.lib.constants;

import snake.mcmods.theinvoker.utils.Utils;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

public class Textures
{
	public static final String ITEM_BASE_PATH = "/textures/items/";
	public static final String MODEL_BASE_PATH = "/textures/models/";
	public static final String GUI_BASE_PATH = "/textures/gui/";

    public static final ResourceLocation VANILLA_BLOCK_TEXTURE_SHEET = TextureMap.field_110575_b;
    public static final ResourceLocation VANILLA_ITEM_TEXTURE_SHEET = TextureMap.field_110576_c;

	public static final ResourceLocation MODEL_TOTEM_SOUL_ATTRACTIVE = Utils.wrapResourcePath(MODEL_BASE_PATH + "totem_soul_attractive.png");
	public static final ResourceLocation MODEL_TOTEM_SOUL = Utils.wrapResourcePath(MODEL_BASE_PATH + "totem_soul.png");
	public static final ResourceLocation MODEL_TOTEM_RUNE = Utils.wrapResourcePath(MODEL_BASE_PATH + "totem_rune.png");
	public static final ResourceLocation MODEL_TOTEM_MASSACRE = Utils.wrapResourcePath(MODEL_BASE_PATH + "totem_massacre.png");
	public static final ResourceLocation MODEL_SEDUCTION_TOTEM = Utils.wrapResourcePath(MODEL_BASE_PATH + "seduction_totem.png");
	public static final ResourceLocation MODEL_BROKEN_SEDUCTION_TOTEM = MODEL_SEDUCTION_TOTEM;
	public static final ResourceLocation MODEL_ELEMENT_PURIFIER = Utils.wrapResourcePath(MODEL_BASE_PATH + "element_purifier.png");

	public static final String BLOCK_SOUL_SMELTER_TOP_ON = TIGlobal.MOD_ID + ":soul_smelter_top_on";
	public static final String BLOCK_SOUL_SMELTER_TOP_OFF = TIGlobal.MOD_ID + ":soul_smelter_top_off";
	public static final String BLOCK_SOUL_SMELTER_SIDE = TIGlobal.MOD_ID + ":soul_smelter_side";
	public static final String BLOCK_SOUL_SMELTER_BOTTOM = TIGlobal.MOD_ID + ":soul_smelter_bot";
	public static final String BLOCK_SOUL_SMELTER_FRONT_OFF = TIGlobal.MOD_ID + ":soul_smelter_front_off";
	public static final String BLOCK_SOUL_SMELTER_FRONT_ON = TIGlobal.MOD_ID + ":soul_smelter_front_on";

	public static final ResourceLocation GUI_SOUL_SMELTER = Utils.wrapResourcePath(GUI_BASE_PATH + "gui_soul_smelter.png");
	public static final ResourceLocation GUI_ELEMENT_PURIFIER = Utils.wrapResourcePath(GUI_BASE_PATH + "gui_element_purifier.png");
	
	public static final ResourceLocation GRIMOIRE_HUD = Utils.wrapResourcePath(GUI_BASE_PATH+"grimoire_hud.png");
}
