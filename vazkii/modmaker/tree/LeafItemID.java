package vazkii.modmaker.tree;

import vazkii.modmaker.gui.GuiLeafEdit;
import vazkii.modmaker.gui.GuiLeafItemID;

import net.minecraft.src.GuiScreen;

public class LeafItemID extends LeafInteger {

	@Override
	public GuiLeafEdit getLeafEditGui(GuiScreen parent) {
		return new GuiLeafItemID(parent, this, label, max, min);
	}

}
