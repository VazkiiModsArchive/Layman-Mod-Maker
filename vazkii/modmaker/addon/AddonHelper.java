package vazkii.modmaker.addon;

import java.util.LinkedList;
import java.util.List;

import vazkii.modmaker.tree.objective.BlockBranch;
import vazkii.modmaker.tree.objective.ItemBranch;

import net.minecraft.src.Block;
import net.minecraft.src.Item;

public class AddonHelper {

	private AddonHelper() {
	}

	private static List<LMMAddon> addons = new LinkedList();

	public static boolean registerAddon(LMMAddon addon) {
		return addons.add(addon);
	}

	public static List<LMMAddon> getAddons() {
		return addons;
	}

	public static int getAddonQtd() {
		return addons.size();
	}

	public static void nullifyItem(int id) {
		Item.itemsList[id] = null;
	}

	public static void nullifyBlock(int id) {
		Block.blocksList[id] = null;
		nullifyItem(id);
	}

	public static void nullifyItem(ItemBranch branch) {
		int id = (Integer) branch.leaves().get("Item ID").read();
		nullifyItem(id);
	}

	public static void nullifyBlock(BlockBranch branch) {
		int id = (Integer) branch.leaves().get("Block ID").read();
		nullifyBlock(id);
	}
}
