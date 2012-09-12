package vazkii.modmaker.entrying;

import net.minecraft.src.ItemStack;
import vazkii.modmaker.tree.LeafStringStack;
import vazkii.modmaker.tree.objective.SmeltingRecipeBranch;
import cpw.mods.fml.common.registry.GameRegistry;

public class SmeltingEntry extends ModEntry<SmeltingEntry> {

	SmeltingRecipeBranch branch;

	@Override
	public SmeltingEntry init(Object... params) {
		branch = (SmeltingRecipeBranch) params[0];
		return this;
	}

	@Override
	public void readEntry() {
		int input = (Integer) branch.leaves().get("Input ID").read();
		int xp = (Integer) branch.leaves().get("XP Awarded").read();
		ItemStack outputStack = ((LeafStringStack) branch.leaves().get("Output")).getStack();
		if (input <= 0 || outputStack == null) return;

		GameRegistry.addSmelting(input, outputStack, xp);
	}

}
