package vazkii.modmaker;

import java.awt.Dimension;
import java.util.Random;

import net.minecraft.src.FontRenderer;
import net.minecraft.src.ItemStack;

import org.lwjgl.opengl.GL11;

import vazkii.codebase.common.CommonUtils;
import vazkii.codebase.common.VazcoreReference;
import vazkii.um.client.GuiModList;
import vazkii.um.common.UpdateManager;
import vazkii.um.common.UpdateManagerMod;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class ModMakerUpdateHandler extends UpdateManagerMod {

	public ModMakerUpdateHandler(Mod m) {
		super(m);
	}

	Random rand = new Random();
	int ticksElapsed = 0;
	int index = rand.nextInt(ModMakerReference.VALID_IDS.length);

	@Override
	public String getModURL() {
		return ModMakerReference.MOD_URL;
	}

	@Override
	public String getUpdateURL() {
		return ModMakerReference.UPDATE_URL;
	}

	@Override
	public String getChangelogURL() {
		return VazcoreReference.CHANGELOG_URL;
	}

	@Override
	public String getUMVersion() {
		return ModMakerReference.VERSION;
	}

	@Override
	public String getSpecialButtonName() {
		return "Twitter";
	}

	@Override
	public void onSpecialButtonClicked() {
		UpdateManager.openWebpage("https://twitter.com/Vazkii");
	}

	@Override
	public String getModName() {
		return "Layman Mod Maker";
	}

	@Override
	public ItemStack getIconStack() {
		if (++ticksElapsed % 25 == 0) index = rand.nextInt(ModMakerReference.VALID_IDS.length);
		return new ItemStack(ModMakerReference.VALID_IDS[index], 1, 0);
	}

	@Override @SideOnly(Side.CLIENT)
	public Dimension renderIcon(int x, int y, GuiModList modList) {
		Dimension dim = super.renderIcon(x, y, modList);

		FontRenderer renderer = CommonUtils.getMc().fontRenderer;
		GL11.glPushMatrix();
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		String bin = Integer.toBinaryString(index);
		renderer.drawString(bin, x * 2, y * 2 + 68, 0xFFFFFF);
		GL11.glScalef(1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();

		return dim;
	}

}
