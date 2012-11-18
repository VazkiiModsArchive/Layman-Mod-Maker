package vazkii.modmaker.addon.event;

import vazkii.modmaker.tree.TreeBranch;

import net.minecraft.src.NBTTagCompound;

/**
 * Called when a Tree Branch is written to NBT.
 */
public class TreeBranchNBTWriteEvent extends LMMEvent {

	public final TreeBranch branch;
	public final TreeBranch superBranch;
	public final NBTTagCompound compound;

	public TreeBranchNBTWriteEvent(EventPeriod period, TreeBranch branch, TreeBranch superBranch, NBTTagCompound cmp) {
		super(period);
		this.branch = branch;
		compound = cmp;
		this.superBranch = superBranch;
	}

}
