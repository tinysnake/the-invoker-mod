package snake.mcmods.theinvoker.logic.grimoire;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import snake.mcmods.theinvoker.lib.constants.TIGlobal;
import snake.mcmods.theinvoker.lib.constants.Textures;
import snake.mcmods.theinvoker.utils.RenderUtils;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class GrimoireHUD implements ITickHandler
{

	public static final GrimoireHUD INSTANCE = new GrimoireHUD();

	private static final int HUD_X = 154;

	private static final int HUD_Y = 180;

	private static final int HUD_WIDTH = 120;

	private static final int HUD_HEIGHT = 10;

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{
		Minecraft mc = Minecraft.getMinecraft();
		if (mc != null && mc.currentScreen == null)
		{
			EntityPlayer player = mc.thePlayer;
			if (player != null)
			{
				GrimoireSystem gsys = GrimoireSystem.INSTANCE;
				if (gsys.getIsCharging() || gsys.getIsCasting())
				{
					mc.renderEngine.func_110577_a(Textures.GRIMOIRE_HUD);
					if (gsys.getIsCasting())
					{
						float prog = (float)gsys.getCastTimer() / gsys.getMaxCastTimer();
						RenderUtils.drawTexturedModalRect(HUD_X, HUD_Y, 0, 0, HUD_WIDTH, HUD_HEIGHT);
						RenderUtils.drawTexturedModalRect(HUD_X, HUD_Y, 0, HUD_HEIGHT * 2, (int)(prog * HUD_WIDTH), HUD_HEIGHT);
						String secStr = String.format("%.1f", (1F - Math.min(1, prog)) * (gsys.getMaxCastTimer() / 20F));
						int fontWidth = mc.fontRenderer.getStringWidth(secStr);
						mc.fontRenderer.drawString(secStr, (TIGlobal.GAME_WIDTH - fontWidth) >> 1, 181, 0x333333);
					}
					else
					{
						float prog = 1 - (gsys.getChargeTimer() / gsys.getMaxChargeTimer());
						RenderUtils.drawTexturedModalRect(HUD_X, HUD_Y, 0, 0, HUD_WIDTH, 10);
						RenderUtils.drawTexturedModalRect(HUD_X, HUD_Y, 0, HUD_HEIGHT, (int)(prog * HUD_WIDTH), HUD_HEIGHT);
						String secStr = String.format("%.1f", Math.min(1, prog) * (gsys.getMaxChargeTimer() / 20F));
						int fontWidth = mc.fontRenderer.getStringWidth(secStr);
						mc.fontRenderer.drawString(secStr, (TIGlobal.GAME_WIDTH - fontWidth) >> 1, 181, 0x333333);
					}
				}
			}
		}
	}

	@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.RENDER);
	}

	@Override
	public String getLabel()
	{
		return TIGlobal.MOD_ID + ":" + this.getClass().getSimpleName();
	}

}
