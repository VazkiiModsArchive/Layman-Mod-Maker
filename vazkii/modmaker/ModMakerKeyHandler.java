package vazkii.modmaker;

import java.util.EnumSet;

import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.KeyBinding;

import org.lwjgl.input.Keyboard;

import vazkii.codebase.common.CommonUtils;
import vazkii.modmaker.gui.GuiStartHere;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.asm.SideOnly;

@SideOnly(Side.CLIENT)
public class ModMakerKeyHandler extends KeyHandler {

	public static KeyBinding key = new KeyBinding("Mod Maker", Keyboard.KEY_F7);

	public ModMakerKeyHandler() {
		super(new KeyBinding[] { key }, new boolean[] { false });
	}

	@Override
	public String getLabel() {
		return "Layman Mod Maker";
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat) {
		GuiScreen activeScreen = CommonUtils.getMc().currentScreen;
		if (activeScreen != null && activeScreen instanceof GuiMainMenu) CommonUtils.getMc().displayGuiScreen(new GuiStartHere());
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.RENDER);
	}

}
