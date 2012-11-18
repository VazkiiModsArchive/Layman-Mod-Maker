package vazkii.modmaker.addon;

/**
 * Interface to flag that a class is an addon.
 */
public interface LMMAddon {

	/**
	 * Gets the name of the addon.
	 */
	public String getAddonName();

	/**
	 * Gets the name of the author.
	 */
	public String getAuthor();

	/**
	 * Gets the description of the addon in an array of strings. If you don't
	 * want a description return an empty array, never null.
	 */
	public String[] getAddonDesc();
}
