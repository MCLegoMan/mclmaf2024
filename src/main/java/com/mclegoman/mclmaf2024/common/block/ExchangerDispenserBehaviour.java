package com.mclegoman.mclmaf2024.common.block;

import com.mclegoman.mclmaf2024.common.registry.LootTablesRegistry;
import com.mclegoman.mclmaf2024.common.registry.TagKeyRegistry;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerLootComponent;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.List;

public class ExchangerDispenserBehaviour implements DispenserBehavior {
	public ExchangerDispenserBehaviour() {
	}
	public final ItemStack dispense(BlockPointer pointer, ItemStack itemStack) {
		return this.dispenseSilently(pointer, itemStack);
	}
	protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
		Direction direction = pointer.state().get(DispenserBlock.FACING);
		Position position = DispenserBlock.getOutputLocation(pointer);
		ItemStack itemStack = stack.split(1);
		if (itemStack.getComponents().contains(DataComponentTypes.CONTAINER_LOOT)) {
			ContainerLootComponent lootTable = itemStack.getComponents().get(DataComponentTypes.CONTAINER_LOOT);
			if (lootTable != null) exchange(lootTable.lootTable(), 1, pointer, direction, position);
		} else {
			if (itemStack.getRarity() == itemStack.getItem().getDefaultStack().getRarity()) {
				if (itemStack.isIn(TagKeyRegistry.exchangerEpic)) {
					dispenseExchange(Rarity.EPIC, pointer, direction, position);
				} else if (itemStack.isIn(TagKeyRegistry.exchangerRare)) {
					dispenseExchange(Rarity.RARE, pointer, direction, position);
				} else if (itemStack.isIn(TagKeyRegistry.exchangerUncommon)) {
					dispenseExchange(Rarity.UNCOMMON, pointer, direction, position);
				} else if (itemStack.isIn(TagKeyRegistry.exchangerCommon)) {
					dispenseExchange(Rarity.COMMON, pointer, direction, position);
				} else {
					dispenseExchange(itemStack.getRarity(), pointer, direction, position);
				}
			} else {
				dispenseExchange(itemStack.getRarity(), pointer, direction, position);
			}
		}
		return stack;
	}
	private void dispenseExchange(Rarity rarity, BlockPointer pointer, Direction direction, Position position) {
		Random random = Random.create();
		int lootTableInt = random.nextInt(99);
		if (lootTableInt <= 98) {
			switch (rarity) {
				case COMMON -> exchange(LootTablesRegistry.exchangerCommon, 1, pointer, direction, position);
				case UNCOMMON -> {
					if (lootTableInt <= 35)
						exchange(LootTablesRegistry.exchangerCommon, 2, pointer, direction, position);
					else exchange(LootTablesRegistry.exchangerUncommon, 1, pointer, direction, position);
				}
				case RARE -> {
					if (lootTableInt <= 35)
						exchange(LootTablesRegistry.exchangerUncommon, 2, pointer, direction, position);
					else exchange(LootTablesRegistry.exchangerRare, 1, pointer, direction, position);
				}
				case EPIC -> {
					if (lootTableInt <= 35) exchange(LootTablesRegistry.exchangerRare, 2, pointer, direction, position);
					else exchange(LootTablesRegistry.exchangerEpic, 1, pointer, direction, position);
				}
			}
		} else {
			exchange(LootTablesRegistry.exchangerLegendary, 1, pointer, direction, position);
		}
	}
	public void exchange(RegistryKey<LootTable> lootTable, int multiplier, BlockPointer pointer, Direction direction, Position position) {
		for (int i = 0; i < multiplier; i++) {
			LootContextParameterSet lootContextParameterSet = new LootContextParameterSet.Builder(pointer.world()).add(LootContextParameters.ORIGIN, new Vec3d(position.getX(), position.getY(), position.getZ())).build(LootContextTypes.CHEST);
			LootTable lootTable1 = pointer.world().getServer().getReloadableRegistries().getLootTable(lootTable);
			List<ItemStack> list = lootTable1.generateLoot(lootContextParameterSet);
			this.playSound(pointer);
			this.spawnParticles(pointer, pointer.state().get(DispenserBlock.FACING));
			for (ItemStack itemStack : list) spawnItem(pointer.world(), itemStack, 6, direction, position);
		}
	}
	public static void spawnItem(World world, ItemStack stack, int speed, Direction side, Position pos) {
		double d = pos.getX();
		double e = pos.getY();
		double f = pos.getZ();
		if (side.getAxis() == Axis.Y) {
			e -= 0.125;
		} else {
			e -= 0.15625;
		}

		ItemEntity itemEntity = new ItemEntity(world, d, e, f, stack);
		double g = world.random.nextDouble() * 0.1 + 0.2;
		itemEntity.setVelocity(world.random.nextTriangular((double)side.getOffsetX() * g, 0.0172275 * (double)speed), world.random.nextTriangular(0.2, 0.0172275 * (double)speed), world.random.nextTriangular((double)side.getOffsetZ() * g, 0.0172275 * (double)speed));
		world.spawnEntity(itemEntity);
	}

	protected void playSound(BlockPointer pointer) {
		pointer.world().syncWorldEvent(1000, pointer.pos(), 0);
	}

	protected void spawnParticles(BlockPointer pointer, Direction side) {
		pointer.world().syncWorldEvent(2000, pointer.pos(), side.getId());
	}
}
