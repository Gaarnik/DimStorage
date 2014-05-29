package com.gaarnik.dimstorage.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class ContainerDimChest extends Container {
	// *******************************************************************

	// *******************************************************************
	private TEDimChest tileEntity;

	// *******************************************************************
	public ContainerDimChest(InventoryPlayer player, TEDimChest tileEntity) {
		this.tileEntity = tileEntity;
	}

	// *******************************************************************

	// *******************************************************************

	// *******************************************************************

	// *******************************************************************
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return this.tileEntity.isUseableByPlayer(entityplayer);
	}

}
