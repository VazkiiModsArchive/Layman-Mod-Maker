package vazkii.modmaker.tree;

import vazkii.modmaker.gui.GuiLeafEdit;
import vazkii.modmaker.gui.GuiLeafInteger;

import net.minecraft.src.GuiScreen;
import net.minecraft.src.NBTTagCompound;

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
		return write(_default);
	}

	@Override
	public String label() {
		return label;
	}

	@Override
	public void writeToNBT(NBTTagCompound cmp, TreeBranch superBranch) {
		cmp.setInteger(label(), read());
	}

	@Override
	public void readFromNBT(NBTTagCompound cmp, TreeBranch superBranch) {
		if (cmp.hasKey(label())) write(cmp.getInteger(label()));
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
