package fr.ftnt.swaggmod.proxy;

import cpw.mods.fml.client.registry.RenderingRegistry;
import fr.ftnt.swaggmod.common.EntitySwagged;
import fr.ftnt.swaggmod.common.RenderSwagged;
import net.minecraft.client.model.ModelBiped;

public class ClientProxy extends CommonProxy
{

    @Override
    public void registerRender()
    {

        RenderingRegistry.registerEntityRenderingHandler(EntitySwagged.class, new RenderSwagged(new ModelBiped()));
    }

}
