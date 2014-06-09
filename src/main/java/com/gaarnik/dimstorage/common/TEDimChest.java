package com.gaarnik.dimstorage.common;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;

import com.gaarnik.dimstorage.DimStorageNetwork;
import com.gaarnik.dimstorage.storage.DimStorageManager;
import com.gaarnik.dimstorage.storage.chest.DimChestStorage;

import dan200.computer.api.IComputerAccess;
import dan200.computer.api.ILuaContext;
import dan200.computer.api.IPeripheral;
import cpw.mods.fml.common.Optional.Interface;

@Interface(iface="dan200.computer.api.IPeripheral", modid="ComputerCraft")
public class TEDimChest extends TileEntity implements IInventory, ISidedInventory, IPeripheral {
	// ****************************************************************
	private static final float MIN_MOVABLE_POSITION = 0f;
	private static final float MAX_MOVABLE_POSITION = 0.5f;

	private static final float OPENING_SPEED = 0.05f;

	// ****************************************************************
	private DimChestStorage storage;

	private String owner;
	private int freq;
	private boolean locked;

	private byte direction;

    private int openCount;
	private float movablePartState;
	private boolean opening;

	// ****************************************************************
	public TEDimChest() {
		this.owner = "public";
		this.freq = 1;
		this.locked = false;

		this.direction = 0;

		this.movablePartState = MIN_MOVABLE_POSITION;
		this.opening = false;
	}

	// ****************************************************************
	@Override
	public void updateEntity() {
		super.updateEntity();

		if(this.opening) {
			if(this.movablePartState < MAX_MOVABLE_POSITION)
				this.movablePartState += OPENING_SPEED;
			
			if(this.movablePartState > MAX_MOVABLE_POSITION)
				this.movablePartState = MAX_MOVABLE_POSITION;
		}
		else {
			if(this.movablePartState > MIN_MOVABLE_POSITION)
				this.movablePartState -= OPENING_SPEED;
			
			if(this.movablePartState < MIN_MOVABLE_POSITION)
				this.movablePartState = MIN_MOVABLE_POSITION;
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

	public void swapLocked() {
		this.locked = !this.locked;
		this.reloadStorage();
	}

	// ****************************************************************
	@Override
	public void openChest() {
		if(this.storage.isClient())
            return;

        synchronized(this) {
            this.openCount++;
            
            if(this.openCount == 1)
                DimStorageNetwork.sendOpenStorageToPlayers(this, true);
        }
	}

	@Override
	public void closeChest() {
		if(this.storage.isClient())
            return;

        synchronized(this) {
            this.openCount--;
            
            if(this.openCount == 0)
                DimStorageNetwork.sendOpenStorageToPlayers(this, false);
        }
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
		if(this.owner.equals("public") || this.owner.equals(entityplayer.getCommandSenderName()))
			return true;
		
		return !this.locked;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		this.storage.setInventorySlotContents(slot, stack);
	}

	// ****************************************************************
	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return !this.locked;
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
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
	public String[] getMethodNames() {
		return new String[] {
				"getOwner", "getFreq", "isLocked",
				"setOwner", "setPublic", "setFreq"
		};
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws Exception {
		switch(method) {

		case 0: // getOwner
			return new Object[] {this.getOwner()};

		case 1: // getFreq
			return new Object[] {this.getFreq()};

		case 2: // isLocked
			return new Object[] {this.isLocked()};

		case 3: // setOwner
			if(this.isLocked())
				throw new Exception("DimChest is locked !");

			this.setOwner((String) arguments[0]);

			this.reloadStorage();
			this.onInventoryChanged();
			DimStorageNetwork.sendUpdateStorageToServer(this);
			
			return new Object[] { true };

		case 4: // setPublic
			if(this.isLocked())
				throw new Exception("DimChest is locked !");

			this.setOwner("public");

			this.reloadStorage();
			this.onInventoryChanged();
			DimStorageNetwork.sendUpdateStorageToServer(this);
			
			return new Object[] { true };

		case 5: // setFreq
			if(this.isLocked())
				throw new Exception("DimChest is locked !");

			Double freq = (Double) arguments[0];
			this.setFreq(freq.intValue());
			
			this.reloadStorage();
			this.onInventoryChanged();
			DimStorageNetwork.sendUpdateStorageToServer(this);
			
			return new Object[] { true };

		default:
			return null;

		}
	}

	@Override
	public boolean canAttachToSide(int side) {
		return true;
	}

	@Override
	public void attach(IComputerAccess computer) {}

	@Override
	public void detach(IComputerAccess computer) {}

	@Override
	public String getType() {
		return this.storage.getType();
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
		this.locked = tag.getBoolean("locked");

		this.direction = tag.getByte("direction");
		this.opening = tag.getBoolean("opening");
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);

		tag.setString("owner", this.owner);
		tag.setInteger("freq", this.freq);
		tag.setBoolean("locked", this.locked);

		tag.setByte("direction", this.direction);
		tag.setBoolean("opening", this.opening);
	}

	// ****************************************************************

	// ****************************************************************
	public DimChestStorage getStorage() { return this.storage; }
	
	public String getOwner() { return this.owner; }
	public void setOwner(String owner) { this.owner = owner; }

	public int getFreq() { return this.freq; }
	public void setFreq(int freq) { this.freq = freq; }

	public boolean isLocked() { return this.locked; }
	public void setLocked(boolean locked) { this.locked = locked; }

	public byte getDirection() { return this.direction; }
	public void setDirection(byte direction) { this.direction = direction; }

	public void setOpening(boolean opening) { this.opening = opening; }
	
	public float getMovablePartState() { return this.movablePartState; }

}
