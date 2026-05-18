package com.wdiscute.artisan.machines;

import com.mojang.serialization.MapCodec;
import com.wdiscute.artisan.recipe.AbstractArtisanRecipe;
import com.wdiscute.artisan.registry.ArtisanRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
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

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDailyBlock extends BaseEntityBlock
{
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<State> STATE = EnumProperty.create("state", State.class);

    public AbstractDailyBlock(Properties properties)
    {
        super(properties
                .noOcclusion()
        );
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec()
    {
        return null;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
    {
        ItemInteractionResult result = null;
        BlockState blockState = level.getBlockState(pos);

        //idle
        if (blockState.getValue(STATE).equals(State.IDLE))
        {
            //store count as we decrease it later on
            int count = stack.getCount();
            //cycle for item count
            for (int i = 0; i < count; i++)
            {
                //continue if one of the previous inputs made the machine start working
                if (level.getBlockState(pos).getValue(AbstractDailyBlock.STATE).equals(State.WORKING)) continue;

                //add item to blockEntity
                if (level.getBlockEntity(pos) instanceof AbstractDailyBlockEntity adbe)
                {
                    //get all recipes using item
                    var recipesUsingItem = level.getRecipeManager().getAllRecipesFor(adbe.getRecipeType())
                            .stream().filter(holder -> holder.value().getIngredients()
                                    .stream().anyMatch(o -> o.test(stack))).toList();

                    //check every recipe if item "fits" into any recipe
                    for (RecipeHolder<? extends AbstractArtisanRecipe> abstractArtisanRecipeRecipeHolder : recipesUsingItem)
                    {
                        //get all ingredients of recipe
                        List<Ingredient> ingredients = new ArrayList<>(abstractArtisanRecipeRecipeHolder.value().getIngredients());

                        //for each item already in the block
                        for (ItemStack item : adbe.getItems())
                        {
                            //if the ingredient matches the item inside the block, remove it from the list
                            if (ingredients.stream().anyMatch(o -> o.test(item)))
                                ingredients.remove(ingredients.stream().filter(o -> o.test(item)).toList().getFirst());
                        }

                        //if there are any ingredients left that match the stack being put in
                        if (ingredients.stream().anyMatch(o -> o.test(stack)))
                        {
                            adbe.putItem(stack);
                            if (!level.isClientSide)
                                stack.shrink(1);
                            result = ItemInteractionResult.SUCCESS;
                        }
                    }
                }
            }
        }

        //working
        if (blockState.getValue(STATE).equals(State.WORKING))
        {
            if (!level.isClientSide && level.getBlockEntity(pos) instanceof AbstractDailyBlockEntity adbe)
                player.displayClientMessage(Component.translatable("block.artisan_machines.machine.currently_making")
                        .append(Component.translatable(adbe.getResultItem().getItem().getDescriptionId())), true);

            return ItemInteractionResult.SUCCESS;
        }

        //working
        if (blockState.getValue(STATE).equals(State.HARVESTABLE))
        {
            if (!level.isClientSide && level.getBlockEntity(pos) instanceof AbstractDailyBlockEntity adbe)
            {
                adbe.harvest();
                return ItemInteractionResult.SUCCESS;
            }
        }


        if (result != null)
            return result;
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
        return AbstractDailyBlockEntity.getTicker(level, state);
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
