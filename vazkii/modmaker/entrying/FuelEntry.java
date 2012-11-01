package vazkii.modmaker.entrying;

import vazkii.codebase.common.CommonUtils;
import vazkii.modmaker.tree.LeafStringStack;
import vazkii.modmaker.tree.objective.FuelBranch;

import net.minecraft.src.ItemStack;

import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.registry.GameRegistry;

public class FuelEntry extends ModEntry<FuelEntry> {

	int time;
	ItemStack stack;

	@Override
	public FuelEntry init(Object... params) {
		FuelBranch fuel = (FuelBranch) params[0];
		time = (Integer) fuel.leaves().get("Time").read();
		ItemStack stack = ((LeafStringStack) fuel.leaves().get("Item")).getStack();
		this.stack = stack;
		time *= 20;
		return this;
	}

	@Override
	public void readEntry() {
		if (stack == null) return;

		GameRegistry.registerFuelHandler(new FuelHandler(time, stack));
	}

	public class FuelHandler implements IFuelHandler {

		ItemStack stack;
		int time;

		public FuelHandler(int time, ItemStack stack) {
			this.stack = stack;
			this.time = time;
		}

		@Override
		public int getBurnTime(ItemStack fuel) {
			return CommonUtils.areStacksEqualIgnoreSize(stack, fuel) ? time : 0;
		}
	}

}
