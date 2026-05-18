package com.wdiscute.artisan.registry;

import com.wdiscute.artisan.Artisan;
import com.wdiscute.artisan.recipe.AbstractArtisanRecipe;
import com.wdiscute.artisan.recipe.ArtisanSerializer;
import com.wdiscute.artisan.recipe.LoomRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public interface ArtisanRecipeSerializers
{
    DeferredRegister<RecipeSerializer<?>> REGISTRY =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, Artisan.MOD_ID);

     Supplier<RecipeSerializer<AbstractArtisanRecipe>> LOOM = REGISTRY.register("loom", () -> new ArtisanSerializer<>(LoomRecipe::new));




    static void register(IEventBus eventBus)
    {
        REGISTRY.register(eventBus);
    }
}
