package com.mclegoman.mclmaf2024.common.entity;

import com.mclegoman.mclmaf2024.common.entity.data.Eonizeable;
import com.mclegoman.mclmaf2024.common.entity.helper.Knockback;
import com.mclegoman.mclmaf2024.common.registry.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SuspiciousStewIngredient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;

import java.util.List;

public class MoobloomEntity extends AnimalEntity implements Shearable, Eonizeable {
	private static final EntityDimensions babyDimensions;
	public MoobloomEntity(EntityType<? extends MoobloomEntity> entityType, World world) {
		super(entityType, world);
	}
	protected void initGoals() {
		this.goalSelector.add(0, new SwimGoal(this));
		this.goalSelector.add(1, new EscapeDangerGoal(this, 2.0));
		this.goalSelector.add(2, new AnimalMateGoal(this, 1.0));
		this.goalSelector.add(3, new TemptGoal(this, 1.25, this::isFoodItem, false));
		this.goalSelector.add(4, new FollowParentGoal(this, 1.25));
		this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
		this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.add(7, new LookAroundGoal(this));
	}
	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.18000000268);
	}
	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_COW_AMBIENT;
	}
	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.ENTITY_COW_HURT;
	}
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_COW_DEATH;
	}
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(SoundEvents.ENTITY_COW_STEP, 0.15F, 1.0F);
	}
	protected float getSoundVolume() {
		return 0.4F;
	}
	public float getPathfindingFavor(BlockPos pos, WorldView world) {
		return world.getBlockState(pos.down()).isIn(TagKeyRegistry.moobloomPathfindingFavor) ? 10.0F : world.getPhototaxisFavor(pos);
	}
	public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
		return world.getBlockState(this.getBlockPos().down()).isIn(TagKeyRegistry.moobloomsSpawnableOn) && isLightLevelValidForNaturalSpawn(world, this.getBlockPos());
	}
	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(Items.BUCKET) && !this.isBaby()) {
			player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
			ItemStack itemStack2 = ItemUsage.exchangeStack(itemStack, player, Items.MILK_BUCKET.getDefaultStack());
			player.setStackInHand(hand, itemStack2);
			return ActionResult.success(this.getWorld().isClient);
		} else if (itemStack.isOf(Items.BOWL) && this.isStewable()) {
			player.playSound(SoundEvents.ENTITY_MOOSHROOM_SUSPICIOUS_MILK, 1.0F, 1.0F);
			ItemStack stew = new ItemStack(Items.SUSPICIOUS_STEW);
			stew.set(DataComponentTypes.SUSPICIOUS_STEW_EFFECTS, ((SuspiciousStewIngredient)getFlower()).getStewEffects());
			player.setStackInHand(hand, ItemUsage.exchangeStack(itemStack, player, stew));
			return ActionResult.success(this.getWorld().isClient);
		} else if (itemStack.isOf(Items.SHEARS) && this.isShearable()) {
			this.sheared(SoundCategory.PLAYERS);
			this.emitGameEvent(GameEvent.SHEAR, player);
			if (!this.getWorld().isClient) itemStack.damage(1, player, getSlotForHand(hand));
			return ActionResult.success(this.getWorld().isClient);
		} else if (this.isEonizeFoodItem(itemStack) && this.isEonizeable()) {
			this.eonized(SoundCategory.PLAYERS);
			this.emitGameEvent(GameEventRegistry.eonized, player);
			if (!this.getWorld().isClient) itemStack.decrement(1);
			return ActionResult.success(this.getWorld().isClient);
		}
		return super.interactMob(player, hand);
	}
	public void shearKnockback(SoundCategory shearedSoundCategory) {
		this.getWorld().addParticle(shearKnockbackParticles(), this.getX(), this.getY(), this.getZ(), 1.0, 0.0, 0.0);
		Knockback.knockbackNearbyEntities(this.getWorld(), this, shearKnockbackArea(), shearKnockbackVelocity(), shearedSoundCategory);
	}
	public ParticleEffect shearKnockbackParticles() {
		return ParticleTypes.GUST_EMITTER_SMALL;
	}
	public float shearKnockbackArea() {
		return 2.5F;
	}
	public float shearKnockbackVelocity() {
		return 0.6F;
	}
	public void sheared(SoundCategory shearedSoundCategory) {
		shearKnockback(shearedSoundCategory);
		this.getWorld().playSound(null, this.getBlockPos(), getShearSound(), shearedSoundCategory, 1.0F, 1.0F);
		if (!this.getWorld().isClient()) {
			MobEntity shearedEntity = getShearedEntity();
			if (shearedEntity != null) {
				((ServerWorld)this.getWorld()).spawnParticles(ParticleTypes.EXPLOSION, this.getX(), this.getBodyY(0.5), this.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
				for (int i = 0; i < getParticleAmount(); i++) ((ServerWorld)this.getWorld()).spawnParticles(ParticleTypes.FALLING_NECTAR, this.getX() + (this.getWorld().random.nextBetween(-10, 10) * 0.1F), this.getBodyY(0.5) + (this.getWorld().random.nextBetween(-5, 10) * 0.1F), this.getZ() + (this.getWorld().random.nextBetween(-10, 10) * 0.1F), 1, 0.0, 0.0, 0.0, 8.0);
				this.discard();
				shearedEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
				shearedEntity.setHealth(this.getHealth());
				shearedEntity.bodyYaw = this.bodyYaw;
				if (this.hasCustomName()) {
					shearedEntity.setCustomName(this.getCustomName());
					shearedEntity.setCustomNameVisible(this.isCustomNameVisible());
				}
				if (this.isPersistent()) shearedEntity.setPersistent();
				shearedEntity.setInvulnerable(this.isInvulnerable());
				this.getWorld().spawnEntity(shearedEntity);
				for(int i = 0; i < 5; ++i) this.getWorld().spawnEntity(new ItemEntity(this.getWorld(), this.getX(), this.getBodyY(1.0), this.getZ(), new ItemStack(getFlower().asItem())));
			}
		}
	}
	public boolean isShearable() {
		return !this.isBaby();
	}
	public void eonized(SoundCategory shearedSoundCategory) {
		this.getWorld().addParticle(ParticleTypes.GUST_EMITTER_SMALL, this.getX(), this.getY(), this.getZ(), 1.0, 0.0, 0.0);
		Knockback.knockbackNearbyEntities(this.getWorld(), this, 2.5F, 0.6F, shearedSoundCategory);
		this.getWorld().playSound(null, this.getBlockPos(), getEonizeSound(), shearedSoundCategory, 1.0F, 1.0F);
		if (!this.getWorld().isClient()) {
			MobEntity shearedEntity = getEonizedEntity();
			if (shearedEntity != null) {
				((ServerWorld)this.getWorld()).spawnParticles(ParticleTypes.EXPLOSION, this.getX(), this.getBodyY(0.5), this.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
				for (int i = 0; i < getParticleAmount(); i++) ((ServerWorld)this.getWorld()).spawnParticles(ParticleTypes.FALLING_NECTAR, this.getX() + (this.getWorld().random.nextBetween(-10, 10) * 0.1F), this.getBodyY(0.5) + (this.getWorld().random.nextBetween(-5, 10) * 0.1F), this.getZ() + (this.getWorld().random.nextBetween(-10, 10) * 0.1F), 1, 0.0, 0.0, 0.0, 8.0);
				this.discard();
				shearedEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
				shearedEntity.setHealth(this.getHealth());
				shearedEntity.bodyYaw = this.bodyYaw;
				if (this.hasCustomName()) {
					shearedEntity.setCustomName(this.getCustomName());
					shearedEntity.setCustomNameVisible(this.isCustomNameVisible());
				}
				if (this.isPersistent()) shearedEntity.setPersistent();
				shearedEntity.setInvulnerable(this.isInvulnerable());
				this.getWorld().spawnEntity(shearedEntity);
				if (this.getWorld().getServer() != null) {
					LootContextParameterSet lootContextParameterSet = new LootContextParameterSet.Builder(((ServerWorld) this.getWorld()).toServerWorld()).add(LootContextParameters.ORIGIN, new Vec3d(this.getX(), this.getY(), this.getZ())).build(LootContextTypes.CHEST);
					LootTable lootTable = this.getWorld().getServer().getReloadableRegistries().getLootTable(this.getEonizeLootTable());
					List<ItemStack> eonizeLoot = lootTable.generateLoot(lootContextParameterSet);
					for (ItemStack loot : eonizeLoot) {
						this.getWorld().spawnEntity(new ItemEntity(this.getWorld(), this.getX(), this.getBodyY(1.0), this.getZ(), loot));
					}
				}
			}
		}
	}
	public boolean isEonizeable() {
		return !this.isBaby();
	}
	public boolean isBreedingItem(ItemStack stack) {
		return stack.isIn(TagKeyRegistry.moobloomFood);
	}
	public boolean isFoodItem(ItemStack stack) {
		return stack.isIn(TagKeyRegistry.moobloomFood);
	}
	public boolean isEonizeFoodItem(ItemStack stack) {
		return stack.isIn(TagKeyRegistry.moobloomEonizeFood);
	}
	public boolean isStewable() {
		return !this.isBaby();
	}
	public Block getFlower() {
		return Blocks.DANDELION;
	}
	public SoundEvent getShearSound() {
		return SoundEventRegistry.moobloomShear;
	}
	public SoundEvent getEonizeSound() {
		return SoundEventRegistry.moobloomEonize;
	}
	public int getParticleAmount() {
		return 32;
	}
	public MobEntity getShearedEntity() {
		return EntityType.COW.create(this.getWorld());
	}
	public MobEntity getEonizedEntity() {
		return EntityRegistry.ancientMoobloom.create(this.getWorld());
	}
	public RegistryKey<LootTable> getEonizeLootTable() {
		return LootTablesRegistry.eonizeMoobloom;
	}
	public MoobloomEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
		return EntityRegistry.moobloom.create(serverWorld);
	}
	public EntityDimensions getBaseDimensions(EntityPose pose) {
		return this.isBaby() ? babyDimensions : super.getBaseDimensions(pose);
	}
	static {
		babyDimensions = EntityRegistry.moobloom.getDimensions().scaled(0.5F).withEyeHeight(0.665F);
	}
}
