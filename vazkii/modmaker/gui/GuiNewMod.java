package vazkii.modmaker.gui;

import vazkii.modmaker.IOHelper;
import vazkii.modmaker.mod_ModMaker;
import vazkii.modmaker.tree.objective.UserMod;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiTextField;
import net.minecraft.src.MathHelper;

public class GuiNewMod extends GuiModMaker {

	GuiTextField nameField;
	GuiTextField authorField;

	@Override
	public void initGui() {
		super.initGui();
		controlList.clear();
		controlList.add(createCenteredButton(0, 155, "Done"));
		controlList.add(createCenteredButton(1, 180, "Cancel"));
		nameField = createCenteredTextField(80, 150);
		authorField = createCenteredTextField(120, 150);

	}

	@Override
	public void drawExtras(int par1, int par2, float par3) {
		drawCompletelyCenteredString("Creating a new Layman Mod.", 25, true, 0xFFFFFF);
		drawCompletelyCenteredString("Mod Name:", 65, false, 0xFFFFFF);
		drawCompletelyCenteredString("Author Name:", 105, false, 0xFFFFFF);
		nameField.drawTextBox();
		authorField.drawTextBox();

		((GuiButton) controlList.get(0)).enabled = !(MathHelper.stringNullOrLengthZero(nameField.getText()) || MathHelper.stringNullOrLengthZero(authorField.getText()) || mod_ModMaker.userMods.containsKey(nameField.getText()));
	}

	@Override
	protected void keyTyped(char par1, int par2) {
		super.keyTyped(par1, par2);
		nameField.textboxKeyTyped(par1, par2);
		authorField.textboxKeyTyped(par1, par2);
	}

	@Override
	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
		nameField.mouseClicked(par1, par2, par3);
		authorField.mouseClicked(par1, par2, par3);
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		if (par1GuiButton.id == 0) {
			UserMod mod = ((UserMod) new UserMod().init(null, nameField.getText())).setAuthor(authorField.getText());
			mod_ModMaker.userMods.put(nameField.getText(), mod);
			IOHelper.saveMod(mod);
		}

		mc.displayGuiScreen(new GuiUserModList());
	}

}
