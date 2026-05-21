package com.wdiscute.artisan.machines;

import com.wdiscute.artisan.recipe.AbstractArtisanRecipe;
import com.wdiscute.artisan.registry.ArtisanBlockEntities;
import com.wdiscute.artisan.registry.ArtisanRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;

public class PreservesJarBlockEntity extends AbstractMachineBlockEntity
{
    public PreservesJarBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        super(ArtisanBlockEntities.PRESERVES_JAR.get(), blockPos, blockState);
    }

    @Override
    public RecipeType<? extends AbstractArtisanRecipe> getRecipeType()
    {
        return ArtisanRecipeTypes.PRESERVES_JAR.get();
    }
}
