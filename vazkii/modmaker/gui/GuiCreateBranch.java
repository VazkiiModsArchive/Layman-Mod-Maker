package vazkii.modmaker.gui;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;
import net.minecraft.src.MathHelper;
import vazkii.modmaker.IOHelper;
import vazkii.modmaker.tree.BranchHelper;
import vazkii.modmaker.tree.TreeBranch;

public class GuiCreateBranch extends GuiModMaker {

	GuiTextField nameField;
	String name;
	TreeBranch branch;
	Class<? extends TreeBranch> branchToCreate;
	GuiScreen parent;

	public GuiCreateBranch(TreeBranch branch, String branchName, Class<? extends TreeBranch> branchToCreate, GuiScreen parent) {
		super();
		name = branchName;
		this.branch = branch;
		this.branchToCreate = branchToCreate;
		this.parent = parent;
	}

	@Override public void initGui() {
		super.initGui();
		nameField = createCenteredTextField(80, 150);

		controlList.clear();
		controlList.add(createCenteredButton(0, 155, "Done"));
		controlList.add(createCenteredButton(1, 180, "Cancel"));
	}

	@Override public void drawExtras(int par1, int par2, float par3) {
		drawCompletelyCenteredString(String.format("Creating a new %s.", name), 25, true, 0xFFFFFF);
		drawCompletelyCenteredString(name + " Name:", 65, false, 0xFFFFFF);

		((GuiButton) controlList.get(0)).enabled = !MathHelper.stringNullOrLengthZero(nameField.getText());
		nameField.drawTextBox();
	}

	@Override protected void keyTyped(char par1, int par2) {
		super.keyTyped(par1, par2);
		nameField.textboxKeyTyped(par1, par2);
	}

	@Override protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
		nameField.mouseClicked(par1, par2, par3);
	}

	@Override protected void actionPerformed(GuiButton par1GuiButton) {
		if (par1GuiButton.id == 0) try {
			branch.addBranch(nameField.getText(), branchToCreate.newInstance().init(branch, nameField.getText()));
			IOHelper.saveMod(BranchHelper.getModFromBranch(branch));
		} catch (Exception e) {
			e.printStackTrace();
		}
		mc.displayGuiScreen(parent);
	}
}
