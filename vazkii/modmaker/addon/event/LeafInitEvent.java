package vazkii.modmaker.addon.event;

import vazkii.modmaker.addon.event.LMMEvent.EventPeriod;
import vazkii.modmaker.addon.event.LMMEvent.Forceful;
import vazkii.modmaker.tree.TreeLeaf;

/**
 * Called when a TreeLeaf inits.
 */
@Forceful(EventPeriod.DURING)
public class LeafInitEvent extends LMMEvent {

	public final TreeLeaf leaf;

	public LeafInitEvent(EventPeriod period, TreeLeaf leaf) {
		super(period);
		this.leaf = leaf;
	}

}
