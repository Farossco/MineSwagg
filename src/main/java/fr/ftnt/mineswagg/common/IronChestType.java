package fr.ftnt.mineswagg.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.ironchest.BlockIronChest;
import cpw.mods.ironchest.TileEntityCopperChest;
import cpw.mods.ironchest.TileEntityCrystalChest;
import cpw.mods.ironchest.TileEntityDiamondChest;
import cpw.mods.ironchest.TileEntityDirtChest;
import cpw.mods.ironchest.TileEntityGoldChest;
import cpw.mods.ironchest.TileEntityIronChest;
import cpw.mods.ironchest.TileEntityObsidianChest;
import cpw.mods.ironchest.TileEntitySilverChest;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.util.IIcon;
import net.minecraftforge.oredict.ShapedOreRecipe;

public enum IronChestType
{
    DIAMOND(108, 12, true, "Diamond Chest", "diamondchest.png", 2, Arrays.asList("gemDiamond"), TileEntityDiamondChest.class, "GGGmPmGGG", "GGGG4Gmmm");
    int size;
    private int rowLength;
    public String friendlyName;
    private boolean tieredChest;
    private String modelTexture;
    private int textureRow;
    public Class<? extends TileEntityIronChest> clazz;
    private String[] recipes;
    private ArrayList<String> matList;
    private Item itemFilter;

    IronChestType(int size, int rowLength, boolean tieredChest, String friendlyName, String modelTexture, int textureRow, List<String> mats, Class<? extends TileEntityIronChest> clazz, String... recipes)
    {
        this(size, rowLength, tieredChest, friendlyName, modelTexture, textureRow, mats, clazz, (Item)null, recipes);
    }

    IronChestType(int size, int rowLength, boolean tieredChest, String friendlyName, String modelTexture, int textureRow, List<String> mats, Class<? extends TileEntityIronChest> clazz, Item itemFilter, String... recipes)
    {
        this.size = size;
        this.rowLength = rowLength;
        this.tieredChest = tieredChest;
        this.friendlyName = friendlyName;
        this.modelTexture = modelTexture;
        this.textureRow = textureRow;
        this.clazz = clazz;
        this.itemFilter = itemFilter;
        this.recipes = recipes;
        this.matList = new ArrayList<String>();
        matList.addAll(mats);
    }

    public String getModelTexture()
    {
        return modelTexture;
    }

    public int getTextureRow()
    {
        return textureRow;
    }

    public static TileEntityIronChest makeEntity(int metadata)
    {
        // Compatibility
        int chesttype = validateMeta(metadata);
        if(chesttype == metadata)
        {
            try
            {
                TileEntityIronChest te = values()[chesttype].clazz.newInstance();
                return te;
            }
            catch(InstantiationException e)
            {
                // unpossible
                e.printStackTrace();
            }
            catch(IllegalAccessException e)
            {
                // unpossible
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void registerBlocksAndRecipes(BlockIronChest blockResult)
    {
        Object previous = "chestWood";
        for(IronChestType typ : values())
        {
            generateRecipesForType(blockResult, previous, typ);
            ItemStack chest = new ItemStack(blockResult, 1, typ.ordinal());
            if(typ.isValidForCreativeMode())
                GameRegistry.registerCustomItemStack(typ.friendlyName, chest);
            if(typ.tieredChest)
                previous = chest;
        }
    }

    public static void generateRecipesForType(BlockIronChest blockResult, Object previousTier, IronChestType type)
    {
        for(String recipe : type.recipes)
        {
            String[] recipeSplit = new String[] {recipe.substring(0, 3), recipe.substring(3, 6), recipe.substring(6, 9)};
            Object mainMaterial = null;
            for(String mat : type.matList)
            {
                mainMaterial = translateOreName(mat);
                addRecipe(new ItemStack(blockResult, 1, type.ordinal()), recipeSplit, 'm', mainMaterial, 'P', previousTier, /* previous tier of chest */
                'G', "blockGlass", 'C', "chestWood", '0', new ItemStack(blockResult, 1, 0), /* Iron Chest */
                '1', new ItemStack(blockResult, 1, 1), /* Gold Chest */
                '2', new ItemStack(blockResult, 1, 2), /* Diamond Chest */
                '3', new ItemStack(blockResult, 1, 3), /* Copper Chest */
                '4', new ItemStack(blockResult, 1, 4) /* Silver Chest */
                );
            }
        }
    }

    public static Object translateOreName(String mat)
    {
        if(mat.equals("obsidian"))
        {
            return Blocks.obsidian;
        }
        else if(mat.equals("dirt"))
        {
            return Blocks.dirt;
        }
        return mat;
    }

    public static void addRecipe(ItemStack is, Object... parts)
    {
        ShapedOreRecipe oreRecipe = new ShapedOreRecipe(is, parts);
        GameRegistry.addRecipe(oreRecipe);
    }

    public int getRowCount()
    {
        return size / rowLength;
    }

    public int getRowLength()
    {
        return rowLength;
    }

    public boolean isTransparent()
    {
        return false;
    }

    public List<String> getMatList()
    {
        return matList;
    }

    public static int validateMeta(int i)
    {
        if(i < values().length && values()[i].size > 0)
        {
            return i;
        }
        else
        {
            return 0;
        }
    }

    public boolean isValidForCreativeMode()
    {
        return validateMeta(ordinal()) == ordinal();
    }

    public boolean isExplosionResistant()
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    @SideOnly(Side.CLIENT)
    public void makeIcons(IIconRegister par1IconRegister)
    {
        if(isValidForCreativeMode())
        {
            icons = new IIcon[3];
            int i = 0;
            for(String s : sideNames)
            {
                icons[i++] = par1IconRegister.registerIcon(String.format("ironchest:%s_%s", name().toLowerCase(), s));
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side)
    {

        return icons[sideMapping[side]];
    }

    private static String[] sideNames = {"top", "front", "side"};
    private static int[] sideMapping = {0, 0, 2, 1, 2, 2, 2};

    public Slot makeSlot(IInventory chestInventory, int index, int x, int y)
    {
        return new ValidatingSlot(chestInventory, index, x, y, this);
    }

    public boolean acceptsStack(ItemStack itemstack)
    {
        return itemFilter == null || itemstack == null || itemstack.getItem() == itemFilter;
    }

    public void adornItemDrop(ItemStack item)
    {

    }
}
