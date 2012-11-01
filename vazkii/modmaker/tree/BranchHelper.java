package vazkii.modmaker.tree;

import java.util.HashMap;

import vazkii.modmaker.tree.objective.AchievementBranch;
import vazkii.modmaker.tree.objective.AchievementsBranch;
import vazkii.modmaker.tree.objective.BlockBranch;
import vazkii.modmaker.tree.objective.BlockDropBranch;
import vazkii.modmaker.tree.objective.BlocksBranch;
import vazkii.modmaker.tree.objective.CraftingRecipeBranch;
import vazkii.modmaker.tree.objective.CraftingRecipesBranch;
import vazkii.modmaker.tree.objective.FoodStatsBranch;
import vazkii.modmaker.tree.objective.FuelBranch;
import vazkii.modmaker.tree.objective.FuelsBranch;
import vazkii.modmaker.tree.objective.ImageBranch;
import vazkii.modmaker.tree.objective.ImagesBranch;
import vazkii.modmaker.tree.objective.ItemBranch;
import vazkii.modmaker.tree.objective.ItemsBranch;
import vazkii.modmaker.tree.objective.OreGenerateBranch;
import vazkii.modmaker.tree.objective.OreGenerationBranch;
import vazkii.modmaker.tree.objective.PotionEffectBranch;
import vazkii.modmaker.tree.objective.RecipesBranch;
import vazkii.modmaker.tree.objective.SmeltingRecipeBranch;
import vazkii.modmaker.tree.objective.SmeltingRecipesBranch;
import vazkii.modmaker.tree.objective.UserMod;

import net.minecraft.src.NBTTagCompound;

public class BranchHelper {

	public static HashMap<String, Class<? extends TreeBranch>> branchMappings = new HashMap();
	public static HashMap<Class<? extends TreeBranch>, String> nameMappings = new HashMap();

	public static void registerMapping(String name, Class<? extends TreeBranch> clazz) {
		branchMappings.put(name, clazz);
		nameMappings.put(clazz, name);
	}

	public static String getNameFromClass(Class<? extends TreeBranch> clazz) {
		Class c = clazz;
		while (true) {
			if (c == TreeBranch.class) throw new NullPointerException("Class '" + clazz + "' is missing a name mapping!");

			if (nameMappings.containsKey(c)) return nameMappings.get(c);

			c = c.getSuperclass();
		}
	}

	public static TreeBranch branchFromNBT(NBTTagCompound cmp) {
		String type = cmp.getString("type");
		if (!branchMappings.containsKey(type)) throw new NullPointerException("Type '" + type + "' is missing a class mapping!");

		try {
			return branchMappings.get(type).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static UserMod getModFromBranch(TreeBranch branch) {
		TreeBranch b = branch;
		while (true) {
			if (b instanceof UserMod) return (UserMod) b;
			b = b.superBranch();
		}
	}

	static {
		registerMapping("core", UserMod.class);
		registerMapping("items", ItemsBranch.class);
		registerMapping("item", ItemBranch.class);
		registerMapping("foodStats", FoodStatsBranch.class);
		registerMapping("potionEffects", PotionEffectBranch.class);
		registerMapping("images", ImagesBranch.class);
		registerMapping("image", ImageBranch.class);
		registerMapping("blocks", BlocksBranch.class);
		registerMapping("block", BlockBranch.class);
		registerMapping("blockDrop", BlockDropBranch.class);
		registerMapping("oreGeneration", OreGenerationBranch.class);
		registerMapping("oreGenerate", OreGenerateBranch.class);
		registerMapping("recipes", RecipesBranch.class);
		registerMapping("craftingRecipes", CraftingRecipesBranch.class);
		registerMapping("craftingRecipe", CraftingRecipeBranch.class);
		registerMapping("smeltingRecipes", SmeltingRecipesBranch.class);
		registerMapping("smeltingRecipe", SmeltingRecipeBranch.class);
		registerMapping("fuels", FuelsBranch.class);
		registerMapping("fuel", FuelBranch.class);
		registerMapping("achievements", AchievementsBranch.class);
		registerMapping("achievement", AchievementBranch.class);
	}
}
