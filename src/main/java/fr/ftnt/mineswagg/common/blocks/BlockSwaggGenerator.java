package fr.ftnt.mineswagg.common.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockSwaggGenerator extends BlockContainer
{

    protected BlockSwaggGenerator(Material material)
    {
        super(material);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_)
    {
        return null;
    }
}