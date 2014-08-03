package com.gaarnik.dimstorage.storage;

import net.minecraft.nbt.NBTTagCompound;

import com.gaarnik.dimstorage.tilentity.TEDimChest;


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
	public AbstractDimStorage(final DimStorageManager manager, String owner, int freq) {
		this.manager = manager;
	
		this.owner = owner;
		this.freq = freq;
		this.locked = false;
		
		this.dirty = false;
		this.changeCount = 0;
	}
	
	// ****************************************************************
	public void swapLocked(TEDimChest te) {
		this.locked = !this.locked;
	}
	
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
    public void loadFromTag(NBTTagCompound tag) {
    	this.locked = tag.getBoolean("locked");
    }

    public NBTTagCompound saveToTag() {
    	NBTTagCompound tag = new NBTTagCompound();

    	tag.setBoolean("locked", this.locked);
    	
        return tag;
    }
    
    public abstract String getType();

	// ****************************************************************

	// ****************************************************************
    public String getOwner() { return this.owner; }
    public int getFreq() { return this.freq; }
    
    public int getChangeCount() { return this.changeCount; }
    
    public boolean isClient() { return this.manager.isClient(); }

	public boolean isLocked() { return this.locked; }
	public void setLocked(boolean locked) {
		this.locked = locked;
		this.setDirty();
	}

}
