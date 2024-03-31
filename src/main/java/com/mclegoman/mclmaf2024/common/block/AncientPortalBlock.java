package com.mclegoman.mclmaf2024.common.block;

import com.mclegoman.mclmaf2024.common.registry.BlockPropertiesRegistry;
import com.mclegoman.mclmaf2024.common.registry.BlockRegistry;
import com.mclegoman.mclmaf2024.common.registry.SoundEventRegistry;
import com.mclegoman.mclmaf2024.common.registry.WorldRegistry;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.HashSet;

public class AncientPortalBlock extends Block {
	public static final MapCodec<AncientPortalBlock> codec = createCodec(AncientPortalBlock::new);
	public static final BooleanProperty natural;
	public static final VoxelShape shape;
	public AncientPortalBlock(Settings settings) {
		super(settings);
		this.setDefaultState((this.stateManager.getDefaultState()).with(natural, false));
	}
	protected MapCodec<AncientPortalBlock> getCodec() {
		return codec;
	}
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(natural);
	}
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		if (random.nextInt(50) == 0) world.playSound((double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundEventRegistry.ancientPortalAmbience, SoundCategory.BLOCKS, 0.5F, random.nextFloat() * 0.4F + 0.8F, false);
		for(int i = 0; i < 4; ++i) {
			double x = (double)pos.getX() + random.nextDouble();
			double y = (double)pos.getY() + random.nextDouble();
			double z = (double)pos.getZ() + random.nextDouble();
			double velocityX = ((double)random.nextFloat() - 0.5) * 0.5;
			double velocityY = ((double)random.nextFloat() - 0.5) * 0.5;
			double velocityZ = ((double)random.nextFloat() - 0.5) * 0.5;
			int multiplier = random.nextInt(2) * 2 - 1;
			if (!world.getBlockState(pos.west()).isOf(this) && !world.getBlockState(pos.east()).isOf(this)) {
				x = (double)pos.getX() + 0.5 + 0.25 * (double)multiplier;
				velocityX = (random.nextFloat() * 2.0F * (float)multiplier);
			} else {
				z = (double)pos.getZ() + 0.5 + 0.25 * (double)multiplier;
				velocityZ = random.nextFloat() * 2.0F * (float)multiplier;
			}
			world.addParticle(ParticleTypes.PORTAL, x, y, z, velocityX, velocityY, velocityZ);
		}
	}
	protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (world instanceof ServerWorld && entity.canUsePortals() && VoxelShapes.matchesAnywhere(VoxelShapes.cuboid(entity.getBoundingBox().offset(-pos.getX(), -pos.getY(), -pos.getZ())), state.getOutlineShape(world, pos), BooleanBiFunction.AND)) {
			if (entity instanceof LivingEntity) teleport(world, (LivingEntity)entity);
		}
		super.onEntityCollision(state, world, pos, entity);
	}
	private void teleport(World world, LivingEntity entity) {
		if (World.isValid(BlockPos.ofFloored(entity.getPos()))) {
			MinecraftServer server = world.getServer();
			if (server != null) {
				ServerWorld destinationWorld = world.getRegistryKey() == WorldRegistry.ancientWorld ? server.getWorld(World.OVERWORLD) : server.getWorld(WorldRegistry.ancientWorld);
				if (destinationWorld != null) {
					Vec3d destinationPos = new Vec3d(16.5F, destinationWorld.getSpawnPos().getY(), -16.5F);
					boolean canTeleport = false;
					while (!canTeleport) {
						if (!teleportCheck(destinationWorld, destinationPos)) {
							destinationPos = destinationPos.add(0, 1, 0);
						} else {
							canTeleport = true;
						}
					}
					teleportFinal(destinationWorld, entity, destinationPos);
				}
			}
		}
	}
	private boolean teleportCheck(ServerWorld destinationWorld, Vec3d destinationPos) {
		return destinationWorld.getBlockState(BlockPos.ofFloored(destinationPos)).isOf(Blocks.AIR);
	}
	private void teleportFinal(ServerWorld destinationWorld, LivingEntity entity, Vec3d destinationPos) {
		if (entity.teleport(destinationWorld, destinationPos.getX(), destinationPos.getY(), destinationPos.getZ(), new HashSet<>(), MathHelper.wrapDegrees(entity.getYaw()), MathHelper.wrapDegrees(entity.getPitch()))) {
			if (entity instanceof PathAwareEntity) ((PathAwareEntity)entity).stopMovement();
			generateReturnPortal(destinationWorld);
		}
	}
	private void generateReturnPortal(ServerWorld destinationWorld) {
		if (destinationWorld.getRegistryKey() == WorldRegistry.ancientWorld) {
			for (int a = -1; a < 2; a++) {
				for (int b = -1; b < 2; b++) {
					destinationWorld.setBlockState(new BlockPos(a, destinationWorld.getSpawnPos().getY(), b), BlockRegistry.ancientPortal.getDefaultState());
				}
			}
			for (int b = -1; b < 2; b++) {
				destinationWorld.setBlockState(new BlockPos(b, destinationWorld.getSpawnPos().getY(), -2), Blocks.BEDROCK.getDefaultState());
				destinationWorld.setBlockState(new BlockPos(-2, destinationWorld.getSpawnPos().getY(), b), Blocks.BEDROCK.getDefaultState());
				destinationWorld.setBlockState(new BlockPos(b, destinationWorld.getSpawnPos().getY(), 2), Blocks.BEDROCK.getDefaultState());
				destinationWorld.setBlockState(new BlockPos(2, destinationWorld.getSpawnPos().getY(), b), Blocks.BEDROCK.getDefaultState());
			}
		}
	}
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return shape;
	}
	static {
		natural = BlockPropertiesRegistry.natural;
		shape = Block.createCuboidShape(0.0, 6.0, 0.0, 16.0, 12.0, 16.0);
	}
}
