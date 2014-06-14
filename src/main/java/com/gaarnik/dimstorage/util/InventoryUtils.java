package com.gaarnik.dimstorage.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class InventoryUtils {

	/**
	 * 
	 * @param inv
	 * @param slot
	 * @param size
	 * @return
	 */
	public static ItemStack decrStackSize(IInventory inv, int slot, int size) {
        ItemStack item = inv.getStackInSlot(slot);

        if (item != null) {
            if (item.stackSize <= size) {
                inv.setInventorySlotContents(slot, null);
                
                return item;
            }
            ItemStack itemstack1 = item.splitStack(size);
            if (item.stackSize == 0)
                inv.setInventorySlotContents(slot, null);

            return itemstack1;
        }
        
        return null;
    }
	
	/**
	 * 
	 * @param inv
	 * @param slot
	 * @return
	 */
	public static ItemStack getStackInSlotOnClosing(IInventory inv, int slot) {
        ItemStack stack = inv.getStackInSlot(slot);
        inv.setInventorySlotContents(slot, null);
        return stack;
    }
	
	/**
	 * 
	 * @param items
	 * @param tagList
	 */
	public static void readItemStacksFromTag(ItemStack[] items, NBTTagList tagList) {
        for (int i = 0; i < tagList.tagCount(); ++i) {
            NBTTagCompound nbttagcompound1 = tagList.getCompoundTagAt(i);
            
            int j = nbttagcompound1.getByte("Slot") & 255;

            if (j >= 0 && j < items.length)
                items[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
        }
    }

	/**
	 * 
	 * @param items
	 * @return
	 */
	public static NBTTagList writeItemStacksToTag(ItemStack[] items) {
		NBTTagList nbttaglist = new NBTTagList();
		
		for (int i = 0; i < items.length; ++i) {
            if (items[i] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                items[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
		
		return nbttaglist;
	}
	
}
