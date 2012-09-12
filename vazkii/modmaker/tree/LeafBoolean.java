package vazkii.modmaker.tree;

import net.minecraft.src.GuiScreen;
import net.minecraft.src.NBTTagCompound;
import vazkii.modmaker.gui.GuiLeafBoolean;
import vazkii.modmaker.gui.GuiLeafEdit;

public class LeafBoolean extends TreeLeaf<Boolean> {

	boolean b;
	String label;

	@Override
	public Boolean read() {
		return b;
	}

	@Override
	public TreeLeaf write(Boolean value) {
		b = value;
		return this;
	}

	@Override
	public TreeLeaf init(TreeBranch superBranch, Boolean _default, String label) {
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
		cmp.setBoolean(label(), read());
	}

	@Override
	public void readFromNBT(NBTTagCompound cmp, TreeBranch superBranch) {
		write(cmp.getBoolean(label()));
	}

	@Override
	public GuiLeafEdit getLeafEditGui(GuiScreen parent) {
		return new GuiLeafBoolean(parent, this, label());
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
