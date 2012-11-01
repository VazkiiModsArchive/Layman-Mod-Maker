package vazkii.modmaker.gui;

import java.io.File;

import net.minecraft.src.GuiButton;
import vazkii.modmaker.mod_ModMaker;

public class GuiUserModList extends GuiModMaker {

	GuiUserModListContainer modList;
	public String mod;

	public GuiUserModList() {
	}

	@Override
	public void initGui() {
		super.initGui();
		modList = new GuiUserModListContainer(this);
		controlList.add(createCenteredButton(0, height - 85, "Create new Mod"));
		controlList.add(createCenteredButton(1, height - 65, "Edit Mod"));
		controlList.add(createCenteredButton(2, height - 45, "Delete Mod"));
		controlList.add(createCenteredButton(3, height - 25, "Back"));
		if (!mod_ModMaker.userMods.isEmpty()) modList.elementClicked(0, false);
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		switch (par1GuiButton.id) {
			case 0: {
				mc.displayGuiScreen(new GuiNewMod());
				break;
			}
			case 1: {
				if (mod != null) mc.displayGuiScreen(new GuiBranch(mod_ModMaker.userMods.get(mod), true));
				break;
			}
			case 2: {
				if (mod != null) {
					mod_ModMaker.userMods.remove(mod);
					modList.selected = -1;
					File f = new File(mod_ModMaker.usermodsFile, mod + ".dat");
					boolean fileDeleted = f.delete();
					System.out.println("Deleted " + f + " " + fileDeleted);
					if (!fileDeleted) f.deleteOnExit();
				}
				break;
			}
			case 3: {
				mc.displayGuiScreen(new GuiStartHere());
			}
		}

		super.actionPerformed(par1GuiButton);
	}

	@Override
	public void drawExtras(int par1, int par2, float par3) {
		modList.drawScreen(par1, par2, par3);
		drawCenteredString(fontRenderer, "Layman Mod List:", width / 2, 20, 16777215);
	}

}
