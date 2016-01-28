package fr.ftnt.mineswagg.client;

import java.util.Calendar;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.common.FMLLog;
import fr.ftnt.mineswagg.common.BlockSwaggiumChest;
import fr.ftnt.mineswagg.common.MineSwagg;
import fr.ftnt.mineswagg.common.TileEntitySwaggiumChest;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelLargeChest;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;

public class TileEntitySwaggiumChestRenderer extends TileEntitySpecialRenderer
{
    public static ResourceLocation texture = new ResourceLocation(MineSwagg.MODID, "/textures/models/blocks/swaggiumchest.png");
    public static ModelSwaggiumChest model = new ModelSwaggiumChest();
    private boolean field_147509_j;

    public TileEntitySwaggiumChestRenderer()
    {
        this.func_147497_a(TileEntityRendererDispatcher.instance);
    }

    public void renderTileEntitySwaggiumChestAt(TileEntitySwaggiumChest tile, double x, double y, double z, float partialRenderTick)
    {
        int i;

        if(!tile.hasWorldObj())
        {
            i = 0;
        }
        else
        {
            Block block = tile.getBlockType();
            i = tile.getBlockMetadata();

            if(block instanceof BlockSwaggiumChest && i == 0)
            {
                try
                {
                    ((BlockSwaggiumChest)block).func_149954_e(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord);
                }
                catch(ClassCastException e)
                {
                    FMLLog.severe("Attempted to render a chest at %d,  %d, %d that was not a chest", tile.xCoord, tile.yCoord, tile.zCoord);
                }
                i = tile.getBlockMetadata();
            }
        }

        ModelSwaggiumChest model;

        model = this.model;

        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslatef((float)x, (float)y + 1.0F, (float)z + 1.0F);
        GL11.glScalef(1.0F, -1.0F, -1.0F);
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        short short1 = 0;

        if(i == 2)
        {
            short1 = 180;
        }

        if(i == 3)
        {
            short1 = 0;
        }

        if(i == 4)
        {
            short1 = 90;
        }

        if(i == 5)
        {
            short1 = -90;
        }

        GL11.glRotatef((float)short1, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        float f1 = tile.prevLidAngle + (tile.lidAngle - tile.prevLidAngle) * partialRenderTick;
        float f2;

        f1 = 1.0F - f1;
        f1 = 1.0F - f1 * f1 * f1;
        model.chestLid.rotateAngleX = -(f1 * (float)Math.PI / 2.0F);
        model.renderAll();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialRenderTick)
    {
        this.renderTileEntitySwaggiumChestAt((TileEntitySwaggiumChest)tile, x, y, z, partialRenderTick);
    }
}