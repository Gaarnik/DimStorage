package com.gaarnik.dimstorage.common;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import com.gaarnik.dimstorage.DimStorage;
import com.gaarnik.dimstorage.DimStorageGUIHandler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDimChest extends BlockContainer {
	// ****************************************************************
	
	// ****************************************************************
	private Icon top, sides, bottom;

	// ****************************************************************
	public BlockDimChest(int id) {
		super(id, Material.rock);
		
		this.setHardness(20F);
		this.setResistance(100F);
		this.setStepSound(soundStoneFootstep);
		
		this.setCreativeTab(DimStorage.tabDimStorage);
	}

	// ****************************************************************
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int idk, float what, float these, float are) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

		if (tileEntity == null || player.isSneaking())
			return false;

		player.openGui(DimStorage.instance, DimStorageGUIHandler.GUI_DIMCHEST, world, x, y, z);

		return true;
	}

	// ****************************************************************
	@Override
	public void registerIcons(IconRegister iconRegister) {
		top = iconRegister.registerIcon(DimStorage.MODID + ":dim_chesttop_top");
		sides = iconRegister.registerIcon(DimStorage.MODID + ":dim_chesttop_sides");
		bottom = iconRegister.registerIcon(DimStorage.MODID + ":dim_chesttop_bottom");
	}

	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int metadata) {
		switch(side) {
			case 0: return  bottom;
			case 1: return  top;
			default: return sides;
		}
	}
	
	// ****************************************************************

	// ****************************************************************
	@Override
	public TileEntity createNewTileEntity(World world) { return new TEDimChest(); }

}
