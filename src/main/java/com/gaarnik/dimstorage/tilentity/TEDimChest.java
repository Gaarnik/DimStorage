package com.gaarnik.dimstorage.tilentity;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import com.gaarnik.dimstorage.DimStorage;
import com.gaarnik.dimstorage.DimStorageGUIHandler;
import com.gaarnik.dimstorage.network.DimStorageNetwork;
import com.gaarnik.dimstorage.storage.DimStorageManager;
import com.gaarnik.dimstorage.storage.chest.DimChestStorage;

import cpw.mods.fml.common.Optional.Interface;

@Interface(iface="dan200.computer.api.IPeripheral", modid="ComputerCraft")
public class TEDimChest extends TEDimStorage implements IInventory, ISidedInventory {
	// ****************************************************************
	private static final float MIN_MOVABLE_POSITION = 0f;
	private static final float MAX_MOVABLE_POSITION = 0.5f;

	private static final float OPENING_SPEED = 0.05f;

	// ****************************************************************
	private DimChestStorage storage;

    private int openCount;
	private float movablePartState;
	
	// ****************************************************************
	public TEDimChest() {
		this.init("public", 1);
	}
	
	public TEDimChest(EntityPlayer player) {
		this.init("public", 1);
		
		this.worldObj = player.worldObj;
		this.reloadStorage();
	}
	
	@Override
	protected void init(String owner, int freq) {
		super.init(owner, freq);
		
		this.movablePartState = MIN_MOVABLE_POSITION;
	}

	// ****************************************************************
	@Override
	public void updateEntity() {
		super.updateEntity();

		if(this.openCount > 0) {
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

	public void changeFreq(int freq) {
		if(!this.worldObj.isRemote)
			return;

		if(freq >= 1 && freq <= 999) {
			this.freq = freq;
			this.reloadStorage();
		}
	}

	public void swapLocked() {
		this.storage.swapLocked(this);
		this.reloadStorage();
	}
	
	@Override
	public boolean activate(World world, int x, int y, int z, EntityPlayer player) {
		if(this.isUseableByPlayer(player)) {
			if(this.storage.isClient())
				this.storage.empty();
			
			player.openGui(DimStorage.instance, DimStorageGUIHandler.GUI_DIMCHEST, world, x, y, z);
		}
		else {
			if(this.storage.isClient())
				player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("tileentity.dimchest.accessDenied")));
		}
		
		return true;
	}

	// ****************************************************************
	@Override
	public void openInventory() {
		if(this.storage.isClient())
            return;

        synchronized(this) {
            this.openCount++;
            
            if(this.openCount == 1)
                DimStorageNetwork.sendOpenStorageToClients(this);
        }
	}

	@Override
	public void closeInventory() {
		if(this.storage.isClient())
            return;

        synchronized(this) {
            this.openCount--;
            
            if(this.openCount == 0)
            	DimStorageNetwork.sendOpenStorageToClients(this);
        }
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		return this.storage.decrStackSize(i, j);
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.dimchest";
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
	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
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
	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return this.storage.canExtractItem(i, itemstack, j);
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		return this.storage.canInsertItem(i, itemstack, j);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		return this.storage.getAccessibleSlotsFromSide(var1);
	}
	
	// ****************************************************************
	// Not used until CC API is released for 1.7.2
	/*@Override
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
	}*/

	// ****************************************************************
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbtTag = new NBTTagCompound();
		this.writeToNBT(nbtTag);

		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbtTag);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		this.readFromNBT(packet.func_148857_g());
	}

	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);

		this.owner = tag.getString("owner");
		this.freq = tag.getInteger("freq");

		this.direction = tag.getByte("direction");
		
		if(tag.hasKey("CustomName"))
			this.customName = tag.getString("CustomName");
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);

		tag.setString("owner", this.owner);
		tag.setInteger("freq", this.freq);

		tag.setByte("direction", this.direction);
		if(this.hasCustomInventoryName())
			tag.setString("CustomName", this.customName);
	}

	// ****************************************************************

	// ****************************************************************
	public DimChestStorage getStorage() { return this.storage; }
	
	public String getOwner() { return this.owner; }
	public void setOwner(String owner) { this.owner = owner; }

	public int getFreq() { return this.freq; }
	public void setFreq(int freq) { this.freq = freq; }

	public int getOpenCount() { return this.openCount; }
	public void setOpenCount(int count) { this.openCount = count; }
	
	public float getMovablePartState() { return this.movablePartState; }

	public boolean isLocked() { return this.storage.isLocked(); }

	public String getStorageType() { return this.storage.getType(); }
	
}
