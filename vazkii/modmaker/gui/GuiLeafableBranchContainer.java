package vazkii.modmaker.gui;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeMap;

import vazkii.codebase.common.CommonUtils;
import vazkii.modmaker.tree.TreeBranch;
import vazkii.modmaker.tree.TreeLeaf;

import net.minecraft.src.FontRenderer;
import net.minecraft.src.Tessellator;

public class GuiLeafableBranchContainer extends GuiBranchContainer {

	TreeMap<String, TreeLeaf> leaves;
	Set<String> leafMappings = new LinkedHashSet();

	public GuiLeafableBranchContainer(GuiBranch screen, TreeMap<String, TreeBranch> branches, TreeMap<String, TreeLeaf> leaves) {
		super(screen, branches);
		this.leaves = leaves;
		labels.addAll(leaves.keySet());
		leafMappings.addAll(leaves.keySet());
	}

	@Override
	public void elementClicked(int var1, boolean var2) {
		selectedIndex = var1;
		String name = labels.get(var1);
		TreeBranch branch = branches.get(name);
		parent.select(branch == null ? leaves.get(name) : branch, name);
	}

	@Override
	protected void drawSlot(int var1, int var2, int var3, int var4, Tessellator var5) {
		FontRenderer font = CommonUtils.getMc().fontRenderer;
		String name = labels.get(var1);
		if (leaves.containsKey(name)) {
			TreeLeaf leaf = leaves.get(name);
			String label = leaf.label();
			font.drawString(label, var2, var3 + 6, 0xFFFFFF);
			font.drawString(leaf.displayString(), var2 + 6, var3 + 18, 0x404040);
		} else super.drawSlot(var1, var2, var3, var4, var5);
	}

}
