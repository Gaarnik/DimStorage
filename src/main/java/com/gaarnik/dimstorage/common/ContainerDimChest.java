package com.gaarnik.dimstorage.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerDimChest extends Container {
	// *******************************************************************

	// *******************************************************************
	private TEDimChest tileEntity;

	// *******************************************************************
	public ContainerDimChest(InventoryPlayer inventory, TEDimChest tileEntity) {
		this.tileEntity = tileEntity;
		this.tileEntity.openInventory();

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
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int position) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(position);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			// chest to inventory
			if (position < 54) {
				if (!this.mergeItemStack(itemstack1, 54, this.inventorySlots.size(), true))
					return null;
			}
			// inventory to chest
			else if (!this.mergeItemStack(itemstack1, 0, 54, false))
				return null;

			if (itemstack1.stackSize == 0)
				slot.putStack((ItemStack)null);
			else
				slot.onSlotChanged();
		}

		return itemstack;
	}

	@Override
	public void onContainerClosed(EntityPlayer par1EntityPlayer) {
		super.onContainerClosed(par1EntityPlayer);
		
		this.tileEntity.closeInventory();
	}
	
	// *******************************************************************

	// *******************************************************************

	// *******************************************************************
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return this.tileEntity.isUseableByPlayer(entityplayer);
	}

}

