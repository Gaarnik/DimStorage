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
	private static final int BUTTON_OWNER 		= 1;
	private static final int BUTTON_FREQ_DOWN 	= 2;
	private static final int BUTTON_FREQ_UP 	= 3;
	private static final int BUTTON_LOCKED 		= 4;
	
	private static final ResourceLocation background = new ResourceLocation("dimstorage", "gui/dimchest.png");

	// ****************************************************************
	private TEDimChest tileEntity;
	
	private String change, owner, freq, locked, yes, no,guiName, inventory;
	
	private GuiButton lockedButton;
	
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
		
		/** Get translation **/
		this.change = StatCollector.translateToLocal("gui.dimchest.change");
		this.owner = StatCollector.translateToLocal("gui.dimchest.owner");
		this.freq = StatCollector.translateToLocal("gui.dimchest.frequency");
		this.locked = StatCollector.translateToLocal("gui.dimchest.locked");
		this.yes = StatCollector.translateToLocal("gui.dimchest.yes");
		this.no = StatCollector.translateToLocal("gui.dimchest.no");
		this.guiName = StatCollector.translateToLocal("container.dimchest");
		this.inventory = StatCollector.translateToLocal("container.inventory");
		
		this.buttonList.clear();
		
		GuiButton ownerButton = new GuiButton(BUTTON_OWNER, this.width / 2 + 55, this.height / 2 - 50, 64, 20, this.change);
		this.buttonList.add(ownerButton);
		
		GuiButton freqDownButton = new GuiButton(BUTTON_FREQ_DOWN, this.width / 2 + 55, this.height / 2 - 1, 20, 20, "<");
		this.buttonList.add(freqDownButton);
		
		GuiButton freqUpButton = new GuiButton(BUTTON_FREQ_UP, this.width / 2 + 99, this.height / 2 - 1, 20, 20, ">");
		this.buttonList.add(freqUpButton);

		this.lockedButton = new GuiButton(BUTTON_LOCKED, this.width / 2 + 55, this.height / 2 + 45, 64, 20, this.no);
		this.buttonList.add(this.lockedButton);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		switch(button.id) {
		
		case BUTTON_OWNER:
			this.tileEntity.swapOwner();
			break;
			
		case BUTTON_FREQ_DOWN:
			this.tileEntity.downFreq();
			break;
			
		case BUTTON_FREQ_UP:
			this.tileEntity.upFreq();
			break;
			
		case BUTTON_LOCKED:
			// temp
			this.lockedButton.displayString = this.lockedButton.displayString.equals(this.yes) ? this.no: this.yes;
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
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		this.fontRenderer.drawString(StatCollector.translateToLocal(this.guiName), 8, 6, 4210752);
		this.fontRenderer.drawString(StatCollector.translateToLocal(this.inventory), 8, 128, 4210752);
		
		// owner
		this.fontRenderer.drawString(StatCollector.translateToLocal(this.owner), 180, 35, 4210752);
		this.fontRenderer.drawString(StatCollector.translateToLocal(this.tileEntity.getOwner()), 180, 50, 4210752);

		// freq
		this.fontRenderer.drawString(StatCollector.translateToLocal(this.freq), 180, 100, 4210752);
		this.fontRenderer.drawString(StatCollector.translateToLocal(""+this.tileEntity.getFreq()), 212, 121, 4210752);
		
		// locked
		this.fontRenderer.drawString(StatCollector.translateToLocal(this.locked), 180, 145, 4210752);
	}

	// ****************************************************************

	// ****************************************************************

	// ****************************************************************

}
