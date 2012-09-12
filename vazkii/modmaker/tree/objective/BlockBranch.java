package vazkii.modmaker.tree.objective;

import java.util.TreeMap;

import net.minecraft.src.NBTTagCompound;
import vazkii.modmaker.entrying.BlockEntry;
import vazkii.modmaker.tree.LeafBlockID;
import vazkii.modmaker.tree.LeafBoolean;
import vazkii.modmaker.tree.LeafDouble;
import vazkii.modmaker.tree.LeafInteger;
import vazkii.modmaker.tree.LeafSprite;
import vazkii.modmaker.tree.LeafString;
import vazkii.modmaker.tree.LeafableBranch;
import vazkii.modmaker.tree.TreeBranch;
import vazkii.modmaker.tree.TreeLeaf;

public class BlockBranch extends TreeBranch implements LeafableBranch {

	private String label;
	private TreeBranch superBranch;
	private TreeMap<String, TreeBranch> subBranches = new TreeMap();
	private TreeMap<String, TreeLeaf> leaves = new TreeMap();

	@Override
	public TreeBranch init(TreeBranch superBranch, String label) {
		this.label = label;
		this.superBranch = superBranch;
		addBranch("blockDrop", new BlockDropBranch().init(this, "Dropped Item"));
		addLeaf(((LeafInteger) new LeafBlockID().init(this, 0, "Block ID")).setMax(4096).setMin(1));
		addLeaf(new LeafSprite().init(this, 0, "Sprite"));
		addLeaf(new LeafString().init(this, "none", "Step Sound", "none", "powder", "wood", "gravel", "grass", "stone", "metal", "glass", "cloth", "sand"));
		addLeaf(new LeafString().init(this, "ground", "Material", "ground", "wood", "rock", "plant", "glass", "metal"));
		addLeaf(((LeafInteger) new LeafInteger().init(this, 0, "Light Value")).setMax(15).setMin(0));
		addLeaf(((LeafDouble) new LeafDouble().init(this, 0D, "Explosion Resistance")).setMin(0D));
		addLeaf(((LeafDouble) new LeafDouble().init(this, 0D, "Hardness")).setMin(0D));
		addLeaf(new LeafBoolean().init(this, false, "Unbreakable"));
		addLeaf(((LeafDouble) new LeafDouble().init(this, 0.6, "Slipperiness")).setMin(0D));
		addLeaf(((LeafInteger) new LeafInteger().init(this, 0xFFFFFF, "Color Overlay")).setMax(0xFFFFFF).setMin(0x000000));
		addLeaf(new LeafString().init(this, "", "Block Name"));
		addLeaf(((LeafInteger) new LeafInteger().init(this, 0, "Creative Tab")).setMax(12).setMin(0));
		addLeaf(new LeafBoolean().init(this, true, "Opaque"));
		//addLeaf(new LeafBoolean().init(this, false, "Gravity"));
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

	@Override public boolean isConstant() {
		return true;
	}

	@Override public void readFromNBT(NBTTagCompound cmp, TreeBranch superBranch) {
		super.readFromNBT(cmp, superBranch);
		new BlockEntry().init(this).readEntry();
	}

	@Override
	public void deleteBranch(String branchName) {}

	@Override
	public void deleteLeaf(String name) {}

}
