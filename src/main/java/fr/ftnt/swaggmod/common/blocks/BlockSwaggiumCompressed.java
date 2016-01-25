package fr.ftnt.swaggmod.common.blocks;

import fr.ftnt.swaggmod.common.SwaggMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCompressed;
import net.minecraft.block.material.MapColor;

public class BlockSwaggiumCompressed extends BlockCompressed
{

    public BlockSwaggiumCompressed(MapColor color)
    {
        super(color);
        this.setHardness(5.0F);
        this.setResistance(10.0F);
        this.setStepSound(Block.soundTypeMetal);
        this.setBlockName("blockSwaggium");
        this.setBlockTextureName(SwaggMod.MODID + ":swaggium_block");
    }

}
