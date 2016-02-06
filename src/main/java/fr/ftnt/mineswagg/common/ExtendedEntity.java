package fr.ftnt.mineswagg.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.MinecraftForge;

public class ExtendedEntity implements IExtendedEntityProperties
{
    public final static String EXT_PROP_NAME = "MineSwaggExtendedPlayer";
    private final EntityPlayer player;
    private int swaggAmount, swaggLevel;
    private final int maxSwagg = 182;

    public ExtendedEntity(EntityPlayer player)
    {
        this.player = player;
    }

    public static final void register(EntityPlayer player)
    {
        player.registerExtendedProperties(ExtendedEntity.EXT_PROP_NAME, new ExtendedEntity(player));
    }

    public static final ExtendedEntity get(EntityPlayer player)
    {
        return (ExtendedEntity)player.getExtendedProperties(EXT_PROP_NAME);
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
        NBTTagCompound properties = (NBTTagCompound)compound.getTag(EXT_PROP_NAME);
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
        boolean sufficient = amount <= this.swaggAmount + swaggLevel * maxSwagg;

        if(sufficient)
        {
            this.swaggAmount -= amount;
            testLevelUp();
        }
        return sufficient;
    }

    public void addSwagg(int amount)
    {
        this.swaggAmount += amount;
        // System.out.println("Adding " + amount + " swagg");
        // System.out.println("Swagg: " + this.swaggAmount);
        testLevelUp();
    }

    private void testLevelUp()
    {
        while(swaggAmount >= maxSwagg)
        {
            swaggAmount -= maxSwagg;
            addSwaggLevel(1);
        }

        while(swaggAmount < 0)
        {
            swaggAmount += maxSwagg;
            swaggLevel--;
        }
    }

    public void addSwaggLevel(int amount)
    {
        this.swaggLevel += amount;
        playSound();
        // System.out.println("Adding " + amount + " swagg level");
        // System.out.println("Swagg level: " + this.swaggLevel);
    }

    private void playSound()
    {
        World world = player.worldObj;
        world.playSoundAtEntity(this.player, "random.levelup", 1, 1);
    }

    public int getSwaggAmount()
    {
        return this.swaggAmount;
    }

    public int getSwaggLevel()
    {
        return this.swaggLevel;
    }

    public int getMaxSwagg()
    {
        return maxSwagg;
    }

    public void setSwaggAmount(int swaggAmount)
    {
        this.swaggAmount = swaggAmount;
        testLevelUp();
    }

    public void setSwaggLevel(int swaggLevel)
    {
        this.swaggLevel = swaggLevel;
    }
}
