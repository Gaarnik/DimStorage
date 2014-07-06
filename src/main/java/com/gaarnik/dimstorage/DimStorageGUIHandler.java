package com.gaarnik.dimstorage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.gaarnik.dimstorage.container.ContainerDimChest;
import com.gaarnik.dimstorage.gui.GUIDimCHest;
import com.gaarnik.dimstorage.tilentity.TEDimChest;

import cpw.mods.fml.common.network.IGuiHandler;

public class DimStorageGUIHandler implements IGuiHandler {
	// ****************************************************************
	public static final int GUI_DIMCHEST 					= 1;
	public static final int GUI_DIMCHEST_FROM_CONTROLLER 	= 2;

	// ****************************************************************
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);

		switch(ID) {

		case GUI_DIMCHEST:
			return new GUIDimCHest(player.inventory, (TEDimChest) tileEntity, false);
			
		case GUI_DIMCHEST_FROM_CONTROLLER:
			return new GUIDimCHest(player.inventory, new TEDimChest(player), true);

		default:
			return null;

		}
	}

	// ****************************************************************
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);

		switch(ID) {

		case GUI_DIMCHEST:
			return new ContainerDimChest(player.inventory, (TEDimChest) tileEntity);

		case GUI_DIMCHEST_FROM_CONTROLLER:
			return new ContainerDimChest(player.inventory, new TEDimChest(player));
			
		default:
			return null;

		}
	}

}
