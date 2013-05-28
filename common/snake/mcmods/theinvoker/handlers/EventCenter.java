package snake.mcmods.theinvoker.handlers;

import snake.mcmods.theinvoker.config.Lang;
import snake.mcmods.theinvoker.constants.LangKeys;
import snake.mcmods.theinvoker.logic.TotemLogicHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

public class EventCenter
{
    @ForgeSubscribe
    public void handlePlayerJoinWorldEvent(EntityJoinWorldEvent e)
    {
        EntityPlayer p = Minecraft.getMinecraft().thePlayer;
        if(e.entity== p)
        {
            p.sendChatToPlayer(Lang.getLocalizedStr(LangKeys.TEXT_WELCOME));
        }
    }
    
    @ForgeSubscribe
    public void handleEntityDropsEvent(LivingDropsEvent e)
    {
        TotemLogicHandler.INSTANCE.updateLogicWhileEntityLivingDrops(e);
    }
}
