package vazkii.modmaker.gui;

import java.util.Set;

import vazkii.codebase.common.ColorCode;
import vazkii.modmaker.tree.LeafString;
import vazkii.modmaker.tree.TreeLeaf;

import net.minecraft.src.GuiScreen;

public class GuiLeafString extends GuiLeafEdit<String> {

	Set<String> validTokens;

	public GuiLeafString(GuiScreen parent, TreeLeaf<String> leaf, String propName, final Set<String> validTokens) {
		this(parent, leaf, propName);
		this.validTokens = validTokens;
	}

	public GuiLeafString(GuiScreen parent, TreeLeaf<String> leaf, String propName) {
		super(parent, leaf, propName);
	}

	@Override
	public String getError() {
		if (validTokens == null || validTokens.size() == 0) return null;

		String text = contentsField.getText();
		if (!validTokens.contains(text)) return String.format("Only '%s' are valid.", getValid());
		else return null;
	}

	public String getValid() {
		String valid = "";
		int iteration = 0;
		for (String s : validTokens) {
			++iteration;
			String splitter = iteration == validTokens.size() - 1 ? " and " : iteration == validTokens.size() ? "" : ", ";
			valid = valid.concat((((LeafString) leaf).isTokenFromAddon(s) ? ColorCode.BRIGHT_GREEN : "") + s.concat(ColorCode.RED + splitter));
		}
		return valid;
	}

	@Override
	public String getValue() {
		return contentsField.getText();
	}

}
