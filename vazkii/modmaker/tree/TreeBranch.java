package vazkii.modmaker.tree;

import java.util.Collection;
import java.util.TreeMap;

import net.minecraft.src.GuiScreen;
import net.minecraft.src.NBTBase;
import net.minecraft.src.NBTTagCompound;
import vazkii.modmaker.gui.GuiBranch;
import vazkii.modmaker.gui.GuiCreateBranch;

public abstract class TreeBranch {

	public abstract TreeBranch init(TreeBranch superBranch, String label);

	public abstract TreeMap<String, TreeBranch> subBranches();

	public abstract TreeBranch superBranch();

	public abstract void addBranch(String branchName, TreeBranch branch);

	public abstract void deleteBranch(String branchName);

	public abstract String label();

	public void writeToNBT(NBTTagCompound cmp, TreeBranch superBranch) {
		cmp.setString("label", label());
		cmp.setString("type", BranchHelper.getNameFromClass(getClass()));
		TreeMap<String, TreeBranch> branches = subBranches();
		if (branches != null) for (String s : branches.keySet()) {
			TreeBranch branch = branches.get(s);
			cmp.setCompoundTag(branch.label(), new NBTTagCompound());
			NBTTagCompound cmp1 = cmp.getCompoundTag(branch.label());
			branch.writeToNBT(cmp1, this);
		}

		if (this instanceof LeafableBranch) {
			TreeMap<String, TreeLeaf> leaves = ((LeafableBranch) this).leaves();
			for (String s : leaves.keySet()) {
				TreeLeaf l = leaves.get(s);
				l.writeToNBT(cmp, this);
			}
		}
	}

	public void readFromNBT(NBTTagCompound cmp, TreeBranch superBranch) {
		cmp.getString("label");
		Collection<NBTBase> tags = cmp.getTags();
		if (tags != null) for (NBTBase b : tags) {
			if (b instanceof NBTTagCompound) {
				NBTTagCompound cmp1 = (NBTTagCompound) b;
				TreeBranch branch = BranchHelper.branchFromNBT(cmp1);
				String type = cmp1.getString("type");
				branch = branch.init(this, cmp1.getString("label"));
				addBranch(label(), branch);
				branch.readFromNBT(cmp1, this);
			}
			if (this instanceof LeafableBranch) for (TreeLeaf leaf : ((LeafableBranch) this).leaves().values())
				leaf.readFromNBT(cmp, this);
		}
	}

	public boolean isConstant() {
		return false;
	}

	public Class<? extends GuiBranch> getSpecialBranchGui() {
		return GuiBranch.class;
	}

	public GuiCreateBranch getBranchCreationGui(GuiScreen screen) {
		return null;
	}

}
