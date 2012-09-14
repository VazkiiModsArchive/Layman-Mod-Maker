package vazkii.modmaker.gui;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EnumOS;
import net.minecraft.src.GuiButton;

import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

import vazkii.codebase.common.ColorCode;
import vazkii.codebase.common.CommonUtils;
import vazkii.modmaker.ModMakerReference;
import vazkii.um.common.UpdateManager;

public class GuiStartHere extends GuiModMaker {

	ColorCode colorMain = ColorCode.WHITE;
	ColorCode colorSub = ColorCode.WHITE;
	String fileLoc;

	@Override
	public void initGui() {
		ColorCode[] allCodes = ColorCode.class.getEnumConstants();
		colorMain = allCodes[CommonUtils.nextIntMinMax(1, allCodes.length - 1)];
		colorSub = allCodes[CommonUtils.nextIntMinMax(1, allCodes.length - 1)];
		controlList.clear();
		controlList.add(createCenteredButton(0, 135, "Manage mods"));
		controlList.add(createCenteredButton(1, 160, "Open User Mods Folder"));
		controlList.add(createCenteredButton(2, 185, "Go Back"));
		controlList.add(createCenteredButton(3, height - 35, "Layman Mod Maker Forum Thread"));
		fileLoc = new File(Minecraft.getMinecraftDir(), "usermods").getAbsolutePath();
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		switch (button.id) {
		case 0: {
			mc.displayGuiScreen(new GuiUserModList());
			break;
		}
		case 1: {
			if (Minecraft.getOs() == EnumOS.MACOS) try {
				System.out.println(fileLoc);
				Runtime.getRuntime().exec(new String[] { "/usr/bin/open", fileLoc });
				return;
			} catch (IOException var7) {
				var7.printStackTrace();
			}
			else if (Minecraft.getOs() == EnumOS.WINDOWS) {
				String var2 = String.format("cmd.exe /C start \"Open file\" \"%s\"", new Object[] { fileLoc });
				try {
					Runtime.getRuntime().exec(var2);
					return;
				} catch (IOException var6) {
					var6.printStackTrace();
				}
			}
			boolean var8 = false;
			try {
				Class var3 = Class.forName("java.awt.Desktop");
				Object var4 = var3.getMethod("getDesktop", new Class[0]).invoke((Object) null, new Object[0]);
				var3.getMethod("browse", new Class[] { URI.class }).invoke(var4, new Object[] { new File(Minecraft.getMinecraftDir(), "texturepacks").toURI() });
			} catch (Throwable var5) {
				var5.printStackTrace();
				var8 = true;
			}
			if (var8) {
				System.out.println("Opening via system class!");
				Sys.openURL("file://" + fileLoc);
			}
		}
		case 2: {
			mc.displayGuiScreen(null);
			break;
		}
		case 3: {
			UpdateManager.openWebpage(ModMakerReference.MOD_URL);
			break;
		}
		}
	}

	@Override
	public void drawExtras(int par1, int par2, float par3) {
		int shifty = 26;
		GL11.glPushMatrix();
		GL11.glScalef(2.0F, 2.0F, 2.0F);
		drawCompletelyCenteredString(colorMain + "Layman Mod Maker!", shifty, true, 0xFFFFFF, 2.0F);
		GL11.glScalef(1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
		shifty += 24;
		drawCompletelyCenteredString(colorSub + "By Vazkii, version " + ModMakerReference.VERSION, shifty, true, 0xFFFFF);
	}

}
