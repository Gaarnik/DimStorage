package com.gaarnik.dimstorage.registry;

import net.minecraft.item.ItemStack;

import com.gaarnik.dimstorage.DimStorage;
import com.gaarnik.dimstorage.block.BlockDimChest;

import cpw.mods.fml.common.registry.GameRegistry;

public class RegistryDimChest {
	// ****************************************************************

	// ****************************************************************
    private static boolean init;

	// ****************************************************************
	public static void init() {
        if(init)
        	return;
        
        init = true;
        
        DimStorage.dimChest = new BlockDimChest().setBlockName("dimchest");
		GameRegistry.registerBlock(DimStorage.dimChest, "block_dimchest");
        
        GameRegistry.addRecipe(new ItemStack(DimStorage.dimChest, 1), new Object[]{
            "www",
            "wcw",
            "www",
            'w', DimStorage.dimWall,
            'c', DimStorage.solidDimCore});
    }

}
