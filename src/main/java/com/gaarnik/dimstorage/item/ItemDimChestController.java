package com.gaarnik.dimstorage.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import com.gaarnik.dimstorage.DimStorage;
import com.gaarnik.dimstorage.DimStorageGUIHandler;
import com.gaarnik.dimstorage.tilentity.TEDimChest;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		return this.linkStorage(stack, world, x, y, z, player);
	}

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b) {
		if(stack.hasTagCompound()) {
			list.add("");
			list.add(EnumChatFormatting.BLUE + "Frequency: " + EnumChatFormatting.WHITE + stack.getTagCompound().getInteger("freq"));
			list.add(EnumChatFormatting.BLUE + "Owner: " + EnumChatFormatting.WHITE + stack.getTagCompound().getString("owner"));
			list.add(EnumChatFormatting.BLUE + "isLocked: " + EnumChatFormatting.WHITE + stack.getTagCompound().getBoolean("locked"));
			list.add(EnumChatFormatting.BLUE + "guiName: " + EnumChatFormatting.WHITE + stack.getTagCompound().getString("CustomName"));
		}
    }
    
	// ****************************************************************
	private void displayGUI(World world, EntityPlayer player) {
		if(!player.isSneaking()) {
			player.openGui(DimStorage.instance, DimStorageGUIHandler.GUI_DIMCHEST_FROM_CONTROLLER, world, 
					(int) player.posX, (int) player.posY, (int) player.posZ);
		}
	}
	
	private boolean linkStorage(ItemStack stack, World world, int x, int y, int z, EntityPlayer player) {
		if(player.isSneaking()) {
			TileEntity te = world.getTileEntity(x, y, z);
			if(te instanceof TEDimChest) {
				
				int freq = ((TEDimChest)te).getFreq();
				boolean isLocked = ((TEDimChest)te).isLocked();
				String owner = ((TEDimChest)te).getOwner();
				String guiName = ((TEDimChest)te).getCustomGuiName();

				if(!stack.hasTagCompound())
					stack.setTagCompound(new NBTTagCompound());
				stack.getTagCompound().setInteger("freq", freq);
				stack.getTagCompound().setString("owner", owner);
				stack.getTagCompound().setBoolean("locked", isLocked);
				stack.getTagCompound().setString("CustomName", guiName);

				return true;
			}
			return false;
		}
		return false;
	}

	// ****************************************************************

	// ****************************************************************

}
