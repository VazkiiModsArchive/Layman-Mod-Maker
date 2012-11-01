package vazkii.modmaker.tree;

import vazkii.modmaker.gui.GuiLeafEdit;
import vazkii.modmaker.gui.GuiLeafStringPNG;

import net.minecraft.src.GuiScreen;

public class LeafStringPNG extends LeafString {

	@Override
	public GuiLeafEdit getLeafEditGui(GuiScreen parent) {
		return new GuiLeafStringPNG(parent, this, label());
	}

}
