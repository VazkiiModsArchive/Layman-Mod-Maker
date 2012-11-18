package vazkii.modmaker.tree.objective;

import java.util.Map;
import java.util.TreeMap;

import vazkii.modmaker.addon.event.LMMEvent.EventPeriod;
import vazkii.modmaker.addon.event.TreeBranchInitEvent;
import vazkii.modmaker.entrying.AchievementEntry;
import vazkii.modmaker.tree.LeafAchievementID;
import vazkii.modmaker.tree.LeafBoolean;
import vazkii.modmaker.tree.LeafInteger;
import vazkii.modmaker.tree.LeafString;
import vazkii.modmaker.tree.LeafStringStack;
import vazkii.modmaker.tree.LeafableBranch;
import vazkii.modmaker.tree.TreeBranch;
import vazkii.modmaker.tree.TreeLeaf;

import net.minecraft.src.NBTTagCompound;

import net.minecraftforge.common.MinecraftForge;

public class AchievementBranch extends TreeBranch implements LeafableBranch {

	private String label;
	private TreeBranch superBranch;
	private TreeMap<String, TreeBranch> subBranches = new TreeMap();
	private TreeMap<String, TreeLeaf> leaves = new TreeMap();

	@Override
	public TreeBranch init(TreeBranch superBranch, String label) {
		this.label = label;
		this.superBranch = superBranch;
		MinecraftForge.EVENT_BUS.post(new TreeBranchInitEvent(EventPeriod.BEFORE, this));
		addLeaf(((LeafInteger) new LeafAchievementID().init(this, 0, "Achievement ID")).setMin(0));
		addLeaf(((LeafInteger) new LeafInteger().init(this, -1, "Parent Achievement ID")).setMin(-1));
		addLeaf(new LeafInteger().init(this, 0, "Grid X Position"));
		addLeaf(new LeafInteger().init(this, 0, "Grid Y Position"));
		addLeaf(new LeafStringStack().init(this, "1:0", "Trigger Item"));
		addLeaf(new LeafStringStack().init(this, "1:0", "Display Item"));
		addLeaf(new LeafString().init(this, "pickup", "Trigger Type", "pickup", "craft", "smelt"));
		addLeaf(new LeafString().init(this, "", "Name"));
		addLeaf(new LeafString().init(this, "", "Description"));
		addLeaf(new LeafBoolean().init(this, false, "Special"));
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

	private static Map<Integer, AchievementEntry> entries = new TreeMap<Integer, AchievementEntry>();

	@Override
	public void readFromNBT(NBTTagCompound cmp, TreeBranch superBranch) {
		super.readFromNBT(cmp, superBranch);
		entries.put((Integer) leaves().get("Achievement ID").read(), new AchievementEntry().init(this));
	}

	public static void registerAllAchievements() {
		for (Integer i : entries.keySet()) {
			AchievementEntry entry = entries.get(i);
			entry.readEntry();
		}
	}

	@Override
	public void deleteBranch(String branchName) {
	}

	@Override
	public void deleteLeaf(String name) {
	}

}
