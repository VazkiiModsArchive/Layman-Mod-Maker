package vazkii.modmaker.gui;

import net.minecraft.src.GuiScreen;
import vazkii.modmaker.tree.TreeLeaf;

public class GuiLeafStringStack extends GuiLeafString {

	boolean qtd;

	public GuiLeafStringStack(GuiScreen parent, TreeLeaf<String> leaf, String propName, boolean qtd) {
		super(parent, leaf, propName);
		this.qtd = qtd;
	}

	@Override
	public String getValidChars() {
		return "-0123456789:";
	}

	@Override
	public String getError() {
		String s = contentsField.getText();
		String[] tokens = s.split(":");
		if (qtd) {
			if (tokens.length != 3 || s.endsWith(":")) return "Invalid syntax, the correct one is 'ID:Quantity:Metadata'";

			try {
				int id = Integer.parseInt(tokens[0]);
				int qtd = Integer.parseInt(tokens[1]);
				int meta = Integer.parseInt(tokens[2]);
				if (id > 32000) return "ID is too high, max is 32000";
				if (id < 1) return "ID is too low, min is 1";
				if (qtd < 1) return "Quantity is too low, min is 1";
				if (qtd > 64) return "Quantity is too high, max is 64";
				if (meta < -1) return "Metadata is too low, min is 0, '-1' acts as a wildcard.";
			} catch (NumberFormatException e) {
				return "Invalid syntax, the correct one is 'ID:Quantity:Metadata'";
			}

			return null;
		}
		else {
			if (tokens.length != 2 || s.endsWith(":")) return "Invalid syntax, the correct one is 'ID:Metadata'";

			try {
				int id = Integer.parseInt(tokens[0]);
				int meta = Integer.parseInt(tokens[1]);
				if (id > 32000) return "ID is too high, max is 32000";
				if (id < 1) return "ID is too low, min is 1";
				if (meta < -1) return "Metadata is too low, min is 0, '-1' acts as a wildcard.";
			} catch (NumberFormatException e) {
				return "Invalid syntax, the correct one is 'ID:Metadata'";
			}
			return null;
		}
	}
}
