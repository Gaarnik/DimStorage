package com.gaarnik.dimstorage.storage;

import net.minecraft.nbt.NBTTagCompound;


public abstract class AbstractDimStorage {
	// ****************************************************************

	// ****************************************************************
	protected final DimStorageManager manager;
	
	protected String owner;
	protected int freq;
	protected boolean locked;
	
	private boolean dirty;
	private int changeCount;

	// ****************************************************************
	public AbstractDimStorage(final DimStorageManager manager, String owner, int freq, boolean locked) {
		this.manager = manager;
	
		this.owner = owner;
		this.freq = freq;
		this.locked = locked;
		
		this.dirty = false;
		this.changeCount = 0;
	}
	
	// ****************************************************************
	public void setDirty() {
        if(this.manager.isClient())
            return;
        
        if(!this.dirty) {
            this.dirty = true;
            this.manager.requestSave(this);
        }
        
        this.changeCount++;
    }
    
    public void setClean() {
        this.dirty = false;
    }
    
	// ****************************************************************
    public abstract String getType();
    
    public abstract void loadFromTag(NBTTagCompound tag);

    public abstract NBTTagCompound saveToTag();

	// ****************************************************************

	// ****************************************************************
    public int getChangeCount() { return this.changeCount; }

}
