package com.wdiscute.artisan.compat;

import de.cadentem.quality_food.core.codecs.Quality;
import de.cadentem.quality_food.registry.QFComponents;
import net.minecraft.core.HolderSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class QualityFoodCompat
{
    public static void applyLowestQualityToItem(List<ItemStack> inputs, List<ItemStack> results, HolderSet<Item> excludedOutputs)
    {
        Quality lowestQuality = Quality.NONE;

        for (ItemStack input : inputs)
        {
            Quality quality = input.get(QFComponents.QUALITY_DATA_COMPONENT);
            if (quality == null) continue;
            if (quality.level() > lowestQuality.level())
                lowestQuality = quality;
        }

        if (lowestQuality == Quality.NONE) return;

        //for every result
        for (ItemStack result : results)
        {
            //if result is excluded, skip
            if (excludedOutputs.stream().anyMatch(o -> o.equals(result.getItemHolder()))) continue;

            Quality resultQuality = result.get(QFComponents.QUALITY_DATA_COMPONENT);

            //if result doesn't have quality, set to lowest
            if (resultQuality == null)
            {
                result.set(QFComponents.QUALITY_DATA_COMPONENT, lowestQuality);
            }
            else
            {
                //if result has quality and result has lower quality than lowest, set to lowest
                if (resultQuality.level() < lowestQuality.level())
                    result.set(QFComponents.QUALITY_DATA_COMPONENT, lowestQuality);
            }
        }
    }
}
