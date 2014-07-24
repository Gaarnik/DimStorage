package com.gaarnik.dimstorage.storage.chest;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;

import com.gaarnik.dimstorage.storage.AbstractDimStorage;
import com.gaarnik.dimstorage.storage.DimStorageManager;
import com.gaarnik.dimstorage.util.InventoryUtils;

public class DimChestStorage extends AbstractDimStorage implements IInventory, ISidedInventory {
	// ****************************************************************
	public static final String TYPE = "DimChest";

	// ****************************************************************
	private ItemStack[] items;

	// ****************************************************************
	public DimChestStorage(DimStorageManager manager, String owner, int freq) {
		super(manager, owner, freq);
		
		this.empty();
	}

	// ****************************************************************
	public void empty() {
		synchronized(this) {
			this.items = new ItemStack[this.getSizeInventory()];
		}
    }

	// ****************************************************************
	@Override
	public ItemStack decrStackSize(int slot, int size) {
		synchronized(this) {
			return InventoryUtils.decrStackSize(this, slot, size);
		}
	}
	
	@Override
	public ItemStack getStackInSlot(int slot) {
		synchronized(this) {
            return this.items[slot];
        }
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		synchronized(this) {
			return InventoryUtils.getStackInSlotOnClosing(this, slot);
		}
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		synchronized(this) {
            this.items[slot] = stack;
            this.markDirty();
        }
	}

	@Override
	public void openInventory() {}
	
	@Override
	public void closeInventory() {}

	@Override
	public String getInventoryName() { return null; }

	@Override
	public int getInventoryStackLimit() { return 64; }

	@Override
	public int getSizeInventory() { return 54; }
	
	@Override
	public void markDirty() {
		this.setDirty();
	}

	@Override
	public boolean hasCustomInventoryName() { return true; }

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) { return true; }

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		if(this.owner.equals("public") || this.owner.equals(entityplayer.getCommandSenderName()))
			return true;
		
		return !this.locked;
	}

	// ****************************************************************
	@Override
	public boolean canExtractItem(int var1, ItemStack var2, int var3) {
		return !this.locked;
	}
	
	@Override
	public boolean canInsertItem(int var1, ItemStack var2, int var3) {
		return !this.locked;
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		int[] slots = new int[54];
		
		for(int i=0;i<54;i++)
			slots[i] = i;
		
		return slots;
	}

	// ****************************************************************
	@Override
	public void loadFromTag(NBTTagCompound tag) {
		super.loadFromTag(tag);
		
		this.empty();
		InventoryUtils.readItemStacksFromTag(this.items, tag.getTagList("items", Constants.NBT.TAG_COMPOUND));
	}

	@Override
	public NBTTagCompound saveToTag() {
		NBTTagCompound compound = super.saveToTag();
        compound.setTag("items", InventoryUtils.writeItemStacksToTag(this.items));

        return compound;
	}

	// ****************************************************************

	// ****************************************************************
	@Override
	public String getType() { return TYPE; }

}
