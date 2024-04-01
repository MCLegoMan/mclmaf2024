package com.mclegoman.mclmaf2024.common.entity;

import com.mclegoman.mclmaf2024.common.registry.EntityRegistry;
import com.mclegoman.mclmaf2024.common.registry.SoundEventRegistry;
import com.mclegoman.mclmaf2024.common.registry.TagKeyRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class PotatoMoobloomEntity extends MoobloomEntity {
	public PotatoMoobloomEntity(EntityType<? extends MoobloomEntity> entityType, World world) {
		super(entityType, world);
	}
	public Block getFlower() {
		return Blocks.POTATO_FLOWER;
	}
	public MobEntity getShearedEntity() {
		return EntityRegistry.moobloom.create(this.getWorld());
	}
	public ParticleEffect shearKnockbackParticles() {
		return ParticleTypes.GUST;
	}
	public float shearKnockbackArea() {
		return 3.75F;
	}
	public float shearKnockbackVelocity() {
		return 0.9F;
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
	public float getPathfindingFavor(BlockPos pos, WorldView world) {
		return world.getBlockState(pos.down()).isIn(TagKeyRegistry.potatoMoobloomPathfindingFavor) ? 10.0F : world.getPhototaxisFavor(pos);
	}
	public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
		return world.getBlockState(this.getBlockPos().down()).isIn(TagKeyRegistry.potatoMoobloomsSpawnableOn) && isLightLevelValidForNaturalSpawn(world, this.getBlockPos());
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
