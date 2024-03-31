package com.mclegoman.mclmaf2024.mixins.common;

import com.mclegoman.mclmaf2024.common.entity.data.Technoblade;
import com.mclegoman.mclmaf2024.common.entity.data.TechnobladeComponent;
import com.mclegoman.mclmaf2024.common.registry.TagKeyRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PigEntity.class)
public abstract class PigEntityMixin extends AnimalEntity implements Technoblade {
	@Unique
	private TechnobladeComponent technobladeComponent;
	@Unique
	private static final TrackedData<Boolean> technoblade;
	protected PigEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
	}
	@Inject(method = "<init>", at = @At("TAIL"))
	private void mclmaf2024$addtechnobladeComponent(EntityType entityType, World world, CallbackInfo ci) {
		this.technobladeComponent = new TechnobladeComponent(this.dataTracker, technoblade);
	}
	@Inject(method = "initDataTracker", at = @At("TAIL"))
	private void mclmaf2024$initDataTracker(DataTracker.Builder builder, CallbackInfo ci) {
		builder.add(technoblade, false);
	}
	@Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
	private void mclmaf2024$writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
		this.technobladeComponent.writeNbt(nbt);
	}
	@Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
	private void mclmaf2024$readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
		this.technobladeComponent.readNbt(nbt);
	}
	@Inject(method = "dropInventory", at = @At("TAIL"))
	private void mclmaf2024$dropInventory(CallbackInfo ci) {
		if (this.technobladeComponent.isTechnoblade()) {
			this.dropItem(Items.GOLDEN_HELMET);
		}
	}
	@Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
	private void mclmaf2024$setTechnobladeHand(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		if ((hand.equals(Hand.MAIN_HAND) && player.getMainHandStack().isIn(TagKeyRegistry.technobladeCrown)) || (hand.equals(Hand.OFF_HAND) && player.getOffHandStack().isIn(TagKeyRegistry.technobladeCrown))) {
			if (hand.equals(Hand.MAIN_HAND)) player.getMainHandStack().decrement(1);
			else player.getOffHandStack().decrement(1);
			mclmaf2024$setTechnoblade();
			cir.setReturnValue(ActionResult.PASS);
		}
	}
	@Inject(method = "onStruckByLightning", at = @At("HEAD"), cancellable = true)
	private void mclmaf2024$technobladeNeverDies_lightning(ServerWorld world, LightningEntity lightning, CallbackInfo ci) {
		if (mclmaf2024$isTechnoblade()) {
			ci.cancel();
			super.onStruckByLightning(world, lightning);
		}
	}
	@Inject(method = "createChild(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/passive/PassiveEntity;)Lnet/minecraft/entity/passive/PassiveEntity;", at = @At("RETURN"), cancellable = true)
	private void mclmaf2024$technobladeBaby(ServerWorld world, PassiveEntity entity, CallbackInfoReturnable<PassiveEntity> cir) {
		PigEntity childEntity = EntityType.PIG.create(world);
		if (childEntity != null) {
			Random random1 = Random.create();
			if (((Technoblade)entity).mclmaf2024$isTechnoblade() || random1.nextInt(1200) == 12) ((Technoblade)childEntity).mclmaf2024$setTechnoblade();
			cir.setReturnValue(childEntity);
		}
	}
	public boolean mclmaf2024$isTechnoblade() {
		return this.technobladeComponent.isTechnoblade();
	}
	public void mclmaf2024$setTechnoblade() {
		this.technobladeComponent.setTechnoblade(true);
		this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ITEM_ARMOR_EQUIP_GOLD.value(), this.getSoundCategory(), 1.0F, 1.0F, this.random.nextLong());
		this.setInvulnerable(true);
	}
	static {
		technoblade = DataTracker.registerData(PigEntityMixin.class, TrackedDataHandlerRegistry.BOOLEAN);
	}
}
