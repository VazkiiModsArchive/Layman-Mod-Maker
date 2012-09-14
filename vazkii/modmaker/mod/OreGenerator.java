package vazkii.modmaker.mod;

import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

import net.minecraft.src.IChunkProvider;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;
import vazkii.codebase.common.CommonUtils;
import vazkii.modmaker.entrying.OreEntry;
import cpw.mods.fml.common.IWorldGenerator;

public class OreGenerator implements IWorldGenerator {

	public static Set<OreEntry.GenerationHelper> generatingHelpers = new LinkedHashSet();

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		actuallyGenerate(random, chunkX << 4, chunkZ << 4, world, chunkGenerator, chunkProvider);
	}

	public void actuallyGenerate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		for (OreEntry.GenerationHelper helper : generatingHelpers) {
			if (!helper.canGenerate(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider)) continue;

			for (int i = 0; i < helper.oreDensity; i++) {
				int posX = chunkX + random.nextInt(16);
				int posY = CommonUtils.nextIntMinMax(helper.minHeight, helper.maxHeight);
				int posZ = chunkZ + random.nextInt(16);
				new GenerationEntry(helper.blockToSet, helper.maxVeinSize, helper.blockToReplace).generate(world, random, posX, posY, posZ);
			}
		}
	}

	public class GenerationEntry extends WorldGenerator {

		private int blockID;
		private int maxBlockCount;
		private int replaceBlockID;

		public GenerationEntry(int blockID, int maxBlockCount, int replaceBlockID) {
			this.blockID = blockID;
			this.maxBlockCount = maxBlockCount;
			this.replaceBlockID = replaceBlockID;
		}

		@Override
		public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5) {
			float var6 = par2Random.nextFloat() * (float) Math.PI;
			double var7 = par3 + 8 + MathHelper.sin(var6) * maxBlockCount / 8.0F;
			double var9 = par3 + 8 - MathHelper.sin(var6) * maxBlockCount / 8.0F;
			double var11 = par5 + 8 + MathHelper.cos(var6) * maxBlockCount / 8.0F;
			double var13 = par5 + 8 - MathHelper.cos(var6) * maxBlockCount / 8.0F;
			double var15 = par4 + par2Random.nextInt(3) - 2;
			double var17 = par4 + par2Random.nextInt(3) - 2;

			for (int var19 = 0; var19 <= maxBlockCount; ++var19) {
				double var20 = var7 + (var9 - var7) * var19 / maxBlockCount;
				double var22 = var15 + (var17 - var15) * var19 / maxBlockCount;
				double var24 = var11 + (var13 - var11) * var19 / maxBlockCount;
				double var26 = par2Random.nextDouble() * maxBlockCount / 16.0D;
				double var28 = (MathHelper.sin(var19 * (float) Math.PI / maxBlockCount) + 1.0F) * var26 + 1.0D;
				double var30 = (MathHelper.sin(var19 * (float) Math.PI / maxBlockCount) + 1.0F) * var26 + 1.0D;
				int var32 = MathHelper.floor_double(var20 - var28 / 2.0D);
				int var33 = MathHelper.floor_double(var22 - var30 / 2.0D);
				int var34 = MathHelper.floor_double(var24 - var28 / 2.0D);
				int var35 = MathHelper.floor_double(var20 + var28 / 2.0D);
				int var36 = MathHelper.floor_double(var22 + var30 / 2.0D);
				int var37 = MathHelper.floor_double(var24 + var28 / 2.0D);

				for (int var38 = var32; var38 <= var35; ++var38) {
					double var39 = (var38 + 0.5D - var20) / (var28 / 2.0D);
					if (var39 * var39 < 1.0D) for (int var41 = var33; var41 <= var36; ++var41) {
						double var42 = (var41 + 0.5D - var22) / (var30 / 2.0D);
						if (var39 * var39 + var42 * var42 < 1.0D) for (int var44 = var34; var44 <= var37; ++var44) {
							double var45 = (var44 + 0.5D - var24) / (var28 / 2.0D);
							int id = par1World.getBlockId(var38, var41, var44);
							if (var39 * var39 + var42 * var42 + var45 * var45 < 1.0D && id == replaceBlockID) par1World.setBlockWithNotify(var38, var41, var44, blockID);
						}
					}
				}
			}

			return true;
		}

	}

}
