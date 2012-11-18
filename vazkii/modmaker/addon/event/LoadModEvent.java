package vazkii.modmaker.addon.event;

import java.io.File;

import vazkii.modmaker.tree.objective.UserMod;

/**
 * Called when a user mod is loaded (from the dat file, not initted).
 */
public class LoadModEvent extends LMMEvent {

	public final File modFile;
	/** Will be null during the before period! */
	public final UserMod mod;

	public LoadModEvent(EventPeriod period, File file) {
		this(period, file, null);
	}

	public LoadModEvent(EventPeriod period, File file, UserMod mod) {
		super(period);
		modFile = file;
		this.mod = mod;
	}

}
