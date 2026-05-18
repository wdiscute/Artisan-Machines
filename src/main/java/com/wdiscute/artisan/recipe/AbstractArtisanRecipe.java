package com.wdiscute.artisan.recipe;

import com.wdiscute.artisan.registry.ArtisanRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AbstractArtisanRecipe implements Recipe<ArtisanRecipeInput>
{
    RecipeType<?> type;
    List<Ingredient> ingredients;
    ItemStack result;
    int processing_days;

    public AbstractArtisanRecipe(RecipeType<?> type, List<Ingredient> ingredients, ItemStack result, int processing_days)
    {
        this.type = type;
        this.ingredients = ingredients;
        this.result = result;
        this.processing_days = processing_days;
    }

    @Override
    public boolean matches(ArtisanRecipeInput input, Level level)
    {
        List<Ingredient> ingLeftToCheck = new ArrayList<>(ingredients);

        for (ItemStack item : input.items)
        {
            List<Ingredient> matchingIngredients = ingLeftToCheck.stream().filter(o -> o.test(item)).toList();
            if (matchingIngredients.isEmpty()) return false;
            ingLeftToCheck.remove(matchingIngredients.getFirst());
        }

        return ingLeftToCheck.isEmpty();
    }

    @Override
    public ItemStack assemble(ArtisanRecipeInput input, HolderLookup.Provider registries)
    {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height)
    {
        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients()
    {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        nonnulllist.addAll(List.copyOf(this.ingredients));
        return nonnulllist;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries)
    {
        return this.result;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ArtisanRecipeSerializers.LOOM.get();
    }

    @Override
    public @NotNull RecipeType<?> getType()
    {
        return type;
    }

    public interface Factory<T extends AbstractArtisanRecipe>
    {
        T create(List<Ingredient> ingredients, ItemStack result, int processing_days);
    }

}
