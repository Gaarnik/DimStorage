package com.gaarnik.dimstorage.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.gaarnik.dimstorage.container.ContainerDimChest;
import com.gaarnik.dimstorage.network.DimStorageNetwork;
import com.gaarnik.dimstorage.tilentity.TEDimChest;

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
	
	private static final int ANIMATION_SPEED = 10;
	private static final int SETTINGS_WIDTH = 80;
	private static final int BUTTON_WIDTH = 20;
	
	private static enum SettingsState {
		STATE_CLOSED, STATE_OPENNING, STATE_OPENED, STATE_CLOSING
	}

	// ****************************************************************
	private TEDimChest tileEntity;
	
	private String change, owner, freq, locked, yes, no,guiName, inventory;
	
	private GuiButton ownerButton, freqButton, lockedButton;
	private GuiTextField freqTextField;
	
	private int currentFreq;
	
	private SettingsState state;
	private int animationState;
	private boolean drawSettings;
	private boolean settingsButtonOver;
	
	private IInventory playerInventory;

	// ****************************************************************
	public GUIDimCHest(InventoryPlayer player, TEDimChest tileEntity) {
		super(new ContainerDimChest(player, tileEntity));
		
		this.tileEntity = tileEntity;
		
		this.xSize = 176;
		this.ySize = 230;
		
		this.state = SettingsState.STATE_CLOSED;
		this.animationState = 0;
		this.drawSettings = false;
		this.settingsButtonOver = false;
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
		
		this.ownerButton = new GuiButton(BUTTON_OWNER, this.width / 2 + 95, this.height / 2 - 42, 64, 20, this.change);
		this.buttonList.add(this.ownerButton);
		
		this.freqButton = new GuiButton(BUTTON_FREQ, this.width / 2 + 95, this.height / 2 + 19, 64, 20, this.change);
		this.buttonList.add(this.freqButton);

		this.lockedButton = new GuiButton(BUTTON_LOCKED, this.width / 2 + 95, this.height / 2 + 58, 64, 20, this.no);
		this.buttonList.add(this.lockedButton);
		
		// add Freq textfield
		this.currentFreq = this.tileEntity.getFreq();
		this.freqTextField = new GuiTextField(this.fontRendererObj, this.width / 2 + 95, this.height / 2, 64, 15);
		this.freqTextField.setMaxStringLength(3);
		this.freqTextField.setFocused(false);
		this.freqTextField.setText(""+this.currentFreq);

		this.drawSettings(this.drawSettings);
	}
	
	@Override
	public void updateScreen() {
		this.freqTextField.updateCursorCounter();
		
		switch(this.state) {
		
		case STATE_OPENNING:
			this.animationState += ANIMATION_SPEED;
			if(this.animationState >= SETTINGS_WIDTH) {
				this.animationState = SETTINGS_WIDTH;
				this.state = SettingsState.STATE_OPENED;
				this.drawSettings(true);
			}
			break;
			
		case STATE_CLOSING:
			this.animationState -= ANIMATION_SPEED;
			if(this.animationState <= 0) {
				this.animationState = 0;
				this.state = SettingsState.STATE_CLOSED;
			}
			break;

		default:
			break;
			
		}
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
	protected void mouseClicked(int mouseX, int mouseY, int par3) {
		super.mouseClicked(mouseX, mouseY, par3);
		
		this.freqTextField.mouseClicked(mouseX, mouseY, par3);
		
		int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        
        int buttonX = x + this.xSize;
        int buttonY = y + 16;
		
		boolean over = false;
		
		if(mouseX >= buttonX && mouseX <= buttonX + BUTTON_WIDTH)
			if(mouseY >= buttonY && mouseY <= buttonY + BUTTON_WIDTH)
				over = true;
		
		if(over == false)
			return;
		
		if(this.state == SettingsState.STATE_CLOSED) {
			this.state = SettingsState.STATE_OPENNING;
		}
		else if(this.state == SettingsState.STATE_OPENED) {
			this.state = SettingsState.STATE_CLOSING;
			this.drawSettings(false);
		}
	}
	
	@Override
	public void handleMouseInput() {
		super.handleMouseInput();
		
		int mouseX = Mouse.getEventX() * this.width / this.mc.displayWidth;
        int mouseY = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
        
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        
        int buttonX = x + this.xSize;
        int buttonY = y + 16;
		
		this.settingsButtonOver = false;
		
		if(mouseX >= buttonX && mouseX <= buttonX + BUTTON_WIDTH)
			if(mouseY >= buttonY && mouseY <= buttonY + BUTTON_WIDTH)
				this.settingsButtonOver = true;
	}

	// ****************************************************************
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(background);
        
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        
        int settingsX = x + (this.xSize- SETTINGS_WIDTH);
        
        this.drawTexturedModalRect(settingsX + this.animationState, y + 36, this.xSize, 36, SETTINGS_WIDTH, this.ySize);
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, 222);
        
        int buttonX = x + this.xSize;
        int buttonY = y + 16;
        
        // button background
        this.drawTexturedModalRect(buttonX, buttonY, this.xSize, 16, BUTTON_WIDTH, BUTTON_WIDTH);
        
        if(this.state == SettingsState.STATE_CLOSED || this.state == SettingsState.STATE_OPENNING) {
        	if(this.settingsButtonOver)
        		this.drawTexturedModalRect(buttonX + 6, buttonY - 3, this.xSize + 28, 16, 8, BUTTON_WIDTH);
        	else
        		this.drawTexturedModalRect(buttonX + 6, buttonY - 3, this.xSize + 20, 16, 8, BUTTON_WIDTH);
        }
        else if(this.state == SettingsState.STATE_OPENED || this.state == SettingsState.STATE_CLOSING) {
        	if(this.settingsButtonOver)
        		this.drawTexturedModalRect(buttonX + 4, buttonY - 3, this.xSize + 44, 16, 8, BUTTON_WIDTH);
        	else
        		this.drawTexturedModalRect(buttonX + 4, buttonY - 3, this.xSize + 36, 16, 8, BUTTON_WIDTH);
        }
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) { //TODO
        this.fontRendererObj.drawString(this.tileEntity.hasCustomInventoryName() ? this.tileEntity.getInventoryName() : I18n.format(this.tileEntity.getInventoryName()),  8, 6, 4210752);
		this.fontRendererObj.drawString(StatCollector.translateToLocal(this.inventory), 8, 128, 4210752);

		if(!this.drawSettings)
			return;

		int posY = 45;
		
		// owner
		this.fontRendererObj.drawString(StatCollector.translateToLocal(this.owner), 185, posY, 4210752);
		posY += 9;
        this.drawHorizontalLine(185, 185 + this.fontRendererObj.getStringWidth(this.owner), posY, 0xFF333333);
        posY += 6;
		int width = this.fontRendererObj.getStringWidth(this.tileEntity.getOwner());
		this.fontRendererObj.drawString(StatCollector.translateToLocal(this.tileEntity.getOwner()), 215 - width / 2, posY, 4210752);
		posY += 40;
		
		// freq
		this.fontRendererObj.drawString(StatCollector.translateToLocal(this.freq), 185, posY, 4210752);
		posY += 9;
		this.drawHorizontalLine(185, 185 + this.fontRendererObj.getStringWidth(this.freq), posY, 0xFF333333);
		posY += 51;
		
		// locked
		this.fontRendererObj.drawString(StatCollector.translateToLocal(this.locked), 185, posY, 4210752);
		posY += 9;
        this.drawHorizontalLine(185, 185 + this.fontRendererObj.getStringWidth(this.locked), posY, 0xFF333333);

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
	private void drawSettings(boolean draw) {
		this.drawSettings = draw;
		
		this.ownerButton.visible = draw;
		this.freqButton.visible = draw;
		this.lockedButton.visible = draw;
		
		this.freqTextField.setVisible(draw);
	}

	// ****************************************************************

}
