package com.mclegoman.mclmaf2024.common.entity.data;

import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.nbt.NbtCompound;

public class TechnobladeComponent {
	private final DataTracker dataTracker;
	private final TrackedData<Boolean> technoblade;

	public TechnobladeComponent(DataTracker dataTracker, TrackedData<Boolean> technoblade) {
		this.dataTracker = dataTracker;
		this.technoblade = technoblade;
	}
	public void writeNbt(NbtCompound nbt) {
		nbt.putBoolean("Technoblade", this.isTechnoblade());
	}
	public void readNbt(NbtCompound nbt) {
		this.setTechnoblade(nbt.getBoolean("Technoblade"));
	}
	public void setTechnoblade(boolean value) {
		this.dataTracker.set(this.technoblade, value);
	}
	public boolean isTechnoblade() {
		return this.dataTracker.get(this.technoblade);
	}
}

