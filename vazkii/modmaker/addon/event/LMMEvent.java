package vazkii.modmaker.addon.event;

import net.minecraftforge.event.Event;

/**
 * Main class for LMM events.
 */
public abstract class LMMEvent extends Event {

	private final EventPeriod period;

	public LMMEvent(EventPeriod period) {
		this.period = period;
	}

	public EventPeriod getForcefulPeriod() {
		Forceful ann = getClass().getAnnotation(Forceful.class);
		return ann != null ? ann.value() : null;
	}

	public EventPeriod getEventPeriod() {
		return period;
	}

	public enum EventPeriod
	{
		BEFORE, DURING, AFTER;
	}

	/**
	 * Marks that this event will always happen during this period.
	 */
	@interface Forceful {
		EventPeriod value();
	}
}
