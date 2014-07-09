package com.gaarnik.dimstorage.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.gaarnik.dimstorage.DimStorage;
import com.gaarnik.dimstorage.tilentity.TEDimStorage;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockDimStorage extends BlockContainer {
	// ****************************************************************

	// ****************************************************************
	private IIcon top, sides;

	// ****************************************************************
	protected BlockDimStorage() {
		super(Material.rock);

		this.setHardness(20F);
		this.setResistance(100F);
		this.setStepSound(soundTypeStone);

		this.setCreativeTab(DimStorage.tabDimStorage);
	}

	// ****************************************************************
	public abstract String getTopIcon();
	public abstract String getSidesIcon();

	// ****************************************************************
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int idk, float what, float these, float are) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);

		if (tileEntity == null || tileEntity instanceof TEDimStorage == false || player.isSneaking())
			return false;

		return ((TEDimStorage) tileEntity).activate(world, x, y, z, player);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		int direction =  (MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3) + 2;
		TileEntity te = world.getTileEntity(x, y, z);

		if(te != null && te instanceof TEDimStorage) {
			((TEDimStorage) te).setDirection((byte)direction);
			((TEDimStorage) te).setCustomGuiName(stack.getDisplayName());
			world.markBlockForUpdate(x, y, z);
		}
	}
	
	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.top = iconRegister.registerIcon(DimStorage.MODID + ":" + this.getTopIcon());
		this.sides = iconRegister.registerIcon(DimStorage.MODID + ":" + this.getSidesIcon());
	}

	@Override
	public IIcon getIcon(int side, int metadata) {
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

}
