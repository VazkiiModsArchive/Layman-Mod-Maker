package vazkii.modmaker.gui;

import net.minecraft.src.GuiScreen;
import vazkii.modmaker.tree.TreeLeaf;

public class GuiLeafInteger extends GuiLeafEdit<Integer> {

	int max = Integer.MAX_VALUE;
	int min = Integer.MIN_VALUE;

	public GuiLeafInteger(GuiScreen parent, TreeLeaf leaf, String propName, int max, int min) {
		super(parent, leaf, propName);
		this.max = max;
		this.min = min;
	}

	@Override public String getValidChars() {
		return "-0123456789";
	}

	@Override public String getError() {
		int i = getValue();
		if (i > max) return propName + " is too high, max is " + max + ".";
		if (i < min) return propName + " is too low, min is " + min + ".";

		return null;
	}

	@Override public int getMaxChars() {
		return 10;
	}

	@Override
	public Integer getValue() {
		try {
			return Integer.parseInt(contentsField.getText());
		} catch (NumberFormatException e) {
			return 0;
		}
	}

}
