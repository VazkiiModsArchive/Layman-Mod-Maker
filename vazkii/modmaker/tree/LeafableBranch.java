package vazkii.modmaker.tree;

import java.util.TreeMap;

public interface LeafableBranch {

	public TreeMap<String, TreeLeaf> leaves();

	public void addLeaf(TreeLeaf leaf);

	public void deleteLeaf(String name);
}
