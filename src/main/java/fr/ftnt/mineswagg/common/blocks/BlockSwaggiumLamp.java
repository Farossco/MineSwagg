package fr.ftnt.mineswagg.common.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.ftnt.mineswagg.common.MineSwagg;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockSwaggiumLamp extends BlockSwaggiumCompressed
{

    private final boolean lightedField;

    public BlockSwaggiumLamp(boolean lighted)
    {
        super();
        this.setBlockName("blockSwaggiumLamp");
        this.lightedField = lighted;

        if(lighted)
        {
            this.setLightLevel(1.0F);
            this.setCreativeTab(null);
            this.setBlockTextureName(MineSwagg.NAME + ":swaggium_lamp_on");
        }
        else
        {
            this.setCreativeTab(MineSwagg.customTab);
            this.setBlockTextureName(MineSwagg.NAME + ":swaggium_lamp_off");
        }
    }

    public void onBlockAdded(World world, int x, int y, int z)
    {
        if(!world.isRemote)
        {
            if(this.lightedField && !world.isBlockIndirectlyGettingPowered(x, y, z))
            {
                world.scheduleBlockUpdate(x, y, z, this, 4);
            }
            else if(!this.lightedField && world.isBlockIndirectlyGettingPowered(x, y, z))
            {
                world.setBlock(x, y, z, MineSwagg.blockSwaggiumLitLamp, 0, 2);
            }
        }
    }

    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
        if(!world.isRemote)
        {
            if(this.lightedField && !world.isBlockIndirectlyGettingPowered(x, y, z))
            {
                world.scheduleBlockUpdate(x, y, z, this, 4);
            }
            else if(!this.lightedField && world.isBlockIndirectlyGettingPowered(x, y, z))
            {
                world.setBlock(x, y, z, MineSwagg.blockSwaggiumLitLamp, 0, 2);
            }
        }
    }

    public void updateTick(World world, int x, int y, int z, Random random)
    {
        if(!world.isRemote && this.lightedField && !world.isBlockIndirectlyGettingPowered(x, y, z))
        {
            world.setBlock(x, y, z, MineSwagg.blockSwaggiumLamp, 0, 2);
        }
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World world, int x, int y, int z)
    {
        return Item.getItemFromBlock(MineSwagg.blockSwaggiumLamp);
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return Item.getItemFromBlock(MineSwagg.blockSwaggiumLamp);
    }

    protected ItemStack createStackedBlock(int p_149644_1_)
    {
        return new ItemStack(MineSwagg.blockSwaggiumLamp);
    }
}
