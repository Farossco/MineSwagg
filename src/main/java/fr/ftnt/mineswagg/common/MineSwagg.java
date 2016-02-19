package fr.ftnt.mineswagg.common;

import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import fr.ftnt.mineswagg.client.guiHandlers.GuiHandlerSwaggiumGenerator;
import fr.ftnt.mineswagg.common.blocks.BlockSwaggTester;
import fr.ftnt.mineswagg.common.blocks.BlockSwaggiumCompressed;
import fr.ftnt.mineswagg.common.blocks.BlockSwaggiumDoor;
import fr.ftnt.mineswagg.common.blocks.BlockSwaggiumFence;
import fr.ftnt.mineswagg.common.blocks.BlockSwaggiumGenerator;
import fr.ftnt.mineswagg.common.blocks.BlockSwaggiumLamp;
import fr.ftnt.mineswagg.common.blocks.BlockSwaggiumOre;
import fr.ftnt.mineswagg.common.entities.EntitySwaggOrb;
import fr.ftnt.mineswagg.common.entities.EntitySwagged;
import fr.ftnt.mineswagg.common.items.ItemSwaggiumArmor;
import fr.ftnt.mineswagg.common.items.ItemSwaggiumAxe;
import fr.ftnt.mineswagg.common.items.ItemSwaggiumDoor;
import fr.ftnt.mineswagg.common.items.ItemSwaggiumHoe;
import fr.ftnt.mineswagg.common.items.ItemSwaggiumPickaxe;
import fr.ftnt.mineswagg.common.items.ItemSwaggiumShovel;
import fr.ftnt.mineswagg.common.items.ItemSwaggiumSword;
import fr.ftnt.mineswagg.common.items.itemSwaggiumIngot;
import fr.ftnt.mineswagg.common.packets.PacketSwaggAmountAnswer;
import fr.ftnt.mineswagg.common.packets.PacketSwaggAmountRequest;
import fr.ftnt.mineswagg.common.packets.PacketSwaggGeneratorAnswer;
import fr.ftnt.mineswagg.common.packets.PacketSwaggGeneratorRequest;
import fr.ftnt.mineswagg.common.tileentities.TileEntitySwaggiumGenerator;
import fr.ftnt.mineswagg.integrations.Integrations;
import fr.ftnt.mineswagg.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
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
import net.minecraftforge.oredict.ShapedOreRecipe;

@Mod(modid = MineSwagg.NAME, name = MineSwagg.NAME)
public class MineSwagg
{
    public static final String NAME = "MineSwagg";

    @Instance(NAME)
    public static MineSwagg instance;

    @SidedProxy(clientSide = "fr.ftnt.mineswagg.proxy.ClientProxy", serverSide = "fr.ftnt.mineswagg.proxy.CommonProxy")
    public static CommonProxy proxy;

    public static final CreativeTabs customTab = new MineSwaggCreativeTab("MineSwagg");

    // Items declaration
    public static Item itemSwaggiumIngot; // Ingot
    public static Item itemSwaggiumHelmet, itemSwaggiumChestplate, itemSwaggiumLeggings, itemSwaggiumBoots; // Armor
    public static Item itemSwaggiumDoor; // Miscellaneous
    public static Item ItemSwaggiumAxe, ItemSwaggiumSword, ItemSwaggiumPickaxe, ItemSwaggiumShovel, ItemSwaggiumHoe; // Tools

    // Blocks declaration
    public static Block blockSwaggiumOre, blockSwaggiumCompressed, BlockSwaggiumDoor, blockSwaggiumFence, blockSwaggiumLamp, blockSwaggiumLitLamp, blockSwaggiumLitGenerator; // Swaggium Blocks
    public static Block blockSwaggiumChest; // Iron Chest integration
    public static Block blockSwaggTester, blockSwaggiumGenerator;

    // Materials Declaration
    public static final ArmorMaterial armorSwaggium = EnumHelper.addArmorMaterial("armorSwaggium", 15, new int[] {2, 6, 5, 2}, 30);
    public static final ToolMaterial toolSwaggium = EnumHelper.addToolMaterial("toolSwaggium", 4, 1337, 25.0F, 5.5F, 30);

    public static SimpleNetworkWrapper network;

    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        this.logger = event.getModLog();

        network = NetworkRegistry.INSTANCE.newSimpleChannel(NAME);
        network.registerMessage(PacketSwaggAmountRequest.Handler.class, PacketSwaggAmountRequest.class, 0, Side.SERVER);
        network.registerMessage(PacketSwaggAmountAnswer.Handler.class, PacketSwaggAmountAnswer.class, 1, Side.CLIENT);
        network.registerMessage(PacketSwaggGeneratorRequest.Handler.class, PacketSwaggGeneratorRequest.class, 2, Side.SERVER);
        network.registerMessage(PacketSwaggGeneratorAnswer.Handler.class, PacketSwaggGeneratorAnswer.class, 3, Side.CLIENT);

        MineSwaggVersion.init(event.getVersionProperties());
        event.getModMetadata().version = MineSwaggVersion.fullVersionString();

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
        blockSwaggiumLamp = new BlockSwaggiumLamp(false);
        blockSwaggiumLitLamp = new BlockSwaggiumLamp(true);
        blockSwaggTester = new BlockSwaggTester();
        blockSwaggiumGenerator = new BlockSwaggiumGenerator(false);
        blockSwaggiumLitGenerator = new BlockSwaggiumGenerator(true);

