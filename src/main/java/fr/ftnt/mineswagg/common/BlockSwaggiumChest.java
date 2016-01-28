package fr.ftnt.mineswagg.common;

import fr.ftnt.mineswagg.proxy.ClientProxy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

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
        this.setBlockTextureName(MineSwagg.MODID + ":swaggium_chest");
        this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
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

    public void breakBlock(World world, int x, int y, int z, Block block, int metadata)
    {
        TileEntitySwaggiumChest tileentityswaggiumchest = (TileEntitySwaggiumChest)world.getTileEntity(x, y, z);

        if(tileentityswaggiumchest != null)
        {
            for(int i1 = 0; i1 < tileentityswaggiumchest.getSizeInventory(); ++i1)
            {
                ItemStack itemstack = tileentityswaggiumchest.getStackInSlot(i1);

                if(itemstack != null)
                {
                    float f = world.rand.nextFloat() * 0.8F + 0.1F;
                    float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
                    EntityItem entityitem;

                    for(float f2 = world.rand.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; world.spawnEntityInWorld(entityitem))
                    {
                        int j1 = world.rand.nextInt(21) + 10;

                        if(j1 > itemstack.stackSize)
                        {
                            j1 = itemstack.stackSize;
                        }

                        itemstack.stackSize -= j1;
                        entityitem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));
                        float f3 = 0.05F;
                        entityitem.motionX = (double)((float)world.rand.nextGaussian() * f3);
                        entityitem.motionY = (double)((float)world.rand.nextGaussian() * f3 + 0.2F);
                        entityitem.motionZ = (double)((float)world.rand.nextGaussian() * f3);

                        if(itemstack.hasTagCompound())
                        {
                            entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                        }
                    }
                }
            }

            world.func_147453_f(x, y, z, block);
        }

        super.breakBlock(world, x, y, z, block, metadata);
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        if(world.isRemote)
        {
            return true;
        }
        else
        {
            player.openGui(MineSwagg.instance, 0, world, x, y, z);
            return true;
        }
    }

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase living, ItemStack stack)
    {
        if(stack.getItemDamage() == 0)
        {
            TileEntity tile = world.getTileEntity(x, y, z);
            if(tile instanceof TileEntitySwaggiumChest)
            {
                if(stack.hasDisplayName())
                {
                    int direction = MathHelper.floor_double((double)(living.rotationYaw * 4.0F - 360.0F) + 2.5D) & 3;
                    ((TileEntitySwaggiumChest)tile).setDirection((byte)direction);
                    ((TileEntitySwaggiumChest)tile).setCustomName(stack.getDisplayName());
                }
            }
        }
    }

    public void func_149954_e(World p_149954_1_, int p_149954_2_, int p_149954_3_, int p_149954_4_)
    {
        if (!p_149954_1_.isRemote)
        {
            Block block = p_149954_1_.getBlock(p_149954_2_, p_149954_3_, p_149954_4_ - 1);
            Block block1 = p_149954_1_.getBlock(p_149954_2_, p_149954_3_, p_149954_4_ + 1);
            Block block2 = p_149954_1_.getBlock(p_149954_2_ - 1, p_149954_3_, p_149954_4_);
            Block block3 = p_149954_1_.getBlock(p_149954_2_ + 1, p_149954_3_, p_149954_4_);
            boolean flag = true;
            int l;
            Block block4;
            int i1;
            Block block5;
            boolean flag1;
            byte b0;
            int j1;

            if (block != this && block1 != this)
            {
                if (block2 != this && block3 != this)
                {
                    b0 = 3;

                    if (block.func_149730_j() && !block1.func_149730_j())
                    {
                        b0 = 3;
                    }

                    if (block1.func_149730_j() && !block.func_149730_j())
                    {
                        b0 = 2;
                    }

                    if (block2.func_149730_j() && !block3.func_149730_j())
                    {
                        b0 = 5;
                    }

                    if (block3.func_149730_j() && !block2.func_149730_j())
                    {
                        b0 = 4;
                    }
                }
                else
                {
                    l = block2 == this ? p_149954_2_ - 1 : p_149954_2_ + 1;
                    block4 = p_149954_1_.getBlock(l, p_149954_3_, p_149954_4_ - 1);
                    i1 = block2 == this ? p_149954_2_ - 1 : p_149954_2_ + 1;
                    block5 = p_149954_1_.getBlock(i1, p_149954_3_, p_149954_4_ + 1);
                    b0 = 3;
                    flag1 = true;

                    if (block2 == this)
                    {
                        j1 = p_149954_1_.getBlockMetadata(p_149954_2_ - 1, p_149954_3_, p_149954_4_);
                    }
                    else
                    {
                        j1 = p_149954_1_.getBlockMetadata(p_149954_2_ + 1, p_149954_3_, p_149954_4_);
                    }

                    if (j1 == 2)
                    {
                        b0 = 2;
                    }

                    if ((block.func_149730_j() || block4.func_149730_j()) && !block1.func_149730_j() && !block5.func_149730_j())
                    {
                        b0 = 3;
                    }

                    if ((block1.func_149730_j() || block5.func_149730_j()) && !block.func_149730_j() && !block4.func_149730_j())
                    {
                        b0 = 2;
                    }
                }
            }
            else
            {
                l = block == this ? p_149954_4_ - 1 : p_149954_4_ + 1;
                block4 = p_149954_1_.getBlock(p_149954_2_ - 1, p_149954_3_, l);
                i1 = block == this ? p_149954_4_ - 1 : p_149954_4_ + 1;
                block5 = p_149954_1_.getBlock(p_149954_2_ + 1, p_149954_3_, i1);
                b0 = 5;
                flag1 = true;

                if (block == this)
                {
                    j1 = p_149954_1_.getBlockMetadata(p_149954_2_, p_149954_3_, p_149954_4_ - 1);
                }
                else
                {
                    j1 = p_149954_1_.getBlockMetadata(p_149954_2_, p_149954_3_, p_149954_4_ + 1);
                }

                if (j1 == 4)
                {
                    b0 = 4;
                }

                if ((block2.func_149730_j() || block4.func_149730_j()) && !block3.func_149730_j() && !block5.func_149730_j())
                {
                    b0 = 5;
                }

                if ((block3.func_149730_j() || block5.func_149730_j()) && !block2.func_149730_j() && !block4.func_149730_j())
                {
                    b0 = 4;
                }
            }

            p_149954_1_.setBlockMetadataWithNotify(p_149954_2_, p_149954_3_, p_149954_4_, b0, 3);
        }
    }
    
    public boolean rotateBlock(World world, int x, int y, int z, ForgeDirection axis)
    {
        if((axis == ForgeDirection.UP || axis == ForgeDirection.DOWN) && !world.isRemote && world.getBlockMetadata(x, y, z) == 0)
        {
            TileEntity tile = world.getTileEntity(x, y, z);
            if(tile instanceof TileEntitySwaggiumChest)
            {
                TileEntitySwaggiumChest tileSwaggiumChest = (TileEntitySwaggiumChest)tile;
                byte direction = tileSwaggiumChest.getDirection();
                direction++;
                if(direction > 3)
                {
                    direction = 0;
                }
                tileSwaggiumChest.setDirection(direction);
                return true;
            }
        }
        return false;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }

    public int getRenderType()
    {
        return ClientProxy.tesrRenderId;
    }

}
