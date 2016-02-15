package fr.ftnt.mineswagg.multipart;

import java.util.Arrays;

import codechicken.lib.vec.BlockCoord;
import codechicken.multipart.MultiPartRegistry;
import codechicken.multipart.MultiPartRegistry.IPartConverter;
import codechicken.multipart.MultiPartRegistry.IPartFactory;
import codechicken.multipart.TMultiPart;
import fr.ftnt.mineswagg.common.MineSwagg;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public class RegisterBlockPart implements IPartFactory, IPartConverter
{
    @Override
    public TMultiPart createPart(String name, boolean client)
    {
        if(name.equals("MineSwagg_mptest"))
            return new WirePart();

        return null;
    }

    public void init()
    {
        MultiPartRegistry.registerConverter(this);
        MultiPartRegistry.registerParts(this, new String[] {"MineSwagg_mptest"});
    }

    @Override
    public Iterable<Block> blockTypes()
    {
        return Arrays.asList(MineSwagg.blockSwaggiumCompressed);
    }

    @Override
    public TMultiPart convert(World world, BlockCoord pos)
    {
        Block block = world.getBlock(pos.x, pos.y, pos.z);
        if(block == MineSwagg.blockSwaggiumCompressed)
            return new WirePart();

        return null;
    }
}