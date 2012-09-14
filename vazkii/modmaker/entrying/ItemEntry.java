package vazkii.modmaker.entrying;

import java.util.TreeMap;
import java.util.logging.Level;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;
import net.minecraft.src.ItemFood;
import net.minecraft.src.ModLoader;
import vazkii.modmaker.mod_ModMaker;
import vazkii.modmaker.mod.CustomizableItem;
import vazkii.modmaker.mod.ItemCustom;
import vazkii.modmaker.mod.ItemCustomFood;
import vazkii.modmaker.tree.TreeLeaf;
import vazkii.modmaker.tree.objective.FoodStatsBranch;
import vazkii.modmaker.tree.objective.ItemBranch;
import vazkii.modmaker.tree.objective.PotionEffectBranch;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ItemEntry extends ModEntry<ItemEntry> {

	ItemBranch itemBranch;

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

			item = new ItemCustomFood(itemID - 256, foodValue, (float) saturationValue, wolfFood);
			if (potionID != 0) {
				int potionTime = (Integer) potionEffect.leaves().get("Potion Time").read();
				int potionLevel = (Integer) potionEffect.leaves().get("Potion Level").read();
				((ItemFood) item).setPotionEffect(potionID, potionTime, potionLevel, 1F);
				((ItemFood) item).setAlwaysEdible();
			}
		}
		else if (name.equals("normal")) item = new ItemCustom(itemID - 256);

		int iconIndex = 0;
		Object o = leaves.get("Sprite").read();

		try {
			iconIndex = o instanceof Integer ? (Integer) o : mod_ModMaker.claimedSprites.contains(o) ? ModLoader.addOverride("/gui/items.png", "/" + (String) o) : 0;
		} catch (Throwable e) {}
		item.setIconIndex(iconIndex);
		item.setMaxStackSize((Integer) leaves.get("Max Stack Size").read());
		if ((Boolean) leaves.get("Full 3D").read()) item.setFull3D();
		String itemName = (String) leaves.get("Item Name").read();
		item.setItemName(itemName);
		LanguageRegistry.addName(item, itemName);
		((CustomizableItem) item).setColorOverlay((Integer) leaves.get("Color Overlay").read());
		((CustomizableItem) item).setShine((Boolean) leaves.get("Shine").read());
		((CustomizableItem) item).setRarity((Integer) leaves.get("Rarity").read());
		int creativeTab = (Integer) leaves.get("Creative Tab").read();
		if (creativeTab > 0) item.setTabToDisplayOn(CreativeTabs.creativeTabArray[creativeTab - 1]);
	}

}
