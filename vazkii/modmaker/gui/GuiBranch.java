package vazkii.modmaker.gui;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import vazkii.codebase.common.ColorCode;
import vazkii.modmaker.addon.AddonMarker;
import vazkii.modmaker.tree.LeafableBranch;
import vazkii.modmaker.tree.TreeBranch;

import net.minecraft.src.GuiButton;

public class GuiBranch extends GuiModMaker {

	GuiBranchContainer container;
	TreeBranch branch;
	TreeBranch selected;
	String selectedName;
	boolean constant;

	public GuiBranch(TreeBranch branch, boolean constant) {
		this.branch = branch;
		this.constant = constant;
	}

	public void select(Object select, String name) {
		if (!(select instanceof TreeBranch)) return;

		selected = (TreeBranch) select;
		selectedName = name;
		((GuiButton) controlList.get(0)).enabled = true;

		if (!constant) ((GuiButton) controlList.get(1)).enabled = true;
	}

	@Override
	public void initGui() {
		super.initGui();
		container = new GuiBranchContainer(this, branch.subBranches(), branch instanceof AddonMarker);
		controlList.clear();
		controlList.add(createCenteredButton(0, height - 85, "Explore Branch"));
		controlList.add(createCenteredButton(1, height - 65, "Create new Branch"));
		controlList.add(createCenteredButton(2, height - 45, "Delete Branch"));
		controlList.add(createCenteredButton(3, height - 25, "Back"));
		((GuiButton) controlList.get(0)).enabled = false;
		((GuiButton) controlList.get(1)).enabled = !branch.isConstant();
		((GuiButton) controlList.get(2)).enabled = !branch.isConstant();
		if (!branch.subBranches().isEmpty() && !(this instanceof GuiLeafableBranch)) container.elementClicked(0, false);
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		switch (par1GuiButton.id) {
			case 0: {
				if (selected != null) mc.displayGuiScreen(selected instanceof LeafableBranch ? new GuiLeafableBranch(selected, selected.isConstant()) : new GuiBranch(selected, selected.isConstant()));
				break;
			}
			case 1: {
				mc.displayGuiScreen(branch.getBranchCreationGui(this));
				break;
			}
			case 2: {
				if (selected != null) branch.deleteBranch(selectedName);
				break;
			}
			case 3: {
				TreeBranch superBranch = branch.superBranch();
				mc.displayGuiScreen(superBranch == null ? new GuiUserModList() : superBranch instanceof LeafableBranch ? new GuiLeafableBranch(superBranch, superBranch.isConstant()) : new GuiBranch(superBranch, superBranch.isConstant()));
			}
		}

		super.actionPerformed(par1GuiButton);
	}

	@Override
	public void drawExtras(int par1, int par2, float par3) {
		container.drawScreen(par1, par2, par3);
		drawCenteredString(fontRenderer, getPath(), width / 2, branch instanceof AddonMarker ? 16 : 20, 16777215);
		if (branch instanceof AddonMarker) drawCenteredString(fontRenderer, ColorCode.BRIGHT_GREEN + "This Feature is provided by " + ((AddonMarker) branch).getProvidingAddon(branch).getAddonName(), width / 2, 32, 16777215);
	}

	public String getPath() {
		String s = "";
		List<String> branches = new LinkedList();
		List<String> organizedBranches = new LinkedList();
		branches.add(branch.label());
		TreeBranch b = branch.superBranch();

		while (true) {
			if (b == null) break;
			branches.add(b.label());
			b = b.superBranch();
		}

		if (branches.size() == 1) return branches.get(0);

		ListIterator<String> it = (ListIterator<String>) branches.iterator();

		while (it.hasNext())
			it.next();

		while (it.hasPrevious()) {
			String str = it.previous();
			organizedBranches.add(str);
		}

		for (String s1 : organizedBranches)
			s = s.concat(s1 + " > ");

				if (s.endsWith(" > ")) s = s.substring(0, s.length() - 3);
				return s;
	}

}
