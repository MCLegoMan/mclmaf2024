package com.mclegoman.mclmaf2024.common.item;

import com.mclegoman.mclmaf2024.common.block.AncientPortalFrameBlock;
import com.mclegoman.mclmaf2024.common.registry.BlockRegistry;
import com.mclegoman.mclmaf2024.common.registry.SoundEventRegistry;
import com.mclegoman.mclmaf2024.common.registry.TagKeyRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.entity.EyeOfEnderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class EonizedPearlItem extends Item {
	public EonizedPearlItem(Settings settings) {
		super(settings);
	}
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		BlockPos blockPos = context.getBlockPos();
		BlockState blockState = world.getBlockState(blockPos);
		if (blockState.isOf(BlockRegistry.ancientPortalFrame) && !(Boolean)blockState.get(AncientPortalFrameBlock.eonized)) {
			if (world.isClient) {
				return ActionResult.SUCCESS;
			} else {
				world.playSound(null, blockPos, SoundEventRegistry.ancientPortalFrameEonized, SoundCategory.BLOCKS, 1.0F, 1.0F);
				BlockState blockState2 = blockState.with(AncientPortalFrameBlock.eonized, true);
				Block.pushEntitiesUpBeforeBlockChange(blockState, blockState2, world, blockPos);
				world.setBlockState(blockPos, blockState2, 2);
				world.updateComparators(blockPos, BlockRegistry.ancientPortalFrame);
				context.getStack().decrement(1);
				BlockPattern.Result result = AncientPortalFrameBlock.getCompletedFramePattern().searchAround(world, blockPos);
				if (result != null) {
					BlockPos blockPos2 = result.getFrontTopLeft().add(-3, 0, -3);
					for(int i = 0; i < 3; ++i) {
						for(int j = 0; j < 3; ++j) {
							world.setBlockState(blockPos2.add(i, 0, j), BlockRegistry.ancientPortal.getDefaultState(), 2);
						}
					}
					world.playSound(null, blockPos, SoundEventRegistry.ancientPortalOpen, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}
				return ActionResult.CONSUME;
			}
		} else {
			return ActionResult.PASS;
		}
	}
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		BlockHitResult blockHitResult = raycast(world, user, RaycastContext.FluidHandling.NONE);
		if (blockHitResult.getType() == HitResult.Type.BLOCK && world.getBlockState(blockHitResult.getBlockPos()).isOf(BlockRegistry.ancientPortalFrame)) {
			return TypedActionResult.pass(itemStack);
		} else {
			user.setCurrentHand(hand);
			if (world instanceof ServerWorld serverWorld) {
				BlockPos blockPos = serverWorld.locateStructure(TagKeyRegistry.ancientPortal, user.getBlockPos(), 100, false);
				if (blockPos != null) {
					EyeOfEnderEntity eyeOfEnderEntity = new EyeOfEnderEntity(world, user.getX(), user.getBodyY(0.5), user.getZ());
					eyeOfEnderEntity.setItem(itemStack);
					eyeOfEnderEntity.initTargetPos(blockPos);
					world.emitGameEvent(GameEvent.PROJECTILE_SHOOT, eyeOfEnderEntity.getPos(), GameEvent.Emitter.of(user));
					world.spawnEntity(eyeOfEnderEntity);

					world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_ENDER_EYE_LAUNCH, SoundCategory.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
					world.syncWorldEvent(null, 1003, user.getBlockPos(), 0);
					itemStack.decrementUnlessCreative(1, user);
					user.incrementStat(Stats.USED.getOrCreateStat(this));
					user.swingHand(hand, true);
					return TypedActionResult.success(itemStack);
				}
			}

			return TypedActionResult.consume(itemStack);
		}
	}
}
