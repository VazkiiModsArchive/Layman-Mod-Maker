package vazkii.modmaker.gui;

import net.minecraft.src.Block;
import net.minecraft.src.GuiScreen;
import vazkii.modmaker.tree.TreeLeaf;

public class GuiLeafBlockID extends GuiLeafInteger {

	public GuiLeafBlockID(GuiScreen parent, TreeLeaf leaf, String propName, int max, int min) {
		super(parent, leaf, propName, max, min);
	}

	@Override public String getError() {
		int i = getValue();
		if (i > max) return propName + " is too high, max is " + max + ".";
		if (i < min) return propName + " is too low, min is " + min + ".";
		if (Block.blocksList[i] != null) return "Block ID " + i + " is already in use.";

		return null;
	}

}
