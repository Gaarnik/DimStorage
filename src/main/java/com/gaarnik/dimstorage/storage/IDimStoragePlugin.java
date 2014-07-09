package com.gaarnik.dimstorage.storage;

public interface IDimStoragePlugin {
	
	public AbstractDimStorage createStorage(DimStorageManager manager, String owner, int freq);

    public String identifer();

}
