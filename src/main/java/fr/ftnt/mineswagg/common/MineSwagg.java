package fr.ftnt.mineswagg.common;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.ironchest.ItemIronChest;
import fr.ftnt.mineswagg.common.blocks.BlockSwaggiumCompressed;
import fr.ftnt.mineswagg.common.blocks.BlockSwaggiumDoor;
import fr.ftnt.mineswagg.common.blocks.BlockSwaggiumFence;
import fr.ftnt.mineswagg.common.blocks.BlockSwaggiumOre;
import fr.ftnt.mineswagg.common.blocks.BlockTutoMetadata;
import fr.ftnt.mineswagg.common.entities.EntitySwagged;
import fr.ftnt.mineswagg.common.items.ItemSwaggiumArmor;
import fr.ftnt.mineswagg.common.items.ItemSwaggiumAxe;
import fr.ftnt.mineswagg.common.items.ItemSwaggiumDoor;
import fr.ftnt.mineswagg.common.items.ItemSwaggiumHoe;
import fr.ftnt.mineswagg.common.items.ItemSwaggiumPickaxe;
import fr.ftnt.mineswagg.common.items.ItemSwaggiumShovel;
import fr.ftnt.mineswagg.common.items.ItemSwaggiumSword;
import fr.ftnt.mineswagg.common.items.itemSwaggiumIngot;
import fr.ftnt.mineswagg.common.items.itemBlocks.ItemBlockSwaggiumMetadata;
import fr.ftnt.mineswagg.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

@Mod(modid = MineSwagg.MODID, name = MineSwagg.NAME)
public class MineSwagg
{

    public static final String MODID = "MineSwagg";
    public static final String NAME = "MineSwagg";
    public static final String VERSION = "1.0.0";
    public static final String BUILD = "1";

    @Instance(MODID)
    public static MineSwagg instance;

    @SidedProxy(clientSide = "fr.ftnt.mineswagg.proxy.ClientProxy", serverSide = "fr.ftnt.mineswagg.proxy.CommonProxy")
    public static CommonProxy proxy;

    // Items declaration
    public static Item itemSwaggiumIngot; // Ingot
    public static Item itemSwaggiumHelmet, itemSwaggiumChestplate, itemSwaggiumLeggings, itemSwaggiumBoots; // Armor
    public static Item itemSwaggiumDoor /* , itemArmorHorseSwaggium */; // Miscellaneous
    public static Item ItemSwaggiumAxe, ItemSwaggiumSword, ItemSwaggiumPickaxe, ItemSwaggiumShovel, ItemSwaggiumHoe; // Tools

    // Blocks declaration
    public static Block blockSwaggiumOre, blockSwaggiumCompressed, BlockSwaggiumDoor, blockSwaggiumFence; // Basic Blocks
    public static Block blockTutoMetadata; // Tuto Blocks
    public static Block blockSwaggiumChest; // Iron Chest integration

    // Materials Declaration
    public static ArmorMaterial armorSwaggium = EnumHelper.addArmorMaterial("armorSwaggium", 15, new int[] {2, 6, 5, 2}, 30);
    public static ToolMaterial toolSwaggium = EnumHelper.addToolMaterial("toolSwaggium", 2, 60, 20.0F, 4.0F, 30);

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        Version.init(event.getVersionProperties());
        event.getModMetadata().version = Version.fullVersionString();
        
        // --------------------------- Items ---------------------------
        // Basic Items
        itemSwaggiumIngot = new itemSwaggiumIngot();
        // Armor
        itemSwaggiumHelmet = new ItemSwaggiumArmor(0);
        itemSwaggiumChestplate = new ItemSwaggiumArmor(1);
        itemSwaggiumLeggings = new ItemSwaggiumArmor(2);
        itemSwaggiumBoots = new ItemSwaggiumArmor(3);
        // Tools
        ItemSwaggiumAxe = new ItemSwaggiumAxe();
        ItemSwaggiumSword = new ItemSwaggiumSword();
        ItemSwaggiumPickaxe = new ItemSwaggiumPickaxe();
        ItemSwaggiumShovel = new ItemSwaggiumShovel();
        ItemSwaggiumHoe = new ItemSwaggiumHoe();
        // Miscellaneous
        itemSwaggiumDoor = new ItemSwaggiumDoor();

        // Registering
        GameRegistry.registerItem(itemSwaggiumIngot, "item_swaggium_ingot");
        GameRegistry.registerItem(itemSwaggiumHelmet, "item_swaggium_helmet");
        GameRegistry.registerItem(itemSwaggiumChestplate, "item_swaggium_chestplate");
        GameRegistry.registerItem(itemSwaggiumLeggings, "item_swaggium_leggings");
        GameRegistry.registerItem(itemSwaggiumBoots, "item_swaggium_boots");
        GameRegistry.registerItem(ItemSwaggiumAxe, "item_swaggium_axe");
        GameRegistry.registerItem(ItemSwaggiumSword, "item_swaggium_sword");
        GameRegistry.registerItem(ItemSwaggiumPickaxe, "item_swaggium_pickaxe");
        GameRegistry.registerItem(ItemSwaggiumShovel, "item_swaggium_shovel");
        GameRegistry.registerItem(ItemSwaggiumHoe, "item_swaggium_hoe");
        GameRegistry.registerItem(itemSwaggiumDoor, "item_door_swaggium");

