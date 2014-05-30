package com.gaarnik.dimstorage.storage.chest;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.gaarnik.dimstorage.storage.AbstractDimStorage;
import com.gaarnik.dimstorage.storage.DimStorageManager;
import com.gaarnik.dimstorage.util.InventoryUtils;

public class DimChestStorage extends AbstractDimStorage implements IInventory {
	// ****************************************************************
	public static final String TYPE = "DimChest";

	// ****************************************************************
	private ItemStack[] items;
    private int openCount;

	// ****************************************************************
	public DimChestStorage(DimStorageManager manager, String owner, int freq, boolean locked) {
		super(manager, owner, freq, locked);
		
		this.empty();
	}

	// ****************************************************************
	public void empty() {
        this.items = new ItemStack[this.getSizeInventory()];
    }

	// ****************************************************************
	@Override
	public ItemStack decrStackSize(int slot, int size) {
		return InventoryUtils.decrStackSize(this, slot, size);
	}
	
	@Override
	public ItemStack getStackInSlot(int slot) {
		synchronized(this) {
            return this.items[slot];
        }
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		return InventoryUtils.getStackInSlotOnClosing(this, slot);
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		synchronized(this) {
            this.items[slot] = stack;
            this.onInventoryChanged();
        }
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) { 
		return (this.owner.equals("public")) || (this.owner.equals(entityplayer.getDisplayName()));
	}

	@Override
	public void openChest() {
		if(this.manager.isClient())
            return;

        synchronized(this) {
            this.openCount++;
            
            //if(this.open == 1)
                //EnderStorageSPH.sendOpenUpdateTo(null, owner, freq, true);
        }
	}
	
	@Override
	public void closeChest() {
		if(this.manager.isClient())
            return;

        synchronized(this) {
            this.openCount--;
            //if(this.open == 0)
                //EnderStorageSPH.sendOpenUpdateTo(null, owner, freq, false);
        }
	}

	@Override
	public String getInvName() { return null; }

	@Override
	public int getInventoryStackLimit() { return 64; }

	@Override
	public int getSizeInventory() { return 54; }

	@Override
	public void onInventoryChanged() {
		this.setDirty();
	}

	@Override
	public boolean isInvNameLocalized() { return true; }

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) { return true; }
	
	// ****************************************************************
	@Override
	public void loadFromTag(NBTTagCompound tag) {
		this.empty();
        
		InventoryUtils.readItemStacksFromTag(this.items, tag.getTagList("items"));
	}

	@Override
	public NBTTagCompound saveToTag() {
		NBTTagCompound compound = new NBTTagCompound();
        compound.setTag("items", InventoryUtils.writeItemStacksToTag(this.items));

        return compound;
	}

	// ****************************************************************

	// ****************************************************************
	@Override
	public String getType() { return TYPE; }
	
	public int getOpenCount() { return this.openCount; }

}
