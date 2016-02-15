package fr.ftnt.mineswagg.common.items;

import fr.ftnt.mineswagg.common.MineSwagg;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemSwaggiumChest extends ItemBlock
{
    public ItemSwaggiumChest(Block block)
    {
        super(block);
        setCreativeTab(MineSwagg.customTab);
    }
}
