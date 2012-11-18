package vazkii.modmaker.tree;

import vazkii.modmaker.addon.event.LMMEvent.EventPeriod;
import vazkii.modmaker.addon.event.LeafInitEvent;
import vazkii.modmaker.addon.event.LeafNBTReadEvent;
import vazkii.modmaker.addon.event.LeafNBTWriteEvent;
import vazkii.modmaker.gui.GuiLeafEdit;
import vazkii.modmaker.gui.GuiLeafInteger;

import net.minecraft.src.GuiScreen;
import net.minecraft.src.NBTTagCompound;

import net.minecraftforge.common.MinecraftForge;

public class LeafInteger extends TreeLeaf<Integer> {

	int i;
	String label;

	int max = Integer.MAX_VALUE;
	int min = Integer.MIN_VALUE;

	@Override
	public Integer read() {
		return i;
	}

	@Override
	public TreeLeaf write(Integer value) {
		i = value;
		return this;
	}

	@Override
	public TreeLeaf init(TreeBranch superBranch, Integer _default, String label) {
		this.label = label;
		branch = superBranch;
		MinecraftForge.EVENT_BUS.post(new LeafInitEvent(EventPeriod.DURING, this));
		return write(_default);
	}

	@Override
	public String label() {
		return label;
	}

	@Override
	public void writeToNBT(NBTTagCompound cmp, TreeBranch superBranch) {
		MinecraftForge.EVENT_BUS.post(new LeafNBTWriteEvent(EventPeriod.BEFORE, this, superBranch, cmp));
		cmp.setInteger(label(), read());
		MinecraftForge.EVENT_BUS.post(new LeafNBTWriteEvent(EventPeriod.AFTER, this, superBranch, cmp));
	}

	@Override
	public void readFromNBT(NBTTagCompound cmp, TreeBranch superBranch) {
		MinecraftForge.EVENT_BUS.post(new LeafNBTReadEvent(EventPeriod.BEFORE, this, superBranch, cmp));
		if (cmp.hasKey(label())) write(cmp.getInteger(label()));
		MinecraftForge.EVENT_BUS.post(new LeafNBTReadEvent(EventPeriod.AFTER, this, superBranch, cmp));
	}

	public LeafInteger setMax(int max) {
		this.max = max;
		return this;
	}

	public LeafInteger setMin(int min) {
		this.min = min;
		return this;
	}

	@Override
	public GuiLeafEdit getLeafEditGui(GuiScreen parent) {
		return new GuiLeafInteger(parent, this, label, max, min);
	}

	@Override
	public String displayString() {
		return read().toString();
	}

	@Override
	public TreeBranch getBranch() {
		return branch;
	}

	TreeBranch branch;
}
