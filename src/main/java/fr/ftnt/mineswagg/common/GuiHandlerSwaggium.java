package fr.ftnt.mineswagg.common;

import cpw.mods.fml.common.network.IGuiHandler;
import fr.ftnt.mineswagg.client.GUISwaggiumChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandlerSwaggium implements IGuiHandler
{

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileEntitySwaggiumChest)
        {
            return new ContainerSwaggiumChest((TileEntitySwaggiumChest)tile, player.inventory);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileEntitySwaggiumChest)
        {
            return new GUISwaggiumChest((TileEntitySwaggiumChest)tile, player.inventory);
        }
        return null;
    }

}
