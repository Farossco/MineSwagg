package fr.ftnt.mineswagg.common;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.ftnt.mineswagg.common.blocks.BlockSwaggiumCompressed;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockSwaggiumCompressedGlow extends BlockSwaggiumCompressed
{

    private final boolean lightedField;
    
    public BlockSwaggiumCompressedGlow(boolean lighted)
    {
        super();
        this.setBlockName("blockSwaggiumLamp");
        this.lightedField = lighted;

        if (lighted)
        {
            this.setLightLevel(1.0F);
        }
    }

    public void onBlockAdded(World world, int x, int y, int z)
    {
        if (!world.isRemote)
        {
            if (this.lightedField && !world.isBlockIndirectlyGettingPowered(x, y, z))
            {
                world.scheduleBlockUpdate(x, y, z, this, 4);
            }
            else if (!this.lightedField && world.isBlockIndirectlyGettingPowered(x, y, z))
            {
                world.setBlock(x, y, z, MineSwagg.blockSwaggiumLitCompressedGlow, 0, 2);
            }
        }
    }

    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
        if (!world.isRemote)
        {
            if (this.lightedField && !world.isBlockIndirectlyGettingPowered(x, y, z))
            {
                world.scheduleBlockUpdate(x, y, z, this, 4);
            }
            else if (!this.lightedField && world.isBlockIndirectlyGettingPowered(x, y, z))
            {
                world.setBlock(x, y, z, MineSwagg.blockSwaggiumLitCompressedGlow, 0, 2);
            }
        }
    }

    public void updateTick(World world, int x, int y, int z, Random random)
    {
        if (!world.isRemote && this.lightedField && !world.isBlockIndirectlyGettingPowered(x, y, z))
        {
            world.setBlock(x, y, z, MineSwagg.blockSwaggiumCompressedGlow, 0, 2);
        }
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World world, int x, int y, int z)
    {
        return Item.getItemFromBlock(MineSwagg.blockSwaggiumCompressedGlow);
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return Item.getItemFromBlock(MineSwagg.blockSwaggiumCompressedGlow);
    }
    
    protected ItemStack createStackedBlock(int p_149644_1_)
    {
        return new ItemStack(MineSwagg.blockSwaggiumCompressedGlow);
    }
}

