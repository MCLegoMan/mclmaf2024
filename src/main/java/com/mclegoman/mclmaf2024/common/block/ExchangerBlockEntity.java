package com.mclegoman.mclmaf2024.common.block;

import com.mclegoman.mclmaf2024.common.registry.BlockEntityRegistry;
import com.mclegoman.mclmaf2024.common.screen.ExchangerScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

public class ExchangerBlockEntity extends DispenserBlockEntity {
	private DefaultedList<ItemStack> inventory;
	protected ExchangerBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
		super(blockEntityType, blockPos, blockState);
		this.inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
	}
	public ExchangerBlockEntity(BlockPos pos, BlockState state) {
		this(BlockEntityRegistry.exchangerEntity, pos, state);
	}
	public int size() {
		return 1;
	}
	public int chooseNonEmptySlot(Random random) {
		this.generateLoot(null);
		int i = -1;
		int j = 1;
		for(int k = 0; k < this.inventory.size(); ++k) {
			if (!this.inventory.get(k).isEmpty() && random.nextInt(j++) == 0) {
				i = k;
			}
		}
		return i;
	}
	public int addToFirstFreeSlot(ItemStack stack) {
		for(int i = 0; i < this.inventory.size(); ++i) {
			if (this.inventory.get(i).isEmpty()) {
				this.setStack(i, stack);
				return i;
			}
		}
		return -1;
	}
	protected Text getContainerName() {
		return Text.translatable("container.exchanger");
	}
	public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.readNbt(nbt, registryLookup);
		this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
		if (!this.readLootTable(nbt)) {
			Inventories.readNbt(nbt, this.inventory, registryLookup);
		}
	}
	protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.writeNbt(nbt, registryLookup);
		if (!this.writeLootTable(nbt)) {
			Inventories.writeNbt(nbt, this.inventory, registryLookup);
		}
	}
	protected DefaultedList<ItemStack> getHeldStacks() {
		return this.inventory;
	}
	protected void setHeldStacks(DefaultedList<ItemStack> inventory) {
		this.inventory = inventory;
	}
	protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
		return new ExchangerScreenHandler(syncId, playerInventory, this);
	}
}
