package vazkii.modmaker.tree.objective;

import java.util.TreeMap;

import vazkii.modmaker.addon.event.LMMEvent.EventPeriod;
import vazkii.modmaker.addon.event.TreeBranchInitEvent;
import vazkii.modmaker.entrying.CraftingEntry;
import vazkii.modmaker.tree.LeafBoolean;
import vazkii.modmaker.tree.LeafCraftingMatrix;
import vazkii.modmaker.tree.LeafableBranch;
import vazkii.modmaker.tree.TreeBranch;
import vazkii.modmaker.tree.TreeLeaf;

import net.minecraft.src.NBTTagCompound;

import net.minecraftforge.common.MinecraftForge;

public class CraftingRecipeBranch extends TreeBranch implements LeafableBranch {

	private String label;
	private TreeBranch superBranch;
	private TreeMap<String, TreeBranch> subBranches = new TreeMap();
	private TreeMap<String, TreeLeaf> leaves = new TreeMap();

	@Override
	public TreeBranch init(TreeBranch superBranch, String label) {
		this.label = label;
		this.superBranch = superBranch;
		MinecraftForge.EVENT_BUS.post(new TreeBranchInitEvent(EventPeriod.BEFORE, this));
		addLeaf(new LeafCraftingMatrix().init(this, "1:0,x,x;x,x,x;x,x,x;1:1:0", "Recipe"));
		addLeaf(new LeafBoolean().init(this, false, "Shapeless"));
		MinecraftForge.EVENT_BUS.post(new TreeBranchInitEvent(EventPeriod.AFTER, this));
		return this;
	}

	@Override
	public TreeMap<String, TreeBranch> subBranches() {
		return subBranches;
	}

	@Override
	public TreeBranch superBranch() {
		return superBranch;
	}

	@Override
	public void addBranch(String branchName, TreeBranch branch) {
		subBranches.put(branchName, branch);
	}

	@Override
	public String label() {
		return label;
	}

	@Override
	public TreeMap<String, TreeLeaf> leaves() {
		return leaves;
	}

	@Override
	public void addLeaf(TreeLeaf leaf) {
		leaves.put(leaf.label(), leaf);
	}

	@Override
	public void readFromNBT(NBTTagCompound cmp, TreeBranch superBranch) {
		super.readFromNBT(cmp, superBranch);
		new CraftingEntry().init(this).readEntry();
	}

	@Override
	public boolean isConstant() {
		return true;
	}

	@Override
	public void deleteBranch(String branchName) {
	}

	@Override
	public void deleteLeaf(String name) {
	}

}
