package com.gaarnik.dimstorage.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.gaarnik.dimstorage.DimStorage;
import com.gaarnik.dimstorage.DimStorageGUIHandler;

public class ItemDimChestController extends Item {
	// ****************************************************************

	// ****************************************************************

	// ****************************************************************
	public ItemDimChestController() {
		super();
		
		this.setCreativeTab(DimStorage.tabDimStorage);
		this.setTextureName(DimStorage.MODID + ":dim_chest_controller");
	}

	// ****************************************************************
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		this.displayGUI(world, player);
		return stack;
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10) {
		return this.linkStorage(world, player);
	}

	// ****************************************************************
	private void displayGUI(World world, EntityPlayer player) {
		if(!player.isSneaking()) {
			player.openGui(DimStorage.instance, DimStorageGUIHandler.GUI_DIMCHEST_FROM_CONTROLLER, world, 
					(int) player.posX, (int) player.posY, (int) player.posZ);
		}
	}
	
	private boolean linkStorage(World world, EntityPlayer player) {
		if(player.isSneaking()) {
			return true;
		}
		
		return false;
	}

	// ****************************************************************

	// ****************************************************************

}
