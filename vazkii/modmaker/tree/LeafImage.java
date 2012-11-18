package vazkii.modmaker.tree;

import java.io.File;

import vazkii.modmaker.IOHelper;
import vazkii.modmaker.addon.event.LMMEvent.EventPeriod;
import vazkii.modmaker.addon.event.LeafInitEvent;
import vazkii.modmaker.addon.event.LeafNBTReadEvent;
import vazkii.modmaker.addon.event.LeafNBTWriteEvent;
import vazkii.modmaker.entrying.ImageEntry;
import vazkii.modmaker.gui.GuiLeafEdit;
import vazkii.modmaker.gui.GuiLeafImage;

import net.minecraft.src.GuiScreen;
import net.minecraft.src.NBTTagCompound;

import net.minecraftforge.common.MinecraftForge;

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
		MinecraftForge.EVENT_BUS.post(new LeafInitEvent(EventPeriod.DURING, this));
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
		MinecraftForge.EVENT_BUS.post(new LeafNBTWriteEvent(EventPeriod.BEFORE, this, superBranch, cmp));
		if (array != null) cmp.setByteArray(label() + "_array", array);
		cmp.setString(label() + "_name", fileName);
		MinecraftForge.EVENT_BUS.post(new LeafNBTWriteEvent(EventPeriod.AFTER, this, superBranch, cmp));
	}

	@Override
	public void readFromNBT(NBTTagCompound cmp, TreeBranch superBranch) {
		MinecraftForge.EVENT_BUS.post(new LeafNBTReadEvent(EventPeriod.BEFORE, this, superBranch, cmp));
		if (cmp.hasKey(label() + "_name")) fileName = cmp.getString(label() + "_name");
		if (cmp.hasKey(label() + "_array")) array = cmp.getByteArray(label() + "_array");
		if (array != null) new ImageEntry().init(((LeafableBranch) getBranch()).leaves().get("Image Name").read(), array, BranchHelper.getModFromBranch(getBranch()).label()).readEntry();
		MinecraftForge.EVENT_BUS.post(new LeafNBTReadEvent(EventPeriod.AFTER, this, superBranch, cmp));
	}

	@Override
	public GuiLeafEdit getLeafEditGui(GuiScreen parent) {
		return new GuiLeafImage(parent, this, label());
	}

}
