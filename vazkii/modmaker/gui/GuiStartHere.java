package vazkii.modmaker.gui;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Random;

import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

import updatemanager.common.UpdateManager;
import vazkii.codebase.client.ColorRGB;
import vazkii.codebase.common.ColorCode;
import vazkii.codebase.common.CommonUtils;
import vazkii.codebase.common.VazcoreReference;
import vazkii.modmaker.ModMakerReference;
import net.minecraft.client.Minecraft;

import net.minecraft.src.EnumOS;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiConnecting;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.RenderItem;
import net.minecraft.src.ServerData;
import net.minecraft.src.Tessellator;

public class GuiStartHere extends GuiModMaker {

	ColorCode colorMain = ColorCode.WHITE;
	ColorCode colorSub = ColorCode.WHITE;
	String fileLoc;
	boolean cake;

	boolean displayUpdate = false;

	@Override
	public void initGui() {
		ColorCode[] allCodes = ColorCode.class.getEnumConstants();
		colorMain = allCodes[CommonUtils.nextIntMinMax(1, allCodes.length - 1)];
		do
			colorSub = allCodes[CommonUtils.nextIntMinMax(1, allCodes.length - 1)];
		while (colorMain == colorSub);
		controlList.clear();
		controlList.add(new NoTextureButton(0, 20, 135, fontRenderer.getStringWidth("Manage Mods") + 8, 20, "Manage mods", colorSub));
		controlList.add(new NoTextureButton(1, 20, 160, fontRenderer.getStringWidth("Open User Mods Folder") + 8, 20, "Open User Mods Folder", colorSub));
		controlList.add(new NoTextureButton(2, 20, 185, fontRenderer.getStringWidth("Go Back") + 8, 20, "Go Back", colorSub));
		controlList.add(new NoTextureButton(3, 20, height - 50, fontRenderer.getStringWidth("Layman Mod Maker Thread") + 8, 20, "Layman Mod Maker Thread", colorSub));
		controlList.add(new NoTextureButton(4, width - fontRenderer.getStringWidth("List of Layman Mods") - 25, 50, fontRenderer.getStringWidth("List of Layman Mods"), 20, "List of Layman Mods", colorSub, true));
		controlList.add(new NoTextureButton(5, width - fontRenderer.getStringWidth("Playlist of LMM Videos") - 25, 70, fontRenderer.getStringWidth("Playlist of LMM Videos"), 20, "Playlist of LMM Videos", colorSub, true));
		controlList.add(new NoTextureButton(6, width - fontRenderer.getStringWidth("Official Server (Click here to join)") - 25, 90, fontRenderer.getStringWidth("Official Server (Click here to join)"), 20, "Official Server (Click here to join)", colorSub, true));
		fileLoc = new File(Minecraft.getMinecraftDir(), "usermods").getAbsolutePath();
		cake = new Random().nextInt(500) == 0;
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
					var3.getMethod("browse", new Class[] { URI.class }).invoke(var4, new Object[] { new File(Minecraft.getMinecraftDir(), "usermods").toURI() });
				} catch (Throwable var5) {
					var5.printStackTrace();
					var8 = true;
				}
				if (var8) Sys.openURL("file://" + fileLoc);
			}
			case 2: {
				mc.displayGuiScreen(null);
				break;
			}
			case 3: {
				UpdateManager.openWebpage(ModMakerReference.MOD_URL);
				break;
			}
			case 4: {
				UpdateManager.openWebpage("http://www.minecraftforum.net/topic/1492684-official-list-o-layman-mods-layman-mod-maker/#entry18329097");
				break;
			}
			case 5: {
				UpdateManager.openWebpage("http://www.youtube.com/playlist?list=PLHjE1HOInJLWFv82Eq4RlWFsDpZlOTMui&feature=view_all");
				break;
			}
			case 6: {
				CommonUtils.getMc().displayGuiScreen(new GuiConnecting(CommonUtils.getMc(), new ServerData(VazcoreReference.VAZKII_MODS_SERVER_NAME, VazcoreReference.VAZKII_MODS_SERVER_IP)));
				break;
			}
		}
	}

	@Override
	public void drawExtras(int par1, int par2, float par3) {
		int shifty = 26;
		String s = cake ? "Cake" : "Mod";
		String displayString = colorMain + String.format("Layman %s Maker!", s);
		int displayStringWidth = fontRenderer.getStringWidth(displayString) * 2;
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		Tessellator tessellator = Tessellator.instance;

		ColorRGB mainColor = colorMain.asRGB();
		colorSub.asRGB();

		tessellator.startDrawingQuads();
		mainColor.colorizeTessellator(tessellator, 63);
		tessellator.addVertex(15D, 20D, zLevel);
		tessellator.addVertex(15D, height - 20, zLevel);
		tessellator.addVertex(displayStringWidth + 20, height - 20, zLevel);
		tessellator.addVertex(displayStringWidth + 20, 20D, zLevel);
		tessellator.draw();

		tessellator.startDrawingQuads();
		mainColor.colorizeTessellator(tessellator);
		tessellator.addVertex(12D, 17D, zLevel);
		tessellator.addVertex(12D, 20D, zLevel);
		tessellator.addVertex(displayStringWidth + 23, 20D, zLevel);
		tessellator.addVertex(displayStringWidth + 23, 17D, zLevel);
		tessellator.draw();

		tessellator.startDrawingQuads();
		mainColor.colorizeTessellator(tessellator);
		tessellator.addVertex(12D, height - 20, zLevel);
		tessellator.addVertex(12D, height - 17, zLevel);
		tessellator.addVertex(displayStringWidth + 23, height - 17, zLevel);
		tessellator.addVertex(displayStringWidth + 23, height - 20, zLevel);
		tessellator.draw();

		tessellator.startDrawingQuads();
		mainColor.colorizeTessellator(tessellator);
		tessellator.addVertex(15D, 20D, zLevel);
		tessellator.addVertex(12D, 20D, zLevel);
		tessellator.addVertex(12D, height - 17, zLevel);
		tessellator.addVertex(15D, height - 20, zLevel);
		tessellator.draw();

		tessellator.startDrawingQuads();
		mainColor.colorizeTessellator(tessellator);
		tessellator.addVertex(displayStringWidth + 23, 20D, zLevel);
		tessellator.addVertex(displayStringWidth + 20, 20D, zLevel);
		tessellator.addVertex(displayStringWidth + 20, height - 17, zLevel);
		tessellator.addVertex(displayStringWidth + 23, height - 20, zLevel);
		tessellator.draw();

		tessellator.startDrawingQuads();
		mainColor.colorizeTessellator(tessellator, 63);
		tessellator.addVertex(displayStringWidth + 35, 20D, zLevel);
		tessellator.addVertex(displayStringWidth + 35, 180D, zLevel);
		tessellator.addVertex(width - 20, 180D, zLevel);
		tessellator.addVertex(width - 20, 20D, zLevel);
		tessellator.draw();

		tessellator.startDrawingQuads();
		mainColor.colorizeTessellator(tessellator);
		tessellator.addVertex(displayStringWidth + 32, 17D, zLevel);
		tessellator.addVertex(displayStringWidth + 32, 20D, zLevel);
		tessellator.addVertex(width - 17, 20D, zLevel);
		tessellator.addVertex(width - 17, 17D, zLevel);
		tessellator.draw();

		tessellator.startDrawingQuads();
		mainColor.colorizeTessellator(tessellator);
		tessellator.addVertex(displayStringWidth + 32, 180D, zLevel);
		tessellator.addVertex(displayStringWidth + 32, 183D, zLevel);
		tessellator.addVertex(width - 17, 183D, zLevel);
		tessellator.addVertex(width - 17, 180, zLevel);
		tessellator.draw();

		tessellator.startDrawingQuads();
		mainColor.colorizeTessellator(tessellator);
		tessellator.addVertex(displayStringWidth + 35, 20D, zLevel);
		tessellator.addVertex(displayStringWidth + 32, 20D, zLevel);
		tessellator.addVertex(displayStringWidth + 32, 180D, zLevel);
		tessellator.addVertex(displayStringWidth + 35, 180D, zLevel);
		tessellator.draw();

		tessellator.startDrawingQuads();
		mainColor.colorizeTessellator(tessellator);
		tessellator.addVertex(displayStringWidth + 35, 20D, zLevel);
		tessellator.addVertex(displayStringWidth + 32, 20D, zLevel);
		tessellator.addVertex(displayStringWidth + 32, 180D, zLevel);
		tessellator.addVertex(displayStringWidth + 35, 180D, zLevel);
		tessellator.draw();

		tessellator.startDrawingQuads();
		mainColor.colorizeTessellator(tessellator);
		tessellator.addVertex(width - 17, 20D, zLevel);
		tessellator.addVertex(width - 20, 20D, zLevel);
		tessellator.addVertex(width - 20, 180D, zLevel);
		tessellator.addVertex(width - 17, 180D, zLevel);
		tessellator.draw();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glScalef(2.0F, 2.0F, 2.0F);
		fontRenderer.drawStringWithShadow(displayString, 10, shifty / 2, 0xFFFFFF);
		fontRenderer.drawStringWithShadow(colorMain + "Community:", (displayStringWidth + 38) / 2, shifty / 2, 0xFFFFFF);
		if (cake) {
			Minecraft mc = CommonUtils.getMc();
			RenderHelper.enableStandardItemLighting();
			RenderItem renderItem = new RenderItem();
			renderItem.renderItemIntoGUI(mc.fontRenderer, mc.renderEngine, new ItemStack(Item.cake), displayStringWidth / 2 - 16, 23);

			RenderHelper.disableStandardItemLighting();
		}
		GL11.glScalef(1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		shifty += 20;
		fontRenderer.drawStringWithShadow("" + colorSub + (cake ? "Not a lie!" : "By Vazkii, version " + ModMakerReference.VERSION), 30, shifty, 0xFFFFF);
		shifty += 6;
		fontRenderer.drawString("This is the community section", displayStringWidth + 38, shifty, 4210752);
		shifty += 12;
		fontRenderer.drawString("if you have anything to add here", displayStringWidth + 38, shifty, 4210752);
		shifty += 12;
		fontRenderer.drawString("PM Vazkii on the forums", displayStringWidth + 38, shifty, 4210752);
		shifty += 12;
		fontRenderer.drawString("or Tweet @Vazkii.", displayStringWidth + 38, shifty, 4210752);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	public class NoTextureButton extends GuiButton {

		ColorCode color;
		boolean inverted;

		int current = 0;

		public NoTextureButton(int par1, int par2, int par3, int par4, int par5, String par6Str, ColorCode color) {
			this(par1, par2, par3, par4, par5, par6Str, color, false);
		}

		public NoTextureButton(int par1, int par2, int par3, int par4, int par5, String par6Str, ColorCode color, boolean inverted) {
			super(par1, par2, par3, par4, par5, par6Str);
			this.inverted = inverted;
			this.color = color;
		}

		@Override
		public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
			if (drawButton) {
				FontRenderer var4 = par1Minecraft.fontRenderer;
				boolean isHovering = par2 >= xPosition && par3 >= yPosition && par2 < xPosition + width && par3 < yPosition + height;
				int state = getHoverState(isHovering);

				if (state == 2 && (inverted ? current > -16 : current < 16)) current = inverted ? --current : ++current;
				if (state == 1 && (inverted ? current < 0 : current > 0)) current = inverted ? ++current : --current;

				if (state == 2) {
					FontRenderer sgtRenderer = par1Minecraft.standardGalacticFontRenderer;
					sgtRenderer.drawString(displayString, (inverted ? -12 : 12) + xPosition + width / 2 + current - sgtRenderer.getStringWidth(displayString) / 2, yPosition + (height - 8) / 2, 0x000000);
				}

				drawCenteredString(var4, color + displayString, xPosition + width / 2 + current, yPosition + (height - 8) / 2, 0xFFFFFF);
			}
		}

	}

}
