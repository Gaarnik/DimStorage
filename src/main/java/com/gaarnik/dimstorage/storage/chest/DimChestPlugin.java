package com.gaarnik.dimstorage.storage.chest;

import com.gaarnik.dimstorage.storage.AbstractDimStorage;
import com.gaarnik.dimstorage.storage.DimStorageManager;
import com.gaarnik.dimstorage.storage.IDimStoragePlugin;

public class DimChestPlugin implements IDimStoragePlugin {
	// ****************************************************************

	// ****************************************************************

	// ****************************************************************
	public DimChestPlugin() {}

	// ****************************************************************
	@Override
	public AbstractDimStorage createEnderStorage(DimStorageManager manager, String owner, int freq) {
		return new DimChestStorage(manager, owner, freq);
	}

	// ****************************************************************

	// ****************************************************************

	// ****************************************************************
	@Override
	public String identifer() { return "DimChest"; }

}
