package fr.ftnt.mineswagg.client.guiHandlers;

import cpw.mods.fml.common.network.IGuiHandler;
import fr.ftnt.mineswagg.client.GuiSwaggiumGenerator;
import fr.ftnt.mineswagg.common.containers.ContainerSwaggiumGenerator;
import fr.ftnt.mineswagg.common.tileentities.TileEntitySwaggiumGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandlerSwaggiumGenerator implements IGuiHandler
{

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity tile = world.getTileEntity(x, y, z);
        if(tile instanceof TileEntitySwaggiumGenerator)
        {
            return new ContainerSwaggiumGenerator((TileEntitySwaggiumGenerator)tile, player.inventory);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity tile = world.getTileEntity(x, y, z);
        if(tile instanceof TileEntitySwaggiumGenerator)
        {
            return new GuiSwaggiumGenerator((TileEntitySwaggiumGenerator)tile, player.inventory);
        }
        return null;
    }

}