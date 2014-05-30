package com.gaarnik.dimstorage.storage.chest;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.gaarnik.dimstorage.storage.AbstractDimStorage;
import com.gaarnik.dimstorage.storage.DimStorageManager;

public class DimChestStorage extends AbstractDimStorage implements IInventory {
	// ****************************************************************

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
        this.items = new ItemStack[getSizeInventory()];
    }

	// ****************************************************************
	@Override
	public ItemStack decrStackSize(int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ItemStack getStackInSlot(int slot) {
		synchronized(this) {
            return this.items[slot];
        }
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		// TODO Auto-generated method stub
		return null;
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
		// entityplayer.getCommandSenderName() it's better, 
		// getDisplayName() could be modify with a forge event
		return (this.owner.equals("public")) || (this.owner.equals(entityplayer.getCommandSenderName()));
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public NBTTagCompound saveToTag() {
		// TODO Auto-generated method stub
		return null;
	}

	// ****************************************************************

	// ****************************************************************
	@Override
	public String getType() { return "chest"; }
	
	public int getOpenCount() { return this.openCount; }

}
