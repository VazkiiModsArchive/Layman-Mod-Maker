package vazkii.modmaker.tree;

import java.io.File;

import net.minecraft.src.GuiScreen;
import net.minecraft.src.NBTTagCompound;
import vazkii.modmaker.IOHelper;
import vazkii.modmaker.entrying.ImageEntry;
import vazkii.modmaker.gui.GuiLeafEdit;
import vazkii.modmaker.gui.GuiLeafImage;

public class LeafImage extends TreeLeaf<File> {

	File f;
	String fileName = "";
	byte[] array;
	String label;

	@Override
	public File read() {
		return f;
	}

	@Override
	public String displayString() {
		return fileName;
	}

	@Override
	public TreeLeaf write(File value) {
		f = value;
		fileName = f.getName();
		array = IOHelper.convertFileToByteArray(f);
		return this;
	}

	@Override
	public TreeLeaf init(TreeBranch superBranch, File _default, String label) {
		this.label = label;
		branch = superBranch;
		return this;
	}

	@Override
	public TreeBranch getBranch() {
		return branch;
	}

	TreeBranch branch;

	@Override
	public String label() {
		return label;
	}

	@Override
	public void writeToNBT(NBTTagCompound cmp, TreeBranch superBranch) {
		if (array != null) cmp.setByteArray(label() + "_array", array);
		cmp.setString(label() + "_name", fileName);
	}

	@Override
	public void readFromNBT(NBTTagCompound cmp, TreeBranch superBranch) {
		fileName = cmp.getString(label() + "_name");
		array = cmp.getByteArray(label() + "_array");
		if (array != null) new ImageEntry().init(((LeafableBranch) getBranch()).leaves().get("Image Name").read(), array, BranchHelper.getModFromBranch(getBranch()).label()).readEntry();
	}

	@Override
	public GuiLeafEdit getLeafEditGui(GuiScreen parent) {
		return new GuiLeafImage(parent, this, label());
	}

}
