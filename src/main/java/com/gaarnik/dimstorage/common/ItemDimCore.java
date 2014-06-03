package com.gaarnik.dimstorage.common;

import com.gaarnik.dimstorage.DimStorage;

import net.minecraft.item.Item;

public class ItemDimCore extends Item {
	// ****************************************************************

	// ****************************************************************

	// ****************************************************************
	public ItemDimCore(int id) {
		super(id);
		
		this.setCreativeTab(DimStorage.tabDimStorage);
		this.setTextureName(DimStorage.MODID + ":dim_core");
	}

	// ****************************************************************

	// ****************************************************************

	// ****************************************************************

	// ****************************************************************

}
