package vazkii.modmaker.entrying;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.logging.Level;

import vazkii.codebase.common.TypeContainer;
import vazkii.modmaker.mod_ModMaker;
import vazkii.modmaker.addon.event.EntryInitEvent;
import vazkii.modmaker.addon.event.EntryReadEvent;
import vazkii.modmaker.addon.event.LMMEvent.EventPeriod;
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

import net.minecraftforge.common.MinecraftForge;

/**
 * <b>On read sends:<br>
 * <br>
 * </b> Before: nothing<br>
 * <br>
 * During and After:<br>
 * 0: (TypeContainer Block) The block being set, must be an instance of
 * CustomizableBlock.
 */
public class BlockEntry extends ModEntry<BlockEntry> {

	public BlockBranch blockBranch;
	static HashMap<String, Material> materialMappings = new HashMap();
	static HashMap<String, StepSound> stepMappings = new HashMap();
	static boolean initted = false;

	@Override
	public BlockEntry init(Object... params) {
		MinecraftForge.EVENT_BUS.post(new EntryInitEvent(EventPeriod.BEFORE, this));
		blockBranch = (BlockBranch) params[0];
		MinecraftForge.EVENT_BUS.post(new EntryInitEvent(EventPeriod.AFTER, this));
		return this;
	}

	@Override
	public void readEntry() {
		init();
		MinecraftForge.EVENT_BUS.post(new EntryReadEvent(EventPeriod.BEFORE, this));
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
		TypeContainer<Block> block = new TypeContainer(null);
		String name = (String) leaves.get("Block Type").read();
		if (name.equals("regular")) block.setObj(new BlockCustom(blockID, sprite, mat));
		MinecraftForge.EVENT_BUS.post(new EntryReadEvent(EventPeriod.DURING, this, block));
		String stepSound = (String) leaves.get("Step Sound").read();
		if (!stepSound.equalsIgnoreCase("none")) block.getObj().setStepSound(stepMappings.get(stepSound));
		block.getObj().setLightValue((Integer) leaves.get("Light Value").read() / 15F);
		block.getObj().setResistance((float) ((Double) leaves.get("Explosion Resistance").read()).doubleValue());
		block.getObj().setHardness((float) ((Double) leaves.get("Hardness").read()).doubleValue());
		if ((Boolean) leaves.get("Unbreakable").read()) block.getObj().setHardness(-1.0F);
		block.getObj().slipperiness = (float) ((Double) leaves.get("Slipperiness").read()).doubleValue();
		((BlockCustom) block.getObj()).setColorOverlay((Integer) leaves.get("Color Overlay").read());
		((BlockCustom) block.getObj()).setDropStats((BlockDropBranch) blockBranch.subBranches().get("blockDrop"));
		((BlockCustom) block.getObj()).setOpaque((Boolean) blockBranch.leaves().get("Opaque").read());
		int creativeTab = (Integer) leaves.get("Creative Tab").read();
		if (creativeTab > 0) block.getObj().setCreativeTab(CreativeTabs.creativeTabArray[creativeTab - 1]);
		String blockName = (String) leaves.get("Block Name").read();
		((BlockCustom) block.getObj()).setGravity((Boolean) blockBranch.leaves().get("Gravity").read());
		block.getObj().setBlockName(blockName);
		LanguageRegistry.addName(block.getObj(), blockName);
		GameRegistry.registerBlock(block.getObj());
		MinecraftForge.EVENT_BUS.post(new EntryReadEvent(EventPeriod.AFTER, this, block));
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
