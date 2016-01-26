package fr.ftnt.swaggmod.common;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

public class BlockSwaggiumChest extends Block
{

    protected BlockSwaggiumChest()
    {
        super(Material.iron);
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setHardness(3.0F);
        this.setResistance(5.0F);
        this.setStepSound(Block.soundTypePiston);
        this.setBlockName("blockSwaggiumChest");
        this.setBlockTextureName(SwaggMod.MODID + ":swaggium_chest");
    }

    @Override
    public boolean hasTileEntity(int metadata)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata)
    {
        return new TileEntitySwaggiumChest();
    }
    
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        if(world.isRemote)
            return true;
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileEntitySwaggiumChest)
        {
            TileEntitySwaggiumChest tileSwaggiumChest = (TileEntitySwaggiumChest)tile;
            if (side == 0)
            {
                tileSwaggiumChest.decrease();
            }else if (side == 1){
                tileSwaggiumChest.increase();
            }
            
            player.addChatMessage(new ChatComponentTranslation("chat.SwaggiumChest.number", tileSwaggiumChest.getNumber()));
            
            return true;
        }
        return false;
    }

}
