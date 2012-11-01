package vazkii.modmaker;

import java.util.EnumSet;

import org.lwjgl.input.Keyboard;

import vazkii.modmaker.gui.GuiStartHere;

import net.minecraft.src.KeyBinding;

import cpw.mods.fml.client.FMLClientHandler;
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
		if (tickEnd) {
			FMLClientHandler handler = FMLClientHandler.instance();
			handler.showGuiScreen(new GuiStartHere());
		}
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.RENDER);
	}

}
