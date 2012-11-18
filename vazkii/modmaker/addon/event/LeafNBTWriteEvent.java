package vazkii.modmaker.addon.event;

import vazkii.modmaker.tree.TreeBranch;
import vazkii.modmaker.tree.TreeLeaf;

import net.minecraft.src.NBTTagCompound;

/**
 * Called when a TreeLeaf is written to NBT.
 */
public class LeafNBTWriteEvent extends LMMEvent {

	public final TreeLeaf leaf;
	public final TreeBranch superBranch;
	public final NBTTagCompound compound;

	public LeafNBTWriteEvent(EventPeriod period, TreeLeaf leaf, TreeBranch superBranch, NBTTagCompound cmp) {
		super(period);
		this.leaf = leaf;
		compound = cmp;
		this.superBranch = superBranch;
	}

}
