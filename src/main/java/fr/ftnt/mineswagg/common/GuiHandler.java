package fr.ftnt.mineswagg.common;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.ironchest.ContainerIronChest;
import cpw.mods.ironchest.IronChestType;
import cpw.mods.ironchest.TileEntityIronChest;
import fr.ftnt.mineswagg.common.tileentities.TileEntitySwaggiumChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler
{
    public void registerRenderInformation()
    {

    }

    public void registerTileEntitySpecialRenderer(IronChestType typ)
    {

    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        return null;
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int X, int Y, int Z)
    {
        TileEntity te = world.getTileEntity(X, Y, Z);
        if (te != null && te instanceof TileEntitySwaggiumChest)
        {
            TileEntitySwaggiumChest icte = (TileEntitySwaggiumChest) te;
            return new ContainerIronChest(player.inventory, icte, icte.getType(), 0, 0);
        }
        else
        {
            return null;
        }
    }

    public World getClientWorld()
    {
        return null;
    }

}
