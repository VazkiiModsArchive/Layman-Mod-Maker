package vazkii.modmaker.gui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import vazkii.codebase.common.ColorCode;
import vazkii.codebase.common.CommonUtils;
import vazkii.modmaker.addon.AddonMarker;
import vazkii.modmaker.tree.LeafableBranch;
import vazkii.modmaker.tree.TreeBranch;

import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiSlot;
import net.minecraft.src.Tessellator;

public class GuiBranchContainer extends GuiSlot {

	TreeMap<String, TreeBranch> branches;
	List<String> labels;
	GuiBranch parent;
	int selectedIndex;
	boolean addon;

	public GuiBranchContainer(GuiBranch screen, TreeMap<String, TreeBranch> branches) {
		this(screen, branches, false);
	}

	public GuiBranchContainer(GuiBranch screen, TreeMap<String, TreeBranch> branches, boolean addon) {
		super(CommonUtils.getMc(), screen.width, screen.height, 32 + (addon ? 14 : 0), screen.height - 92, 36);
		parent = screen;
		labels = branches == null || branches.isEmpty() ? new LinkedList() : new ArrayList(branches.keySet());
		this.branches = branches;
	}

	@Override
	protected int getSize() {
		return labels.size();
	}

	@Override
	public void elementClicked(int var1, boolean var2) {
		selectedIndex = var1;
		String name = labels.get(var1);
		parent.select(branches.get(name), name);
	}

	@Override
	protected boolean isSelected(int var1) {
		return var1 == selectedIndex;
	}

	@Override
	protected int getContentHeight() {
		return getSize() * 36;
	}

	@Override
	protected void drawBackground() {
		parent.drawDefaultBackground();
	}

	@Override
	protected void drawSlot(int var1, int var2, int var3, int var4, Tessellator var5) {
		FontRenderer font = CommonUtils.getMc().fontRenderer;
		String name = labels.get(var1);
		TreeBranch branch = branches.get(name);
		if (branch == null) return;

		String label = branches.get(name).label();
		TreeMap<String, TreeBranch> map = branches.get(name).subBranches();
		font.drawString(branch instanceof AddonMarker ? label + ColorCode.BRIGHT_GREEN + " (" + ((AddonMarker) branch).getProvidingAddon(branch).getAddonName() + ")" : label, var2, var3 + 6, 0xFFFFFF);
		int entries = map == null ? 0 : map.size();
		if (branch instanceof LeafableBranch) entries += ((LeafableBranch) branch).leaves().size();

		font.drawString(String.format("%s entries", entries), var2 + 6, var3 + 18, 0x404040);
	}

}
