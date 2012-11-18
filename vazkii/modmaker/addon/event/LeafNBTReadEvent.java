package vazkii.modmaker.addon.event;

import vazkii.modmaker.tree.TreeBranch;
import vazkii.modmaker.tree.TreeLeaf;

import net.minecraft.src.NBTTagCompound;

/**
 * Called when a TreeLeaf is read from NBT.
 */
public class LeafNBTReadEvent extends LMMEvent {

	public final TreeLeaf leaf;
	public final TreeBranch superBranch;
	public final NBTTagCompound compound;

	public LeafNBTReadEvent(EventPeriod period, TreeLeaf leaf, TreeBranch superBranch, NBTTagCompound cmp) {
		super(period);
		this.leaf = leaf;
		compound = cmp;
		this.superBranch = superBranch;
	}

}
