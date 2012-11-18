package vazkii.modmaker.tree;

import vazkii.modmaker.addon.AddonMarker;
import vazkii.modmaker.addon.Addonable;
import vazkii.modmaker.addon.LMMAddon;
import vazkii.modmaker.gui.GuiLeafEdit;

import net.minecraft.src.GuiScreen;
import net.minecraft.src.NBTTagCompound;

public abstract class TreeLeaf<T> implements Addonable {

	private LMMAddon addon;

	public abstract T read();

	public abstract String displayString();

	public abstract TreeLeaf write(T value);

	public abstract TreeLeaf init(TreeBranch superBranch, T _default, String label);

	public abstract TreeBranch getBranch();

	public abstract String label();

	public abstract void writeToNBT(NBTTagCompound cmp, TreeBranch superBranch);

	public abstract void readFromNBT(NBTTagCompound cmp, TreeBranch superBranch);

	public abstract GuiLeafEdit getLeafEditGui(GuiScreen parent);

	public final TreeLeaf setAddon(LMMAddon addon) {
		this.addon = addon;
		return this;
	}

	public final LMMAddon getAddon() {
		return this instanceof AddonMarker ? ((AddonMarker) this).getProvidingAddon(this) : addon;
	}

	public final boolean isAddonFeature() {
		return getAddon() != null;
	}
}
