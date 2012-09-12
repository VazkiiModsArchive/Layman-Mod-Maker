package vazkii.modmaker.mod;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.Material;
import vazkii.codebase.common.CommonUtils;
import vazkii.modmaker.tree.objective.BlockDropBranch;

public class BlockCustom extends Block implements CustomizableBlock {

	int colorOverlay;
	boolean opaque;
	boolean gravity;

	int dropID;
	int dropMeta;
	int dropQtdMin;
	int dropQtdMax;

	public BlockCustom(int par1, int par2, Material par3Material) {
		super(par1, par2, par3Material);
	}

	@Override
	public void setColorOverlay(int i) {
		colorOverlay = i;
	}

	@Override
	public int getRenderColor(int par1) {
		return colorOverlay;
	}

	@Override public int quantityDropped(Random par1Random) {
		return dropQtdMin == dropQtdMax ? dropQtdMin : CommonUtils.nextIntMinMax(dropQtdMin, dropQtdMax);
	}

	@Override public int idDropped(int par1, Random par2Random, int par3) {
		return dropID < 0 ? blockID : dropID;
	}

	@Override protected int damageDropped(int par1) {
		return dropMeta < 0 ? par1 : dropMeta;
	}

	@Override
	public void setDropStats(BlockDropBranch branch) {
		dropID = (Integer) branch.leaves().get("Drop ID").read();
		dropMeta = (Integer) branch.leaves().get("Drop Metadata").read();
		dropQtdMin = (Integer) branch.leaves().get("Drop Count Min").read();
		dropQtdMax = (Integer) branch.leaves().get("Drop Count Max").read();
	}

	@Override
	public void setOpaque(boolean b) {
		opaque = b;
	}

	@Override public boolean isOpaqueCube() {
		return opaque;
	}

	@Override
	public void setGravity(boolean b) {
		gravity = b;
	}
}
