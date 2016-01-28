package fr.ftnt.mineswagg.common.items.itemBlocks;

import fr.ftnt.mineswagg.common.blocks.BlockTutoMetadata;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemBlockSwaggiumMetadata extends ItemBlock
{

    public ItemBlockSwaggiumMetadata(Block block)
    {
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    public IIcon getIconFromDamage(int metadata)
    {
        return this.field_150939_a.getIcon(2, metadata);
    }
    
    public int getMetadata(int metadata)
    {
        return metadata;
    }
    
    public String getUnlocalizedName(ItemStack stack)
    {
        int metadata = stack.getItemDamage();

        if (metadata < 0 || metadata >= BlockTutoMetadata.textureName.length)
        {
            metadata = 0;
        }

        return super.getUnlocalizedName() + "." + BlockTutoMetadata.textureName[metadata];
    }
}
