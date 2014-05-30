package com.gaarnik.dimstorage.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;

import com.gaarnik.dimstorage.storage.DimStorageManager;
import com.gaarnik.dimstorage.storage.chest.DimChestStorage;

public class TEDimChest extends TileEntity implements IInventory {
	// ****************************************************************

	// ****************************************************************
	private DimChestStorage storage;
	
	private String owner;
	private int freq;

	// ****************************************************************
	public TEDimChest() {
		this.freq = 1;
	}

	// ****************************************************************
	@Override
	public void validate() {
		super.validate();

		if(!(worldObj instanceof WorldServer) == worldObj.isRemote)
			this.reloadStorage();
	}

	public void reloadStorage() {
		this.storage = (DimChestStorage) DimStorageManager.instance(worldObj.isRemote).getStorage(this.owner, this.freq, "DimChest");
	}

	// ****************************************************************
	@Override
	public void openChest() {
		this.storage.openChest();
	}
	
	@Override
	public void closeChest() {
		this.storage.closeChest();
	}
	
	@Override
	public ItemStack decrStackSize(int i, int j) {
		return this.storage.decrStackSize(i, j);
	}

	@Override
	public String getInvName() {
		return this.storage.getInvName();
	}

	@Override
	public int getInventoryStackLimit() {
		return this.storage.getInventoryStackLimit();
	}

	@Override
	public int getSizeInventory() {
		return this.storage.getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return this.storage.getStackInSlot(slot);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		return this.storage.getStackInSlotOnClosing(slot);
	}

	@Override
	public boolean isInvNameLocalized() {
		return this.storage.isInvNameLocalized();
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return this.storage.isItemValidForSlot(i, itemstack);
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return this.storage.isUseableByPlayer(entityplayer);
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		this.storage.setInventorySlotContents(slot, stack);
	}

	// ****************************************************************

	public void setOwner(String str) {
		this.owner = str;
	}
	
	public void setFreq(int i) {
		this.freq = i;
	}
	
	// ****************************************************************

	public String getOwner() {
		return this.owner;
	}
	
	public int getFreq() {
		return this.freq;
	}
	// ****************************************************************

}
