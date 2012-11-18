package vazkii.modmaker.addon.event;

import vazkii.modmaker.tree.TreeBranch;

/**
 * This is called when a TreeBranch inits.
 */
public class TreeBranchInitEvent extends LMMEvent {

	public final TreeBranch branch;

	public TreeBranchInitEvent(EventPeriod period, TreeBranch branch) {
		super(period);
		this.branch = branch;
	}

}
