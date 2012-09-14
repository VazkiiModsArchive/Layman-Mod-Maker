package vazkii.modmaker.tree;

import net.minecraft.src.GuiScreen;
import vazkii.modmaker.gui.GuiLeafCraftingMatrix;
import vazkii.modmaker.gui.GuiLeafEdit;

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
