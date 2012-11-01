package vazkii.modmaker.gui;

import vazkii.codebase.common.CommonUtils;
import vazkii.modmaker.tree.TreeLeaf;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;

public class GuiLeafBoolean extends GuiLeafEdit<Boolean> {

	boolean flag;
	GuiButton flagButton;

	public GuiLeafBoolean(GuiScreen parent, TreeLeaf leaf, String propName) {
		super(parent, leaf, propName);
	}

	@Override
	public void initGui() {
		super.initGui();
		contentsField = null;
		flagButton = createCenteredButton(2, 100, propName + " = " + flag);
		controlList.add(flagButton);
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		if (par1GuiButton == flagButton) {
			flag = CommonUtils.flipBoolean(flag);
			flagButton.displayString = propName + " = " + flag;
			return;
		}
		super.actionPerformed(par1GuiButton);
	}

	@Override
	public Boolean getValue() {
		return flag;
	}

}
