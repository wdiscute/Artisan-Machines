package com.wdiscute.artisan.datagen;

import com.wdiscute.artisan.ChancedStack;
import com.wdiscute.artisan.recipe.*;
import com.wdiscute.artisan.registry.ArtisanItems;
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
        ItemStack loom = Items.WHITE_WOOL.getDefaultInstance();
        loom.setCount(4);
        new ArtisanRecipeBuilder(
                loom,
                LoomRecipe::new,
                8,
                Ingredient.of(Items.STRING),
                Ingredient.of(Items.STRING),
                Ingredient.of(Items.STRING),
                Ingredient.of(Items.STRING)
        )
                .group("loom")
                .unlockedBy("has_string", has(Items.STRING))
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
                Items.SPLASH_POTION.getDefaultInstance(),
                AgingCaskRecipe::new,
                48,
                Ingredient.of(Items.SWEET_BERRIES)
        )
                .group("aging_cask")
                .unlockedBy("has_grapes", has(Items.SWEET_BERRIES))
                .save(output);

        //ancient cask
        new ArtisanRecipeBuilder(
                Items.LINGERING_POTION.getDefaultInstance(),
                AncientCaskRecipe::new,
                8,
                List.of(),
                List.of(Ingredient.of(ArtisanItems.INSERTER)),
                Ingredient.of(Items.SWEET_BERRIES)
        )
                .group("ancient_cask")
                .unlockedBy("has_grapes", has(Items.SWEET_BERRIES))
                .save(output);

        //ancient cask - upgraded
        new ArtisanRecipeBuilder(
                List.of(
                        new ChancedStack(Items.LINGERING_POTION.getDefaultInstance(), 1),
                        new ChancedStack(Items.LINGERING_POTION.getDefaultInstance(), 1),
                        new ChancedStack(Items.LINGERING_POTION.getDefaultInstance(), 1),
                        new ChancedStack(Items.LINGERING_POTION.getDefaultInstance(), 1)
                ),
                AncientCaskRecipe::new,
                8,
                List.of(Ingredient.of(ArtisanItems.INSERTER)),
                List.of(),
                Ingredient.of(Items.SWEET_BERRIES)
        )
                .group("ancient_cask")
                .unlockedBy("has_grapes", has(Items.SWEET_BERRIES))
                .save(output, "upgraded");

        //crystalarium
        ItemStack crystalarium = Items.DIAMOND.getDefaultInstance();
        crystalarium.setCount(2);
        new ArtisanRecipeBuilder(
                crystalarium,
                CrystalariumRecipe::new,
                8,
                Ingredient.of(Items.DIAMOND)
        )
                .group("crystalarium")
                .unlockedBy("has_diamond", has(Items.DIAMOND))
                .save(output);

        //deluxe worm farm
        new ArtisanRecipeBuilder(
                Items.SLIME_BALL.getDefaultInstance(),
                DeluxeWormFarmRecipe::new,
                4,
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
                List.of(
                        new ChancedStack(Items.HAY_BLOCK.getDefaultInstance(), 1)
                ),
                DehydratorRecipe::new,
                8,
                Ingredient.of(ItemTags.VILLAGER_PLANTABLE_SEEDS),
                Ingredient.of(ItemTags.VILLAGER_PLANTABLE_SEEDS),
                Ingredient.of(ItemTags.VILLAGER_PLANTABLE_SEEDS),
                Ingredient.of(ItemTags.VILLAGER_PLANTABLE_SEEDS),
                Ingredient.of(ItemTags.VILLAGER_PLANTABLE_SEEDS),
                Ingredient.of(ItemTags.VILLAGER_PLANTABLE_SEEDS),
                Ingredient.of(ItemTags.VILLAGER_PLANTABLE_SEEDS),
                Ingredient.of(ItemTags.VILLAGER_PLANTABLE_SEEDS),
                Ingredient.of(ItemTags.VILLAGER_PLANTABLE_SEEDS)
        )
                .group("dehydrator")
                .unlockedBy("has_seeds", has(ItemTags.VILLAGER_PLANTABLE_SEEDS))
                .save(output);

        //dehydrator upgraded
        new ArtisanRecipeBuilder(
                List.of(
                        new ChancedStack(Items.HAY_BLOCK.getDefaultInstance(), 1),
                        new ChancedStack(Items.HAY_BLOCK.getDefaultInstance(), 1)
                ),
                DehydratorRecipe::new,
                8,
                Ingredient.of(ItemTags.VILLAGER_PLANTABLE_SEEDS),
                Ingredient.of(ItemTags.VILLAGER_PLANTABLE_SEEDS),
                Ingredient.of(ItemTags.VILLAGER_PLANTABLE_SEEDS),
                Ingredient.of(ItemTags.VILLAGER_PLANTABLE_SEEDS),
                Ingredient.of(ItemTags.VILLAGER_PLANTABLE_SEEDS),
                Ingredient.of(ItemTags.VILLAGER_PLANTABLE_SEEDS),
                Ingredient.of(ItemTags.VILLAGER_PLANTABLE_SEEDS),
                Ingredient.of(ItemTags.VILLAGER_PLANTABLE_SEEDS),
                Ingredient.of(ItemTags.VILLAGER_PLANTABLE_SEEDS)
        )
                .group("dehydrator")
                .unlockedBy("has_seeds", has(ItemTags.VILLAGER_PLANTABLE_SEEDS))
                .save(output, "upgraded");

        //mayonnaise machine
        ItemStack mayo = Items.WHITE_CARPET.getDefaultInstance();
        mayo.set(DataComponents.CUSTOM_NAME, Component.literal("Mayo"));
        new ArtisanRecipeBuilder(
                mayo,
                MayonnaiseMachineRecipe::new,
                4,
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
                Ingredient.of(Items.CACTUS),
                Ingredient.of(Items.CACTUS),
                Ingredient.of(Items.CACTUS),
                Ingredient.of(Items.CACTUS)
        )
                .group("preserves_jar")
                .unlockedBy("has_cactus", has(Items.CACTUS))
                .save(output);

        //preserves jar - Upgraded
        new ArtisanRecipeBuilder(
                Items.SEA_PICKLE.getDefaultInstance(),
                PreservesJarRecipe::new,
                2,
                List.of(),
                List.of(Ingredient.of(ArtisanItems.STONE_HAND)),
                Ingredient.of(Items.CACTUS),
                Ingredient.of(Items.CACTUS)
        )
                .group("preserves_jar")
                .unlockedBy("has_cactus", has(Items.CACTUS))
                .save(output, "upgraded");

        //seed maker
        ItemStack wheatSeeds = Items.WHEAT_SEEDS.getDefaultInstance();
        wheatSeeds.setCount(10);
        new ArtisanRecipeBuilder(
                wheatSeeds,
                SeedMakerRecipe::new,
                2,
                Ingredient.of(Items.SHORT_GRASS),
                Ingredient.of(Items.SHORT_GRASS),
                Ingredient.of(Items.SHORT_GRASS)
        )
                .group("seed_maker")
                .unlockedBy("has_grass", has(Items.SHORT_GRASS))
                .save(output);

        //recycling machine
        new ArtisanRecipeBuilder(
                List.of(
                        new ChancedStack(Items.RAW_COPPER.getDefaultInstance(), 0.3f),
                        new ChancedStack(Items.RAW_IRON.getDefaultInstance(), 0.1f),
                        new ChancedStack(Items.LAPIS_LAZULI.getDefaultInstance(), 0.1f),
                        new ChancedStack(Items.DIAMOND.getDefaultInstance(), 0.01f),
                        new ChancedStack(Items.COBBLESTONE.getDefaultInstance(), 0.7f),
                        new ChancedStack(Items.DEEPSLATE.getDefaultInstance(), 0.6f)
                ),
                RecyclingMachineRecipe::new,
                2,
                Ingredient.of(ItemTags.STONE_CRAFTING_MATERIALS),
                Ingredient.of(ItemTags.STONE_CRAFTING_MATERIALS),
                Ingredient.of(ItemTags.STONE_CRAFTING_MATERIALS),
                Ingredient.of(ItemTags.STONE_CRAFTING_MATERIALS),
                Ingredient.of(ItemTags.STONE_CRAFTING_MATERIALS)
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
        ItemStack oil = Items.BLACK_CANDLE.getDefaultInstance();
        oil.set(DataComponents.CUSTOM_NAME, Component.literal("Oil"));
        new ArtisanRecipeBuilder(
                oil,
                OilMakerRecipe::new,
                2,
                Ingredient.of(Items.TROPICAL_FISH)
        )
                .group("oil_maker")
                .unlockedBy("has_tropical_fish", has(Items.TROPICAL_FISH))
                .save(output);


    }
}
