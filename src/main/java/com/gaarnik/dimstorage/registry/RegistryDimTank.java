package com.gaarnik.dimstorage.registry;

import com.gaarnik.dimstorage.DimStorage;
import com.gaarnik.dimstorage.block.BlockDimTank;

import cpw.mods.fml.common.registry.GameRegistry;

public class RegistryDimTank {
	// ****************************************************************

	// ****************************************************************
	private static boolean init;

	// ****************************************************************
	public static void init() {
		if(init)
			return;

		init = true;

		DimStorage.dimTank = new BlockDimTank().setBlockName("dimtank");
		GameRegistry.registerBlock(DimStorage.dimTank, "block_dimtank");

		/*GameRegistry.addRecipe(new ItemStack(DimStorage.dimChest, 1), new Object[]{
			"www",
			"wcw",
			"www",
			'w', DimStorage.dimWall,
			'c', DimStorage.solidDimCore});*/
	}

}
