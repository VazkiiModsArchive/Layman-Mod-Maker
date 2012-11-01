package vazkii.modmaker.tree.objective;

import java.util.TreeMap;

import vazkii.modmaker.tree.LeafBoolean;
import vazkii.modmaker.tree.LeafDouble;
import vazkii.modmaker.tree.LeafInteger;
import vazkii.modmaker.tree.LeafableBranch;
import vazkii.modmaker.tree.TreeBranch;
import vazkii.modmaker.tree.TreeLeaf;

public class FoodStatsBranch extends TreeBranch implements LeafableBranch {

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
		addBranch("potionEffects", new PotionEffectBranch().init(this, "Potion Effect"));
		addLeaf(((LeafInteger) new LeafInteger().init(this, 0, "Food Value")).setMax(20).setMin(0));
		addLeaf(((LeafDouble) new LeafDouble().init(this, 0D, "Saturation Value")).setMax(2.0).setMin(0D));
		addLeaf(new LeafBoolean().init(this, false, "Wolf Food"));
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
	public boolean branchesFromLabels() {
		return false;
	}

	@Override
	public void deleteLeaf(String name) {
	}

}
