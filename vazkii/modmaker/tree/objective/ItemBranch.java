package vazkii.modmaker.tree.objective;

import java.util.TreeMap;

import vazkii.modmaker.addon.event.LMMEvent.EventPeriod;
import vazkii.modmaker.addon.event.TreeBranchInitEvent;
import vazkii.modmaker.entrying.ItemEntry;
import vazkii.modmaker.tree.LeafBoolean;
import vazkii.modmaker.tree.LeafInteger;
import vazkii.modmaker.tree.LeafItemID;
import vazkii.modmaker.tree.LeafSprite;
import vazkii.modmaker.tree.LeafString;
import vazkii.modmaker.tree.LeafableBranch;
import vazkii.modmaker.tree.TreeBranch;
import vazkii.modmaker.tree.TreeLeaf;

import net.minecraft.src.NBTTagCompound;

import net.minecraftforge.common.MinecraftForge;

public class ItemBranch extends TreeBranch implements LeafableBranch {

	private String label;
	private TreeBranch superBranch;
	private TreeMap<String, TreeBranch> subBranches = new TreeMap();
	private TreeMap<String, TreeLeaf> leaves = new TreeMap();

	@Override
	public TreeBranch init(TreeBranch superBranch, String label) {
		this.label = label;
		this.superBranch = superBranch;
		MinecraftForge.EVENT_BUS.post(new TreeBranchInitEvent(EventPeriod.BEFORE, this));
		addBranch("foodStats", new FoodStatsBranch().init(this, "Food Stats"));
		addLeaf(new LeafString().init(this, "normal", "Item Type", "normal", "food"));
		addLeaf(((LeafInteger) new LeafItemID().init(this, 0, "Item ID")).setMax(32000).setMin(256));
		addLeaf(new LeafSprite().init(this, 0, "Sprite"));
		addLeaf(((LeafInteger) new LeafInteger().init(this, 64, "Max Stack Size")).setMax(64).setMin(1));
		addLeaf(new LeafBoolean().init(this, false, "Full 3D"));
		addLeaf(new LeafString().init(this, "", "Item Name"));
		addLeaf(((LeafInteger) new LeafInteger().init(this, 0xFFFFFF, "Color Overlay")).setMax(0xFFFFFF).setMin(0x000000));
		addLeaf(new LeafBoolean().init(this, false, "Shine"));
		addLeaf(((LeafInteger) new LeafInteger().init(this, 0, "Rarity")).setMax(3).setMin(0));
		addLeaf(((LeafInteger) new LeafInteger().init(this, 0, "Creative Tab")).setMax(12).setMin(0));
		addLeaf(new LeafString().init(this, "none", "Brewing Effect", "none", "wart", "glowstone", "redstone", "ferm_eye", "magma", "sugar", "melon", "eye", "tear", "blaze", "carrot", "gunpowder"));
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
	public boolean isConstant() {
		return true;
	}

	@Override
	public boolean branchesFromLabels() {
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound cmp, TreeBranch superBranch) {
		super.readFromNBT(cmp, superBranch);
		new ItemEntry().init(this).readEntry();
	}

	@Override
	public void deleteBranch(String branchName) {
	}

	@Override
	public void deleteLeaf(String name) {
	}
}
