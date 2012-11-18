package vazkii.modmaker.addon.event;

import vazkii.modmaker.entrying.ModEntry;

/**
 * Called when a ModEntry is read.
 */
public class EntryReadEvent extends LMMEvent {

	public final ModEntry entry;
	/**
	 * Check documentation on the events to see what extra data is sent.
	 */
	public final Object[] extraData;

	public EntryReadEvent(EventPeriod period, ModEntry entry, Object... extraData) {
		super(period);
		this.entry = entry;
		this.extraData = extraData;
	}

}
