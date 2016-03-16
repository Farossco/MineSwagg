package fr.ftnt.mineswagg.common.entities;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.ftnt.mineswagg.common.MineSwagg;
import fr.ftnt.mineswagg.common.MineSwaggExtendedEntityPlayer;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;

public class EntitySwaggOrb extends EntityXPOrb
{
    private MineSwaggExtendedEntityPlayer props;
    private int swaggValue;

    public int getSwaggValue()
    {
        return swaggValue;
    }

    public void setSwaggValue(int swaggValue)
    {
        this.swaggValue = swaggValue;
    }

    public EntitySwaggOrb(World world)
    {
        super(world);
    }

    public EntitySwaggOrb(World world, double posX, double posY, double posZ, int amount)
    {
        super(world);
        this.setSize(0.5F, 0.5F);
        this.yOffset = this.height / 2.0F;
        this.setPosition(posX, posY, posZ);
        this.rotationYaw = (float)(Math.random() * 360.0D);
        this.motionX = (double)((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 2.0F);
        this.motionY = (double)((float)(Math.random() * 0.2D) * 2.0F);
        this.motionZ = (double)((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 2.0F);
        this.swaggValue = amount;
    }

    public void writeEntityToNBT(NBTTagCompound compound)
    {
        compound.setShort("Value", (short)this.swaggValue);
    }

    public void readEntityFromNBT(NBTTagCompound compound)
    {
        this.swaggValue = compound.getShort("Value");
    }

    public void onCollideWithPlayer(EntityPlayer player)
    {
        if(!this.worldObj.isRemote)
        {
            if(this.field_70532_c == 0 && player.xpCooldown == 0)
            {
                if(MinecraftForge.EVENT_BUS.post(new PlayerPickupXpEvent(player, this)))
                    return;
                props = MineSwaggExtendedEntityPlayer.get(player);
                player.xpCooldown = 1;
                this.worldObj.playSoundAtEntity(player, "random.orb", 0.1F, 0.5F * ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.8F));
                player.onItemPickup(this, 1);
                props.addSwaggAmount(this.swaggValue, true, true);
                this.setDead();
            }
        }
    }

    // Have to sync this value from server (Later...)
    @SideOnly(Side.CLIENT)
    public int getTextureBySwagg()
    {
        return 1;
    }
}
