package fr.ftnt.mineswagg.common.blocks;

import java.util.Random;

import fr.ftnt.mineswagg.common.MineSwagg;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class BlockSwaggiumOre extends BlockOre
{
    public BlockSwaggiumOre()
    {
        super();
        this.setCreativeTab(MineSwagg.customTab);
        this.setHardness(3.0F);
        this.setResistance(5.0F);
        this.setStepSound(Block.soundTypePiston);
        this.setBlockName("oreSwaggium");
        this.setBlockTextureName(MineSwagg.NAME + ":swaggium_ore");
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return Item.getItemFromBlock(this);
    }

    public int quantityDropped(Random p_149745_1_)
    {
        return 1;
    }

    public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_)
    {
        super.dropBlockAsItemWithChance(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, p_149690_5_, p_149690_6_, p_149690_7_);
    }

    public int damageDropped(int p_149692_1_)
    {
        return 0;
    }

}
