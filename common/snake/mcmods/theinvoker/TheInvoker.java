package snake.mcmods.theinvoker;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.potion.Potion;
import snake.mcmods.theinvoker.blocks.TIBlocks;
import snake.mcmods.theinvoker.client.gui.TIGuiHanlder;
import snake.mcmods.theinvoker.config.Lang;
import snake.mcmods.theinvoker.energy.TIEnergy;
import snake.mcmods.theinvoker.items.TIItems;
import snake.mcmods.theinvoker.lib.constants.TIGlobal;
import snake.mcmods.theinvoker.logic.elempurifier.ElementPurifierMisc;
import snake.mcmods.theinvoker.logic.soulsmelter.SoulSmelterMisc;
import snake.mcmods.theinvoker.net.PacketHandler;
import snake.mcmods.theinvoker.utils.others.CreativeTabTI;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
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

	@SidedProxy(clientSide = "snake.mcmods.theinvoker.client.ClientProxy", serverSide = "snake.mcmods.theinvoker.CommonProxy")
	public static CommonProxy proxy;

	public static CreativeTabs tab = new CreativeTabTI(CreativeTabs.getNextID(), TIGlobal.MOD_ID);

	@EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		for(Field f:Potion.class.getDeclaredFields())
		{
			int modifier = f.getModifiers();
			if(Modifier.isStatic(modifier)&&Modifier.isFinal(modifier)&&f.getType().equals(Potion[].class))
			{
				try
                {
					Field modfield;
	                modfield = Field.class.getDeclaredField("modifiers");
					modfield.setAccessible(true);
					modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);
					Potion[] potionTypes = (Potion[])f.get(null);
					final Potion[] newPotionTypes = new Potion[256];
					System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length);
					f.set(null, newPotionTypes);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
			}
		}
	}

	@EventHandler
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

	@EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{

	}
}
