package net.ktnlvr.one.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.resource.featuretoggle.FeatureSet;

public class FrostbiteEnchantment extends Enchantment {
    public FrostbiteEnchantment() {
        super(Enchantment.properties(ItemTags.WEAPON_ENCHANTABLE, 2, 4, Enchantment.leveledCost(15, 9), Enchantment.leveledCost(65, 9), 4, FeatureSet.of(FeatureFlags.VANILLA), EquipmentSlot.MAINHAND));
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (target instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity)target;
            int i = 20 + user.getRandom().nextInt(10 * level);
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, i, 3));
        }
    }
}
