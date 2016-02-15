package fr.ftnt.mineswagg.common.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.ftnt.mineswagg.common.MineSwagg;
import fr.ftnt.mineswagg.common.tileentities.TileEntitySwaggiumGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSwaggiumGenerator extends BlockContainer
{
    private IIcon blockIconFront;
    private IIcon blockIconTop;
    private final boolean isGenerating;
    private static boolean field_149934_M;

    public BlockSwaggiumGenerator(boolean isGenerating)
    {
        super(Material.iron);
        this.isGenerating = isGenerating;
        this.setHardness(4.5F);
        this.setResistance(4.5F);
        this.setStepSound(soundTypePiston);
        this.setBlockName("swaggiumGenerator");
        this.setBlockTextureName(MineSwagg.NAME + ":swaggium_generator");

        if(isGenerating)
        {
            this.setLightLevel(0.875F);
        }
        else
        {
            this.setCreativeTab(MineSwagg.customTab);
        }
    }

    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess block, int x, int y, int z)
    {
        return 0xffffff;
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return Item.getItemFromBlock(MineSwagg.blockSwaggiumGenerator);
    }

    public void onBlockAdded(World world, int x, int y, int z)
    {
        super.onBlockAdded(world, x, y, z);
        this.func_149930_e(world, x, y, z);
    }

    private void func_149930_e(World world, int x, int y, int z)
    {
        if(!world.isRemote)
        {
            Block block = world.getBlock(x, y, z - 1);
            Block block1 = world.getBlock(x, y, z + 1);
            Block block2 = world.getBlock(x - 1, y, z);
            Block block3 = world.getBlock(x + 1, y, z);
            byte b0 = 3;

            if(block.func_149730_j() && !block1.func_149730_j())
            {
                b0 = 3;
            }

            if(block1.func_149730_j() && !block.func_149730_j())
            {
                b0 = 2;
            }

            if(block2.func_149730_j() && !block3.func_149730_j())
            {
                b0 = 5;
            }

            if(block3.func_149730_j() && !block2.func_149730_j())
            {
                b0 = 4;
            }

            world.setBlockMetadataWithNotify(x, y, z, b0, 2);
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
        return side == 1 ? this.blockIconTop : (side == 0 ? this.blockIconTop : (metadata == 2 && side == 2 ? this.blockIconFront : (metadata == 3 && side == 5 ? this.blockIconFront : (metadata == 0 && side == 3 ? this.blockIconFront : (metadata == 1 && side == 4 ? this.blockIconFront : this.blockIcon)))));
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        this.blockIcon = register.registerIcon(MineSwagg.NAME + ":swaggium_generator_side");
        this.blockIconFront = register.registerIcon(MineSwagg.NAME + ":swaggium_generator_" + (this.isGenerating ? "on" : "off"));
        this.blockIconTop = register.registerIcon(MineSwagg.NAME + ":swaggium_generator_top");
    }

    @Override
    public boolean hasTileEntity(int metadata)
    {
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TileEntitySwaggiumGenerator();
    }

    public void breakBlock(World world, int x, int y, int z, Block block, int metadata)
    {
        if(!field_149934_M)
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
        }
        super.breakBlock(world, x, y, z, block, metadata);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
    {
        int l = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
        world.setBlockMetadataWithNotify(x, y, z, l, 2);

        System.out.println(world.getBlockMetadata(x, y, z));

        TileEntity tile = world.getTileEntity(x, y, z);
        TileEntitySwaggiumGenerator tileSwaggium;
        if(tile instanceof TileEntitySwaggiumGenerator)
        {
            tileSwaggium = (TileEntitySwaggiumGenerator)tile;
            if(stack.hasDisplayName())
            {
                (tileSwaggium).setCustomName(stack.getDisplayName());
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
            TileEntity tileEntity = world.getTileEntity(x, y, z);
            TileEntitySwaggiumGenerator tileSwaggium;
            if(tileEntity instanceof TileEntitySwaggiumGenerator)
            {
                tileSwaggium = (TileEntitySwaggiumGenerator)tileEntity;
                if(tileSwaggium != null)
                {
                    player.openGui(MineSwagg.instance, 0, world, x, y, z);
                }
            }
            return true;
        }
    }

    public static void updateSwaggGeneratorBlockState(boolean isGenerating, World world, int x, int y, int z)
    {
        int l = world.getBlockMetadata(x, y, z);
        TileEntity tileentity = world.getTileEntity(x, y, z);
        field_149934_M = true;

        if(isGenerating)
        {
            world.setBlock(x, y, z, MineSwagg.blockSwaggiumLitGenerator);
        }
        else
        {
            world.setBlock(x, y, z, MineSwagg.blockSwaggiumGenerator);
        }

        field_149934_M = false;
        world.setBlockMetadataWithNotify(x, y, z, l, 2);

        if(tileentity != null)
        {
            tileentity.validate();
            world.setTileEntity(x, y, z, tileentity);
        }
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World world, int x, int y, int z)
    {
        return Item.getItemFromBlock(MineSwagg.blockSwaggiumGenerator);
    }
}