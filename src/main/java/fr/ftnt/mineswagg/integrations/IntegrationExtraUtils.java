package fr.ftnt.mineswagg.integrations;

import com.rwtema.extrautils.ExtraUtils;
import com.rwtema.extrautils.block.BlockColor;
import com.rwtema.extrautils.helper.XUHelper;
import com.rwtema.extrautils.item.ItemBlockColor;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import fr.ftnt.mineswagg.common.MineSwagg;
import net.minecraft.block.BlockColored;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class IntegrationExtraUtils
{
    public static BlockColor blockSwaggiumCompressedColor;

    public static void init(FMLInitializationEvent event)
    {
        blockSwaggiumCompressedColor = new BlockColor(MineSwagg.blockSwaggiumCompressed, "swaggium block");
        ExtraUtils.registerBlock(blockSwaggiumCompressedColor, ItemBlockColor.class);
    }

    public static void registerRecipes()
    {
        for(int i = 0; i < 16; i++)
        {
            if(blockSwaggiumCompressedColor.oreName != null)
            {
                OreDictionary.registerOre(blockSwaggiumCompressedColor.oreName, new ItemStack(blockSwaggiumCompressedColor, 1, i));
                OreDictionary.registerOre(blockSwaggiumCompressedColor.oreName + XUHelper.dyes[i].substring(3), new ItemStack(blockSwaggiumCompressedColor, 1, i));
            }

            ExtraUtils.addRecipe(new ShapedOreRecipe(new ItemStack(blockSwaggiumCompressedColor, 7, BlockColored.func_150032_b(i)), new Object[] {"SSS", "SDS", "SPS", 'S', blockSwaggiumCompressedColor.baseBlock, 'D', XUHelper.dyes[i], 'P', ExtraUtils.paintBrush}));
            ExtraUtils.addRecipe(new ShapedOreRecipe(new ItemStack(blockSwaggiumCompressedColor, 7, BlockColored.func_150032_b(i)), new Object[] {"SSS", "SDS", "SPS", 'S', blockSwaggiumCompressedColor.baseBlock, 'D', XUHelper.dyes[i], 'P', ExtraUtils.paintBrush}));
        }
    }
}