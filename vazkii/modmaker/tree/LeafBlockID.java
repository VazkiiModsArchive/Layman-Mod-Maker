package vazkii.modmaker.tree;

import net.minecraft.src.GuiScreen;
import vazkii.modmaker.gui.GuiLeafBlockID;
import vazkii.modmaker.gui.GuiLeafEdit;

public class LeafBlockID extends LeafInteger {

	@Override
	public GuiLeafEdit getLeafEditGui(GuiScreen parent) {
		return new GuiLeafBlockID(parent, this, label, max, min);
	}

}
