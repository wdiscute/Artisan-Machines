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
