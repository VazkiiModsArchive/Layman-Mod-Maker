package vazkii.modmaker.tree;

import net.minecraft.src.GuiScreen;
import net.minecraft.src.NBTTagCompound;
import vazkii.modmaker.gui.GuiLeafDouble;
import vazkii.modmaker.gui.GuiLeafEdit;

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
		return write(_default);
	}

	@Override
	public String label() {
		return label;
	}

	@Override
	public void writeToNBT(NBTTagCompound cmp, TreeBranch superBranch) {
		cmp.setDouble(label(), read());
	}

	@Override
	public void readFromNBT(NBTTagCompound cmp, TreeBranch superBranch) {
		write(cmp.getDouble(label()));
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
