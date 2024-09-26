package net.maxxetto.maxxiummod.recipe;

import artifacts.common.init.ModItems;
import cursedflames.bountifulbaubles.recipe.AnvilRecipes;
import net.minecraft.item.ItemStack;

public class ModdedAnvilRecipes {

    public static void registerRecipes() {
        // Adding the Anvil Recipe for Lucky Red Balloon
        AnvilRecipes.add(cursedflames.bountifulbaubles.item.ModItems.balloon, cursedflames.bountifulbaubles.item.ModItems.trinketLuckyHorseshoe, 10, new ItemStack(net.maxxetto.maxxiummod.item.ModItems.luckyRedBalloon));
        // Adding the Anvil Recipe for Fart in a Balloon
        AnvilRecipes.add(cursedflames.bountifulbaubles.item.ModItems.balloon, ModItems.BOTTLED_CLOUD, 10, new ItemStack(net.maxxetto.maxxiummod.item.ModItems.BALLOON_CLOUD));
        // Adding the Anvil Recipe for Cloud in a Balloon
        AnvilRecipes.add(cursedflames.bountifulbaubles.item.ModItems.balloon, ModItems.BOTTLED_FART, 10, new ItemStack(net.maxxetto.maxxiummod.item.ModItems.BALLOON_FART));
        // Adding the Anvil Recipe for Twin Balloons
        AnvilRecipes.add(net.maxxetto.maxxiummod.item.ModItems.BALLOON_FART, net.maxxetto.maxxiummod.item.ModItems.BALLOON_CLOUD, 10, new ItemStack(net.maxxetto.maxxiummod.item.ModItems.fartCloudBalloon));
        // Adding the Anvil Recipe for Twin Balloons
        AnvilRecipes.add(net.maxxetto.maxxiummod.item.ModItems.luckyRedBalloon, net.maxxetto.maxxiummod.item.ModItems.fartCloudBalloon, 10, new ItemStack(net.maxxetto.maxxiummod.item.ModItems.balloonBundle));
    }
}
