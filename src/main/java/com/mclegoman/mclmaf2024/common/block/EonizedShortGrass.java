package com.mclegoman.mclmaf2024.common.block;

import com.mclegoman.mclmaf2024.common.registry.BlockPropertiesRegistry;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.ShortPlantBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EonizedShortGrass extends PlantBlock {
	public static final MapCodec<ShortPlantBlock> codec = createCodec(ShortPlantBlock::new);
	public static final BooleanProperty natural;
	public EonizedShortGrass(Settings settings) {
		super(settings);
		this.setDefaultState((this.stateManager.getDefaultState()).with(natural, false));
	}
	protected MapCodec<ShortPlantBlock> getCodec() {
		return codec;
	}
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(natural);
	}
	protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		// When an entity collides with Eonized Short Grass, they will be given the speed effect for 3 seconds.
		if (state.get(BlockPropertiesRegistry.natural) && entity instanceof LivingEntity) ((LivingEntity)entity).addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60, 0));
		super.onEntityCollision(state, world, pos, entity);
	}
	static {
		natural = BlockPropertiesRegistry.natural;
	}
}
