package vazkii.modmaker.entrying;

import vazkii.modmaker.addon.Addonable;

public abstract class ModEntry<T> implements Addonable {

	public abstract T init(Object... params);

	public abstract void readEntry();
}
