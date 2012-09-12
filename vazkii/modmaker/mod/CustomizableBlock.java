package vazkii.modmaker.mod;

import vazkii.modmaker.tree.objective.BlockDropBranch;

public interface CustomizableBlock {

	public void setColorOverlay(int i);

	public void setDropStats(BlockDropBranch branch);

	public void setOpaque(boolean b);
	
	public void setGravity(boolean b);

}
