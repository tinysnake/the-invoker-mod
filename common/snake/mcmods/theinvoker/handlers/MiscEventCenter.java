package snake.mcmods.theinvoker.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import snake.mcmods.theinvoker.config.Lang;
import snake.mcmods.theinvoker.lib.constants.LangKeys;

public class MiscEventCenter
{
	@ForgeSubscribe
	public void handlePlayerJoinWorldEvent(EntityJoinWorldEvent e)
	{
		if (e.world.isRemote)
		{
			EntityPlayer p = Minecraft.getMinecraft().thePlayer;
			if (e.entity == p)
			{
				p.sendChatToPlayer(Lang.getLocalizedStr(LangKeys.TEXT_WELCOME));
			}
		}
	}
}
