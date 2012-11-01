package vazkii.modmaker.mod;

import static net.minecraft.src.BlockSand.canFallBelow;
import static net.minecraft.src.BlockSand.fallInstantly;

import java.util.Random;

import vazkii.codebase.common.CommonUtils;
import vazkii.modmaker.tree.objective.BlockDropBranch;

import net.minecraft.src.Block;
import net.minecraft.src.EntityFallingSand;
import net.minecraft.src.Material;
import net.minecraft.src.World;

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
	public void onBlockAdded(World par1World, int par2, int par3, int par4) {
		par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate());
	}

	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
		par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate());
	}

	@Override
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		if (!par1World.isRemote) tryToFall(par1World, par2, par3, par4);
	}

	@Override
	public int tickRate() {
		return gravity ? 3 : super.tickRate();
	}

	@Override
	public void setColorOverlay(int i) {
		colorOverlay = i;
	}

	@Override
	public int getRenderColor(int par1) {
		return colorOverlay;
	}

	@Override
	public int quantityDropped(Random par1Random) {
		return dropQtdMin == dropQtdMax ? dropQtdMin : CommonUtils.nextIntMinMax(dropQtdMin, dropQtdMax);
	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return dropID < 0 ? blockID : dropID;
	}

	@Override
	public int damageDropped(int par1) {
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

	@Override
	public boolean isOpaqueCube() {
		return opaque;
	}

	@Override
	public void setGravity(boolean b) {
		gravity = b;
	}

	private void tryToFall(World par1World, int par2, int par3, int par4) {
		if (!gravity) return;

		if (canFallBelow(par1World, par2, par3 - 1, par4) && par3 >= 0) {
			byte var8 = 32;

			if (!fallInstantly && par1World.checkChunksExist(par2 - var8, par3 - var8, par4 - var8, par2 + var8, par3 + var8, par4 + var8)) {
				if (!par1World.isRemote) {
					EntityFallingSand var9 = new EntityFallingSand(par1World, par2 + 0.5F, par3 + 0.5F, par4 + 0.5F, blockID);
					par1World.spawnEntityInWorld(var9);
				}
			} else {
				par1World.setBlockWithNotify(par2, par3, par4, 0);
				while (canFallBelow(par1World, par2, par3 - 1, par4) && par3 > 0)
					--par3;

				if (par3 > 0) par1World.setBlockWithNotify(par2, par3, par4, blockID);
			}
		}
	}
}
