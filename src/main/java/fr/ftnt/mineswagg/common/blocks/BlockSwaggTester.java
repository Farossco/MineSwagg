package fr.ftnt.mineswagg.common.blocks;

import fr.ftnt.mineswagg.common.MineSwaggExtendedEntity;
import fr.ftnt.mineswagg.common.MineSwagg;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockSwaggTester extends Block
{
    public int SwaggLevel = 0;

    public BlockSwaggTester()
    {
        super(Material.iron);
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setHardness(3.0F);
        this.setResistance(5.0F);
        this.setStepSound(Block.soundTypePiston);
        this.setBlockName("testerSwagg");
        this.setBlockTextureName(MineSwagg.MODID + ":swaggium_block");
        // this.setBlockTextureName(MineSwagg.MODID + ":swagg_tester");
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float posX, float posY, float posZ)
    {
        MineSwaggExtendedEntity props = MineSwaggExtendedEntity.get(player);

        if(!world.isRemote)
        {
            if(side == 1)
                props.addSwagg(5);

            if(side == 3)
                System.out.println("Swagg: " + props.getSwaggAmount());

            //System.out.println("Side: " + side);
        }
        return true;
    }
}
