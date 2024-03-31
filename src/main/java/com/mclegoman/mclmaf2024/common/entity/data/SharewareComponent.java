package com.mclegoman.mclmaf2024.common.entity.data;

import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.nbt.NbtCompound;

public class SharewareComponent {
	private final DataTracker dataTracker;
	private final TrackedData<Boolean> shareware;

	public SharewareComponent(DataTracker dataTracker, TrackedData<Boolean> shareware) {
		this.dataTracker = dataTracker;
		this.shareware = shareware;
	}
	public void writeNbt(NbtCompound nbt) {
		nbt.putBoolean("Shareware", this.isShareware());
	}
	public void readNbt(NbtCompound nbt) {
		this.setShareware(nbt.getBoolean("Shareware"));
	}
	public void setShareware(boolean value) {
		this.dataTracker.set(this.shareware, value);
	}
	public boolean isShareware() {
		return this.dataTracker.get(this.shareware);
	}
}

