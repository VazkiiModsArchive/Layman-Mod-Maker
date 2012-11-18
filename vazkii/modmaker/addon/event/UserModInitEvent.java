package vazkii.modmaker.addon.event;

import vazkii.modmaker.tree.objective.UserMod;

/**
 * Called when a LMM Mod is loaded.
 */
public class UserModInitEvent extends TreeBranchInitEvent {

	public UserMod mod;

	public UserModInitEvent(EventPeriod period, UserMod mod) {
		super(period, mod);
		this.mod = mod;
	}

}
