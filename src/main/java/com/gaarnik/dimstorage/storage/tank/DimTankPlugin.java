package com.gaarnik.dimstorage.storage.tank;

import com.gaarnik.dimstorage.storage.AbstractDimStorage;
import com.gaarnik.dimstorage.storage.DimStorageManager;
import com.gaarnik.dimstorage.storage.IDimStoragePlugin;

public class DimTankPlugin implements IDimStoragePlugin {
	// ****************************************************************

	// ****************************************************************

	// ****************************************************************
	public DimTankPlugin() {}

	// ****************************************************************
	@Override
	public AbstractDimStorage createStorage(DimStorageManager manager, String owner, int freq) {
		return null;
	}

	// ****************************************************************

	// ****************************************************************

	// ****************************************************************
	@Override
	public String identifer() { return "DimTank"; }

}
