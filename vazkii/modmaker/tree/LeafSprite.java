package vazkii.modmaker.tree;

import vazkii.modmaker.addon.event.LMMEvent.EventPeriod;
import vazkii.modmaker.addon.event.LeafInitEvent;
import vazkii.modmaker.addon.event.LeafNBTReadEvent;
import vazkii.modmaker.addon.event.LeafNBTWriteEvent;
import vazkii.modmaker.gui.GuiLeafEdit;
import vazkii.modmaker.gui.GuiLeafSprite;

import net.minecraft.src.GuiScreen;
import net.minecraft.src.NBTBase;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagInt;

import net.minecraftforge.common.MinecraftForge;

public class LeafSprite extends TreeLeaf {

	Object obj;
	String label;

	@Override
	public Object read() {
		return obj;
	}

	@Override
	public TreeLeaf write(Object value) {
		obj = value;
		return this;
	}

	@Override
	public TreeLeaf init(TreeBranch superBranch, Object _default, String label) {
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
		if (obj instanceof String) cmp.setString(label(), (String) read());
		else if (obj instanceof Integer) cmp.setInteger(label(), (Integer) read());
		MinecraftForge.EVENT_BUS.post(new LeafNBTWriteEvent(EventPeriod.AFTER, this, superBranch, cmp));
	}

	@Override
	public void readFromNBT(NBTTagCompound cmp, TreeBranch superBranch) {
		MinecraftForge.EVENT_BUS.post(new LeafNBTReadEvent(EventPeriod.BEFORE, this, superBranch, cmp));
		if (cmp.hasKey(label())) {
			NBTBase tag = cmp.getTag(label());
			if (tag instanceof NBTTagInt) write(cmp.getInteger(label()));
			else write(cmp.getString(label()));
		}
		MinecraftForge.EVENT_BUS.post(new LeafNBTReadEvent(EventPeriod.AFTER, this, superBranch, cmp));
	}

	@Override
	public GuiLeafEdit getLeafEditGui(GuiScreen parent) {
		return new GuiLeafSprite(parent, this, "Sprite");
	}

	@Override
	public String displayString() {
		return read().toString();
	}

	@Override
	public TreeBranch getBranch() {
		return branch;
	}

	TreeBranch branch;

}
