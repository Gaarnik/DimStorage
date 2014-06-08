package com.gaarnik.dimstorage.common;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.gaarnik.dimstorage.DimStorage;
import com.gaarnik.dimstorage.DimStorageGUIHandler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDimChest extends BlockContainer {
	// ****************************************************************

	// ****************************************************************
	private Icon top, sides;

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
		((TEDimChest) tileEntity).openChest();

		return true;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		int direction =  (MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3) + 2;
		TileEntity te = world.getBlockTileEntity(x, y, z);

		if(te != null && te instanceof TEDimChest) {
			((TEDimChest) te).setDirection((byte)direction);;
			world.markBlockForUpdate(x, y, z);
		}
	}

	// ****************************************************************
	@Override
	public void registerIcons(IconRegister iconRegister) {
		this.top = iconRegister.registerIcon(DimStorage.MODID + ":dimchest_top");
		this.sides = iconRegister.registerIcon(DimStorage.MODID + ":dimchest_sides");
	}

	@Override
	public Icon getIcon(int side, int metadata) {
		switch(side) {
		case 1: return  this.top;
		default: return this.sides;
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldSideBeRendered(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5) {
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	// ****************************************************************

	// ****************************************************************
	@Override
	public TileEntity createNewTileEntity(World world) { return new TEDimChest(); }

}
