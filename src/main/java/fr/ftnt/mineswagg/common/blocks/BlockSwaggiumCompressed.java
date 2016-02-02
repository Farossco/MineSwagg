package fr.ftnt.mineswagg.common.blocks;

import fr.ftnt.mineswagg.common.MineSwagg;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCompressed;
import net.minecraft.block.material.MapColor;

public class BlockSwaggiumCompressed extends BlockCompressed
{

    public BlockSwaggiumCompressed()
    {
        super(MapColor.lapisColor);
        this.setHardness(5.0F);
        this.setResistance(10.0F);
        this.setStepSound(Block.soundTypeMetal);
        this.setBlockName("blockSwaggium");
        this.setBlockTextureName(MineSwagg.MODID + ":swaggium_block");
    }

}