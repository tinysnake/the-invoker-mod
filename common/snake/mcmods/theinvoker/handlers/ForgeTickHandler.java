package snake.mcmods.theinvoker.handlers;

import java.util.EnumSet;

import snake.mcmods.theinvoker.logic.TotemLogicHandler;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class ForgeTickHandler implements ITickHandler
{

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {
        TotemLogicHandler.INSTANCE.update();
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) {
        
    }

    @Override
    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.WORLD);
    }

    @Override
    public String getLabel() {
        return "TITickHandler";
    }

}
