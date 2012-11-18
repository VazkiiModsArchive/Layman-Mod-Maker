package vazkii.modmaker.tree;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import vazkii.modmaker.addon.event.LMMEvent.EventPeriod;
import vazkii.modmaker.addon.event.LeafInitEvent;
import vazkii.modmaker.addon.event.LeafNBTReadEvent;
import vazkii.modmaker.addon.event.LeafNBTWriteEvent;
import vazkii.modmaker.gui.GuiLeafEdit;
import vazkii.modmaker.gui.GuiLeafString;

import net.minecraft.src.GuiScreen;
import net.minecraft.src.NBTTagCompound;

import net.minecraftforge.common.MinecraftForge;

public class LeafString extends TreeLeaf<String> {

	String s;
	String label;
	Set<String> validTokens = new TreeSet();
	Set<String> addonTokens = new TreeSet();

	@Override
	public String read() {
		return s;
	}

	@Override
	public TreeLeaf write(String value) {
		s = value;
		return this;
	}

	@Override
	public TreeLeaf init(TreeBranch superBranch, String _default, String label) {
		return init(superBranch, _default, label, (String[]) null);
	}

	public TreeLeaf init(TreeBranch superBranch, String _default, String label, final String... validTokens) {
		this.label = label;
		if (validTokens != null) this.validTokens.addAll(Arrays.asList(validTokens));
		branch = superBranch;
		MinecraftForge.EVENT_BUS.post(new LeafInitEvent(EventPeriod.DURING, this));
		return write(_default);
	}

	public boolean addValidToken(String token) {
		return validTokens.add(token) ? addonTokens.add(token) : false;
	}

	public boolean isTokenFromAddon(String token) {
		return addonTokens.contains(token);
	}

	@Override
	public String label() {
		return label;
	}

	@Override
	public void writeToNBT(NBTTagCompound cmp, TreeBranch superBranch) {
		MinecraftForge.EVENT_BUS.post(new LeafNBTWriteEvent(EventPeriod.BEFORE, this, superBranch, cmp));
		cmp.setString(label(), read());
		MinecraftForge.EVENT_BUS.post(new LeafNBTWriteEvent(EventPeriod.AFTER, this, superBranch, cmp));
	}

	@Override
	public void readFromNBT(NBTTagCompound cmp, TreeBranch superBranch) {
		MinecraftForge.EVENT_BUS.post(new LeafNBTReadEvent(EventPeriod.BEFORE, this, superBranch, cmp));
		cmp.getTag(label());
		write(cmp.getString(label()));
		MinecraftForge.EVENT_BUS.post(new LeafNBTReadEvent(EventPeriod.AFTER, this, superBranch, cmp));
	}

	@Override
	public GuiLeafEdit getLeafEditGui(GuiScreen parent) {
		return new GuiLeafString(parent, this, label(), validTokens);
	}

	@Override
	public TreeBranch getBranch() {
		return branch;
	}

	TreeBranch branch;

	@Override
	public String displayString() {
		return read();
	}

}
