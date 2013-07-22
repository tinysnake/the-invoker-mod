package snake.mcmods.theinvoker.logic.grimoires;

import java.util.ArrayList;
import java.util.HashMap;

import cpw.mods.fml.common.IPlayerTracker;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import snake.mcmods.theinvoker.items.ItemGrimoire;
import snake.mcmods.theinvoker.lib.GrimoireInfo;

public class GrimoireRegistry
{
	public GrimoireRegistry()
	{
		allGrimoires = new ArrayList<GrimoireInfo>();
		activedGrimoires = new HashMap<String, ItemGrimoire>();
	}
	private ArrayList<GrimoireInfo> allGrimoires;
	private HashMap<String, ItemGrimoire> activedGrimoires;
}
