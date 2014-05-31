package com.gaarnik.dimstorage.common;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;

import com.gaarnik.dimstorage.storage.DimStorageManager;
import com.gaarnik.dimstorage.storage.chest.DimChestStorage;

public class TEDimChest extends TileEntity implements IInventory {
	// ****************************************************************
	private static final float MIN_MOVABLE_POSITION = 0f;
	private static final float MAX_MOVABLE_POSITION = 0.5f;
	
	private static final float OPENING_SPEED = 0.05f;

	// ****************************************************************
	private DimChestStorage storage;

	private String owner;
	private int freq;
	private byte direction = 0;
	
	private float movablePartState;
	private boolean opening;

	// ****************************************************************
	public TEDimChest() {
		this.owner = "public";
		this.freq = 1;
		this.direction = 0;
		
		this.movablePartState = MIN_MOVABLE_POSITION;
		this.opening = true;
	}

	// ****************************************************************
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(this.opening) {
			this.movablePartState += OPENING_SPEED;
			if(this.movablePartState >= MAX_MOVABLE_POSITION) {
				this.movablePartState = MAX_MOVABLE_POSITION;
				this.opening = false;
			}
		}
		else {
			this.movablePartState -= OPENING_SPEED;
			if(this.movablePartState <= MIN_MOVABLE_POSITION) {
				this.movablePartState = MIN_MOVABLE_POSITION;
				this.opening = true;
			}
		}
	}
	
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

	public void downFreq() {
		if(!this.worldObj.isRemote)
			return;

		if(this.freq > 1) {
			this.freq--;
			this.reloadStorage();
		}
	}

	public void upFreq() {
		if(!this.worldObj.isRemote)
			return;

		this.freq++;
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
	public Packet getDescriptionPacket() {
		NBTTagCompound nbtTag = new NBTTagCompound();
		this.writeToNBT(nbtTag);
		
		return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, nbtTag);
	}

	public void onDataPacket(INetworkManager net, Packet132TileEntityData packet) {
		this.readFromNBT(packet.data);
	}

	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);

		this.owner = tag.getString("owner");
		this.freq = tag.getInteger("freq");
		this.direction = tag.getByte("direction");

	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);

		tag.setString("owner", this.owner);
		tag.setInteger("freq", this.freq);
		tag.setByte("direction", this.direction);

	}

	// ****************************************************************

	// ****************************************************************
	public String getOwner() { return this.owner; }
	public void setOwner(String owner) { this.owner = owner; }

	public int getFreq() { return this.freq; }
	public void setFreq(int freq) { this.freq = freq; }

	public byte getDirection() { return this.direction; }
	public void setDirection(byte direction) { this.direction = direction; }
	
	public float getMovablePartState() { return this.movablePartState; }
	
}
