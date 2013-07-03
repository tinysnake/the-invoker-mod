package snake.mcmods.theinvoker;

import net.minecraft.creativetab.CreativeTabs;
import snake.mcmods.theinvoker.blocks.TIBlocks;
import snake.mcmods.theinvoker.config.Lang;
import snake.mcmods.theinvoker.energy.TIEnergy;
import snake.mcmods.theinvoker.gui.TIGuiHanlder;
import snake.mcmods.theinvoker.items.TIItems;
import snake.mcmods.theinvoker.lib.constants.TIGlobal;
import snake.mcmods.theinvoker.logic.elempurifier.ElementPurifierMisc;
import snake.mcmods.theinvoker.logic.soulsmelter.SoulSmelterMisc;
import snake.mcmods.theinvoker.net.PacketHandler;
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
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = TIGlobal.MOD_ID, name = TIGlobal.MOD_NAME, version = TIGlobal.VERSION)
@NetworkMod(serverSideRequired = true, clientSideRequired = false, channels = { TIGlobal.CHANNEL }, packetHandler = PacketHandler.class)
public class TheInvoker
{

	@Instance(TIGlobal.MOD_ID)
	public static TheInvoker instance;

	@SidedProxy(clientSide = "snake.mcmods.theinvoker.ClientProxy", serverSide = "snake.mcmods.theinvoker.CommonProxy")
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
		TIEnergy.init();

		NetworkRegistry.instance().registerGuiHandler(TheInvoker.instance, new TIGuiHanlder());

		proxy.registerTileEntities();

		proxy.initRenderingStuff();

		proxy.registerTickHandlers();

		proxy.registerEventHooks();

		Lang.loadLocalizedFiles();

		SoulSmelterMisc.initDefaultRecipies();
		ElementPurifierMisc.initDefaultRecipies();
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent e)
	{

	}
}
