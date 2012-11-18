package vazkii.modmaker.tree.objective;

import java.util.TreeMap;

import vazkii.modmaker.addon.event.LMMEvent.EventPeriod;
import vazkii.modmaker.addon.event.TreeBranchInitEvent;
import vazkii.modmaker.gui.GuiCreateBranch;
import vazkii.modmaker.tree.TreeBranch;

import net.minecraft.src.GuiScreen;

import net.minecraftforge.common.MinecraftForge;

public class ImagesBranch extends TreeBranch {

	private String label;
	private TreeBranch superBranch;
	private TreeMap<String, TreeBranch> subBranches = new TreeMap();

	@Override
	public TreeBranch init(TreeBranch superBranch, String label) {
		this.label = label;
		this.superBranch = superBranch;
		MinecraftForge.EVENT_BUS.post(new TreeBranchInitEvent(EventPeriod.DURING, this));
		return this;
	}

	@Override
	public TreeMap<String, TreeBranch> subBranches() {
		return subBranches;
	}

	@Override
	public TreeBranch superBranch() {
		return superBranch;
	}

	@Override
	public void addBranch(String branchName, TreeBranch branch) {
		if (branch instanceof ImageBranch) subBranches.put(branchName, branch);
	}

	@Override
	public String label() {
		return label;
	}

	@Override
	public void deleteBranch(String branchName) {
		subBranches.remove(branchName);
	}

	@Override
	public GuiCreateBranch getBranchCreationGui(GuiScreen screen) {
		return new GuiCreateBranch(this, "Image", ImageBranch.class, screen);
	}

}
