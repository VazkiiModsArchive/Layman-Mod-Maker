package vazkii.modmaker.tree;

import vazkii.modmaker.gui.GuiLeafAchievementID;
import vazkii.modmaker.gui.GuiLeafEdit;

import net.minecraft.src.GuiScreen;

public class LeafAchievementID extends LeafInteger {

	@Override
	public GuiLeafEdit getLeafEditGui(GuiScreen parent) {
		return new GuiLeafAchievementID(parent, this, label, max, min);
	}

}
