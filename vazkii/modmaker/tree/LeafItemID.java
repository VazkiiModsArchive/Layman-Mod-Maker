package vazkii.modmaker.tree;

import net.minecraft.src.GuiScreen;
import vazkii.modmaker.gui.GuiLeafEdit;
import vazkii.modmaker.gui.GuiLeafItemID;

public class LeafItemID extends LeafInteger {

	@Override
	public GuiLeafEdit getLeafEditGui(GuiScreen parent) {
		return new GuiLeafItemID(parent, this, label, max, min);
	}

}
