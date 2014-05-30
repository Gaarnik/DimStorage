package com.gaarnik.dimstorage.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerDimChest extends Container {
	// *******************************************************************

	// *******************************************************************
	private TEDimChest tileEntity;

	// *******************************************************************
	public ContainerDimChest(InventoryPlayer inventory, TEDimChest tileEntity) {
		this.tileEntity = tileEntity;

		int y;
		
		// chest inventory
		for (y = 0; y < 6; y++)
			for (int x = 0; x < 9; x++)
				this.addSlotToContainer(new Slot(this.tileEntity, x + y * 9, 8 + x * 18, 18 + y * 18));
		
		// player inventory
		for (y = 0; y < 3; ++y)
			for (int x = 0; x < 9; ++x)
				this.addSlotToContainer(new Slot(inventory, x + y * 9 + 9, 8 + x * 18, 140 + y * 18));

		for (y = 0; y < 9; ++y)
			this.addSlotToContainer(new Slot(inventory, y, 8 + y * 18, 198));
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
