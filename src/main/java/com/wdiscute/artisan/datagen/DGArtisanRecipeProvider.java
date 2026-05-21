package com.wdiscute.artisan.datagen;

import com.wdiscute.artisan.ChancedStack;
import com.wdiscute.artisan.recipe.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

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
        ItemStack shinyBone = Items.BONE.getDefaultInstance();
        shinyBone.setCount(2);
        shinyBone.set(DataComponents.CUSTOM_NAME, Component.literal("Super Shiny Bone"));
        new ArtisanRecipeBuilder(
                List.of(
                        new ChancedStack(Items.BONE.getDefaultInstance(), 1),
                        new ChancedStack(shinyBone, 0.1f)
                ),
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

        //bait maker
        new ArtisanRecipeBuilder(
                Items.BONE.getDefaultInstance(),
                BaitMakerRecipe::new,
                2,
                Ingredient.of(Items.SALMON)
        )
                .group("bait_maker")
                .unlockedBy("has_salmon", has(Items.SALMON))
                .save(output);

        //oil maker
        new ArtisanRecipeBuilder(
                Items.BLACK_CANDLE.getDefaultInstance(),
                OilMakerRecipe::new,
                2,
                Ingredient.of(Items.TROPICAL_FISH)
        )
                .group("oil_maker")
                .unlockedBy("has_tropical_fish", has(Items.TROPICAL_FISH))
                .save(output);


    }
}
