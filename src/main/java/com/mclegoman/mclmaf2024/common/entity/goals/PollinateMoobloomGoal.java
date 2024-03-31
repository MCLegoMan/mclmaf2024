package com.mclegoman.mclmaf2024.common.entity.goals;

import com.mclegoman.mclmaf2024.common.entity.MoobloomEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.BeeEntity;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class PollinateMoobloomGoal extends Goal {
	protected final PathAwareEntity mob;
	private final double speed;
	private double lastMoobloomX;
	private double lastMoobloomY;
	private double lastMoobloomZ;
	private double lastMoobloomPitch;
	private double lastMoobloomYaw;
	@Nullable
	protected MoobloomEntity closestMoobloom;
	private int cooldown;
	private boolean active;
	private int ticks;
	private final boolean canBeScared;

	public PollinateMoobloomGoal(PathAwareEntity entity, double speed, boolean canBeScared) {
		this.mob = entity;
		this.speed = speed;
		this.canBeScared = canBeScared;
		this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
	}

	public boolean canStart() {
		if (this.cooldown > 0) {
			--this.cooldown;
			return false;
		} else {
			this.mob.getWorld().getEntitiesByClass(LivingEntity.class, this.mob.getBoundingBox().expand(10.0), (livingEntity) -> true).forEach((livingEntity) -> {
				if (livingEntity instanceof MoobloomEntity && !livingEntity.isBaby()) this.closestMoobloom = (MoobloomEntity)livingEntity;
			});
			return this.closestMoobloom != null;
		}
	}

	public boolean shouldContinue() {
		if (this.canBeScared()) {
			if (this.mob.squaredDistanceTo(this.closestMoobloom) < 36.0) {
				if (this.closestMoobloom.squaredDistanceTo(this.lastMoobloomX, this.lastMoobloomY, this.lastMoobloomZ) > 0.010000000000000002) {
					return false;
				}

				if (Math.abs((double)this.closestMoobloom.getPitch() - this.lastMoobloomPitch) > 5.0 || Math.abs((double)this.closestMoobloom.getYaw() - this.lastMoobloomYaw) > 5.0) {
					return false;
				}
			} else {
				this.lastMoobloomX = this.closestMoobloom.getX();
				this.lastMoobloomY = this.closestMoobloom.getY();
				this.lastMoobloomZ = this.closestMoobloom.getZ();
			}

			this.lastMoobloomPitch = this.closestMoobloom.getPitch();
			this.lastMoobloomYaw = this.closestMoobloom.getYaw();
		}

		return this.canStart();
	}

	protected boolean canBeScared() {
		return this.canBeScared;
	}

	public void start() {
		if (this.mob instanceof BeeEntity) {
			if (!((BeeEntity)this.mob).hasNectar()) {
				this.lastMoobloomX = this.closestMoobloom.getX();
				this.lastMoobloomY = this.closestMoobloom.getY();
				this.lastMoobloomZ = this.closestMoobloom.getZ();
				this.active = true;
			}
		}
	}

	public void stop() {
		this.closestMoobloom = null;
		this.mob.getNavigation().stop();
		this.cooldown = toGoalTicks(100);
		this.ticks = 0;
		if (this.mob instanceof BeeEntity) ((BeeEntity)this.mob).setHasNectar(true);
		this.active = false;
	}
	public void tick() {
		if (this.active) {
			this.ticks++;
			if (this.ticks > 160) {
				this.stop();
			} else {
				this.mob.getLookControl().lookAt(this.closestMoobloom, (float)(this.mob.getMaxHeadRotation() + 20), (float)this.mob.getMaxLookPitchChange());
				if (this.closestMoobloom != null && this.mob.squaredDistanceTo(this.closestMoobloom) > 6.25) this.mob.getNavigation().startMovingTo(this.closestMoobloom, this.speed);
				else this.mob.getNavigation().stop();
			}
		}
	}
}
