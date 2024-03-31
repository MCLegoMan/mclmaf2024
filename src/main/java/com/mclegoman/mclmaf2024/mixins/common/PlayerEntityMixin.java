package com.mclegoman.mclmaf2024.mixins.common;

import com.mclegoman.mclmaf2024.common.entity.data.Shareware;
import com.mclegoman.mclmaf2024.common.entity.data.SharewareComponent;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements Shareware {
	@Unique
	private SharewareComponent sharewareComponent;
	@Unique
	private static final TrackedData<Boolean> shareware;
	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}
	@Inject(method = "<init>", at = @At("TAIL"))
	private void mclmaf2024$addSharewareComponent(World world, BlockPos pos, float yaw, GameProfile gameProfile, CallbackInfo ci) {
		this.sharewareComponent = new SharewareComponent(this.dataTracker, shareware);
	}
	@Inject(method = "initDataTracker", at = @At("TAIL"))
	private void mclmaf2024$initDataTracker(DataTracker.Builder builder, CallbackInfo ci) {
		builder.add(shareware, false);
	}
	@Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
	private void mclmaf2024$writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
		this.sharewareComponent.writeNbt(nbt);
	}
	@Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
	private void mclmaf2024$readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
		this.sharewareComponent.readNbt(nbt);
	}
	public boolean mclmaf2024$isShareware() {
		return this.sharewareComponent.isShareware();
	}
	public void mclmaf2024$setShareware(boolean value) {
		if (this.sharewareComponent.isShareware() != value) this.sharewareComponent.setShareware(value);
	}
	static {
		shareware = DataTracker.registerData(PlayerEntityMixin.class, TrackedDataHandlerRegistry.BOOLEAN);
	}
}
