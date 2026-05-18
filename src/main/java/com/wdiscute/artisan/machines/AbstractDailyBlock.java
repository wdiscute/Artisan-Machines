package com.wdiscute.artisan.machines;

import com.mojang.serialization.MapCodec;
import com.wdiscute.artisan.registry.ArtisanRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractDailyBlock extends BaseEntityBlock
{
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<State> STATE = EnumProperty.create("state", State.class);

    public AbstractDailyBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec()
    {
        return null;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
    {

        BlockState blockState = level.getBlockState(pos);

        //idle
        if (blockState.getValue(STATE).equals(State.IDLE))
        {
            int count = stack.getCount();
            for (int i = 0; i < count; i++)
            {
                //add item to blockEntity
                if (level.getBlockEntity(pos) instanceof AbstractDailyBlockEntity adbe)
                {
                    boolean isItemUsedInARecipe = level.getRecipeManager().getAllRecipesFor(ArtisanRecipeTypes.LOOM.get())
                            .stream().anyMatch(holder -> holder.value().getIngredients()
                                    .stream().anyMatch(ingredient -> ingredient.test(stack)));

                    if (isItemUsedInARecipe)
                    {
                        adbe.putItem(stack);
                    }
                }


            }

        }


        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
        builder.add(STATE);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state)
    {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType)
    {
        return AbstractDailyBlockEntity.getTicker(level);
    }

    public enum State implements StringRepresentable
    {
        IDLE("idle"),
        WORKING("working"),
        HARVESTABLE("harvestable");

        String name;

        State(String name)
        {
            this.name = name;
        }

        @Override
        public String getSerializedName()
        {
            return name;
        }
    }
}
