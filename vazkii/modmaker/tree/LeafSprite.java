package vazkii.modmaker.tree;

import vazkii.modmaker.gui.GuiLeafEdit;
import vazkii.modmaker.gui.GuiLeafSprite;

import net.minecraft.src.GuiScreen;
import net.minecraft.src.NBTBase;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagInt;

public class LeafSprite extends TreeLeaf {

	Object obj;
	String label;

	@Override
	public Object read() {
		return obj;
	}

	@Override
	public TreeLeaf write(Object value) {
		obj = value;
		return this;
	}

	@Override
	public TreeLeaf init(TreeBranch superBranch, Object _default, String label) {
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
		if (obj instanceof String) cmp.setString(label(), (String) read());
		else if (obj instanceof Integer) cmp.setInteger(label(), (Integer) read());
	}

	@Override
	public void readFromNBT(NBTTagCompound cmp, TreeBranch superBranch) {
		if (cmp.hasKey(label())) {
			NBTBase tag = cmp.getTag(label());
			if (tag instanceof NBTTagInt) write(cmp.getInteger(label()));
			else write(cmp.getString(label()));
		}
	}

	@Override
	public GuiLeafEdit getLeafEditGui(GuiScreen parent) {
		return new GuiLeafSprite(parent, this, "Sprite");
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
