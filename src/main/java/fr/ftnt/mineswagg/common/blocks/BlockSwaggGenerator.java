package fr.ftnt.mineswagg.common.blocks;

import fr.ftnt.mineswagg.common.MineSwagg;
import fr.ftnt.mineswagg.common.tileentities.TileEntitySwaggGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockSwaggGenerator extends BlockContainer
{
    public BlockSwaggGenerator()
    {
        super(Material.iron);
    }

    @Override
    public boolean hasTileEntity(int metadata)
    {
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TileEntitySwaggGenerator();
    }

    public void breakBlock(World world, int x, int y, int z, Block block, int metadata)
    {
        TileEntity tileentity = world.getTileEntity(x, y, z);

        if(tileentity instanceof IInventory)
        {
            IInventory inventory = (IInventory)tileentity;

            ItemStack itemstack = inventory.getStackInSlot(0);

            if(itemstack != null)
            {
                while(itemstack.stackSize > 0)
                {
                    int j1 = world.rand.nextInt(21) + 10;

                    if(j1 > itemstack.stackSize)
                    {
                        j1 = itemstack.stackSize;
                    }

                    itemstack.stackSize -= j1;
                    EntityItem entityitem = new EntityItem(world, x, y, z, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

                    if(itemstack.hasTagCompound())
                    {
                        entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                    }

                    float f3 = 0.05F;
                    entityitem.motionX = (double)((float)world.rand.nextGaussian() * f3);
                    entityitem.motionY = (double)((float)world.rand.nextGaussian() * f3 + 0.2F);
                    entityitem.motionZ = (double)((float)world.rand.nextGaussian() * f3);
                    world.spawnEntityInWorld(entityitem);
                }
            }
            world.func_147453_f(x, y, z, block);
        }
        super.breakBlock(world, x, y, z, block, metadata);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
    {
        TileEntity tile = world.getTileEntity(x, y, z);
        if(tile instanceof TileEntitySwaggGenerator)
        {
            if(stack.hasDisplayName())
            {
                ((TileEntitySwaggGenerator)tile).setCustomName(stack.getDisplayName());
            }
        }
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float posX, float posY, float posZ)
    {
        if(world.isRemote)
        {
            return true;
        }
        else
        {
            TileEntitySwaggGenerator tileEntity = (TileEntitySwaggGenerator)world.getTileEntity(x, y, z);

            if(tileEntity != null)
            {
                player.openGui(MineSwagg.instance, 0, world, x, y, z);
            }
            return true;
        }
    }
}