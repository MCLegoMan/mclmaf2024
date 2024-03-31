package com.mclegoman.mclmaf2024.common.block;

import com.mclegoman.mclmaf2024.common.Main;
import com.mclegoman.mclmaf2024.common.registry.BlockEntityRegistry;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class ExchangerBlock extends DispenserBlock {
	public static final MapCodec<ExchangerBlock> codec = createCodec(ExchangerBlock::new);
	private static final DispenserBehavior behavior = new ExchangerDispenserBehaviour();
	public MapCodec<ExchangerBlock> getCodec() {
		return codec;
	}
	public ExchangerBlock(AbstractBlock.Settings settings) {
		super(settings);
		this.setDefaultState((this.stateManager.getDefaultState()).with(FACING, Direction.NORTH).with(TRIGGERED, false));
	}
	protected DispenserBehavior getBehaviorForItem(ItemStack stack) {
		return behavior;
	}
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new ExchangerBlockEntity(pos, state);
	}
	protected void dispense(ServerWorld world, BlockState state, BlockPos pos) {
		DispenserBlockEntity dispenserBlockEntity = world.getBlockEntity(pos, BlockEntityRegistry.exchangerEntity).orElse(null);
		if (dispenserBlockEntity == null) {
			Main.logger.warn("Ignoring dispensing attempt for exchanger without matching block entity at {}", pos);
		} else {
			BlockPointer blockPointer = new BlockPointer(world, pos, state, dispenserBlockEntity);
			int i = dispenserBlockEntity.chooseNonEmptySlot(world.random);
			if (i < 0) {
				world.syncWorldEvent(1001, pos, 0);
			} else {
				ItemStack itemStack = dispenserBlockEntity.getStack(i);
				if (!itemStack.isEmpty()) {
					ItemStack itemStack2 = behavior.dispense(blockPointer, itemStack);
					dispenserBlockEntity.setStack(i, itemStack2);
				}
			}
		}
	}
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING, TRIGGERED);
	}
}
