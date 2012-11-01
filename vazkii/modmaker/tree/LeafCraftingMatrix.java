package vazkii.modmaker.tree;

import vazkii.modmaker.gui.GuiLeafCraftingMatrix;
import vazkii.modmaker.gui.GuiLeafEdit;

import net.minecraft.src.GuiScreen;

public class LeafCraftingMatrix extends LeafString {

	@Override
	public GuiLeafEdit getLeafEditGui(GuiScreen parent) {
		return new GuiLeafCraftingMatrix(parent, this, label());
	}

	@Override
	public String displayString() {
		return "Output: " + read().split(";")[3];
	}

}
