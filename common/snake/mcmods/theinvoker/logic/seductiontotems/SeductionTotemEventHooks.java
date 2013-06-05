package snake.mcmods.theinvoker.logic.seductiontotems;

import snake.mcmods.theinvoker.logic.ai.EntityAISeduction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class SeductionTotemEventHooks
{
    @ForgeSubscribe
    public void handleLivingEntityJoinedTheWorld(EntityJoinWorldEvent e)
    {
        if(e.world.isRemote)
            addSeductionAIToEntity(e.entity);
    }

    public void addSeductionAIToEntity(Entity entity)
    {
        if (!(entity instanceof EntityLiving))
            return;
        EntityLiving e = (EntityLiving) entity;
        for (String name : SeductionTotemMisc.SEDUCTION_AI_LIST)
        {
            if (name.equals(entity.getEntityName()))
            {
                //int size = e.tasks.taskEntries.size();
                e.tasks.addTask(4, new EntityAISeduction(e));
                break;
            }
        }
    }
}
