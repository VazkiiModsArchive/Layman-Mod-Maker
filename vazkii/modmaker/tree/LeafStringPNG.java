package vazkii.modmaker.tree;

import net.minecraft.src.GuiScreen;
import vazkii.modmaker.gui.GuiLeafEdit;
import vazkii.modmaker.gui.GuiLeafStringPNG;

public class LeafStringPNG extends LeafString {

	@Override
	public GuiLeafEdit getLeafEditGui(GuiScreen parent) {
		return new GuiLeafStringPNG(parent, this, label());
	}

}
