package com.wdiscute.artisan.registry;

import com.wdiscute.artisan.Artisan;
import com.wdiscute.artisan.recipe.AbstractArtisanRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public interface ArtisanRecipeTypes
{
    DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, Artisan.MOD_ID);

    Supplier<RecipeType<AbstractArtisanRecipe>> LOOM = register("loom");
    Supplier<RecipeType<AbstractArtisanRecipe>> CHEESE_PRESS = register("cheese_press");
    Supplier<RecipeType<AbstractArtisanRecipe>> WINE_KEG = register("wine_keg");
    Supplier<RecipeType<AbstractArtisanRecipe>> AGING_CASK = register("aging_cask");
    Supplier<RecipeType<AbstractArtisanRecipe>> ANCIENT_CASK = register("ancient_cask");
    Supplier<RecipeType<AbstractArtisanRecipe>> CRYSTALARIUM = register("crystalarium");
    Supplier<RecipeType<AbstractArtisanRecipe>> DELUXE_WORM_FARM = register("deluxe_worm_farm");
    Supplier<RecipeType<AbstractArtisanRecipe>> FISH_SMOKER = register("fish_smoker");
    Supplier<RecipeType<AbstractArtisanRecipe>> DEHYDRATOR = register("dehydrator");
    Supplier<RecipeType<AbstractArtisanRecipe>> MAYONNAISE_MACHINE = register("mayonnaise_machine");
    Supplier<RecipeType<AbstractArtisanRecipe>> PRESERVES_JAR = register("preserves_jar");
    Supplier<RecipeType<AbstractArtisanRecipe>> SEED_MAKER = register("seed_maker");
    Supplier<RecipeType<AbstractArtisanRecipe>> RECYCLING_MACHINE = register("recycling_machine");
    Supplier<RecipeType<AbstractArtisanRecipe>> BAIT_MAKER = register("bait_maker");
    Supplier<RecipeType<AbstractArtisanRecipe>> OIL_MAKER = register("oil_maker");




    static Supplier<RecipeType<AbstractArtisanRecipe>> register(String name)
    {
        return RECIPE_TYPES.register(name, simple(name));
    }

    static Supplier<RecipeType<AbstractArtisanRecipe>> simple(String name)
    {
        final String toString = Artisan.rl(name).toString();
        return () -> new RecipeType<>()
        {
            @Override
            public String toString()
            {
                return toString;
            }
        };
    }

    static void register(IEventBus eventBus)
    {
        RECIPE_TYPES.register(eventBus);
    }
}
