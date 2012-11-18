package vazkii.modmaker.gui;

import vazkii.codebase.common.ColorCode;
import vazkii.modmaker.IOHelper;
import vazkii.modmaker.addon.LMMAddon;
import vazkii.modmaker.tree.BranchHelper;
import vazkii.modmaker.tree.TreeLeaf;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;

public abstract class GuiLeafEdit<T> extends GuiModMaker {

	TreeLeaf<T> leaf;
	GuiTextField contentsField;
	GuiScreen parent;
	String propName;

	public GuiLeafEdit(GuiScreen parent, TreeLeaf<T> leaf, String propName) {
		super();
		this.leaf = leaf;
		this.parent = parent;
		this.propName = propName;
	}

	@Override
	public void drawExtras(int par1, int par2, float par3) {
		drawCompletelyCenteredString(String.format("Editing %s.", propName), 25, true, 0xFFFFFF);
		if (leaf.isAddonFeature()) {
			LMMAddon addon = leaf.getAddon();
			drawCompletelyCenteredString(ColorCode.BRIGHT_GREEN + "This Feature is provided by " + addon.getAddonName(), 40, true, 0xFFFFFF);

		}
		if (drawValueString()) drawCompletelyCenteredString("Value:", 85, false, 0xFFFFFF);
		String error = getError();
		if (!validate(error)) {
			drawCompletelyCenteredString(ColorCode.RED + error, getErrorMessageY(), false, 0xFFFFFF);
			((GuiButton) controlList.get(0)).enabled = false;
		} else ((GuiButton) controlList.get(0)).enabled = true;

		if (contentsField != null) contentsField.drawTextBox();
	}

	@Override
	public void initGui() {
		super.initGui();
		contentsField = createCenteredTextField(100, 200, getValidChars());
		contentsField.setMaxStringLength(getMaxChars());
		controlList.clear();
		controlList.add(createCenteredButton(0, 140, "Done"));
		controlList.add(createCenteredButton(1, 185, "Cancel"));
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		if (par1GuiButton.id == 0) {
			leaf.write(getValue());
			IOHelper.saveMod(BranchHelper.getModFromBranch(leaf.getBranch()));
		}

		mc.displayGuiScreen(parent);
	}

	@Override
	protected void keyTyped(char par1, int par2) {
		super.keyTyped(par1, par2);
		if (contentsField != null) contentsField.textboxKeyTyped(par1, par2);
	}

	@Override
	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
		if (contentsField != null) contentsField.mouseClicked(par1, par2, par3);
	}

	public abstract T getValue();

	public String getValidChars() {
		return "*";
	}

	public String getError() {
		return null;
	}

	public int getErrorMessageY() {
		return 165;
	}

	public boolean drawValueString() {
		return true;
	}

	public int getMaxChars() {
		return 40;
	}

	public boolean validate(String error) {
		return error == null;
	}

}
