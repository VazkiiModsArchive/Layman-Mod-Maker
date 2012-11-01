package vazkii.modmaker.entrying;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.logging.Level;

import vazkii.modmaker.mod_ModMaker;
import vazkii.modmaker.mod.BlockCustom;
import vazkii.modmaker.tree.TreeLeaf;
import vazkii.modmaker.tree.objective.BlockBranch;
import vazkii.modmaker.tree.objective.BlockDropBranch;

import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;
import net.minecraft.src.StepSound;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class BlockEntry extends ModEntry<BlockEntry> {

	BlockBranch blockBranch;
	static HashMap<String, Material> materialMappings = new HashMap();
	static HashMap<String, StepSound> stepMappings = new HashMap();
	static boolean initted = false;

	@Override
	public BlockEntry init(Object... params) {
		blockBranch = (BlockBranch) params[0];
		return this;
	}

	@Override
	public void readEntry() {
		init();
		TreeMap<String, TreeLeaf> leaves = blockBranch.leaves();
		Object o = leaves.get("Sprite").read();
		int blockID = (Integer) leaves.get("Block ID").read();
		if (Block.blocksList[blockID] != null || Item.itemsList[blockID] != null) {
			FMLCommonHandler.instance().getFMLLogger().log(Level.WARNING, "Block ID " + blockID + " is already occupied, aborting registry of block " + blockBranch.label());
			return;
		}

		int sprite = 0;
		try {
			sprite = o instanceof Integer ? (Integer) o : mod_ModMaker.claimedSprites.contains(o) ? ModLoader.addOverride("/terrain.png", "/" + (String) o) : 0;
		} catch (Throwable e) {
		}

		Material mat = materialMappings.get(leaves.get("Material").read());
		Block block = null;
		block = new BlockCustom(blockID, sprite, mat);
		String stepSound = (String) leaves.get("Step Sound").read();
		if (!stepSound.equalsIgnoreCase("none")) block.setStepSound(stepMappings.get(stepSound));
		block.setLightValue((Integer) leaves.get("Light Value").read() / 15F);
		block.setResistance((float) ((Double) leaves.get("Explosion Resistance").read()).doubleValue());
		block.setHardness((float) ((Double) leaves.get("Hardness").read()).doubleValue());
		if ((Boolean) leaves.get("Unbreakable").read()) block.setHardness(-1.0F); // mimic
		// to
		// setUnbreakable()
		// protected
		// method
		// >_>
		block.slipperiness = (float) ((Double) leaves.get("Slipperiness").read()).doubleValue();
		((BlockCustom) block).setColorOverlay((Integer) leaves.get("Color Overlay").read());
		((BlockCustom) block).setDropStats((BlockDropBranch) blockBranch.subBranches().get("blockDrop"));
		((BlockCustom) block).setOpaque((Boolean) blockBranch.leaves().get("Opaque").read());
		int creativeTab = (Integer) leaves.get("Creative Tab").read();
		if (creativeTab > 0) block.setCreativeTab(CreativeTabs.creativeTabArray[creativeTab - 1]);
		String blockName = (String) leaves.get("Block Name").read();
		((BlockCustom) block).setGravity((Boolean) blockBranch.leaves().get("Gravity").read());
		block.setBlockName(blockName);
		LanguageRegistry.addName(block, blockName);
		GameRegistry.registerBlock(block);
	}

	static void init() {
		if (initted) return;

		initted = true;

		materialMappings.put("ground", Material.ground);
		materialMappings.put("wood", Material.wood);
		materialMappings.put("rock", Material.rock);
		materialMappings.put("plant", Material.plants);
		materialMappings.put("glass", Material.glass);
		materialMappings.put("metal", Material.iron);

		stepMappings.put("powder", Block.soundPowderFootstep);
		stepMappings.put("wood", Block.soundWoodFootstep);
		stepMappings.put("gravel", Block.soundGravelFootstep);
		stepMappings.put("grass", Block.soundGrassFootstep);
		stepMappings.put("stone", Block.soundStoneFootstep);
		stepMappings.put("metal", Block.soundMetalFootstep);
		stepMappings.put("glass", Block.soundGlassFootstep);
		stepMappings.put("cloth", Block.soundClothFootstep);
		stepMappings.put("sand", Block.soundSandFootstep);
	}

}
