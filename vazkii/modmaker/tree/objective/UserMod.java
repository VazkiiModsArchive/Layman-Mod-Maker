package vazkii.modmaker.tree.objective;

import java.util.TreeMap;

import vazkii.modmaker.tree.BranchHelper;
import vazkii.modmaker.tree.TreeBranch;

import net.minecraft.src.NBTTagCompound;

public class UserMod extends TreeBranch {

	private TreeMap<String, TreeBranch> subBranches = new TreeMap();
	private String label;

	public String author = "";

	@Override
	public TreeBranch init(TreeBranch superBranch, String label) {
		this.label = label;
		addBranch("images", new ImagesBranch().init(this, "Images"));
		addBranch("items", new ItemsBranch().init(this, "Items"));
		addBranch("blocks", new BlocksBranch().init(this, "Blocks"));
		addBranch("oreGeneration", new OreGenerationBranch().init(this, "Ore Generation"));
		addBranch("recipes", new RecipesBranch().init(this, "Recipes"));
		addBranch("achievements", new AchievementsBranch().init(this, "Achievements"));
		return this;
	}

	@Override
	public TreeMap<String, TreeBranch> subBranches() {
		return subBranches;
	}

	public UserMod setAuthor(String author) {
		this.author = author;
		return this;
	}

	@Override
	public TreeBranch superBranch() {
		return null;
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
	public void addBranch(String branchName, TreeBranch branch) {
		subBranches.put(branchName, branch);
	}

	@Override
	public void deleteBranch(String branchName) {
		subBranches.remove(branchName);
	}

	@Override
	public void writeToNBT(NBTTagCompound cmp, TreeBranch superBranch) {
		cmp.setString("author", author);
		super.writeToNBT(cmp, superBranch);
	}

	@Override
	public void readFromNBT(NBTTagCompound cmp, TreeBranch superBranch) {
		author = cmp.getString("author");
		cmp.getString("label");
		if (cmp.hasKey("Images")) readBranch(cmp.getCompoundTag("Images"));
		if (cmp.hasKey("Items")) readBranch(cmp.getCompoundTag("Items"));
		if (cmp.hasKey("Blocks")) readBranch(cmp.getCompoundTag("Blocks"));
		if (cmp.hasKey("Ore Generation")) readBranch(cmp.getCompoundTag("Ore Generation"));
		if (cmp.hasKey("Recipes")) readBranch(cmp.getCompoundTag("Recipes"));
		if (cmp.hasKey("Achievements")) readBranch(cmp.getCompoundTag("Achievements"));
	}

	private void readBranch(NBTTagCompound cmp) {
		String type = cmp.getString("type");
		TreeBranch branch = BranchHelper.branchFromNBT(cmp).init(this, cmp.getString("label"));
		addBranch(type, branch);
		branch.readFromNBT(cmp, this);
	}
}
