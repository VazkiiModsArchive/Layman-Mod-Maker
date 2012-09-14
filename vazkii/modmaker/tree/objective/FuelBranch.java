package vazkii.modmaker.tree.objective;

import java.util.TreeMap;

import net.minecraft.src.NBTTagCompound;
import vazkii.modmaker.entrying.FuelEntry;
import vazkii.modmaker.tree.LeafInteger;
import vazkii.modmaker.tree.LeafStringStack;
import vazkii.modmaker.tree.LeafableBranch;
import vazkii.modmaker.tree.TreeBranch;
import vazkii.modmaker.tree.TreeLeaf;

public class FuelBranch extends TreeBranch implements LeafableBranch {

	private String label;
	private TreeBranch superBranch;
	private TreeMap<String, TreeBranch> subBranches = new TreeMap();
	private TreeMap<String, TreeLeaf> leaves = new TreeMap();

	@Override
	public TreeBranch init(TreeBranch superBranch, String label) {
		this.label = label;
		this.superBranch = superBranch;
		addLeaf(new LeafStringStack().init(this, "1:0", "Item"));
		addLeaf(new LeafInteger().init(this, 0, "Time"));
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
		new FuelEntry().init(this).readEntry();
	}

	@Override
	public boolean isConstant() {
		return true;
	}

	@Override
	public void deleteBranch(String branchName) {}

	@Override
	public void deleteLeaf(String name) {}

}
