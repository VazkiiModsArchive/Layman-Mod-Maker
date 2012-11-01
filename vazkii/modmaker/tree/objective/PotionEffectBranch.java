package vazkii.modmaker.tree.objective;

import java.util.TreeMap;

import vazkii.modmaker.tree.LeafDouble;
import vazkii.modmaker.tree.LeafInteger;
import vazkii.modmaker.tree.LeafableBranch;
import vazkii.modmaker.tree.TreeBranch;
import vazkii.modmaker.tree.TreeLeaf;

public class PotionEffectBranch extends TreeBranch implements LeafableBranch {

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
		addLeaf(((LeafInteger) new LeafInteger().init(this, 0, "Potion ID")).setMax(19).setMin(0));
		addLeaf(((LeafInteger) new LeafInteger().init(this, 0, "Potion Time")).setMin(0));
		addLeaf(((LeafInteger) new LeafInteger().init(this, 0, "Potion Level")).setMax(4).setMin(0));
		addLeaf(((LeafDouble) new LeafDouble().init(this, 1D, "Chance")).setMax(1D).setMin(0D));
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
	public void deleteBranch(String branchName) {
	}

	@Override
	public String label() {
		return label;
	}

	@Override
	public boolean isConstant() {
		return true;
	}

	@Override
	public void deleteLeaf(String name) {
	}

}
