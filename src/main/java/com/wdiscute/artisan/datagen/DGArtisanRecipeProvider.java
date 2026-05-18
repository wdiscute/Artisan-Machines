package com.wdiscute.artisan.datagen;

import com.wdiscute.artisan.recipe.ArtisanRecipeBuilder;
import com.wdiscute.artisan.recipe.ArtisanRecipeInput;
import com.wdiscute.artisan.recipe.LoomRecipe;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DGArtisanRecipeProvider extends RecipeProvider
{
    public DGArtisanRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries)
    {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output)
    {
        //loom
        new ArtisanRecipeBuilder(
                Items.DIAMOND.getDefaultInstance(),
                LoomRecipe::new,
                1,
                Ingredient.of(ItemTags.WOOL),
                Ingredient.of(ItemTags.WOOL),
                Ingredient.of(ItemTags.WOOL)
        )
                .unlockedBy("has_wool", has(ItemTags.WOOL))
                .save(output);





    }
}
