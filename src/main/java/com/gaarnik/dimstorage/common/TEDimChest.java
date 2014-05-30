package com.gaarnik.dimstorage.common;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
		this.owner = "public";
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
		this.storage = (DimChestStorage) DimStorageManager.instance(worldObj.isRemote).getStorage(this.owner, this.freq, DimChestStorage.TYPE);
	}

	public void swapOwner() {
		if(!this.worldObj.isRemote)
			return;
		
		if(this.owner.equals("public"))
			this.owner = Minecraft.getMinecraft().thePlayer.getCommandSenderName();
		else
			this.owner = "public";
		
		this.reloadStorage();
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
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);

		this.owner = tag.getString("owner");
		this.freq = tag.getInteger("freq");
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		
		tag.setString("owner", this.owner);
		tag.setInteger("freq", this.freq);
	}

	// ****************************************************************

	// ****************************************************************
	public String getOwner() { return this.owner; }
	public void setOwner(String owner) { this.owner = owner; }

	public int getFreq() { return this.freq; }
	public void setFreq(int freq) { this.freq = freq; }

}
