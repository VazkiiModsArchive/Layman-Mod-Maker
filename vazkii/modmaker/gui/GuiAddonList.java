package vazkii.modmaker.gui;

import org.lwjgl.opengl.GL11;

import vazkii.codebase.common.ColorCode;
import vazkii.codebase.common.CommonUtils;
import vazkii.modmaker.addon.AddonHelper;
import vazkii.modmaker.addon.LMMAddon;

import net.minecraft.src.GuiButton;

public class GuiAddonList extends GuiModMaker {

	GuiAddonListSlot slotList;
	int selected;
	int listWidth;

	@Override
	public void initGui() {
		for (LMMAddon addon : AddonHelper.getAddons())
			listWidth = Math.max(listWidth, Math.max(fontRenderer.getStringWidth(addon.getAddonName()), fontRenderer.getStringWidth("by " + addon.getAuthor()) + 20));
				slotList = new GuiAddonListSlot(this, listWidth);
				controlList.add(createCenteredButton(0, height - 35, "Done!"));
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.id == 0) CommonUtils.getMc().displayGuiScreen(new GuiStartHere());
		super.actionPerformed(button);
	}

	@Override
	public void drawExtras(int par1, int par2, float par3) {
		slotList.drawScreen(par1, par2, par3);
		if (AddonHelper.getAddons().isEmpty()) {
			GL11.glPushMatrix();
			GL11.glScalef(2.0F, 2.0F, 2.0F);
			drawCompletelyCenteredString(ColorCode.RED + "No Addons Loaded :(", 80, true, 0xFFFFFF, 2.0F);
			GL11.glScalef(1.0F, 1.0F, 1.0F);
			GL11.glPopMatrix();
			drawCompletelyCenteredString(ColorCode.GREY + "You can get addons at the Official Layman Mod Maker Thread!", 110, true, 0xFFFFFF, 1.0F);
			return;
		}
		drawCompletelyCenteredString(String.format("Layman Mod Maker Addons: %s Addons Loaded!", AddonHelper.getAddonQtd()), 10, true, 0xFFFFFF);
		LMMAddon add = AddonHelper.getAddons().get(selected);
		if (add != null) {
			GL11.glPushMatrix();
			GL11.glScalef(2.0F, 2.0F, 2.0F);
			fontRenderer.drawStringWithShadow(ColorCode.BRIGHT_GREEN + add.getAddonName(), listWidth / 2 + 10, 20, 0xFFFFFF);
			GL11.glScalef(1.0F, 1.0F, 1.0F);
			GL11.glPopMatrix();
			fontRenderer.drawStringWithShadow("by " + ColorCode.INDIGO + add.getAuthor(), listWidth + 20, 60, 0xFFFFFF);
			int it = 0;
			for (String s : add.getAddonDesc()) {
				fontRenderer.drawStringWithShadow(ColorCode.GREY + s, listWidth + 40, 90 + it * 11, 0xFFFFFF);
				++it;
			}
		}

	}

	public void selectIndex(int index) {
		selected = index;
	}

	public boolean isIndexSelected(int index) {
		return selected == index;
	}

}
