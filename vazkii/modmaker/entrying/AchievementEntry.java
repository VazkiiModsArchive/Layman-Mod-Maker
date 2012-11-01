package vazkii.modmaker.entrying;

import java.util.TreeMap;

import vazkii.modmaker.mod.Achievements;
import vazkii.modmaker.mod.Achievements.AchievementType;
import vazkii.modmaker.tree.LeafStringStack;
import vazkii.modmaker.tree.TreeLeaf;
import vazkii.modmaker.tree.objective.AchievementBranch;

import net.minecraft.src.Achievement;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;

public class AchievementEntry extends ModEntry<AchievementEntry> {

	AchievementBranch branch;

	@Override
	public AchievementEntry init(Object... params) {
		branch = (AchievementBranch) params[0];
		return this;
	}

	@Override
	public void readEntry() {
		TreeMap<String, TreeLeaf> leaves = branch.leaves();
		int id = (Integer) leaves.get("Achievement ID").read();
		int parentID = (Integer) leaves.get("Parent Achievement ID").read();
		int gridX = (Integer) leaves.get("Grid X Position").read();
		int gridY = (Integer) leaves.get("Grid Y Position").read();
		ItemStack triggerStack = LeafStringStack.getStack((String) leaves.get("Trigger Item").read());
		ItemStack displayStack = LeafStringStack.getStack((String) leaves.get("Display Item").read());
		AchievementType type = AchievementType.fromString((String) leaves.get("Trigger Type").read());
		String name = (String) leaves.get("Name").read();
		String desc = (String) leaves.get("Description").read();
		boolean special = (Boolean) leaves.get("Special").read();

		Achievement parent = Achievements.fromID(parentID);
		Achievement ach = new Achievement(id, name, gridX, gridY, displayStack, parent);
		if (parent == null) ach.setIndependent();
		if (special) ach.setSpecial();
		ModLoader.addAchievementDesc(ach, name, desc);

		ach.registerAchievement();
		Achievements.achievementTriggerers.add(new AchievementTriggerer(ach, type, triggerStack));
	}

	public class AchievementTriggerer {

		public Achievement ach;
		public AchievementType type;
		public ItemStack stack;

		public AchievementTriggerer(Achievement ach, AchievementType type, ItemStack stack) {
			this.ach = ach;
			this.type = type;
			this.stack = stack;
		}

	}

}
