package vazkii.modmaker.mod;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import vazkii.codebase.common.CommonUtils;
import vazkii.modmaker.entrying.AchievementEntry.AchievementTriggerer;

import net.minecraft.src.Achievement;
import net.minecraft.src.AchievementList;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;

import cpw.mods.fml.common.ICraftingHandler;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;

public class Achievements implements ICraftingHandler {

	public static Set<AchievementTriggerer> achievementTriggerers = new LinkedHashSet();

	public void onEvent(AchievementType type, ItemStack stack, EntityPlayer player) {
		for (AchievementTriggerer triggerer : achievementTriggerers)
			if (triggerer.type == type && CommonUtils.areStacksEqualIgnoreSize(triggerer.stack, stack)) player.triggerAchievement(triggerer.ach);
	}

	@ForgeSubscribe
	public void onPickup(EntityItemPickupEvent event) {
		onEvent(AchievementType.PICKUP, event.item.item, event.entityPlayer);
	}

	@Override
	public void onCrafting(EntityPlayer player, ItemStack item, IInventory craftMatrix) {
		onEvent(AchievementType.CRAFT, item, player);
	}

	@Override
	public void onSmelting(EntityPlayer player, ItemStack item) {
		onEvent(AchievementType.SMELT, item, player);
	}

	public static Achievement fromID(int id) {
		List<Achievement> achList = AchievementList.achievementList;
		for (Achievement ach : achList)
			if (ach.statId == id + 5242880) return ach;
				return null;
	}

	public enum AchievementType
	{
		PICKUP, CRAFT, SMELT;

		public static AchievementType fromString(String s) {
			if (s.equalsIgnoreCase("pickup")) return PICKUP;
			if (s.equalsIgnoreCase("craft")) return CRAFT;
			if (s.equalsIgnoreCase("smelt")) return SMELT;
			return null;
		}
	}

}
