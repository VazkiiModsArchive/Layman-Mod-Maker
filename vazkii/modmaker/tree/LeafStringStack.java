package vazkii.modmaker.tree;

import vazkii.modmaker.gui.GuiLeafEdit;
import vazkii.modmaker.gui.GuiLeafStringStack;

import net.minecraft.src.Block;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class LeafStringStack extends LeafString {

	boolean qtd = false;

	public TreeLeaf init(TreeBranch superBranch, String _default, String label, boolean qtd) {
		this.qtd = qtd;
		return init(superBranch, _default, label);
	}

	@Override
	public GuiLeafEdit getLeafEditGui(GuiScreen parent) {
		return new GuiLeafStringStack(parent, this, label(), qtd);
	}

	public ItemStack getStack() {
		return getStack(read());
	}

	public static ItemStack getStack(String s) {
		String tokens[] = s.split(":");
		int offset = tokens.length == 2 ? 1 : 0;
		try {
			int id = Integer.parseInt(tokens[0]);
			int qtd = offset == 0 ? Integer.parseInt(tokens[1]) : 1;
			int meta = Integer.parseInt(tokens[2 - offset]);
			if (Block.blocksList[id] == null && Item.itemsList[id] == null) return null;

			return new ItemStack(id, qtd, meta);
		} catch (NumberFormatException e) {
			return null;
		}
	}

}
