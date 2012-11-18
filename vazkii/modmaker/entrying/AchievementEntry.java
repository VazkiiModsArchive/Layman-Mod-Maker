package vazkii.modmaker.entrying;

import java.util.TreeMap;

import vazkii.codebase.common.TypeContainer;
import vazkii.modmaker.addon.event.EntryInitEvent;
import vazkii.modmaker.addon.event.EntryReadEvent;
import vazkii.modmaker.addon.event.LMMEvent.EventPeriod;
import vazkii.modmaker.mod.Achievements;
import vazkii.modmaker.mod.Achievements.AchievementType;
import vazkii.modmaker.tree.LeafStringStack;
import vazkii.modmaker.tree.TreeLeaf;
import vazkii.modmaker.tree.objective.AchievementBranch;

import net.minecraft.src.Achievement;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;

import net.minecraftforge.common.MinecraftForge;

/**
 * <b>On read sends:<br>
 * <br>
 * </b> Before: nothing<br>
 * <br>
 * During and After:<br>
 * 0: (TypeContainer Achievement) The achievement being set. <br>
 * 1: (TypeContainer Achievement) The parent achievement.<br>
 */
public class AchievementEntry extends ModEntry<AchievementEntry> {
	AchievementBranch branch;

	@Override
	public AchievementEntry init(Object... params) {
		MinecraftForge.EVENT_BUS.post(new EntryInitEvent(EventPeriod.BEFORE, this));
		branch = (AchievementBranch) params[0];
		MinecraftForge.EVENT_BUS.post(new EntryInitEvent(EventPeriod.AFTER, this));
		return this;
	}

	@Override
	public void readEntry() {
		MinecraftForge.EVENT_BUS.post(new EntryReadEvent(EventPeriod.BEFORE, this));
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

		TypeContainer<Achievement> parent = new TypeContainer(Achievements.fromID(parentID));
		TypeContainer<Achievement> ach = new TypeContainer(new Achievement(id, name, gridX, gridY, displayStack, parent.getObj()));

		MinecraftForge.EVENT_BUS.post(new EntryReadEvent(EventPeriod.DURING, this, ach, parent));

		if (parent.getObj() == null) ach.getObj().setIndependent();
		if (special) ach.getObj().setSpecial();
		ModLoader.addAchievementDesc(ach.getObj(), name, desc);

		ach.getObj().registerAchievement();
		Achievements.achievementTriggerers.add(new AchievementTriggerer(ach.getObj(), type, triggerStack));
		MinecraftForge.EVENT_BUS.post(new EntryReadEvent(EventPeriod.AFTER, this, ach, parent));
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