        // Registering
        GameRegistry.registerBlock(blockSwaggiumOre, "block_swaggium");
        GameRegistry.registerBlock(blockSwaggiumCompressed, "block_antiswagger");
        GameRegistry.registerBlock(BlockSwaggiumDoor, "block_swaggium_door");
        GameRegistry.registerBlock(blockSwaggiumFence, "block_swaggium_fence");
        GameRegistry.registerBlock(blockSwaggiumLamp, "block_swaggium_lamp");
        GameRegistry.registerBlock(blockSwaggiumLitLamp, "block_swaggium_lit_lamp");
        GameRegistry.registerBlock(blockSwaggTester, "block_swagg_tester");
        GameRegistry.registerBlock(blockSwaggiumGenerator, "block_swaggium_generator");
        GameRegistry.registerBlock(blockSwaggiumLitGenerator, "block_swaggium_lit_generator");

        OreDictionary.registerOre("ingotSwaggium", itemSwaggiumIngot);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // --------------------------- Entities ---------------------------
        addEntityMob(EntitySwagged.class, "swagged", 420, 0xd4192b, 0x522dbc);
        addEntityMob(EntitySwaggOrb.class, "swaggOrb", 421, 0xd4192b, 0x522dbc);
        MinecraftForge.EVENT_BUS.register(new MineSwaggEventHandler());
        proxy.registerRender();

        // --------------------------- TileEntities ---------------------------
        // GameRegistry.registerTileEntity(fr.ftnt.mineswagg.common.tileentities.TileEntitySwaggiumChest.class, MODID + ":SwaggiumChest");
        GameRegistry.registerTileEntity(TileEntitySwaggiumGenerator.class, NAME + ":SwaggGenerator");

        // --------------------------- GUI ---------------------------
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandlerSwaggiumGenerator());

        Integrations.init(event);

        registerRecipes();
    }

    private void registerRecipes()
    {
        GameRegistry.addShapelessRecipe(new ItemStack(blockSwaggiumOre), new Object[] {new ItemStack(itemSwaggiumIngot), new ItemStack(Blocks.stone)});

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(itemSwaggiumHelmet), new Object[] {"III", "I I", 'I', "ingotSwaggium"}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(itemSwaggiumChestplate), new Object[] {"I I", "III", "III", 'I', "ingotSwaggium"}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(itemSwaggiumLeggings), new Object[] {"III", "I I", "I I", 'I', "ingotSwaggium"}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(itemSwaggiumBoots), new Object[] {"I I", "I I", 'I', "ingotSwaggium"}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemSwaggiumAxe), new Object[] {"II ", "IS ", " S ", 'I', "ingotSwaggium", 'S', Items.stick}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemSwaggiumSword), new Object[] {" I ", " I ", " S ", 'I', "ingotSwaggium", 'S', Items.stick}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemSwaggiumPickaxe), new Object[] {"III", " S ", " S ", 'I', "ingotSwaggium", 'S', Items.stick}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemSwaggiumShovel), new Object[] {" I ", " S ", " S ", 'I', "ingotSwaggium", 'S', Items.stick}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemSwaggiumHoe), new Object[] {"II ", " S ", " S ", 'I', "ingotSwaggium", 'S', Items.stick}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(itemSwaggiumDoor), new Object[] {"II", "II", "II", 'I', "ingotSwaggium"}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockSwaggiumCompressed), new Object[] {"III", "III", "III", 'I', "ingotSwaggium"}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockSwaggiumFence), new Object[] {"III", "III", 'I', "ingotSwaggium"}));

        GameRegistry.addRecipe(new ItemStack(blockSwaggiumLamp), new Object[] {"GRG", "RBR", "GRG", 'R', Items.redstone, 'B', blockSwaggiumCompressed, 'G', Items.glowstone_dust});
        GameRegistry.addRecipe(new ItemStack(blockSwaggiumGenerator), new Object[] {"GDG", "INI", "OEO", 'G', Item.getItemFromBlock(Blocks.gold_block), 'D', Item.getItemFromBlock(Blocks.diamond_block), 'I', Item.getItemFromBlock(Blocks.iron_block), 'N', Items.nether_star, 'O', Item.getItemFromBlock(Blocks.obsidian), 'E', Items.emerald});

        GameRegistry.addSmelting(blockSwaggiumOre, new ItemStack(itemSwaggiumIngot), 0.0F);

        Integrations.registerRecipes();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.registerRender();
        MinecraftForge.EVENT_BUS.register(new MineSwaggEventHandler());
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new MineSwaggCommands());
    }

    public void addEntityMob(Class<? extends Entity> entityClass, String name, int id, int backgroungEggColor, int foregroundEggColor)
    {
        EntityRegistry.registerGlobalEntityID(entityClass, name, EntityRegistry.findGlobalUniqueEntityId(), backgroungEggColor, foregroundEggColor);
        EntityRegistry.registerModEntity(entityClass, name, id, this, 40, 1, true);
    }

    public void addEntity(Class<? extends Entity> entityClass, String name, int id)
    {
        EntityRegistry.registerGlobalEntityID(entityClass, name, EntityRegistry.findGlobalUniqueEntityId());
        EntityRegistry.registerModEntity(entityClass, name, id, this, 40, 1, true);
    }
}