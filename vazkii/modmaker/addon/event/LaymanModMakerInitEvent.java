package vazkii.modmaker.addon.event;

import cpw.mods.fml.common.event.FMLStateEvent;

/**
 * Called when Layman Mod Maker is loaded.
 */
public class LaymanModMakerInitEvent extends LMMEvent {

	public final FMLStateEvent stateEvent;

	public LaymanModMakerInitEvent(EventPeriod period, FMLStateEvent stateEvent) {
		super(period);
		this.stateEvent = stateEvent;
	}

}
