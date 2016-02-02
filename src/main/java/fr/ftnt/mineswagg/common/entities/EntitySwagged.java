package fr.ftnt.mineswagg.common.entities;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class EntitySwagged extends EntityMob
{

    public EntitySwagged(World world)
    {
        super(world);
    }

    public void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(20.0D);
    }

    public Item getFropItem()
    {
        return Items.apple;

    }
}
