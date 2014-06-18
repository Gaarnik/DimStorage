package com.gaarnik.dimstorage.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import com.gaarnik.dimstorage.common.ContainerDimChest;
import com.gaarnik.dimstorage.common.TEDimChest;
import com.gaarnik.dimstorage.network.DimStorageNetwork;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIDimCHest extends GuiContainer {
	// ****************************************************************
	private static final ResourceLocation background = new ResourceLocation("dimstorage", "gui/dimchest.png");
	
	private static final int BUTTON_OWNER 		= 1;
	private static final int BUTTON_FREQ		= 2;
	private static final int BUTTON_LOCKED 		= 3;
	
	private static final String ALLOWED_CHARACTERS = "\b0123456789"; 

	// ****************************************************************
	private TEDimChest tileEntity;
	
	private String change, owner, freq, locked, yes, no,guiName, inventory;
	
	private GuiButton lockedButton;
	private GuiTextField freqTextField;
	
	private int currentFreq;
	
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
		
		// Get translation
		this.change = StatCollector.translateToLocal("gui.dimchest.change");
		this.owner = StatCollector.translateToLocal("gui.dimchest.owner");
		this.freq = StatCollector.translateToLocal("gui.dimchest.frequency");
		this.locked = StatCollector.translateToLocal("gui.dimchest.locked");
		this.yes = StatCollector.translateToLocal("gui.dimchest.yes");
		this.no = StatCollector.translateToLocal("gui.dimchest.no");
		this.guiName = StatCollector.translateToLocal("container.dimchest");
		this.inventory = StatCollector.translateToLocal("container.inventory");
		
		// init buttons list
		this.buttonList.clear();
		
		GuiButton ownerButton = new GuiButton(BUTTON_OWNER, this.width / 2 + 55, this.height / 2 - 52, 64, 20, this.change);
		this.buttonList.add(ownerButton);
		
		GuiButton freqButton = new GuiButton(BUTTON_FREQ, this.width / 2 + 55, this.height / 2 + 9, 64, 20, this.change);
		this.buttonList.add(freqButton);

		this.lockedButton = new GuiButton(BUTTON_LOCKED, this.width / 2 + 55, this.height / 2 + 48, 64, 20, this.no);
		this.buttonList.add(this.lockedButton);
		
		// add Freq textfield
		this.currentFreq = this.tileEntity.getFreq();
		this.freqTextField = new GuiTextField(this.fontRendererObj, this.width / 2 + 55, this.height / 2 - 10, 64, 15);
		this.freqTextField.setMaxStringLength(3);
		this.freqTextField.setFocused(false);
		this.freqTextField.setText(""+this.currentFreq);
	}
	
	@Override
	public void updateScreen() {
		this.freqTextField.updateCursorCounter();
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		switch(button.id) {
		
		case BUTTON_OWNER:
			this.tileEntity.swapOwner();
			break;
			
		case BUTTON_FREQ:
			int freq = Integer.parseInt(this.freqTextField.getText());
			this.tileEntity.changeFreq(freq);
			break;
			
		case BUTTON_LOCKED:
			this.tileEntity.swapLocked();
			break;
			
		}
		
		DimStorageNetwork.sendUpdateStorageToServer(this.tileEntity);
	}
	
	
	@Override
	protected void keyTyped(char c, int code) {
		super.keyTyped(c, code);
		
		if(ALLOWED_CHARACTERS.contains(""+c))
			this.freqTextField.textboxKeyTyped(c, code);
	}
	
	@Override
	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
		
		this.freqTextField.mouseClicked(par1, par2, par3);
	}

	// ****************************************************************
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
		this.fontRendererObj.drawString(StatCollector.translateToLocal(this.guiName), 8, 6, 4210752);
		this.fontRendererObj.drawString(StatCollector.translateToLocal(this.inventory), 8, 128, 4210752);

		// owner
		this.fontRendererObj.drawString(StatCollector.translateToLocal(this.owner), 185, 35, 4210752);
        this.drawHorizontalLine(185, 185 + this.fontRendererObj.getStringWidth(this.owner), 44, 0xFF333333);
		int width = this.fontRendererObj.getStringWidth(this.tileEntity.getOwner());
		this.fontRendererObj.drawString(StatCollector.translateToLocal(this.tileEntity.getOwner()), 215 - width / 2, 50, 4210752);
		
		// freq
		this.fontRendererObj.drawString(StatCollector.translateToLocal(this.freq), 185, 90, 4210752);
		this.drawHorizontalLine(185, 185 + this.fontRendererObj.getStringWidth(this.freq), 99, 0xFF333333);
		
		// locked
		this.fontRendererObj.drawString(StatCollector.translateToLocal(this.locked), 185, 150, 4210752);
        this.drawHorizontalLine(185, 185 + this.fontRendererObj.getStringWidth(this.locked), 159, 0xFF333333);

		// refresh button label
		this.lockedButton.displayString = this.tileEntity.isLocked() ? this.yes: this.no;
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
		
		// freq
		this.freqTextField.drawTextBox();
	}

	// ****************************************************************

	// ****************************************************************

}
