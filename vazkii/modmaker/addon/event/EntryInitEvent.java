package vazkii.modmaker.addon.event;

import vazkii.modmaker.entrying.ModEntry;

/**
 * Called when a ModEntry inits.
 */
public class EntryInitEvent extends LMMEvent {

	public final ModEntry entry;

	public EntryInitEvent(EventPeriod period, ModEntry entry) {
		super(period);
		this.entry = entry;
	}

}
