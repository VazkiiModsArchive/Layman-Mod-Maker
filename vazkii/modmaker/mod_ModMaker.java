package vazkii.modmaker;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.TreeMap;

import updatemanager.common.ModConverter;
import vazkii.codebase.common.CommonUtils;
import vazkii.codebase.common.EnumVazkiiMods;
import vazkii.codebase.common.mod_Vazcore;
import vazkii.modmaker.mod.Achievements;
import vazkii.modmaker.mod.OreGenerator;
import vazkii.modmaker.tree.objective.AchievementBranch;
import vazkii.modmaker.tree.objective.UserMod;
import net.minecraft.client.Minecraft;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraftforge.common.MinecraftForge;

@Mod(modid = "modmaker_Vz", name = "Layman Mod Maker", version = "by Vazkii. Version [1.1] for 1.4.2.")
public class mod_ModMaker {

	public static TreeMap<String, UserMod> userMods = new TreeMap();
	public static LinkedHashSet<String> claimedSprites = new LinkedHashSet();
	public static File usermodsFile;

	public static ModMakerUpdateHandler updateHandler;

	@Init
	public void onInit(FMLInitializationEvent event) {
		mod_Vazcore.loadedVzMods.add(EnumVazkiiMods.LAYMAN_MOD_MAKER.getAcronym());
		usermodsFile = CommonUtils.getSide().isClient() ? Minecraft.getAppDir("minecraft/usermods") : new File(".", "usermods");
		usermodsFile.mkdir();
		try {
			keybindInit();
		} catch (Throwable e) {
		}
		updateHandler = new ModMakerUpdateHandler(ModConverter.getMod(getClass()));
	}

	public void keybindInit() {
		KeyBindingRegistry.registerKeyBinding(new ModMakerKeyHandler());
	}

	@PostInit
	public void onPostInit(FMLPostInitializationEvent event) {
		IOHelper.loadAllMods();
		GameRegistry.registerWorldGenerator(new OreGenerator());
		Achievements ach = new Achievements();
		GameRegistry.registerCraftingHandler(ach);
		MinecraftForge.EVENT_BUS.register(ach);
		AchievementBranch.registerAllAchievements();
	}

}
