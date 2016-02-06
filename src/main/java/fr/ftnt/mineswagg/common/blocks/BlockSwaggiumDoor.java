package fr.ftnt.mineswagg.common.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.ftnt.mineswagg.common.MineSwagg;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class BlockSwaggiumDoor extends BlockDoor
{

    public BlockSwaggiumDoor()
    {
        super(Material.iron);
        this.setHardness(5.0F);
        this.setStepSound(Block.soundTypeMetal);
        this.setBlockName("doorSwaggium");
        this.setBlockTextureName(MineSwagg.MODID + ":door_swaggium");
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return (p_149650_1_ & 8) != 0 ? null : (MineSwagg.itemSwaggiumDoor);
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
    {
        return MineSwagg.itemSwaggiumDoor;
    }
}
