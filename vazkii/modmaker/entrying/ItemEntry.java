package vazkii.modmaker.entrying;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.logging.Level;

import vazkii.modmaker.mod_ModMaker;
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

public class ItemEntry extends ModEntry<ItemEntry> {

	ItemBranch itemBranch;
	static HashMap<String, String> potionMappings = new HashMap();

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
		itemBranch = (ItemBranch) params[0];
		return this;
	}

	@Override
	public void readEntry() {
		TreeMap<String, TreeLeaf> leaves = itemBranch.leaves();
		String name = (String) leaves.get("Item Type").read();

		int itemID = (Integer) leaves.get("Item ID").read();
		if (Item.itemsList[itemID] != null) {
			FMLCommonHandler.instance().getFMLLogger().log(Level.WARNING, "Item ID " + itemID + " is already occupied, aborting registry of item " + itemBranch.label());
			return;
		}
		Item item = null;

		if (name.equals("food")) {
			FoodStatsBranch foodStats = (FoodStatsBranch) itemBranch.subBranches().get("foodStats");
			PotionEffectBranch potionEffect = (PotionEffectBranch) foodStats.subBranches().get("potionEffects");

			int foodValue = (Integer) foodStats.leaves().get("Food Value").read();
			double saturationValue = (Double) foodStats.leaves().get("Saturation Value").read();
			boolean wolfFood = (Boolean) foodStats.leaves().get("Wolf Food").read();
			int potionID = (Integer) potionEffect.leaves().get("Potion ID").read();
			double chance = (Double) potionEffect.leaves().get("Chance").read();

			item = new ItemCustomFood(itemID - 256, foodValue, (float) saturationValue, wolfFood);
			if (potionID != 0) {
				int potionTime = (Integer) potionEffect.leaves().get("Potion Time").read();
				int potionLevel = (Integer) potionEffect.leaves().get("Potion Level").read();
				((ItemFood) item).setPotionEffect(potionID, potionTime, potionLevel, (float) chance);
				((ItemFood) item).setAlwaysEdible();
			}
		} else if (name.equals("normal")) item = new ItemCustom(itemID - 256);

		int iconIndex = 0;
		Object o = leaves.get("Sprite").read();

		try {
			iconIndex = o instanceof Integer ? (Integer) o : mod_ModMaker.claimedSprites.contains(o) ? ModLoader.addOverride("/gui/items.png", "/" + (String) o) : 0;
		} catch (Throwable e) {
		}
		item.setIconIndex(iconIndex);
		item.setMaxStackSize((Integer) leaves.get("Max Stack Size").read());
		if ((Boolean) leaves.get("Full 3D").read()) item.setFull3D();
		String itemName = (String) leaves.get("Item Name").read();
		item.setItemName(itemName);
		LanguageRegistry.addName(item, itemName);
		((CustomizableItem) item).setColorOverlay((Integer) leaves.get("Color Overlay").read());
		((CustomizableItem) item).setShine((Boolean) leaves.get("Shine").read());
		((CustomizableItem) item).setRarity((Integer) leaves.get("Rarity").read());
		String potionEffect = (String) leaves.get("Brewing Effect").read();
		((CustomizableItem) item).setPotionEffect0(potionMappings.containsKey(potionEffect) ? potionMappings.get(potionEffect) : "none");
		int creativeTab = (Integer) leaves.get("Creative Tab").read();
		if (creativeTab > 0) item.setCreativeTab(CreativeTabs.creativeTabArray[creativeTab - 1]);
	}

}
