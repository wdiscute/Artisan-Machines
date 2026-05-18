package com.wdiscute.artisan.datagen;

import com.wdiscute.artisan.recipe.*;
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
                24,
                Ingredient.of(ItemTags.WOOL),
                Ingredient.of(ItemTags.WOOL),
                Ingredient.of(ItemTags.WOOL)
        )
                .group("loom")
                .unlockedBy("has_wool", has(ItemTags.WOOL))
                .save(output);

        //cheese press
        new ArtisanRecipeBuilder(
                Items.YELLOW_WOOL.getDefaultInstance(),
                CheesePressRecipe::new,
                8,
                Ingredient.of(Items.MILK_BUCKET)
        )
                .group("cheese_press")
                .unlockedBy("has_milk", has(Items.MILK_BUCKET))
                .save(output);

        //wine keg
        new ArtisanRecipeBuilder(
                Items.POTION.getDefaultInstance(),
                WineKegRecipe::new,
                8,
                Ingredient.of(Items.SWEET_BERRIES)
        )
                .group("wine_keg")
                .unlockedBy("has_grapes", has(Items.SWEET_BERRIES))
                .save(output);

        //aging cask
        new ArtisanRecipeBuilder(
                Items.POTION.getDefaultInstance(),
                AgingCaskRecipe::new,
                48,
                Ingredient.of(Items.GLOW_BERRIES)
        )
                .group("aging_cask")
                .unlockedBy("has_glow_berries", has(Items.GLOW_BERRIES))
                .save(output);


    }
}
