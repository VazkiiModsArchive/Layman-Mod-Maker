package vazkii.modmaker.tree;

import net.minecraft.src.GuiScreen;
import net.minecraft.src.NBTTagCompound;
import vazkii.modmaker.gui.GuiLeafEdit;

public abstract class TreeLeaf<T> {

	public abstract T read();

	public abstract String displayString();

	public abstract TreeLeaf write(T value);

	public abstract TreeLeaf init(TreeBranch superBranch, T _default, String label);

	public abstract TreeBranch getBranch();

	public abstract String label();

	public abstract void writeToNBT(NBTTagCompound cmp, TreeBranch superBranch);

	public abstract void readFromNBT(NBTTagCompound cmp, TreeBranch superBranch);

	public abstract GuiLeafEdit getLeafEditGui(GuiScreen parent);
}
