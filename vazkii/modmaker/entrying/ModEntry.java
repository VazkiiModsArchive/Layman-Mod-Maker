package vazkii.modmaker.entrying;

public abstract class ModEntry<T> {

	public abstract T init(Object... params);

	public abstract void readEntry();
}
