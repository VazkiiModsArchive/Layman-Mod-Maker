package vazkii.modmaker.entrying;

import java.util.Random;

import vazkii.modmaker.mod.OreGenerator;
import vazkii.modmaker.tree.BranchHelper;
import vazkii.modmaker.tree.objective.OreGenerateBranch;

import net.minecraft.src.ChunkProviderEnd;
import net.minecraft.src.ChunkProviderFlat;
import net.minecraft.src.ChunkProviderGenerate;
import net.minecraft.src.ChunkProviderHell;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.World;

public class OreEntry extends ModEntry<OreEntry> {

	GenerationHelper helper;
	OreGenerateBranch branch;

	@Override
	public OreEntry init(Object... params) {
		branch = (OreGenerateBranch) params[0];
		return this;
	}

	@Override
	public void readEntry() {
		String label = branch.label();
		String world = (String) branch.leaves().get("World").read();
		int oreDensity = (Integer) branch.leaves().get("Ore Density").read();
		int maxVeinSize = (Integer) branch.leaves().get("Max Vein Size").read();
		int minHeight = (Integer) branch.leaves().get("Min Height").read();
		int maxHeight = (Integer) branch.leaves().get("Max Height").read();
		int blockToReplace = (Integer) branch.leaves().get("Block to Replace").read();
		int blockToSet = (Integer) branch.leaves().get("Block to Set").read();
		OreGenerator.generatingHelpers.add(new GenerationHelper(BranchHelper.getModFromBranch(branch).label() + "_" + label, world, oreDensity, maxVeinSize, minHeight, maxHeight, blockToReplace, blockToSet));
	}

	public class GenerationHelper {

		public final int oreDensity;
		public final int maxVeinSize;
		public final int minHeight;
		public final int maxHeight;
		public final int blockToReplace;
		public final int blockToSet;
		public final String world;

		public final String label;

		public GenerationHelper(String label, String world, int oreDensity, int maxVeinSize, int minHeight, int maxHeight, int blockToReplace, int blockToSet) {
			this.oreDensity = oreDensity;
			this.maxVeinSize = maxVeinSize;
			this.minHeight = minHeight;
			this.maxHeight = maxHeight;
			this.blockToReplace = blockToReplace;
			this.blockToSet = blockToSet;
			this.world = world;
			this.label = label;
		}

		public boolean canGenerate(Random random, int chunkX, int chunkZ, World worldObj, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
			if (world.equalsIgnoreCase("surface") && chunkGenerator instanceof ChunkProviderGenerate) return true;
			if (world.equalsIgnoreCase("nether") && chunkGenerator instanceof ChunkProviderHell) return true;
			if (world.equalsIgnoreCase("ender") && chunkGenerator instanceof ChunkProviderEnd) return true;
			if (world.equalsIgnoreCase("flat") && chunkGenerator instanceof ChunkProviderFlat) return true;
			return false;
		}

		@Override
		public int hashCode() {
			return label.hashCode();
		}
	}

}
