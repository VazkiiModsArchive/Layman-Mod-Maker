package vazkii.modmaker.tree.objective;

import java.util.TreeMap;

import vazkii.modmaker.tree.TreeBranch;

public class RecipesBranch extends TreeBranch {

	private String label;
	private TreeBranch superBranch;
	private TreeMap<String, TreeBranch> subBranches = new TreeMap();

	@Override
	public TreeBranch init(TreeBranch superBranch, String label) {
		this.label = label;
		this.superBranch = superBranch;
		addBranch("craftingRecipes", new CraftingRecipesBranch().init(this, "Crafting Recipes"));
		addBranch("smeltingRecipes", new SmeltingRecipesBranch().init(this, "Smelting Recipes"));
		addBranch("fuels", new FuelsBranch().init(this, "Fuels"));
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
	public void deleteBranch(String branchName) {
		subBranches.remove(branchName);
	}

	@Override
	public boolean branchesFromLabels() {
		return false;
	}

	@Override
	public boolean isConstant() {
		return true;
	}

}
