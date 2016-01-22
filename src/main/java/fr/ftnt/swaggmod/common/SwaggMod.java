package fr.ftnt.swaggmod.common;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import fr.ftnt.swaggmod.common.tools.ItemSwaggiumAxe;
import fr.ftnt.swaggmod.common.tools.ItemSwaggiumHoe;
import fr.ftnt.swaggmod.common.tools.ItemSwaggiumPickaxe;
import fr.ftnt.swaggmod.common.tools.ItemSwaggiumShovel;
import fr.ftnt.swaggmod.common.tools.ItemSwaggiumSword;
import fr.ftnt.swaggmod.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCompressed;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockOre;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;

@Mod(modid = SwaggMod.MODID, name = "Swagg Mod", version = "1.0.0")
public class SwaggMod
{

    public static final String MODID = "swaggmod";

    @Instance(MODID)
    public static SwaggMod instance;

    @SidedProxy(clientSide = "fr.ftnt.swaggmod.proxy.ClientProxy", serverSide = "fr.ftnt.swaggmod.proxy.CommonProxy")
    public static CommonProxy proxy;

    // Items declaration
    public static Item itemIngotSwaggium; // Ingot
    public static Item itemHelmetSwaggium, itemChestplateSwaggium, itemLeggingsSwaggium, itemBootsSwaggium; // Armor
    public static Item itemDoorSwaggium, itemArmorHorseSwaggium; // Miscellaneous
    public static Item itemAxeSwaggium, itemSwordSwaggium, itemPickaxeSwaggium, itemShovelSwaggium, itemHoeSwaggium; // Tools

    // Blocks declaration
    public static Block blockOreSwaggium, blockBlockSwaggium, blockDoorSwaggium, blockFenceSwaggium; // Basic Blocks

    // Materials Declaration
    public static ArmorMaterial armorSwaggium = EnumHelper.addArmorMaterial("armorSwaggium", 15, new int[] {2, 6, 5, 2}, 30);
    public static ToolMaterial toolSwaggium = EnumHelper.addToolMaterial("toolSwaggium", 2, 854, 10.0F, 4.0F, 30);

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        // -- Items --
        // Basic Items
        itemIngotSwaggium = new Item().setUnlocalizedName("ingotSwaggium").setCreativeTab(CreativeTabs.tabMaterials).setTextureName(MODID + ":swaggium_ingot");
        // Armor
        itemHelmetSwaggium = new ItemSwaggiumArmor(armorSwaggium, 0).setUnlocalizedName("helmetSwaggium").setTextureName(MODID + ":swaggium_helmet");
        itemChestplateSwaggium = new ItemSwaggiumArmor(armorSwaggium, 1).setUnlocalizedName("chestplateSwaggium").setTextureName(MODID + ":swaggium_chestplate");
        itemLeggingsSwaggium = new ItemSwaggiumArmor(armorSwaggium, 2).setUnlocalizedName("leggingsSwaggium").setTextureName(MODID + ":swaggium_leggings");
        itemBootsSwaggium = new ItemSwaggiumArmor(armorSwaggium, 3).setUnlocalizedName("bootsSwaggium").setTextureName(MODID + ":swaggium_boots");
        // Tools
        itemAxeSwaggium = new ItemSwaggiumAxe(toolSwaggium).setUnlocalizedName("axeSwaggium").setTextureName(MODID + ":swaggium_axe");
        itemSwordSwaggium = new ItemSwaggiumSword(toolSwaggium).setUnlocalizedName("swordSwaggium").setTextureName(MODID + ":swaggium_sword");
        itemPickaxeSwaggium = new ItemSwaggiumPickaxe(toolSwaggium).setUnlocalizedName("pickaxeSwaggium").setTextureName(MODID + ":swaggium_pickaxe");
        itemShovelSwaggium = new ItemSwaggiumShovel(toolSwaggium).setUnlocalizedName("shovelSwaggium").setTextureName(MODID + ":swaggium_shovel");
        itemHoeSwaggium = new ItemSwaggiumHoe(toolSwaggium).setUnlocalizedName("hoeSwaggium").setTextureName(MODID + ":swaggium_hoe");
        // Miscellaneous
        itemDoorSwaggium = new ItemDoorSwaggium(Material.iron).setUnlocalizedName("doorSwaggium").setTextureName(MODID + ":door_swaggium");
        itemArmorHorseSwaggium = new Item().setUnlocalizedName("HorseArmorSwaggium").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabMisc).setTextureName(MODID + ":swaggium_horse_armor");
        
        
        // Registering
        GameRegistry.registerItem(itemIngotSwaggium, "item_swaggium_ingot");
        GameRegistry.registerItem(itemHelmetSwaggium, "item_swaggium_helmet");
        GameRegistry.registerItem(itemChestplateSwaggium, "item_swaggium_chestplate");
        GameRegistry.registerItem(itemLeggingsSwaggium, "item_swaggium_leggings");
        GameRegistry.registerItem(itemBootsSwaggium, "item_swaggium_boots");
        GameRegistry.registerItem(itemAxeSwaggium, "item_swaggium_axe");
        GameRegistry.registerItem(itemSwordSwaggium, "item_swaggium_sword");
        GameRegistry.registerItem(itemPickaxeSwaggium, "item_swaggium_pickaxe");
        GameRegistry.registerItem(itemShovelSwaggium, "item_swaggium_shovel");
        GameRegistry.registerItem(itemHoeSwaggium, "item_swaggium_hoe");
        GameRegistry.registerItem(itemDoorSwaggium, "item_door_swaggium");
        GameRegistry.registerItem(itemArmorHorseSwaggium, "item_swaggium_horse_armor");
        
        // -- Blocks --
        // Basic blocks
        blockOreSwaggium = new BlockOre().setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundTypePiston).setBlockName("oreSwaggium").setBlockTextureName(MODID + ":swaggium_ore");
        blockBlockSwaggium = new BlockCompressed(MapColor.lapisColor).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal).setBlockName("blockSwaggium").setBlockTextureName(MODID + ":swaggium_block");
        blockDoorSwaggium = new BlockDoorSwaggium(Material.iron).setHardness(5.0F).setStepSound(Block.soundTypeMetal).setBlockName("doorSwaggium").setBlockTextureName(MODID + ":door_swaggium");
        blockFenceSwaggium = new BlockPaneSwaggium(MODID + ":swaggium_bars", MODID + ":swaggium_bars", Material.iron, true/* (do Tile Drop ?) */).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal).setBlockName("fenceSwaggium");
        // Registering
        GameRegistry.registerBlock(blockOreSwaggium, "block_swaggium");
        GameRegistry.registerBlock(blockBlockSwaggium, "block_antiswagger");
        GameRegistry.registerBlock(blockDoorSwaggium, "block_swaggium_door");
        GameRegistry.registerBlock(blockFenceSwaggium, "block_swaggium_fence");
        
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        addEntity(EntitySwagged.class, "swagged", 420, 0xd4192b, 0x522dbc);

        MinecraftForge.EVENT_BUS.register(new LivingEventHandler());
        proxy.registerRender();
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
