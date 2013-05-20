package snake.mcmods.theinvoker;

import snake.mcmods.theinvoker.constants.TIGlobal;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid=TIGlobal.MOD_ID,name=TIGlobal.MOD_NAME,version=TIGlobal.VERSION)
@NetworkMod(serverSideRequired=true,clientSideRequired=true)
public class TheInvoker
{
    @PreInit
    public void preInit(FMLPreInitializationEvent e)
    {
        
    }
    
    @Init
    public void init(FMLInitializationEvent e)
    {
        
    }
    
    @PostInit
    public void postInit(FMLPostInitializationEvent e)
    {
        
    }
}
