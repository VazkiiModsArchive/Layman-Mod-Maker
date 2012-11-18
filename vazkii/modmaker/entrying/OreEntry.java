package vazkii.modmaker.entrying;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import vazkii.modmaker.addon.event.EntryInitEvent;
import vazkii.modmaker.addon.event.EntryReadEvent;
import vazkii.modmaker.addon.event.LMMEvent.EventPeriod;
import vazkii.modmaker.mod.OreGenerator;
import vazkii.modmaker.tree.BranchHelper;
import vazkii.modmaker.tree.objective.OreGenerateBranch;

import net.minecraft.src.ChunkProviderEnd;
import net.minecraft.src.ChunkProviderFlat;
import net.minecraft.src.ChunkProviderGenerate;
import net.minecraft.src.ChunkProviderHell;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.World;

import net.minecraftforge.common.MinecraftForge;

/**
 * <b>On read sends:<br>
 * <br>
 * </b> Before and After: nothing<br>
 */
public class OreEntry extends ModEntry<OreEntry> {

	GenerationHelper helper;
	OreGenerateBranch branch;

	public static Map<String, Class<? extends IChunkProvider>> chunkProviders = new HashMap();

	static {
		chunkProviders.put("surface", ChunkProviderGenerate.class);
		chunkProviders.put("nether", ChunkProviderHell.class);
		chunkProviders.put("ender", ChunkProviderEnd.class);
		chunkProviders.put("flat", ChunkProviderFlat.class);
	}

	@Override
	public OreEntry init(Object... params) {
		MinecraftForge.EVENT_BUS.post(new EntryInitEvent(EventPeriod.BEFORE, this));
		branch = (OreGenerateBranch) params[0];
		MinecraftForge.EVENT_BUS.post(new EntryInitEvent(EventPeriod.AFTER, this));
		return this;
	}

	@Override
	public void readEntry() {
		MinecraftForge.EVENT_BUS.post(new EntryReadEvent(EventPeriod.BEFORE, this));
		String label = branch.label();
		String world = (String) branch.leaves().get("World").read();
		int oreDensity = (Integer) branch.leaves().get("Ore Density").read();
		int maxVeinSize = (Integer) branch.leaves().get("Max Vein Size").read();
		int minHeight = (Integer) branch.leaves().get("Min Height").read();
		int maxHeight = (Integer) branch.leaves().get("Max Height").read();
		int blockToReplace = (Integer) branch.leaves().get("Block to Replace").read();
		int blockToSet = (Integer) branch.leaves().get("Block to Set").read();
		OreGenerator.generatingHelpers.add(new GenerationHelper(BranchHelper.getModFromBranch(branch).label() + "_" + label, world, oreDensity, maxVeinSize, minHeight, maxHeight, blockToReplace, blockToSet));
		MinecraftForge.EVENT_BUS.post(new EntryReadEvent(EventPeriod.AFTER, this));
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
			for (String s : chunkProviders.keySet())
				if (world.equals(s) && chunkProviders.get(s).isInstance(chunkGenerator)) return true;

					return false;
		}

		@Override
		public int hashCode() {
			return label.hashCode();
		}
	}

}
