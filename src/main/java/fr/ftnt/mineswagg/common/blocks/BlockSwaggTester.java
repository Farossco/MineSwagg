package fr.ftnt.mineswagg.common.blocks;

import fr.ftnt.mineswagg.common.MineSwagg;
import fr.ftnt.mineswagg.common.MineSwaggExtendedEntity;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockSwaggTester extends Block
{
    public static int swaggAmount, swaggLevel, maxSwagg;

    public BlockSwaggTester()
    {
        super(Material.iron);
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setHardness(3.0F);
        this.setResistance(5.0F);
        this.setStepSound(Block.soundTypePiston);
        this.setBlockName("swaggTester");
        this.setBlockTextureName(MineSwagg.MODID + ":swaggium_block");
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float posX, float posY, float posZ)
    {
        MineSwaggExtendedEntity props = MineSwaggExtendedEntity.get(player);

        if(side == 1)
        {
            if(player.isSneaking())
            {
                props.addSwaggLevel(10);
            }
            else
            {
                props.addSwaggLevel(1);
            }

        }

        if(side == 0)
        {
            if(player.isSneaking())
            {
                props.consumeSwaggLevel(10);
            }
            else
            {
                props.consumeSwaggLevel(1);

            }
        }

        // System.out.println((!world.isRemote ? "Server: " : "Client: ") + "Swagg: " + props.getSwaggAmount());
        // System.out.println((!world.isRemote ? "Server: " : "Client: ") + "Swagg total: " + (props.getSwaggAmount() + props.getSwaggLevel() * props.getMaxSwagg()));
        // System.out.println((!world.isRemote ? "Server: " : "Client: ") + "Swagg level: " + props.getSwaggLevel() + "\n");

        // System.out.println("Side: " + side);

        return true;
    }
}