package fr.ftnt.mineswagg.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.MinecraftForge;

public class MineSwaggExtendedEntityCreature implements IExtendedEntityProperties
{
    public final static String EXT_PROP_NAME = "MineSwaggExtendedLiving";
    private EntityLivingBase entity;
    public int recentlyHit;

    public MineSwaggExtendedEntityCreature(EntityCreature entity)
    {
        this.entity = entity;
    }

    public static final void register(EntityCreature entity)
    {
        entity.registerExtendedProperties(MineSwaggExtendedEntityCreature.EXT_PROP_NAME, new MineSwaggExtendedEntityCreature(entity));
    }

    public static final MineSwaggExtendedEntityCreature get(EntityCreature entity)
    {
        return (MineSwaggExtendedEntityCreature)entity.getExtendedProperties(EXT_PROP_NAME);
    }

    @Override
    public void saveNBTData(NBTTagCompound compound)
    {
        NBTTagCompound properties = new NBTTagCompound();
        properties.setInteger("recentlyHit", this.recentlyHit);
        compound.setTag(EXT_PROP_NAME, properties);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound)
    {
        NBTTagCompound properties = (NBTTagCompound)compound.getTag(EXT_PROP_NAME);
        this.recentlyHit = properties.getInteger("recentlyHit");
    }

    @Override
    public void init(Entity entity, World world)
    {
        MinecraftForge.EVENT_BUS.register(new MineSwaggEventHandler());
    }
}
