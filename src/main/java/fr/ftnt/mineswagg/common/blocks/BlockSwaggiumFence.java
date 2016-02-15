package fr.ftnt.mineswagg.common.blocks;

import fr.ftnt.mineswagg.common.MineSwagg;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;

public class BlockSwaggiumFence extends BlockPane
{

    public BlockSwaggiumFence()
    {
        super(MineSwagg.NAME + ":swaggium_bars", MineSwagg.NAME + ":swaggium_bars", Material.iron, true);
        this.setHardness(5.0F);
        this.setResistance(10.0F);
        this.setStepSound(Block.soundTypeMetal);
        this.setBlockName("fenceSwaggium");
        this.setCreativeTab(MineSwagg.customTab);
    }

}
