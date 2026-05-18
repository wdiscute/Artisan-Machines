package com.wdiscute.artisan.registry;

import com.wdiscute.artisan.Artisan;
import com.wdiscute.artisan.recipe.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public interface ArtisanRecipeSerializers
{
    DeferredRegister<RecipeSerializer<?>> REGISTRY =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, Artisan.MOD_ID);

    Supplier<RecipeSerializer<AbstractArtisanRecipe>> LOOM = register("loom", LoomRecipe::new);
    Supplier<RecipeSerializer<AbstractArtisanRecipe>> CHEESE_PRESS = register("cheese_press", CheesePressRecipe::new);
    Supplier<RecipeSerializer<AbstractArtisanRecipe>> WINE_KEG = register("wine_keg", WineKegRecipe::new);
    Supplier<RecipeSerializer<AbstractArtisanRecipe>> AGING_CASK = register("aging_cask", AgingCaskRecipe::new);
    Supplier<RecipeSerializer<AbstractArtisanRecipe>> ANCIENT_CASK = register("ancient_cask", AncientCaskRecipe::new);
    Supplier<RecipeSerializer<AbstractArtisanRecipe>> CRYSTALARIUM = register("crystalarium", CrystalariumRecipe::new);
    Supplier<RecipeSerializer<AbstractArtisanRecipe>> DELUXE_WORM_FARM = register("deluxe_worm_farm", DeluxeWormFarmRecipe::new);
    Supplier<RecipeSerializer<AbstractArtisanRecipe>> FISH_SMOKER = register("fish_smoker", FishSmokerRecipe::new);
    Supplier<RecipeSerializer<AbstractArtisanRecipe>> DEHYDRATOR = register("dehydrator", DehydratorRecipe::new);
    Supplier<RecipeSerializer<AbstractArtisanRecipe>> MAYONNAISE_MACHINE = register("mayonnaise_machine", MayonnaiseMachineRecipe::new);


    static Supplier<RecipeSerializer<AbstractArtisanRecipe>> register(
            String name, AbstractArtisanRecipe.Factory<AbstractArtisanRecipe> factory)
    {
        return REGISTRY.register(name, () -> new ArtisanSerializer<>(factory));
    }

    static void register(IEventBus eventBus)
    {
        REGISTRY.register(eventBus);
    }
}
