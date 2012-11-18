package vazkii.modmaker.entrying;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;

import vazkii.codebase.common.TypeContainer;
import vazkii.modmaker.mod_ModMaker;
import vazkii.modmaker.addon.event.EntryInitEvent;
import vazkii.modmaker.addon.event.EntryReadEvent;
import vazkii.modmaker.addon.event.LMMEvent.EventPeriod;
import vazkii.modmaker.mod.CustomizableItem;
import vazkii.modmaker.mod.ItemCustom;
import vazkii.modmaker.mod.ItemCustomFood;
import vazkii.modmaker.tree.TreeLeaf;
import vazkii.modmaker.tree.objective.FoodStatsBranch;
import vazkii.modmaker.tree.objective.ItemBranch;
import vazkii.modmaker.tree.objective.PotionEffectBranch;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;
import net.minecraft.src.ItemFood;
import net.minecraft.src.ModLoader;
import net.minecraft.src.PotionHelper;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.LanguageRegistry;

import net.minecraftforge.common.MinecraftForge;

/**
 * <b>On read sends:<br>
 * <br>
 * </b> Before: nothing<br>
 * <br>
 * During and After:<br>
 * 0: (TypeContainer Item) The item being set, must be an instance of
 * CustomizableItem.
 */
public class ItemEntry extends ModEntry<ItemEntry> {

	public ItemBranch itemBranch;
	public static Map<String, String> potionMappings = new HashMap();

	static {
		potionMappings.put("wart", "+4");
		potionMappings.put("glowstone", PotionHelper.glowstoneEffect);
		potionMappings.put("redstone", PotionHelper.redstoneEffect);
		potionMappings.put("ferm_eye", PotionHelper.fermentedSpiderEyeEffect);
		potionMappings.put("magma", PotionHelper.magmaCreamEffect);
		potionMappings.put("sugar", PotionHelper.sugarEffect);
		potionMappings.put("melon", PotionHelper.speckledMelonEffect);
		potionMappings.put("eye", PotionHelper.spiderEyeEffect);
		potionMappings.put("tear", PotionHelper.ghastTearEffect);
		potionMappings.put("blaze", PotionHelper.blazePowderEffect);
		potionMappings.put("carrot", PotionHelper.field_82818_l);
		potionMappings.put("gunpowder", PotionHelper.gunpowderEffect);
	}

	@Override
	public ItemEntry init(Object... params) {
		MinecraftForge.EVENT_BUS.post(new EntryInitEvent(EventPeriod.BEFORE, this));
		itemBranch = (ItemBranch) params[0];
		MinecraftForge.EVENT_BUS.post(new EntryInitEvent(EventPeriod.AFTER, this));
		return this;
	}

	@Override
	public void readEntry() {
		MinecraftForge.EVENT_BUS.post(new EntryReadEvent(EventPeriod.BEFORE, this));
		TreeMap<String, TreeLeaf> leaves = itemBranch.leaves();
		String name = (String) leaves.get("Item Type").read();

		int itemID = (Integer) leaves.get("Item ID").read();
		if (Item.itemsList[itemID] != null) {
			FMLCommonHandler.instance().getFMLLogger().log(Level.WARNING, "Item ID " + itemID + " is already occupied, aborting registry of item " + itemBranch.label());
			return;
		}
		TypeContainer<Item> item = new TypeContainer(null);

		if (name.equals("food")) {
			FoodStatsBranch foodStats = (FoodStatsBranch) itemBranch.subBranches().get("foodStats");
			PotionEffectBranch potionEffect = (PotionEffectBranch) foodStats.subBranches().get("potionEffects");

			int foodValue = (Integer) foodStats.leaves().get("Food Value").read();
			double saturationValue = (Double) foodStats.leaves().get("Saturation Value").read();
			boolean wolfFood = (Boolean) foodStats.leaves().get("Wolf Food").read();
			int potionID = (Integer) potionEffect.leaves().get("Potion ID").read();
			double chance = (Double) potionEffect.leaves().get("Chance").read();

			item.setObj(new ItemCustomFood(itemID - 256, foodValue, (float) saturationValue, wolfFood));
			if (potionID != 0) {
				int potionTime = (Integer) potionEffect.leaves().get("Potion Time").read();
				int potionLevel = (Integer) potionEffect.leaves().get("Potion Level").read();
				((ItemFood) item.getObj()).setPotionEffect(potionID, potionTime, potionLevel, (float) chance);
				((ItemFood) item.getObj()).setAlwaysEdible();
			}
		} else if (name.equals("normal")) item.setObj(new ItemCustom(itemID - 256));

		MinecraftForge.EVENT_BUS.post(new EntryReadEvent(EventPeriod.DURING, this, item));

		int iconIndex = 0;
		Object o = leaves.get("Sprite").read();

		try {
			iconIndex = o instanceof Integer ? (Integer) o : mod_ModMaker.claimedSprites.contains(o) ? ModLoader.addOverride("/gui/items.png", "/" + (String) o) : 0;
		} catch (Throwable e) {
		}
		item.getObj().setIconIndex(iconIndex);
		item.getObj().setMaxStackSize((Integer) leaves.get("Max Stack Size").read());
		if ((Boolean) leaves.get("Full 3D").read()) item.getObj().setFull3D();
		String itemName = (String) leaves.get("Item Name").read();
		item.getObj().setItemName(itemName);
		LanguageRegistry.addName(item.getObj(), itemName);
		((CustomizableItem) item.getObj()).setColorOverlay((Integer) leaves.get("Color Overlay").read());
		((CustomizableItem) item.getObj()).setShine((Boolean) leaves.get("Shine").read());
		((CustomizableItem) item.getObj()).setRarity((Integer) leaves.get("Rarity").read());
		String potionEffect = (String) leaves.get("Brewing Effect").read();
		((CustomizableItem) item.getObj()).setPotionEffect0(potionMappings.containsKey(potionEffect) ? potionMappings.get(potionEffect) : "none");
		int creativeTab = (Integer) leaves.get("Creative Tab").read();
		if (creativeTab > 0) item.getObj().setCreativeTab(CreativeTabs.creativeTabArray[creativeTab - 1]);

		MinecraftForge.EVENT_BUS.post(new EntryReadEvent(EventPeriod.AFTER, this, item));
	}

}
