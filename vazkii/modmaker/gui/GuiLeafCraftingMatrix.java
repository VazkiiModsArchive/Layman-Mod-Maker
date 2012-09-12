package vazkii.modmaker.gui;

import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;
import net.minecraft.src.MathHelper;
import vazkii.modmaker.tree.TreeLeaf;
import vazkii.um.client.GuiRestrictedTextField;

public class GuiLeafCraftingMatrix extends GuiLeafEdit<String> {

	GuiTextField[][] matrixTextFields = new GuiTextField[3][3];
	GuiTextField outputTextField;

	String horizontal = "ABC";
	String vertical = "123";

	public GuiLeafCraftingMatrix(GuiScreen parent, TreeLeaf<String> leaf, String propName) {
		super(parent, leaf, propName);
	}

	@Override public void initGui() {
		super.initGui();
		for (int i = 0; i < 3; i++)
			for (int i1 = 0; i1 < 3; i1++) {
				matrixTextFields[i][i1] = new GuiRestrictedTextField(mc.fontRenderer, 50 + 85 * i, 100 + 30 * i1, 75, 20, "-0123456789:");
				matrixTextFields[i][i1].setMaxStringLength(10);
			}

		contentsField = null;
		outputTextField = new GuiRestrictedTextField(mc.fontRenderer, 350, 130, 200, 20, "-0123456789:");
		controlList.clear();
		controlList.add(createCenteredButton(0, 200, "Done"));
		controlList.add(createCenteredButton(1, 255, "Cancel"));
	}

	@Override protected void keyTyped(char par1, int par2) {
		super.keyTyped(par1, par2);
		for (int i = 0; i < 3; i++)
			for (int i1 = 0; i1 < 3; i1++)
				matrixTextFields[i][i1].textboxKeyTyped(par1, par2);
		outputTextField.textboxKeyTyped(par1, par2);
	}

	@Override protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
		for (int i = 0; i < 3; i++)
			for (int i1 = 0; i1 < 3; i1++)
				matrixTextFields[i][i1].mouseClicked(par1, par2, par3);
		outputTextField.mouseClicked(par1, par2, par3);
	}

	@Override
	public void drawExtras(int par1, int par2, float par3) {
		super.drawExtras(par1, par2, par3);
		for (GuiTextField[] fArray : matrixTextFields)
			for (GuiTextField f : fArray)
				f.drawTextBox();
					fontRenderer.drawString("" + horizontal.charAt(0), 85, 86, 0xFFFFFF);
					fontRenderer.drawString("" + horizontal.charAt(0), 170, 86, 0xFFFFFF);
					fontRenderer.drawString("" + horizontal.charAt(0), 255, 86, 0xFFFFFF);
					fontRenderer.drawString("" + vertical.charAt(0), 38, 106, 0xFFFFFF);
					fontRenderer.drawString("" + vertical.charAt(1), 38, 136, 0xFFFFFF);
					fontRenderer.drawString("" + vertical.charAt(2), 38, 166, 0xFFFFFF);

					outputTextField.drawTextBox();
	}

	@Override public String getError() {
		boolean foundNonEmpty = false;
		findNonEmpty:
		{
			for (int i = 0; i < 3; i++)
				for (int i1 = 0; i1 < 3; i1++)
					if (!MathHelper.stringNullOrLengthZero(matrixTextFields[i][i1].getText())) {
						foundNonEmpty = true;
						break findNonEmpty;
					}
		}
		if (!foundNonEmpty) return "You must set a recipe.";
		if (MathHelper.stringNullOrLengthZero(outputTextField.getText())) return "You must set an ouput.";

		for (int i = 0; i < 3; i++)
			for (int i1 = 0; i1 < 3; i1++) {
				String s = matrixTextFields[i][i1].getText();
				String e = getErrorOn(s, false);
				if (!MathHelper.stringNullOrLengthZero(s) && e != null) return "[" + vertical.charAt(i1) + horizontal.charAt(i) + "] " + e;
			}

		String s = getErrorOn(outputTextField.getText(), true);
		if (s != null) return "[OUT] " + s;

		return null;
	}

	public String getErrorOn(String s, boolean qtd) {
		String[] tokens = s.split(":");
		if (qtd) {
			if (tokens.length != 3 || s.endsWith(":")) return "Invalid syntax, the correct one is 'ID:Quantity:Metadata'.";

			try {
				int id = Integer.parseInt(tokens[0]);
				int qtdVal = Integer.parseInt(tokens[1]);
				int meta = Integer.parseInt(tokens[2]);
				if (id > 32000) return "ID is too high, max is 32000.";
				if (id < 1) return "ID is too low, min is 1.";
				if (qtdVal < 1) return "Quantity is too low, min is 1.";
				if (qtdVal > 64) return "Quantity is too high, max is 64.";
				if (meta < -1) return "Metadata is too low, min is 0, '-1' acts as a wildcard.";
			} catch (NumberFormatException e) {
				return "Invalid syntax, the correct one is 'ID:Quantity:Metadata'.";
			}

			return null;
		}
		else {
			if (tokens.length != 2 || s.endsWith(":")) return "Invalid syntax, the correct one is 'ID:Metadata'.";

			try {
				int id = Integer.parseInt(tokens[0]);
				int meta = Integer.parseInt(tokens[1]);
				if (id > 32000) return "ID is too high, max is 32000.";
				if (id < 1) return "ID is too low, min is 1.";
				if (meta < -1) return "Metadata is too low, min is 0, '-1' acts as a wildcard.";
			} catch (NumberFormatException e) {
				return "Invalid syntax, the correct one is 'ID:Metadata'.";
			}
			return null;
		}
	}

	@Override public boolean drawValueString() {
		return false;
	}

	@Override public int getErrorMessageY() {
		return 225;
	}

	@Override
	public String getValue() {
		String s = "";
		for (int i = 0; i < 3; i++) {
			if (i != 0) s = s.concat(";");
			for (int i1 = 0; i1 < 3; i1++) {
				if (i1 != 0) s = s.concat(",");
				String s1 = matrixTextFields[i1][i].getText();
				s = s.concat(MathHelper.stringNullOrLengthZero(s1) ? "x" : s1);
			}
		}

		s = s.concat(";").concat(outputTextField.getText());

		return s;
	}

}
