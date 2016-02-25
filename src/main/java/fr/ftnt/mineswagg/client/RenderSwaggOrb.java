package fr.ftnt.mineswagg.client;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import fr.ftnt.mineswagg.common.MineSwagg;
import fr.ftnt.mineswagg.common.entities.EntitySwaggOrb;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderXPOrb;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderSwaggOrb extends RenderXPOrb
{
    @Override
    public void doRender(Entity entity, double posX, double posY, double posZ, float arg8, float lightlevel)
    {
        if(entity instanceof EntitySwaggOrb)
            this.doRender((EntitySwaggOrb)entity, posX, posY, posZ, arg8, lightlevel);
    }

    public void doRender(EntitySwaggOrb entity, double posX, double posY, double posZ, float arg8, float lightlevel)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)posX, (float)posY, (float)posZ);
        this.bindEntityTexture(entity);
        int i = entity.getTextureBySwagg();
        float f2 = (float)(i % 4 * 16 + 0) / 64.0F;
        float f3 = (float)(i % 4 * 16 + 16) / 64.0F;
        float f4 = (float)(i / 4 * 16 + 0) / 64.0F;
        float f5 = (float)(i / 4 * 16 + 16) / 64.0F;
        float f6 = 1.0F;
        float f7 = 0.5F;
        float f8 = 0.25F;
        int j = entity.getBrightnessForRender(lightlevel);
        int k = j % 65536;
        int l = j / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)k / 1.0F, (float)l / 1.0F);
        float f10 = 255.0F;
        float f11 = ((float)entity.xpColor + lightlevel) / 2.0F;
        l = (int)((MathHelper.sin(f11 + 0.0F) + 1.0F) * 0.5F * f10);
        int i1 = (int)f10;
        int j1 = (int)((MathHelper.sin(f11 + 4.1887903F) + 1.0F) * 0.1F * f10);
        int k1 = l << 16 | i1 << 8 | j1;
        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        float f9 = 0.3F;
        GL11.glScalef(f9, f9, f9);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        tessellator.addVertexWithUV((double)(0.0F - f7), (double)(0.0F - f8), 0.0D, (double)f2, (double)f5);
        tessellator.addVertexWithUV((double)(f6 - f7), (double)(0.0F - f8), 0.0D, (double)f3, (double)f5);
        tessellator.addVertexWithUV((double)(f6 - f7), (double)(1.0F - f8), 0.0D, (double)f3, (double)f4);
        tessellator.addVertexWithUV((double)(0.0F - f7), (double)(1.0F - f8), 0.0D, (double)f2, (double)f4);
        tessellator.draw();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return new ResourceLocation(MineSwagg.NAME + ":textures/entity/swagg_orb.png");
    }
}
