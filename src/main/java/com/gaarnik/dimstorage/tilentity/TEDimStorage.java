package com.gaarnik.dimstorage.tilentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class TEDimStorage extends TileEntity {
	// ****************************************************************

	// ****************************************************************
	protected String owner;
	protected int freq;
	
	protected byte direction;
	
	protected String customName;

	// ****************************************************************
	protected void init(String owner, int freq) {
		this.owner = owner;
		this.freq = freq;
		
		this.direction = 0;
	}

	// ****************************************************************
	public abstract boolean activate(World world, int x, int y, int z, EntityPlayer player);

	// ****************************************************************

	// ****************************************************************

	// ****************************************************************
	public byte getDirection() { return this.direction; }
	public void setDirection(byte direction) { this.direction = direction; }
	
	public void setCustomGuiName(String name) { this.customName = name; }
	
	public String getCustomGuiName() { 
		if(! this.customName.isEmpty() && this.customName != null)
			return this.customName; 
		return "";
	}

}
