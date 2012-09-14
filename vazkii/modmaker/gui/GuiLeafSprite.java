package vazkii.modmaker.gui;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import vazkii.codebase.common.CommonUtils;
import vazkii.modmaker.tree.TreeLeaf;

public class GuiLeafSprite extends GuiLeafEdit {

	boolean index = true;

	public GuiLeafSprite(GuiScreen parent, TreeLeaf leaf, String propName) {
		super(parent, leaf, propName);
	}

	@Override
	public void initGui() {
		super.initGui();
		controlList.add(createCenteredButton(2, 75, "Sprite Index"));
	}

	public void resetFields() {
		((GuiButton) controlList.get(2)).displayString = index ? "Sprite Index" : "Sprite File";
		contentsField = createCenteredTextField(100, 200, getValidChars());
		contentsField.setMaxStringLength(getMaxChars());
	}

	@Override
	public String getValidChars() {
		return index ? "0123456789" : "*";
	}

	@Override
	public int getMaxChars() {
		return index ? 3 : super.getMaxChars();
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		if (par1GuiButton.id == 2) {
			index = CommonUtils.flipBoolean(index);
			resetFields();
			return;
		}
		super.actionPerformed(par1GuiButton);
	}

	@Override
	public String getError() {
		Object o = getValue();
		if (o instanceof String) {
			String s = (String) o;
			if (s.equals(".png")) return "You must specify a file name!";
			if (!s.endsWith(".png")) return "Sprite File must end with .png";
		}
		if (o instanceof Integer) {
			int i = (Integer) o;
			if (i > 255) return "Sprite Index is too high, max is 255.";
		}

		return null;
	}

	@Override
	public Object getValue() {
		String text = contentsField.getText();
		if (index) try {
			return Integer.parseInt(text);
		} catch (NumberFormatException e) {
			return 0;
		}
		else return text;
	}

}
