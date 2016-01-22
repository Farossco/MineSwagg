package fr.ftnt.swaggmod.common;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class BlockDoorSwaggium extends BlockDoor
{

    protected BlockDoorSwaggium(Material material)
    {
        super(material);
        float f = 0.5F;
        float f1 = 1.0F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
    }
    
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return (p_149650_1_ & 8) != 0 ? null : (SwaggMod.itemDoorSwaggium);
    }
    
    @SideOnly(Side.CLIENT)
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
    {
        return SwaggMod.itemDoorSwaggium;
    }
}
