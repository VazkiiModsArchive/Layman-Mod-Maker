package vazkii.modmaker.gui;

import net.minecraft.src.GuiButton;
import vazkii.modmaker.tree.LeafableBranch;
import vazkii.modmaker.tree.TreeBranch;
import vazkii.modmaker.tree.TreeLeaf;

public class GuiLeafableBranch extends GuiBranch {

	Object selected;

	public GuiLeafableBranch(TreeBranch branch, boolean constant) {
		super(branch, constant);
		if (!(branch instanceof LeafableBranch)) throw new IllegalArgumentException(branch + " isn't a Leafable Branch!");
	}

	@Override
	public void initGui() {
		super.initGui();
		container = new GuiLeafableBranchContainer(this, branch.subBranches(), ((LeafableBranch) branch).leaves());
		if (!branch.subBranches().isEmpty() || !((LeafableBranch) branch).leaves().isEmpty()) container.elementClicked(0, false);
	}

	@Override
	public void select(Object select, String name) {
		selected = select;
		selectedName = name;

		if (selected instanceof TreeLeaf) {
			((GuiButton) controlList.get(0)).displayString = "Edit Leaf";
			((GuiButton) controlList.get(1)).displayString = "Create new Leaf";
			((GuiButton) controlList.get(2)).displayString = "Delete Leaf";
		} else {
			((GuiButton) controlList.get(0)).displayString = "Explore Branch";
			((GuiButton) controlList.get(1)).displayString = "Create new Branch";
			((GuiButton) controlList.get(2)).displayString = "Delete Branch";
		}

		((GuiButton) controlList.get(0)).enabled = true;
		((GuiButton) controlList.get(1)).enabled = !constant;
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		switch (par1GuiButton.id) {
			case 0: {
				if (selected != null) if (selected instanceof TreeLeaf) mc.displayGuiScreen(((TreeLeaf) selected).getLeafEditGui(this));
				else if (selected instanceof TreeBranch) mc.displayGuiScreen(selected instanceof LeafableBranch ? new GuiLeafableBranch((TreeBranch) selected, ((TreeBranch) selected).isConstant()) : new GuiBranch((TreeBranch) selected, ((TreeBranch) selected).isConstant()));
				break;
			}
			case 1: {
				mc.displayGuiScreen(branch.getBranchCreationGui(this));
				break;
			}
			case 2: {
				if (selected != null) if (selected instanceof TreeLeaf) ((LeafableBranch) branch).deleteLeaf(selectedName);
				else if (selected instanceof TreeBranch) branch.deleteBranch(selectedName);
				break;
			}
			case 3: {
				TreeBranch superBranch = branch.superBranch();
				mc.displayGuiScreen(superBranch == null ? new GuiUserModList() : superBranch instanceof LeafableBranch ? new GuiLeafableBranch(superBranch, superBranch.isConstant()) : new GuiBranch(superBranch, superBranch.isConstant()));
				break;
			}
		}
	}

}
