package com.gaarnik.dimstorage.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import com.gaarnik.dimstorage.common.ContainerDimChest;
import com.gaarnik.dimstorage.common.TEDimChest;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIDimCHest extends GuiContainer {
	// ****************************************************************
	private static final int BUTTON_OWNER = 1;
	
	private static final ResourceLocation background = new ResourceLocation("dimstorage", "gui/dimchest.png");

	// ****************************************************************
	private TEDimChest tileEntity;
	
	// ****************************************************************
	public GUIDimCHest(InventoryPlayer player, TEDimChest tileEntity) {
		super(new ContainerDimChest(player, tileEntity));
		
		this.tileEntity = tileEntity;
		
		this.xSize = 256;
		this.ySize = 230;
	}

	// ****************************************************************
	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		super.initGui();
		
		this.buttonList.clear();
		
		GuiButton ownerButton = new GuiButton(BUTTON_OWNER, 297, 75, 60, 20, "Change");
		this.buttonList.add(ownerButton);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		switch(button.id) {
		
		case BUTTON_OWNER:
			this.tileEntity.swapOwner();
			break;
			
		}
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(background);
        
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRenderer.drawString(StatCollector.translateToLocal("DimChest"), 8, 6, 4210752);
		this.fontRenderer.drawString(StatCollector.translateToLocal("Inventory"), 8, 128, 4210752);
		
		// owner
		this.fontRenderer.drawString(StatCollector.translateToLocal("Owner:"), 180, 35, 4210752);
		this.fontRenderer.drawString(StatCollector.translateToLocal(this.tileEntity.getOwner()), 180, 50, 4210752);

		// freq
		this.fontRenderer.drawString(StatCollector.translateToLocal("Freq:"), 180, 100, 4210752);
		this.fontRenderer.drawString(StatCollector.translateToLocal(""+this.tileEntity.getFreq()), 210, 120, 4210752);
		
		// lock
		//TODO add lock checkbox
	}

	// ****************************************************************

	// ****************************************************************

	// ****************************************************************

}
