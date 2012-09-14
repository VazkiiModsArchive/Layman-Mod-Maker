package vazkii.modmaker.gui;

import net.minecraft.src.GuiScreen;
import vazkii.modmaker.tree.TreeLeaf;

public class GuiLeafStringPNG extends GuiLeafString {

	public GuiLeafStringPNG(GuiScreen parent, TreeLeaf<String> leaf, String propName) {
		super(parent, leaf, propName);
	}

	@Override
	public String getError() {
		String text = contentsField.getText();
		if (text.equals(".png")) return "You must sepcify a file name!";
		if (!text.endsWith(".png")) return "Sprite file name must end with .png!";

		return super.getError();
	}

}
