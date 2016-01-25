package fr.ftnt.swaggmod.common.items;

import fr.ftnt.swaggmod.common.SwaggMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemSwaggiumDoor extends ItemDoor
{

    private Material doorMaterial;
    private static final String __OBFID = "CL_00000020";
    
    public ItemSwaggiumDoor()
    {
        super(Material.iron);
        this.setUnlocalizedName("doorSwaggium");
        this.setTextureName(SwaggMod.MODID + ":door_swaggium");
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
        if (p_77648_7_ != 1)
        {
            return false;
        }
        else
        {
            ++p_77648_5_;
            Block block;

            block = SwaggMod.BlockSwaggiumDoor;

            if (player.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, stack) && player.canPlayerEdit(p_77648_4_, p_77648_5_ + 1, p_77648_6_, p_77648_7_, stack))
            {
                if (!block.canPlaceBlockAt(world, p_77648_4_, p_77648_5_, p_77648_6_))
                {
                    return false;
                }
                else
                {
                    int i1 = MathHelper.floor_double((double)((player.rotationYaw + 180.0F) * 4.0F / 360.0F) - 0.5D) & 3;
                    placeDoorBlock(world, p_77648_4_, p_77648_5_, p_77648_6_, i1, block);
                    --stack.stackSize;
                    return true;
                }
            }
            else
            {
                return false;
            }
        }
    }
}