        // --------------------------- Blocks ---------------------------
        // Basic blocks
        blockSwaggiumOre = new BlockSwaggiumOre();
        blockSwaggiumCompressed = new BlockSwaggiumCompressed();
        BlockSwaggiumDoor = new BlockSwaggiumDoor();
        blockSwaggiumFence = new BlockSwaggiumFence();
        // Tuto Blocks
        blockTutoMetadata = new BlockTutoMetadata();
        // Iron Chest integration

        // Registering
        GameRegistry.registerBlock(blockSwaggiumOre, "block_swaggium");
        GameRegistry.registerBlock(blockSwaggiumCompressed, "block_antiswagger");
        GameRegistry.registerBlock(BlockSwaggiumDoor, "block_swaggium_door");
        GameRegistry.registerBlock(blockSwaggiumFence, "block_swaggium_fence");
        GameRegistry.registerBlock(blockTutoMetadata, ItemBlockSwaggiumMetadata.class, "block_tuto_metadata");

        if(Loader.isModLoaded("IronChest"))
        {
            blockSwaggiumChest = new BlockSwaggiumChest();
            GameRegistry.registerBlock(blockSwaggiumChest, ItemIronChest.class, "block_swaggium_chest");
        }

        OreDictionary.registerOre("ingotSwaggium", itemSwaggiumIngot);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // --------------------------- Entities ---------------------------
        addEntity(EntitySwagged.class, "swagged", 420, 0xd4192b, 0x522dbc);
        MinecraftForge.EVENT_BUS.register(new LivingEventHandler());
        proxy.registerRender();

        // --------------------------- TileEntities ---------------------------
        // GameRegistry.registerTileEntity(fr.ftnt.mineswagg.common.tileentities.TileEntitySwaggiumChest.class, MODID + ":SwaggiumChest");

        // --------------------------- GUI ---------------------------
        // NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandlerSwaggium());
        registerRecipes();
        
        
        if(Loader.isModLoaded("ironchest"))
        {
            blockSwaggiumChest = new BlockSwaggiumChest();
            GameRegistry.registerBlock(blockSwaggiumChest, "block_swaggium_chest");
        }

    }

    private void registerRecipes()
    {
        GameRegistry.addRecipe(new ItemStack(itemSwaggiumHelmet), new Object[] {"III", "I I", 'I', itemSwaggiumIngot});
        GameRegistry.addRecipe(new ItemStack(itemSwaggiumChestplate), new Object[] {"I I", "III", "III", 'I', itemSwaggiumIngot});
        GameRegistry.addRecipe(new ItemStack(itemSwaggiumLeggings), new Object[] {"III", "I I", "I I", 'I', itemSwaggiumIngot});
        GameRegistry.addRecipe(new ItemStack(itemSwaggiumBoots), new Object[] {"I I", "I I", 'I', itemSwaggiumIngot});
        GameRegistry.addRecipe(new ItemStack(ItemSwaggiumAxe), new Object[] {"II ", "IS ", " S ", 'I', itemSwaggiumIngot, 'S', Items.stick});
        GameRegistry.addRecipe(new ItemStack(ItemSwaggiumSword), new Object[] {" I ", " I ", " S ", 'I', itemSwaggiumIngot, 'S', Items.stick});
        GameRegistry.addRecipe(new ItemStack(ItemSwaggiumPickaxe), new Object[] {"III", " S ", " S ", 'I', itemSwaggiumIngot, 'S', Items.stick});
        GameRegistry.addRecipe(new ItemStack(ItemSwaggiumShovel), new Object[] {" I ", " S ", " S ", 'I', itemSwaggiumIngot, 'S', Items.stick});
        GameRegistry.addRecipe(new ItemStack(ItemSwaggiumHoe), new Object[] {"II ", " S ", " S ", 'I', itemSwaggiumIngot, 'S', Items.stick});
        GameRegistry.addRecipe(new ItemStack(itemSwaggiumDoor), new Object[] {"II", "II", "II", 'I', itemSwaggiumIngot});
        GameRegistry.addShapelessRecipe(new ItemStack(blockSwaggiumOre), new Object[] {new ItemStack(itemSwaggiumIngot), new ItemStack(Blocks.stone)});
        GameRegistry.addRecipe(new ItemStack(blockSwaggiumCompressed), new Object[] {"III", "III", "III", 'I', itemSwaggiumIngot});
        GameRegistry.addRecipe(new ItemStack(blockSwaggiumFence), new Object[] {"III", "III", 'I', itemSwaggiumIngot});
        GameRegistry.addSmelting(blockSwaggiumOre, new ItemStack(itemSwaggiumIngot), 0.0F);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {

        proxy.registerRender();

    }

    public void addEntity(Class<? extends Entity> entityClass, String name, int id, int backgroungEggColor, int foregroundEggColor)
    {
        EntityRegistry.registerGlobalEntityID(entityClass, name, EntityRegistry.findGlobalUniqueEntityId(), backgroungEggColor, foregroundEggColor);
        EntityRegistry.registerModEntity(entityClass, name, id, this, 40, 1, true);
    }
}
