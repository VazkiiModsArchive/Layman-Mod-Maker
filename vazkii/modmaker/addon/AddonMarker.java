package vazkii.modmaker.addon;

/**
 * Interface to mark that something is provided by an addon.
 */
public interface AddonMarker {

	/**
	 * Gets the addon that provides this feature.
	 */
	public LMMAddon getProvidingAddon(Addonable obj);

}
