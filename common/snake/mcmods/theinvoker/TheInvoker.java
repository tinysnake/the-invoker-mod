package snake.mcmods.theinvoker;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import snake.mcmods.theinvoker.blocks.TIBlocks;
import snake.mcmods.theinvoker.config.Lang;
import snake.mcmods.theinvoker.constants.TIGlobal;
import snake.mcmods.theinvoker.handlers.EventCenter;
import snake.mcmods.theinvoker.items.TIItems;
import snake.mcmods.theinvoker.proxy.CommonProxy;
import snake.mcmods.theinvoker.utils.others.CreativeTabTI;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid=TIGlobal.MOD_ID,name=TIGlobal.MOD_NAME,version=TIGlobal.VERSION)
@NetworkMod(serverSideRequired=true,clientSideRequired=true)
public class TheInvoker
{
    
    @Instance(TIGlobal.MOD_ID)
    public static TheInvoker instance;
    
    @SidedProxy(clientSide="snake.mcmods.theinvoker.proxy.ClientProxy",
            serverSide="snake.mcmods.theinvoker.proxy.CommonProxy")
    public static CommonProxy proxy;
    
    public static CreativeTabs tab = new CreativeTabTI(CreativeTabs.getNextID(), TIGlobal.MOD_ID);
    
    
    @PreInit
    public void preInit(FMLPreInitializationEvent e)
    {
        
    }
    
    @Init
    public void init(FMLInitializationEvent e)
    {
        TIBlocks.init();
        TIItems.init();
        
        proxy.registerRenderers();
        
        MinecraftForge.EVENT_BUS.register(new EventCenter());
        
        Lang.loadLocalizedFiles();
    }
    
    @PostInit
    public void postInit(FMLPostInitializationEvent e)
    {
        
    }
}
