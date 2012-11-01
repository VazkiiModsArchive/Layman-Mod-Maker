package vazkii.modmaker.gui;

import vazkii.modmaker.mod.Achievements;
import vazkii.modmaker.tree.TreeLeaf;

import net.minecraft.src.GuiScreen;

public class GuiLeafAchievementID extends GuiLeafInteger {

	public GuiLeafAchievementID(GuiScreen parent, TreeLeaf leaf, String propName, int max, int min) {
		super(parent, leaf, propName, max, min);
	}

	@Override
	public String getError() {
		int i = getValue();
		if (i > max) return propName + " is too high, max is " + max + ".";
		if (i < min) return propName + " is too low, min is " + min + ".";
		if (Achievements.fromID(i) != null) return "Achievement ID " + i + " is already in use.";

		return null;
	}

}
