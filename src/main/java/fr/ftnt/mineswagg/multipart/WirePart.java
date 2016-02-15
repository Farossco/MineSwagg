package fr.ftnt.mineswagg.multipart;

import codechicken.lib.vec.Cuboid6;
import codechicken.multipart.minecraft.McMetaPart;
import fr.ftnt.mineswagg.common.MineSwagg;
import fr.ftnt.mineswagg.common.blocks.BlockSwaggiumCompressed;
import net.minecraft.block.Block;

public class WirePart extends McMetaPart
{
    public static BlockSwaggiumCompressed blockSwaggiumCompressed = (BlockSwaggiumCompressed)MineSwagg.blockSwaggiumCompressed;

    public WirePart()
    {

    }

    @Override
    public Cuboid6 getBounds()
    {
        return new Cuboid6(0.2, 0.2, 0.2, 0.7, 0.7, 0.7);
    }

    @Override
    public Block getBlock()
    {
        return blockSwaggiumCompressed;
    }

    @Override
    public String getType()
    {
        return "MineSwagg_mptest";
    }

}
