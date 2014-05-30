package com.gaarnik.dimstorage.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.gaarnik.dimstorage.DimStorage;
import com.gaarnik.dimstorage.client.model.ModelDimChest;
import com.gaarnik.dimstorage.common.TEDimChest;

public class TERendererDimChest extends TileEntitySpecialRenderer {
	// ****************************************************************
	private static final ResourceLocation texture = new ResourceLocation("dimstorage", "textures/models/dimchest.png");

	// ****************************************************************
	private ModelDimChest model;

	// ****************************************************************
	public TERendererDimChest() {
		this.model = new ModelDimChest();
	}

	// ****************************************************************
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float f) {
		GL11.glPushMatrix();

		GL11.glTranslatef((float)d0, (float)d1, (float)d2);

		TEDimChest tileEntityYour = (TEDimChest) tileentity;
		this.renderBlock(tileEntityYour, tileentity.worldObj, tileentity.xCoord, tileentity.yCoord, tileentity.zCoord, DimStorage.dimChest);

		GL11.glPopMatrix();
	}

	// ****************************************************************
	private void renderBlock(TEDimChest entity, World world, int i, int j, int k, Block block) {
		Tessellator tessellator = Tessellator.instance;
		float f = block.getBlockBrightness(world, i, j, k);
		int l = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
		int l1 = l % 65536;
		int l2 = l / 65536;
		tessellator.setColorOpaque_F(f, f, f);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)l1, (float)l2); 
		int dir = 0;
		if(entity != null)
			dir = entity.getDirection();
		
		GL11.glPushMatrix();
		this.bindTexture(texture);

		GL11.glTranslatef(0.5F, -0.5F, 0.5F);
		//This line actually rotates the renderer.

		/** direction **/
		GL11.glRotatef(dir * (-90F), 0F, 1F, 0F);
		GL11.glRotatef(180F, 0F, 1F, 0F);

		/** sens **/
		GL11.glRotatef(180F, 1F, 0F, 0F);

		/** Ajustement **/
		GL11.glTranslatef(0F, -2F, 0F);
		
		this.model.render((Entity)null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

		GL11.glPopMatrix();
	}

}
