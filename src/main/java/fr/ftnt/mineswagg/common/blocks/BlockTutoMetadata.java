package fr.ftnt.mineswagg.common.blocks;

import java.util.List;

import fr.ftnt.mineswagg.common.MineSwagg;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockTutoMetadata extends Block
{

    public static String[] textureName = new String[] {"block1", "block2", "block3", "block4"};
    public IIcon[] iconArray = new IIcon[textureName.length];

    public BlockTutoMetadata()
    {
        super(Material.rock);
        this.setBlockName("metadataTuto");
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
    }

    public int damageDropped(int metadata)
    {
        return metadata;
    }

    public void registerBlockIcons(IIconRegister register)
    {
        for(int i = 0; i < textureName.length; i++)
            this.iconArray[i] = register.registerIcon(MineSwagg.MODID + ":" + textureName[i]);
    }

    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
        for(int i = 0; i < textureName.length; i++)
            list.add(new ItemStack(item, 1, i));

    }

    public IIcon getIcon(int side, int metadata)
    {
        return((metadata >= 0 && metadata < textureName.length) ? this.iconArray[metadata] : this.iconArray[0]);

    }

}
