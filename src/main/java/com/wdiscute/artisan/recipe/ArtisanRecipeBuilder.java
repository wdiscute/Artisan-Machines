package com.wdiscute.artisan.recipe;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nullable;
import java.util.*;

public class ArtisanRecipeBuilder implements RecipeBuilder
{
    private final ItemStack stackResult;
    private final List<Ingredient> ingredients;
    private final int processing_time;
    private String group;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    private final AbstractArtisanRecipe.Factory<?> factory;

    public ArtisanRecipeBuilder(ItemStack result, AbstractArtisanRecipe.Factory<?> factory, int processing_time, Ingredient... ingredients)
    {
        this.ingredients = Arrays.stream(ingredients).toList();
        this.stackResult = result;
        this.processing_time = processing_time;
        this.factory = factory;
    }

    public ArtisanRecipeBuilder unlockedBy(String name, Criterion<?> criterion)
    {
        this.criteria.put(name, criterion);
        return this;
    }

    public ArtisanRecipeBuilder group(@Nullable String groupName)
    {
        this.group = groupName;
        return this;
    }

    @Override
    public Item getResult()
    {
        return this.stackResult.getItem();
    }

    @Override
    public void save(RecipeOutput recipeOutput)
    {
        save(recipeOutput, ResourceLocation.withDefaultNamespace(
                BuiltInRegistries.ITEM.getKey(stackResult.getItem()).getPath()
                        + "_from_" + group));
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id)
    {
        this.ensureValid(id);

        Advancement.Builder advancement$builder = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);

        this.criteria.forEach(advancement$builder::addCriterion);

        AbstractArtisanRecipe abstractArtisanRecipe = this.factory
                .create(this.ingredients, this.stackResult, this.processing_time);

        recipeOutput.accept(id, abstractArtisanRecipe, advancement$builder
                .build(id.withPrefix("recipes/")));
    }

    private void ensureValid(ResourceLocation id)
    {
        if (this.criteria.isEmpty())
        {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }
}
