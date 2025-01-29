package com.mclegoman.mclmaf2024.common.entity;

import com.mclegoman.mclmaf2024.common.entity.helper.Knockback;
import com.mclegoman.mclmaf2024.common.registry.EntityRegistry;
import com.mclegoman.mclmaf2024.common.registry.SoundEventRegistry;
import com.mclegoman.mclmaf2024.common.registry.TagKeyRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
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
	protected void initGoals() {
		super.initGoals();
		this.targetSelector.add(0, new ActiveTargetGoal<>(this, MoobloomEntity.class, true, (livingEntity) -> !(livingEntity instanceof PotatoMoobloomEntity)));
		this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0, false));
	}
	public boolean onKilledOther(ServerWorld world, LivingEntity other) {
		boolean output = super.onKilledOther(world, other);
		this.getWorld().playSound(null, this.getBlockPos(), getEonizeSound(), this.getSoundCategory(), 1.0F, 1.0F);
		if (other instanceof MoobloomEntity moobloomEntity) {
			PotatoMoobloomEntity entity = moobloomEntity.convertTo(EntityRegistry.potatoMoobloom, false);
			if (entity != null) {
				entity.initialize(world, world.getLocalDifficulty(entity.getBlockPos()), SpawnReason.CONVERSION, null);
				if (!this.isSilent()) world.syncWorldEvent(null, 1051, this.getBlockPos(), 0);
				output = false;
			}
		}
		this.getWorld().addParticle(ParticleTypes.GUST_EMITTER_SMALL, this.getX(), this.getY(), this.getZ(), 1.0, 0.0, 0.0);
		Knockback.knockbackNearbyEntities(this.getWorld(), this, 2.5F, 0.6F, this.getSoundCategory());
		return output;
	}
	public static DefaultAttributeContainer.Builder createAttributes() {
		return MoobloomEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10.0);
	}
}
