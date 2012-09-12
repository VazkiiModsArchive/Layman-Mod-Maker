package vazkii.modmaker.gui;

import java.util.Arrays;
import java.util.List;

import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSlot;
import net.minecraft.src.Tessellator;
import vazkii.codebase.common.CommonUtils;
import vazkii.modmaker.mod_ModMaker;
import vazkii.modmaker.tree.objective.UserMod;

public class GuiUserModListContainer extends GuiSlot {

	private List<String> entries;
	private GuiScreen parent;
	public int selected;

	public GuiUserModListContainer(GuiScreen screen) {
		super(CommonUtils.getMc(), screen.width, screen.height, 32, screen.height - 92, 36);
		parent = screen;
		entries = Arrays.asList(mod_ModMaker.userMods.keySet().toArray(new String[mod_ModMaker.userMods.keySet().size()]));
	}

	@Override
	protected int getSize() {
		return entries.size();
	}

	@Override
	public void elementClicked(int var1, boolean var2) {
		selected = var1;
		((GuiUserModList) parent).mod = entries.get(selected);
	}

	@Override
	protected int getContentHeight() {
		return getSize() * 36;
	}

	@Override
	protected boolean isSelected(int var1) {
		return var1 == selected;
	}

	@Override
	protected void drawBackground() {
		parent.drawDefaultBackground();
	}

	@Override
	protected void drawSlot(int var1, int var2, int var3, int var4, Tessellator var5) {
		UserMod mod = mod_ModMaker.userMods.get(entries.get(var1));
		if (mod == null) return;

		FontRenderer font = CommonUtils.getMc().fontRenderer;
		font.drawStringWithShadow(mod.label(), var2, var3 + 6, 0xFFFFFF);
		font.drawStringWithShadow("by " + mod.author, var2, var3 + 18, 0x404040);
	}
}
