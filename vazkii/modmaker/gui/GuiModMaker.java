package vazkii.modmaker.gui;

import updatemanager.client.GuiRestrictedTextField;
import vazkii.codebase.common.CommonUtils;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;

public class GuiModMaker extends GuiScreen {

	@Override
	public final void drawScreen(int par1, int par2, float par3) {
		drawWorldBackground(0);
		drawExtras(par1, par2, par3);
		super.drawScreen(par1, par2, par3);
	}

	final void drawCompletelyCenteredString(String string, int height, boolean shadow, int color) {
		drawCompletelyCenteredString(string, height, shadow, color, 1.0F);
	}

	final void drawCompletelyCenteredString(String string, int height, boolean shadow, int color, float glScale) {
		if (shadow) fontRenderer.drawStringWithShadow(string, (int) (width / (2 * glScale)) - fontRenderer.getStringWidth(string) / 2, (int) (height / glScale), color);
		else fontRenderer.drawString(string, (int) (width / (2 * glScale)) - (int) (fontRenderer.getStringWidth(string) / (2 * glScale)), (int) (height / glScale), color);
	}

	final GuiButton createCenteredButton(int index, int height, String contents) {
		int size = fontRenderer.getStringWidth(contents);
		return new GuiButton(index, width / 2 - (12 + size) / 2, height, size + 12, 20, contents);
	}

	final GuiTextField createCenteredTextField(int height, int size) {
		return createCenteredTextField(height, size, "*");
	}

	final GuiTextField createCenteredTextField(int height, int size, String restriction) {
		if (restriction.equals("*")) return new GuiTextField(CommonUtils.getMc().fontRenderer, width / 2 - (12 + size) / 2, height, size + 12, 20);
		return new GuiRestrictedTextField(CommonUtils.getMc().fontRenderer, width / 2 - (12 + size) / 2, height, size + 12, 20, restriction);
	}

	public void drawExtras(int par1, int par2, float par3) {
	}

}
