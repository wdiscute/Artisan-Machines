package com.wdiscute.artisan.machines;

import com.mojang.serialization.MapCodec;
import com.wdiscute.artisan.recipe.AbstractArtisanRecipe;
import com.wdiscute.artisan.registry.ArtisanDataMaps;
import com.wdiscute.artisan.upgrades.MachineSettings;
import com.wdiscute.artisan.upgrades.MachineUpgrade;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.BlockPlaceContext;
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
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMachineBlock extends BaseEntityBlock
{
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<State> STATE = EnumProperty.create("state", State.class);

    public AbstractMachineBlock(Properties properties)
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

        //idle state interaction logic
        if (blockState.getValue(STATE).equals(State.IDLE))
        {
            //"place upgrade" checks ⏬
            //if the item has a machine upgrade
            MachineUpgrade machineUpgrade = ArtisanDataMaps.getOrDefault(stack);
            if (!machineUpgrade.equals(MachineUpgrade.EMPTY))
            {
                //if the machine upgrade accepts this machine type
                if (level.getBlockEntity(pos) instanceof AbstractMachineBlockEntity adbe && machineUpgrade.machines().contains(adbe.getType()))
                {
                    //if machine can use repeated upgrades, OR none of the upgrade items already inside match the item attempting to be put in
                    MachineSettings machineSettings = ArtisanDataMaps.getOrDefault(adbe);
                    if (machineSettings.canUseRepeatedUpgrades() || adbe.upgrades.stream().noneMatch(o -> o.is(stack.getItem())))
                    {
                        //if there is space for another upgrade
                        if (machineSettings.upgradeSlots() > adbe.upgrades.size())
                        {
                            //put upgrade
                            if (!level.isClientSide)
                                adbe.putUpgrade(stack, machineUpgrade);
                            stack.shrink(1);
                            return ItemInteractionResult.CONSUME;
                        }
                    }
                }
            }


            //store count as we decrease it later on
            int count = stack.getCount();
            //cycle for item count
            for (int i = 0; i < count; i++)
            {
                //continue if one of the previous inputs made the machine start working
                if (level.getBlockState(pos).getValue(AbstractMachineBlock.STATE).equals(State.WORKING)) continue;

                //add item to blockEntity
                if (level.getBlockEntity(pos) instanceof AbstractMachineBlockEntity adbe)
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

        //working state interaction logic
        if (blockState.getValue(STATE).equals(State.WORKING))
        {
            if (!level.isClientSide && level.getBlockEntity(pos) instanceof AbstractMachineBlockEntity adbe)
            {
                //dirty check to prevent crashes/not saving the world to lock that block into negative hours remaining
                if (adbe.getHoursRemaining() < 0)
                    level.setBlockAndUpdate(pos, state.setValue(AbstractMachineBlock.STATE, AbstractMachineBlock.State.IDLE));
                else
                    displayTimeRemainingClientMessage(player, adbe);
            }

            return ItemInteractionResult.SUCCESS;
        }

        //harvestable state interaction logic
        if (blockState.getValue(STATE).equals(State.HARVESTABLE))
        {
            if (!level.isClientSide && level.getBlockEntity(pos) instanceof AbstractMachineBlockEntity adbe)
            {
                //all harvesting logic is done on block entity
                adbe.harvest(level);
            }
            return ItemInteractionResult.SUCCESS;
        }


        if (result != null)
            return result;
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston)
    {
        //prevents running on setBlock() etc
        if (state.is(newState.getBlock())) return;

        if (level.getBlockEntity(pos) instanceof AbstractMachineBlockEntity ambe)
        {
            //drop result if harvestable
            if (state.getValue(AbstractMachineBlock.STATE).equals(State.HARVESTABLE))
            {
                for (ItemStack stack : ambe.getHarvestResults())
                {
                    Vec3 vec3 = Vec3.atLowerCornerWithOffset(pos, 0.5, 1.01, 0.5).offsetRandom(level.random, 0.7F);
                    ItemEntity itementity = new ItemEntity(level, vec3.x(), vec3.y(), vec3.z(), stack);
                    itementity.setDefaultPickUpDelay();
                    level.addFreshEntity(itementity);
                }
            }
            //drop items stored if working/idle
            else
            {
                for (ItemStack stack : ambe.getItems())
                {
                    Vec3 vec3 = Vec3.atLowerCornerWithOffset(pos, 0.5, 1.01, 0.5).offsetRandom(level.random, 0.7F);
                    ItemEntity itementity = new ItemEntity(level, vec3.x(), vec3.y(), vec3.z(), stack);
                    itementity.setDefaultPickUpDelay();
                    level.addFreshEntity(itementity);
                }
            }

            //drop upgrades regardless
            for (ItemStack stack : ambe.upgrades)
            {
                Vec3 vec3 = Vec3.atLowerCornerWithOffset(pos, 0.5, 1.01, 0.5).offsetRandom(level.random, 0.7F);
                ItemEntity itementity = new ItemEntity(level, vec3.x(), vec3.y(), vec3.z(), stack);
                itementity.setDefaultPickUpDelay();
                level.addFreshEntity(itementity);
            }
        }

        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    public void displayTimeRemainingClientMessage(Player player, AbstractMachineBlockEntity adbe)
    {
        if (!adbe.getResultItem().isEmpty())
            player.displayClientMessage(Component.translatable("block.artisan_machines.machine.currently_making")
                    .append(adbe.recipeResult.getFirst().stack().getHoverName())
                    .append(Component.translatable("block.artisan_machines.machine.hours", adbe.getHoursRemaining())), true);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
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
        return AbstractMachineBlockEntity.getTicker(level, state);
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
