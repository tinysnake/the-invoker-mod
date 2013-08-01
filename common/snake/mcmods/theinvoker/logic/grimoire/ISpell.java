package snake.mcmods.theinvoker.logic.grimoire;

import net.minecraft.entity.player.EntityPlayer;

public interface ISpell
{
	String getName();
	boolean getIsInstant();
	int getMaxCastDuration();
	int getMaxChargeDuration();
	void cast(EntityPlayer p);
	void onCastFailed(EntityPlayer p);
	void onCastSuccess(EntityPlayer p);
}
