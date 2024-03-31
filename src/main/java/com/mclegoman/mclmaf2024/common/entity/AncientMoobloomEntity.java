package com.mclegoman.mclmaf2024.common.entity;

import com.mclegoman.mclmaf2024.common.registry.EntityRegistry;
import com.mclegoman.mclmaf2024.common.registry.SoundEventRegistry;
import com.mclegoman.mclmaf2024.common.registry.TagKeyRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;

public class AncientMoobloomEntity extends MoobloomEntity {
	public AncientMoobloomEntity(EntityType<? extends MoobloomEntity> entityType, World world) {
		super(entityType, world);
	}
	public Block getFlower() {
		return Blocks.TORCHFLOWER;
	}
	public MobEntity getShearedEntity() {
		return EntityRegistry.moobloom.create(this.getWorld());
	}
	public ParticleEffect shearKnockbackParticles() {
		return ParticleTypes.GUST_EMITTER_LARGE;
	}
	public float shearKnockbackArea() {
		return 5.0F;
	}
	public float shearKnockbackVelocity() {
		return 1.2F;
	}
	public void sheared(SoundCategory shearedSoundCategory) {
		if (this.isDeeonizeable()) this.getWorld().playSound(null, this.getBlockPos(), getDeeonizeSound(), shearedSoundCategory, 1.0F, 1.0F);
		super.sheared(shearedSoundCategory);
	}
	public int getParticleAmount() {
		return 64;
	}
	public MoobloomEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
		return EntityRegistry.ancientMoobloom.create(serverWorld);
	}
	public boolean isEonizeable() {
		return false;
	}
	public boolean isDeeonizeable() {
		return true;
	}
	public SoundEvent getShearSound() {
		return SoundEventRegistry.ancientMoobloomShear;
	}
	public SoundEvent getDeeonizeSound() {
		return SoundEventRegistry.ancientMoobloomDeeonize;
	}
	public boolean isFoodItem(ItemStack stack) {
		return stack.isIn(TagKeyRegistry.ancientMoobloomFood);
	}
	public boolean isBreedingItem(ItemStack stack) {
		return stack.isIn(TagKeyRegistry.ancientMoobloomFood);
	}
}
