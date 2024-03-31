package com.mclegoman.mclmaf2024.common.entity.helper;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class Knockback {
	public static void knockbackNearbyEntities(World world, LivingEntity entity, float area, float velocity, SoundCategory soundCategory) {
		if (world != null && entity != null) {
			playKnockbackSound(world, entity, soundCategory);
			world.getEntitiesByClass(LivingEntity.class, entity.getBoundingBox().expand(area), (livingEntity) -> true).forEach((livingEntity) -> knockbackEntitySilently(livingEntity, area, velocity));
		}
	}
	public static void knockbackEntity(World world, LivingEntity entity, float area, float velocity, SoundCategory soundCategory) {
		playKnockbackSound(world, entity, soundCategory);
		knockbackEntitySilently(entity, area, velocity);
	}
	private static void playKnockbackSound(World world, LivingEntity entity, SoundCategory soundCategory) {
		if (world != null && entity != null && soundCategory != null) {
			Random random = Random.create();
			world.playSound(null, entity.getBlockPos(), SoundEvents.ENTITY_WIND_CHARGE_WIND_BURST.value(), soundCategory, 0.4F, 2.0F + random.nextFloat() * 0.4F);
		}
	}
	public static void knockbackEntitySilently(LivingEntity entity, float area, float velocity) {
		if (entity != null) {
			Vec3d vec3d = entity.getPos().subtract(entity.getPos());
			Vec3d vec3d2 = vec3d.normalize().multiply((area - vec3d.length()) * (1.0 - entity.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE)));
			// the y velocity gets multiplied by -1 if it's negative, so the entity gets knocked upwards.
			// the y velocity is clamped between 0.1 and 10 to make sure the velocity is always positive, and isn't too high as autoclickers spawning eggs could crash the game whilst also making the entity go hundreds of thousands of blocks up into the air.
			entity.setVelocity(entity.getVelocity().add(vec3d2.x, MathHelper.clamp(entity.getVelocity().y < 0 ? (entity.getVelocity().y * -1) + velocity : entity.getVelocity().y + velocity, 0.1, 10), vec3d2.z));
		}
	}
}
