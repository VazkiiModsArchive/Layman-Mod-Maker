package vazkii.modmaker.entrying;

import vazkii.modmaker.addon.event.EntryInitEvent;
import vazkii.modmaker.addon.event.EntryReadEvent;
import vazkii.modmaker.addon.event.LMMEvent.EventPeriod;
import vazkii.modmaker.tree.LeafStringStack;
import vazkii.modmaker.tree.objective.SmeltingRecipeBranch;

import net.minecraft.src.ItemStack;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraftforge.common.MinecraftForge;

/**
 * <b>On read sends:<br>
 * <br>
 * </b> Before and After: nothing<br>
 */
public class SmeltingEntry extends ModEntry<SmeltingEntry> {

	SmeltingRecipeBranch branch;

	@Override
	public SmeltingEntry init(Object... params) {
		MinecraftForge.EVENT_BUS.post(new EntryInitEvent(EventPeriod.BEFORE, this));
		branch = (SmeltingRecipeBranch) params[0];
		MinecraftForge.EVENT_BUS.post(new EntryInitEvent(EventPeriod.AFTER, this));
		return this;
	}

	@Override
	public void readEntry() {
		MinecraftForge.EVENT_BUS.post(new EntryReadEvent(EventPeriod.BEFORE, this));
		int input = (Integer) branch.leaves().get("Input ID").read();
		int xp = (Integer) branch.leaves().get("XP Awarded").read();
		ItemStack outputStack = ((LeafStringStack) branch.leaves().get("Output")).getStack();
		if (input <= 0 || outputStack == null) return;

		GameRegistry.addSmelting(input, outputStack, xp);
		MinecraftForge.EVENT_BUS.post(new EntryReadEvent(EventPeriod.AFTER, this));
	}

}
