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

        //ancient cask
        new ArtisanRecipeBuilder(
                Items.POTION.getDefaultInstance(),
                AncientCaskRecipe::new,
                8,
                Ingredient.of(Items.BIG_DRIPLEAF)
        )
                .group("ancient_cask")
                .unlockedBy("has_big_dripleaf", has(Items.BIG_DRIPLEAF))
                .save(output);

        //crystalarium
        new ArtisanRecipeBuilder(
                Items.DIAMOND.getDefaultInstance(),
                CrystalariumRecipe::new,
                8,
                Ingredient.of(Items.COAL)
        )
                .group("crystalarium")
                .unlockedBy("has_coal", has(Items.COAL))
                .save(output);

        //deluxe worm farm
        new ArtisanRecipeBuilder(
                Items.SLIME_BALL.getDefaultInstance(),
                DeluxeWormFarmRecipe::new,
                8,
                Ingredient.of(Items.DIRT)
        )
                .group("deluxe_worm_farm")
                .unlockedBy("has_dirt", has(Items.DIRT))
                .save(output);

        //fish smoker
        new ArtisanRecipeBuilder(
                Items.COOKED_COD.getDefaultInstance(),
                FishSmokerRecipe::new,
                8,
                Ingredient.of(Items.COD)
        )
                .group("fish_smoker")
                .unlockedBy("has_cod", has(Items.COD))
                .save(output);


        //dehydrator
        new ArtisanRecipeBuilder(
                Items.BONE.getDefaultInstance(),
                DehydratorRecipe::new,
                8,
                Ingredient.of(Items.COD)
        )
                .group("dehydrator")
                .unlockedBy("has_cod", has(Items.COD))
                .save(output);

        //mayonnaise machine
        new ArtisanRecipeBuilder(
                Items.WHITE_BANNER.getDefaultInstance(),
                MayonnaiseMachineRecipe::new,
                2,
                Ingredient.of(Items.EGG)
        )
                .group("mayonnaise_machine")
                .unlockedBy("has_egg", has(Items.EGG))
                .save(output);

        //preserves jar
        new ArtisanRecipeBuilder(
                Items.SEA_PICKLE.getDefaultInstance(),
                PreservesJarRecipe::new,
                2,
                Ingredient.of(Items.CACTUS)
        )
                .group("preserves_jar")
                .unlockedBy("has_cactus", has(Items.CACTUS))
                .save(output);

        //seed maker
        ItemStack wheatSeeds = Items.WHEAT_SEEDS.getDefaultInstance();
        wheatSeeds.setCount(10);
        new ArtisanRecipeBuilder(
                wheatSeeds,
                SeedMakerRecipe::new,
                2,
                Ingredient.of(Items.SHORT_GRASS)
        )
                .group("seed_maker")
                .unlockedBy("has_grass", has(Items.SHORT_GRASS))
                .save(output);

        //recycling machine
        new ArtisanRecipeBuilder(
                Items.DIAMOND.getDefaultInstance(),
                RecyclingMachineRecipe::new,
                2,
                Ingredient.of(Items.STONE)
        )
                .group("recycling_machine")
                .unlockedBy("has_stone", has(Items.STONE))
                .save(output);


    }
}
