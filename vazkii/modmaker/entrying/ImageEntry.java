package vazkii.modmaker.entrying;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import vazkii.modmaker.IOHelper;
import vazkii.modmaker.mod_ModMaker;

public class ImageEntry extends ModEntry<ImageEntry> {

	String fileName;
	byte[] byteData;
	String modName;

	@Override
	public ImageEntry init(Object... params) {
		fileName = (String) params[0];
		byteData = (byte[]) params[1];
		modName = (String) params[2];
		return this;
	}

	@Override
	public void readEntry() {
		try {
			File spritesZip = IOHelper.createSpritesZip('\u017F' + " LMM_SPRITE_" + modName + "_" + fileName.replace(".png", "") + ".zip");
			if (!spritesZip.exists()) {
				spritesZip.createNewFile();
				ZipOutputStream outStream = new ZipOutputStream(new FileOutputStream(spritesZip));
				ZipEntry entry = new ZipEntry(fileName);
				outStream.putNextEntry(entry);
				outStream.write(byteData);
				outStream.closeEntry();
				outStream.close();
			} else mod_ModMaker.claimedSprites.add(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
