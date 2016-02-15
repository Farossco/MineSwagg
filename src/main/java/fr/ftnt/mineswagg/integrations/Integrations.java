package fr.ftnt.mineswagg.integrations;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import fr.ftnt.mineswagg.multipart.RegisterBlockPart;

public class Integrations
{
    public static void preInit(FMLPreInitializationEvent event)
    {

    }

    public static void init(FMLInitializationEvent event)
    {
        if(Loader.isModLoaded("ForgeMultipart"))
        {
            new RegisterBlockPart().init();
        }

        if(Loader.isModLoaded("ExtraUtilities"))
        {
            IntegrationExtraUtils.init(event);
        }
    }

    public static void registerRecipes()
    {
        if(Loader.isModLoaded("ExtraUtilities"))
        {
            IntegrationExtraUtils.registerRecipes();
        }
    }

    public static void postInit(FMLPostInitializationEvent event)
    {

    }

    public static void serverStarting(FMLServerStartingEvent event)
    {

    }
}