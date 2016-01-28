package fr.ftnt.mineswagg.common;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderSwagged extends RenderBiped
{

    public final ResourceLocation texture = new ResourceLocation("textures/entity/steve.png");

    public RenderSwagged(ModelBiped p_i1257_1_)
    {
        super(p_i1257_1_, 0.5F);
    }

    public ResourceLocation bindEntityTexture(EntitySwagged mod)
    {
        return texture;
    }

    protected ResourceLocation getEntityTexture(EntityLiving entity)
    {
        return bindEntityTexture((EntitySwagged)entity);
    }

}
