package fr.ftnt.mineswagg.common;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.ironchest.IronChest;
import cpw.mods.ironchest.IronChestType;
import cpw.mods.ironchest.TileEntityIronChest;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockSwaggiumChest extends BlockContainer
{
    public BlockSwaggiumChest()
    {
        super(Material.iron);
        setBlockName("SwaggiumChest");
        setHardness(3.0F);
        setBlockBounds(0.0625F, 0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
        setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int i1, float f1, float f2, float f3)
    {
        TileEntity te = world.getTileEntity(i, j, k);

        if (te == null || !(te instanceof TileEntityIronChest))
        {
            return true;
        }

        if (world.isSideSolid(i, j + 1, k, ForgeDirection.DOWN))
        {
            return true;
        }

        if (world.isRemote)
        {
            return true;
        }

        player.openGui(IronChest.instance, ((TileEntityIronChest) te).getType().ordinal(), world, i, j, k);
        return true;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
        list.add(new ItemStack(item, 1, 0));
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata)
    {
        return IronChestType.makeEntity(metadata);
    }

    @Override
    public TileEntity createNewTileEntity(World w, int i)
    {
        return null;
    }
}
