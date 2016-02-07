package fr.ftnt.mineswagg.proxy;

import cpw.mods.fml.client.registry.RenderingRegistry;
import fr.ftnt.mineswagg.client.RenderSwagged;
import fr.ftnt.mineswagg.common.entities.EntitySwagged;
import net.minecraft.client.model.ModelBiped;

public class ClientProxy extends CommonProxy
{
    public static int tesrRenderId;

    @Override
    public void registerRender()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntitySwagged.class, new RenderSwagged(new ModelBiped()));

        tesrRenderId = RenderingRegistry.getNextAvailableRenderId();
        // RenderingRegistry.registerBlockHandler(new TileEntitySwaggiumChestRendererInventory());

        // ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySwaggiumChest.class, new TileEntitySwaggiumChestRenderer());
    }

}
