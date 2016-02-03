package fr.ftnt.mineswagg.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.MinecraftForge;

public class MineSwaggExtendedEntity implements IExtendedEntityProperties
{
    public final static String EXT_PROP_NAME = "MineSwaggExtendedPlayer";
    private final EntityPlayer player;
    private int swaggAmount, swaggLevel;

    public MineSwaggExtendedEntity(EntityPlayer player)
    {
        this.player = player;
        this.swaggAmount = this.swaggLevel = 5;
    }

    public static final void register(EntityPlayer player)
    {
        player.registerExtendedProperties(MineSwaggExtendedEntity.EXT_PROP_NAME, new MineSwaggExtendedEntity(player));
    }

    public static final MineSwaggExtendedEntity get(EntityPlayer player)
    {
        return (MineSwaggExtendedEntity)player.getExtendedProperties(EXT_PROP_NAME);
    }

    @Override
    public void saveNBTData(NBTTagCompound compound)
    {
        NBTTagCompound properties = new NBTTagCompound();
        properties.setInteger("swaggAmount", this.swaggAmount);
        properties.setInteger("swaggLevel", this.swaggLevel);
        compound.setTag(EXT_PROP_NAME, properties);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound)
    {
        NBTTagCompound properties = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);
        this.swaggAmount = properties.getInteger("swaggAmount");
        this.swaggLevel = properties.getInteger("swaggLevel");
    }

    @Override
    public void init(Entity entity, World world)
    {
        MinecraftForge.EVENT_BUS.register(new MineSwaggEventHandler());
    }

    public boolean consumeSwagg(int amount)
    {
        boolean sufficient = amount <= this.swaggAmount;
        this.swaggAmount -= (amount < this.swaggAmount ? amount : this.swaggAmount);
        return sufficient;
    }

    public void addSwagg(int amount)
    {
        this.swaggAmount += amount;
        System.out.println("Adding " + amount + " swagg");
        System.out.println("Swagg: " + this.swaggAmount);
    }
    
    public void addSwaggLevel(int amount)
    {
        this.swaggLevel += amount;
        System.out.println("Adding " + amount + " swagg level");
        System.out.println("Swagg level: " + this.swaggLevel);
    }

    public int getSwaggAmount()
    {
        return this.swaggAmount;
    }
    
    public int getSwaggLevel()
    {
        return this.swaggLevel;
    }
}
