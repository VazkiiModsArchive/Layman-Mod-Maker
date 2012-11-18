package vazkii.modmaker.tree;

import vazkii.modmaker.addon.event.LMMEvent.EventPeriod;
import vazkii.modmaker.addon.event.LeafInitEvent;
import vazkii.modmaker.addon.event.LeafNBTReadEvent;
import vazkii.modmaker.addon.event.LeafNBTWriteEvent;
import vazkii.modmaker.gui.GuiLeafBoolean;
import vazkii.modmaker.gui.GuiLeafEdit;

import net.minecraft.src.GuiScreen;
import net.minecraft.src.NBTTagCompound;

import net.minecraftforge.common.MinecraftForge;

public class LeafBoolean extends TreeLeaf<Boolean> {

	boolean b;
	String label;

	@Override
	public Boolean read() {
		return b;
	}

	@Override
	public TreeLeaf write(Boolean value) {
		b = value;
		return this;
	}

	@Override
	public TreeLeaf init(TreeBranch superBranch, Boolean _default, String label) {
		this.label = label;
		branch = superBranch;
		MinecraftForge.EVENT_BUS.post(new LeafInitEvent(EventPeriod.DURING, this));
		return write(_default);
	}

	@Override
	public String label() {
		return label;
	}

	@Override
	public void writeToNBT(NBTTagCompound cmp, TreeBranch superBranch) {
		MinecraftForge.EVENT_BUS.post(new LeafNBTWriteEvent(EventPeriod.BEFORE, this, superBranch, cmp));
		cmp.setBoolean(label(), read());
		MinecraftForge.EVENT_BUS.post(new LeafNBTReadEvent(EventPeriod.AFTER, this, superBranch, cmp));
	}

	@Override
	public void readFromNBT(NBTTagCompound cmp, TreeBranch superBranch) {
		MinecraftForge.EVENT_BUS.post(new LeafNBTReadEvent(EventPeriod.BEFORE, this, superBranch, cmp));
		if (cmp.hasKey(label())) write(cmp.getBoolean(label()));
		MinecraftForge.EVENT_BUS.post(new LeafNBTReadEvent(EventPeriod.AFTER, this, superBranch, cmp));
	}

	@Override
	public GuiLeafEdit getLeafEditGui(GuiScreen parent) {
		return new GuiLeafBoolean(parent, this, label());
	}

	@Override
	public TreeBranch getBranch() {
		return branch;
	}

	TreeBranch branch;

	@Override
	public String displayString() {
		return read().toString();
	}

}
