package vazkii.modmaker.tree;

import net.minecraft.src.GuiScreen;
import net.minecraft.src.NBTTagCompound;
import vazkii.modmaker.gui.GuiLeafEdit;
import vazkii.modmaker.gui.GuiLeafString;

public class LeafString extends TreeLeaf<String> {

	String s;
	String label;
	String[] validTokens;

	@Override
	public String read() {
		return s;
	}

	@Override
	public TreeLeaf write(String value) {
		s = value;
		return this;
	}

	@Override
	public TreeLeaf init(TreeBranch superBranch, String _default, String label) {
		return init(superBranch, _default, label, (String[]) null);
	}

	public TreeLeaf init(TreeBranch superBranch, String _default, String label, final String... validTokens) {
		this.label = label;
		this.validTokens = validTokens;
		branch = superBranch;
		return write(_default);
	}

	@Override
	public String label() {
		return label;
	}

	@Override
	public void writeToNBT(NBTTagCompound cmp, TreeBranch superBranch) {
		cmp.setString(label(), read());
	}

	@Override
	public void readFromNBT(NBTTagCompound cmp, TreeBranch superBranch) {
		write(cmp.getString(label()));
	}

	@Override
	public GuiLeafEdit getLeafEditGui(GuiScreen parent) {
		return new GuiLeafString(parent, this, label(), validTokens);
	}

	@Override
	public TreeBranch getBranch() {
		return branch;
	}

	TreeBranch branch;

	@Override
	public String displayString() {
		return read();
	}

}
