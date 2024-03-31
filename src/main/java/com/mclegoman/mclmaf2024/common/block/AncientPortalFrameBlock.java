package com.mclegoman.mclmaf2024.common.block;

import com.google.common.base.Predicates;
import com.mclegoman.mclmaf2024.common.registry.BlockPropertiesRegistry;
import com.mclegoman.mclmaf2024.common.registry.BlockRegistry;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class AncientPortalFrameBlock extends Block {
	public static final MapCodec<AncientPortalFrameBlock> CODEC = createCodec(AncientPortalFrameBlock::new);
	public static final DirectionProperty facing;
	public static final BooleanProperty eonized;
	protected static final VoxelShape frameShape;
	protected static final VoxelShape eonizedShape;
	protected static final VoxelShape frameWitheonizedShape;
	private static BlockPattern completedFrame;
	public MapCodec<AncientPortalFrameBlock> getCodec() {
		return CODEC;
	}
	public AncientPortalFrameBlock(AbstractBlock.Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(facing, Direction.NORTH).with(eonized, false));
	}
	protected boolean hasSidedTransparency(BlockState state) {
		return true;
	}
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return state.get(eonized) ? frameWitheonizedShape : frameShape;
	}
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(facing, ctx.getHorizontalPlayerFacing().getOpposite()).with(eonized, false);
	}
	protected boolean hasComparatorOutput(BlockState state) {
		return true;
	}
	protected int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		return state.get(eonized) ? 15 : 0;
	}
	protected BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(facing, rotation.rotate(state.get(facing)));
	}
	protected BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(facing)));
	}
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(facing, eonized);
	}
	public static BlockPattern getCompletedFramePattern() {
		if (completedFrame == null) {
			completedFrame = BlockPatternBuilder.start().aisle("?vvv?", ">???<", ">???<", ">???<", "?^^^?").where('?', CachedBlockPosition.matchesBlockState(BlockStatePredicate.ANY)).where('^', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(BlockRegistry.ancientPortalFrame).with(eonized, Predicates.equalTo(true)).with(facing, Predicates.equalTo(Direction.SOUTH)))).where('>', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(BlockRegistry.ancientPortalFrame).with(eonized, Predicates.equalTo(true)).with(facing, Predicates.equalTo(Direction.WEST)))).where('v', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(BlockRegistry.ancientPortalFrame).with(eonized, Predicates.equalTo(true)).with(facing, Predicates.equalTo(Direction.NORTH)))).where('<', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(BlockRegistry.ancientPortalFrame).with(eonized, Predicates.equalTo(true)).with(facing, Predicates.equalTo(Direction.EAST)))).build();
		}
		return completedFrame;
	}
	protected boolean canPathfindThrough(BlockState state, NavigationType type) {
		return false;
	}
	static {
		facing = HorizontalFacingBlock.FACING;
		eonized = BlockPropertiesRegistry.eonized;
		frameShape = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 13.0, 16.0);
		eonizedShape = Block.createCuboidShape(4.0, 13.0, 4.0, 12.0, 16.0, 12.0);
		frameWitheonizedShape = VoxelShapes.union(frameShape, eonizedShape);
	}
}
