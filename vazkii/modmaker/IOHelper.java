package vazkii.modmaker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;

import vazkii.codebase.common.IOUtils;
import vazkii.modmaker.addon.event.LMMEvent.EventPeriod;
import vazkii.modmaker.addon.event.LoadAllModsEvent;
import vazkii.modmaker.addon.event.LoadModEvent;
import vazkii.modmaker.tree.objective.UserMod;

import com.google.common.io.ByteStreams;

import net.minecraft.client.Minecraft;

import net.minecraft.src.NBTTagCompound;

import net.minecraftforge.common.MinecraftForge;

public class IOHelper {

	public static void saveMod(UserMod mod) {
		File modFile = IOUtils.createAndGetNBTFile(IOUtils.createAndGetFile(new File(mod_ModMaker.usermodsFile, mod.label() + ".dat")));
		NBTTagCompound cmp = new NBTTagCompound();
		mod.writeToNBT(cmp, null);
		IOUtils.injectNBTToFile(cmp, modFile);
	}

	public static UserMod loadMod(File file) {
		MinecraftForge.EVENT_BUS.post(new LoadModEvent(EventPeriod.BEFORE, file));
		NBTTagCompound cmp = IOUtils.getTagCompoundInFile(file);
		UserMod mod = ((UserMod) new UserMod().init(null, cmp.getString("label"))).setAuthor(cmp.getString("author"));
		MinecraftForge.EVENT_BUS.post(new LoadModEvent(EventPeriod.DURING, file, mod));
		mod.readFromNBT(cmp, null);
		MinecraftForge.EVENT_BUS.post(new LoadModEvent(EventPeriod.AFTER, file, mod));
		return mod;
	}

	public static void loadAllMods() {
		MinecraftForge.EVENT_BUS.post(new LoadAllModsEvent(EventPeriod.BEFORE));
		for (File f : mod_ModMaker.usermodsFile.listFiles(new VariableEndFilter(".dat"))) {
			UserMod mod = loadMod(f);
			mod_ModMaker.userMods.put(mod.label(), mod);
		}
		MinecraftForge.EVENT_BUS.post(new LoadAllModsEvent(EventPeriod.AFTER));
	}

	public static byte[] convertFileToByteArray(File f) {
		try {
			return ByteStreams.toByteArray(new FileInputStream(f));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void writeImageIn(File f, byte[] imageData) {
		try {
			FileOutputStream outputStream = new FileOutputStream(f);
			outputStream.write(imageData);
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static File createSpritesZip(String name) {
		File modsFolder = Minecraft.getAppDir("minecraft/mods");
		return new File(modsFolder, name);
	}

	public static class VariableEndFilter implements FilenameFilter {
		String end;

		public VariableEndFilter(String end) {
			this.end = end;
		}

		@Override
		public boolean accept(File dir, String name) {
			return name.endsWith(end);
		}

	}
}
