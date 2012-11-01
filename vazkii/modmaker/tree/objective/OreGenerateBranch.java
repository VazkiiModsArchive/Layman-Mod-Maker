package vazkii.modmaker.tree.objective;

import java.util.TreeMap;

import vazkii.modmaker.entrying.OreEntry;
import vazkii.modmaker.tree.LeafInteger;
import vazkii.modmaker.tree.LeafString;
import vazkii.modmaker.tree.LeafableBranch;
import vazkii.modmaker.tree.TreeBranch;
import vazkii.modmaker.tree.TreeLeaf;

import net.minecraft.src.NBTTagCompound;

public class OreGenerateBranch extends TreeBranch implements LeafableBranch {

	private String label;
	private TreeBranch superBranch;
	private TreeMap<String, TreeBranch> subBranches = new TreeMap();
	private TreeMap<String, TreeLeaf> leaves = new TreeMap();

	@Override
	public TreeBranch init(TreeBranch superBranch, String label) {
		this.label = label;
		this.superBranch = superBranch;
		addLeaf(new LeafString().init(this, "surface", "World", "surface", "nether", "ender", "flat"));
		addLeaf(((LeafInteger) new LeafInteger().init(this, 0, "Ore Density")).setMin(0));
		addLeaf(((LeafInteger) new LeafInteger().init(this, 12, "Max Vein Size")).setMin(0));
		addLeaf(((LeafInteger) new LeafInteger().init(this, 0, "Min Height")).setMin(0).setMax(256));
		addLeaf(((LeafInteger) new LeafInteger().init(this, 64, "Max Height")).setMin(0).setMax(256));
		addLeaf(((LeafInteger) new LeafInteger().init(this, 1, "Block to Replace")).setMin(0).setMax(4096));
		addLeaf(((LeafInteger) new LeafInteger().init(this, 0, "Block to Set")).setMin(0).setMax(4096));
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
		new OreEntry().init(this).readEntry();
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
