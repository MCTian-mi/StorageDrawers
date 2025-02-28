package com.jaquadro.minecraft.storagedrawers.block.meta;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class BlockMeta extends HorizontalDirectionalBlock
{
    public static final MapCodec<BlockMeta> CODEC = simpleCodec(BlockMeta::new);

    public static final BooleanProperty HALF = BooleanProperty.create("half");

    public BlockMeta (Properties properties) {
        super(properties);
        registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(HALF, false));
    }

    @Override
    public MapCodec<BlockMeta> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition (StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING).add(HALF);
    }
}
