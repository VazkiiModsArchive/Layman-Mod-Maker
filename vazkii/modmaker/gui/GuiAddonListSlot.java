package vazkii.modmaker.gui;

import vazkii.codebase.common.ColorCode;
import vazkii.modmaker.addon.AddonHelper;
import vazkii.modmaker.addon.LMMAddon;

import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Tessellator;

import cpw.mods.fml.client.GuiScrollingList;

public class GuiAddonListSlot extends GuiScrollingList {

	GuiAddonList parent;

	public GuiAddonListSlot(GuiScreen parentGui, int listWidth) {
		super(ModLoader.getMinecraftInstance(), listWidth, parentGui.height, 32, parentGui.height - 65 + 4, 10, 25);
		parent = (GuiAddonList) parentGui;
	}

	@Override
	protected void drawSlot(int listIndex, int var2, int var3, int var4, Tessellator var5) {
		FontRenderer fr = ModLoader.getMinecraftInstance().fontRenderer;
		LMMAddon addon = AddonHelper.getAddons().get(listIndex);
		fr.drawString(fr.trimStringToWidth(addon.getAddonName(), listWidth - 11), left + 3, var3 + 2, 0xFFFFFF);
		fr.drawString(fr.trimStringToWidth(ColorCode.GREY + "by " + addon.getAuthor(), listWidth - 11), left + 3, var3 + 12, 0xFFFFFF);
	}

	@Override
	protected int getSize() {
		return AddonHelper.getAddonQtd();
	}

	@Override
	protected void elementClicked(int var1, boolean var2) {
		parent.selectIndex(var1);
	}

	@Override
	protected boolean isSelected(int var1) {
		return parent.isIndexSelected(var1);
	}

	@Override
	protected void drawBackground() {
		parent.drawBackground(0);
	}

}
