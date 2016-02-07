package fr.ftnt.mineswagg.common.guiHandlers;

import cpw.mods.fml.common.network.IGuiHandler;
import fr.ftnt.mineswagg.client.GuiSwaggGenerator;
import fr.ftnt.mineswagg.common.containers.ContainerSwaggGenerator;
import fr.ftnt.mineswagg.common.tileentities.TileEntitySwaggGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandlerSwaggGenerator implements IGuiHandler
{

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity tile = world.getTileEntity(x, y, z);
        if(tile instanceof TileEntitySwaggGenerator)
        {
            return new ContainerSwaggGenerator((TileEntitySwaggGenerator)tile, player.inventory);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity tile = world.getTileEntity(x, y, z);
        if(tile instanceof TileEntitySwaggGenerator)
        {
            return new GuiSwaggGenerator((TileEntitySwaggGenerator)tile, player.inventory);
        }
        return null;
    }

}