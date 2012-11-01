package vazkii.modmaker.gui;

import vazkii.modmaker.tree.TreeLeaf;

import net.minecraft.src.GuiScreen;

public class GuiLeafDouble extends GuiLeafEdit<Double> {

	double min = Double.MIN_VALUE;
	double max = Double.MAX_VALUE;

	public GuiLeafDouble(GuiScreen parent, TreeLeaf<Double> leaf, String propName, double min, double max) {
		super(parent, leaf, propName);
		this.min = min;
		this.max = max;
	}

	@Override
	public String getError() {
		double d = getValue();
		if (d > max) return propName + " is too high, max is " + max + ".";
		if (d < min) return propName + " is too low, min is " + min + ".";

		return null;
	}

	@Override
	public int getMaxChars() {
		return 10;
	}

	@Override
	public String getValidChars() {
		return "-0123456789.";
	}

	@Override
	public Double getValue() {
		try {
			return Double.parseDouble(contentsField.getText());
		} catch (NumberFormatException e) {
			return 0D;
		}
	}

}
