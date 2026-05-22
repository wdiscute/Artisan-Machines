package com.wdiscute.artisan.recipe;

import com.wdiscute.artisan.ChancedStack;
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
    private final List<ChancedStack> stackResult;
    private final List<Ingredient> ingredients;
    private final int processing_time;
    private final List<Ingredient> requiredUpgrades;
    private final List<Ingredient> blacklistedUpgrades;
    private String group;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    private final AbstractArtisanRecipe.Factory<?> factory;

    public ArtisanRecipeBuilder(List<ChancedStack> result, AbstractArtisanRecipe.Factory<?> factory, int processing_time, List<Ingredient> requiredUpgrades, List<Ingredient> blacklistedUpgrades, Ingredient... ingredients)
    {
        this.ingredients = Arrays.stream(ingredients).toList();
        this.stackResult = result;
        this.processing_time = processing_time;
        this.factory = factory;
        this.requiredUpgrades = requiredUpgrades;
        this.blacklistedUpgrades = blacklistedUpgrades;
    }

    public ArtisanRecipeBuilder(List<ChancedStack> result, AbstractArtisanRecipe.Factory<?> factory, int processing_time, Ingredient... ingredients)
    {
        this.ingredients = Arrays.stream(ingredients).toList();
        this.stackResult = result;
        this.processing_time = processing_time;
        this.factory = factory;
        this.requiredUpgrades = List.of();
        this.blacklistedUpgrades = List.of();
    }

    public ArtisanRecipeBuilder(ItemStack result, AbstractArtisanRecipe.Factory<?> factory, int processing_time, List<Ingredient> requiredUpgrades, List<Ingredient> blacklistedUpgrades, Ingredient... ingredients)
    {
        this.ingredients = Arrays.stream(ingredients).toList();
        this.stackResult = List.of(new ChancedStack(result, 1));
        this.processing_time = processing_time;
        this.factory = factory;
        this.requiredUpgrades = requiredUpgrades;
        this.blacklistedUpgrades = blacklistedUpgrades;
    }

    public ArtisanRecipeBuilder(ItemStack result, AbstractArtisanRecipe.Factory<?> factory, int processing_time, Ingredient... ingredients)
    {
        this.ingredients = Arrays.stream(ingredients).toList();
        this.stackResult = List.of(new ChancedStack(result, 1));
        this.processing_time = processing_time;
        this.factory = factory;
        this.requiredUpgrades = List.of();
        this.blacklistedUpgrades = List.of();
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
        return this.stackResult.getFirst().stack().getItem();
    }

    @Override
    public void save(RecipeOutput recipeOutput)
    {
        save(recipeOutput, ResourceLocation.withDefaultNamespace(
                BuiltInRegistries.ITEM.getKey(stackResult.getFirst().stack().getItem()).getPath()
                        + "_from_" + group));
    }


    @Override
    public void save(RecipeOutput recipeOutput, String append)
    {
        save(recipeOutput, ResourceLocation.withDefaultNamespace(
                BuiltInRegistries.ITEM.getKey(stackResult.getFirst().stack().getItem()).getPath()
                        + "_from_" + group + "_" + append));
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
                .create(this.ingredients, this.stackResult, this.processing_time, this.requiredUpgrades, this.blacklistedUpgrades);

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
