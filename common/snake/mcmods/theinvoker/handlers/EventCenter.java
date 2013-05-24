package snake.mcmods.theinvoker.handlers;

import snake.mcmods.theinvoker.config.Lang;
import snake.mcmods.theinvoker.constants.LangKeys;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class EventCenter
{
    @ForgeSubscribe
    public void handlePlayerJoinWorld(EntityJoinWorldEvent e)
    {
        EntityPlayer p = Minecraft.getMinecraft().thePlayer;
        if(e.entity== p)
        {
            p.sendChatToPlayer(Lang.getLocalizedStr(LangKeys.TEXT_WELCOME));
        }
    }
}
