package com.wdiscute.artisan.recipe;

import com.wdiscute.artisan.ChancedStack;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractArtisanRecipe implements Recipe<ArtisanRecipeInput>
{
    RecipeType<?> type;
    List<Ingredient> ingredients;
    List<ChancedStack> result;
    int processing_hours;

    public AbstractArtisanRecipe(RecipeType<?> type, List<Ingredient> ingredients, List<ChancedStack> result, int processing_hours)
    {
        this.type = type;
        this.ingredients = ingredients;
        this.result = result;
        this.processing_hours = processing_hours;
    }

    public AbstractArtisanRecipe(RecipeType<?> type, List<Ingredient> ingredients, ItemStack result, int processing_hours)
    {
        this.type = type;
        this.ingredients = ingredients;
        this.result = List.of(new ChancedStack(result, 1));
        this.processing_hours = processing_hours;
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
        return this.result.getFirst().stack();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height)
    {
        return true;
    }

    public int getHours()
    {
        return processing_hours;
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
        return this.result.getFirst().stack();
    }

    public List<ChancedStack> getResult()
    {
        return this.result;
    }

    @Override
    public @NotNull RecipeType<?> getType()
    {
        return type;
    }

    public interface Factory<T extends AbstractArtisanRecipe>
    {
        T create(List<Ingredient> ingredients, List<ChancedStack> result, int processing_hours);
    }
}
