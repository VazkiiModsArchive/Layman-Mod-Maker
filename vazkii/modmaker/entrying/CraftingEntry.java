package vazkii.modmaker.entrying;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.CraftingManager;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ShapedRecipes;
import net.minecraft.src.ShapelessRecipes;
import vazkii.modmaker.tree.LeafStringStack;
import vazkii.modmaker.tree.objective.CraftingRecipeBranch;

public class CraftingEntry extends ModEntry<CraftingEntry> {

	CraftingRecipeBranch branch;

	@Override
	public CraftingEntry init(Object... params) {
		branch = (CraftingRecipeBranch) params[0];
		return this;
	}

	@Override
	public void readEntry() {
		String s = (String) branch.leaves().get("Recipe").read();
		boolean shapeless = (Boolean) branch.leaves().get("Shapeless").read();

		int width;
		int height = 3;

		String[] lines = s.split(";");
		String[][] stacks = new String[3][3];
		ItemStack output = LeafStringStack.getStack(lines[3]);
		if (output == null) return;

		for (int i = 0; i < 3; i++)
			stacks[i] = lines[i].split(",");

		if (shapeless) {
			List<ItemStack> stacksList = new ArrayList();
			for (String[] s1 : stacks)
				for (String s2 : s1) {
					ItemStack stack = LeafStringStack.getStack(s2);
					if (stack != null) stacksList.add(stack);
				}
			CraftingManager.getInstance().getRecipeList().add(new ShapelessRecipes(output, stacksList));
			return;
		}

		int maxHeight = 0;
		boolean[] hasLines = new boolean[3];
		boolean[] hasColumns = new boolean[3];

		for (int i = 0; i < 3; i++) {
			int nonNullStacks = 0;
			for (int i1 = 0; i1 < 3; i1++)
				if (LeafStringStack.getStack(stacks[i][i1]) != null) nonNullStacks++;
			if (nonNullStacks != 0) hasLines[i] = true;
			maxHeight = Math.max(maxHeight, nonNullStacks);
		}

		for (int i = 0; i < 3; i++) {
			boolean found = false;
			for (int i1 = 0; i1 < 3; i1++) {
				String stack = stacks[i1][i];
				if (stack == null || stack.equals("x")) continue;
				found = true;
			}
			if (found) hasColumns[i] = true;
		}

		width = maxHeight;
		int maxWidth = 3;

		if (!hasLines[1] && !hasLines[2]) maxWidth -= 2;
		else if (!hasLines[1] || !hasLines[2]) maxWidth--;

		if (!hasLines[0]) maxWidth--;
		height = maxWidth;

		System.out.println(maxWidth + " " + maxHeight);

		if (maxWidth != 0 && maxHeight != 0) {
			List<ItemStack> stacksList = new ArrayList();
			for (int i = 0; i < 3; i++) {
				if (!hasColumns[i]) continue;

				for (int i1 = 0; i1 < 3; i1++) {
					if (!hasLines[i1]) continue;

					String stackString = stacks[i1][i];

					ItemStack stack = LeafStringStack.getStack(stackString);
					if (stack == null) {
						if (i == 0 || i == 1 && width > 1 || i == 2 && width > 2) stacksList.add(null);
					} else stacksList.add(stack.copy());
				}
			}

			CraftingManager.getInstance().getRecipeList().add(new ShapedRecipes(width, height, stacksList.toArray(new ItemStack[stacksList.size()]), output));
		}
	}
}
