package vazkii.modmaker.gui;

import java.util.Arrays;

import vazkii.modmaker.tree.TreeLeaf;

import net.minecraft.src.GuiScreen;

public class GuiLeafString extends GuiLeafEdit<String> {

	String[] validTokens;

	public GuiLeafString(GuiScreen parent, TreeLeaf<String> leaf, String propName, final String... validTokens) {
		this(parent, leaf, propName);
		this.validTokens = validTokens;
	}

	public GuiLeafString(GuiScreen parent, TreeLeaf<String> leaf, String propName) {
		super(parent, leaf, propName);
	}

	@Override
	public String getError() {
		if (validTokens == null || validTokens.length == 0) return null;

		String text = contentsField.getText();
		if (!Arrays.asList(validTokens).contains(text)) return String.format("Only '%s' are valid.", getValid());
		else return null;
	}

	public String getValid() {
		String valid = "";
		int iteration = 0;
		for (String s : validTokens) {
			++iteration;
			String splitter = iteration == validTokens.length - 1 ? " and " : iteration == validTokens.length ? "" : ", ";
			valid = valid.concat(s.concat(splitter));
		}
		return valid;
	}

	@Override
	public String getValue() {
		return contentsField.getText();
	}

}
