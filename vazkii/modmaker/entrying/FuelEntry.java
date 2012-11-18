package vazkii.modmaker.entrying;

import vazkii.codebase.common.CommonUtils;
import vazkii.modmaker.addon.event.EntryInitEvent;
import vazkii.modmaker.addon.event.EntryReadEvent;
import vazkii.modmaker.addon.event.LMMEvent.EventPeriod;
import vazkii.modmaker.tree.LeafStringStack;
import vazkii.modmaker.tree.objective.FuelBranch;

import net.minecraft.src.ItemStack;

import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraftforge.common.MinecraftForge;

/**
 * <b>On read sends:<br>
 * <br>
 * </b> Before and After: nothing<br>
 */
public class FuelEntry extends ModEntry<FuelEntry> {

	int time;
	ItemStack stack;

	@Override
	public FuelEntry init(Object... params) {
		MinecraftForge.EVENT_BUS.post(new EntryInitEvent(EventPeriod.BEFORE, this));
		FuelBranch fuel = (FuelBranch) params[0];
		time = (Integer) fuel.leaves().get("Time").read();
		ItemStack stack = ((LeafStringStack) fuel.leaves().get("Item")).getStack();
		this.stack = stack;
		time *= 20;
		MinecraftForge.EVENT_BUS.post(new EntryInitEvent(EventPeriod.AFTER, this));
		return this;
	}

	@Override
	public void readEntry() {
		MinecraftForge.EVENT_BUS.post(new EntryReadEvent(EventPeriod.BEFORE, this));
		if (stack == null) return;

		GameRegistry.registerFuelHandler(new FuelHandler(time, stack));
		MinecraftForge.EVENT_BUS.post(new EntryReadEvent(EventPeriod.AFTER, this));
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
