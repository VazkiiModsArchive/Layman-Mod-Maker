package vazkii.modmaker.tree.objective;

import java.util.TreeMap;

import vazkii.modmaker.tree.LeafInteger;
import vazkii.modmaker.tree.LeafableBranch;
import vazkii.modmaker.tree.TreeBranch;
import vazkii.modmaker.tree.TreeLeaf;

public class BlockDropBranch extends TreeBranch implements LeafableBranch {

	private String label;
	private TreeBranch superBranch;
	private TreeMap<String, TreeBranch> subBranches = new TreeMap();
	private TreeMap<String, TreeLeaf> leaves = new TreeMap();

	@Override
	public TreeMap<String, TreeLeaf> leaves() {
		return leaves;
	}

	@Override
	public void addLeaf(TreeLeaf leaf) {
		leaves.put(leaf.label(), leaf);
	}

	@Override
	public TreeBranch init(TreeBranch superBranch, String label) {
		this.superBranch = superBranch;
		this.label = label;
		addLeaf(((LeafInteger) new LeafInteger().init(this, -1, "Drop ID")).setMin(-1).setMax(32000));
		addLeaf(((LeafInteger) new LeafInteger().init(this, -1, "Drop Metadata")).setMin(0));
		addLeaf(((LeafInteger) new LeafInteger().init(this, 1, "Drop Count Min")).setMin(0).setMax(64));
		addLeaf(((LeafInteger) new LeafInteger().init(this, 1, "Drop Count Max")).setMin(0).setMax(64));
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
	public void deleteBranch(String branchName) {}

	@Override
	public String label() {
		return label;
	}

	@Override
	public boolean isConstant() {
		return true;
	}

	@Override
	public void deleteLeaf(String name) {}
}
