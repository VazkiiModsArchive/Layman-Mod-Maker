package vazkii.modmaker.tree;

import vazkii.modmaker.addon.event.LMMEvent.EventPeriod;
import vazkii.modmaker.addon.event.LeafInitEvent;
import vazkii.modmaker.addon.event.LeafNBTReadEvent;
import vazkii.modmaker.addon.event.LeafNBTWriteEvent;
import vazkii.modmaker.gui.GuiLeafDouble;
import vazkii.modmaker.gui.GuiLeafEdit;

import net.minecraft.src.GuiScreen;
import net.minecraft.src.NBTTagCompound;

import net.minecraftforge.common.MinecraftForge;

public class LeafDouble extends TreeLeaf<Double> {

	double d;
	String label;

	double min = Double.MIN_VALUE;
	double max = Double.MAX_VALUE;

	@Override
	public Double read() {
		return d;
	}

	@Override
	public TreeLeaf write(Double value) {
		d = value;
		return this;
	}

	@Override
	public TreeLeaf init(TreeBranch superBranch, Double _default, String label) {
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
		cmp.setDouble(label(), read());
		MinecraftForge.EVENT_BUS.post(new LeafNBTWriteEvent(EventPeriod.AFTER, this, superBranch, cmp));
	}

	@Override
	public void readFromNBT(NBTTagCompound cmp, TreeBranch superBranch) {
		MinecraftForge.EVENT_BUS.post(new LeafNBTReadEvent(EventPeriod.BEFORE, this, superBranch, cmp));
		if (cmp.hasKey(label())) write(cmp.getDouble(label()));
		MinecraftForge.EVENT_BUS.post(new LeafNBTReadEvent(EventPeriod.AFTER, this, superBranch, cmp));
	}

	public LeafDouble setMax(double max) {
		this.max = max;
		return this;
	}

	public LeafDouble setMin(double min) {
		this.min = min;
		return this;
	}

	@Override
	public GuiLeafEdit getLeafEditGui(GuiScreen parent) {
		return new GuiLeafDouble(parent, this, label(), min, max);
	}

	@Override
	public TreeBranch getBranch() {
		return branch;
	}

	TreeBranch branch;

	@Override
	public String displayString() {
		return read().toString();
	}

}
