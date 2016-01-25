package fr.ftnt.swaggmod.common.blocks;

import fr.ftnt.swaggmod.common.SwaggMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;

public class BlockSwaggiumFence extends BlockPane
{

    public BlockSwaggiumFence()
    {
        super(SwaggMod.MODID + ":swaggium_bars", SwaggMod.MODID + ":swaggium_bars", Material.iron, true);
        this.setHardness(5.0F);
        this.setResistance(10.0F);
        this.setStepSound(Block.soundTypeMetal);
        this.setBlockName("fenceSwaggium");
    }

}
