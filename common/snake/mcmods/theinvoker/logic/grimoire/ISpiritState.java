package snake.mcmods.theinvoker.logic.grimoire;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;

public interface ISpiritState
{
	String getName();
	Potion[] getStatePotionEffects();
	ISpell[] getStateSpells();
	int getCurrentSpellIndex();
	void setCurrentSpellIndex(int val);
	ISpell getCurrentSpell();
	void onHit(EntityPlayer ep);
	void onAttacked(EntityPlayer p);
}
