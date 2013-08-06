package snake.mcmods.theinvoker.spirits;

import snake.mcmods.theinvoker.logic.grimoire.ISpell;
import net.minecraft.potion.Potion;

public interface ISpiritState
{
	String getName();
	Potion[] getStatePotionEffects();
	ISpell[] getStateSpells();
	int getCurrentSpellIndex();
	void setCurrentSpellIndex(int val);
	ISpell getCurrentSpell();
}
