package vazkii.modmaker.addon.event;

import vazkii.modmaker.tree.objective.UserMod;

import net.minecraft.src.NBTTagCompound;

public class UserModNBTReadEvent extends TreeBranchNBTReadEvent {

	public final UserMod mod;

	public UserModNBTReadEvent(EventPeriod period, UserMod mod, NBTTagCompound cmp) {
		super(period, mod, null, cmp);
		this.mod = mod;
	}

}
