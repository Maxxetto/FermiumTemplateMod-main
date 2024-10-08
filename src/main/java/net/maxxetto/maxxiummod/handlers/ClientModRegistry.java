package net.maxxetto.maxxiummod.handlers;

import net.maxxetto.maxxiummod.item.ModItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.maxxetto.maxxiummod.MaxxiumMod;

@Mod.EventBusSubscriber(modid = MaxxiumMod.MODID, value = Side.CLIENT)
public class ClientModRegistry {

    @SubscribeEvent
    public static void modelRegisterEvent(ModelRegistryEvent event) {
        registerModels(
                ModRegistry.exampleHelmet, ModRegistry.exampleChestplate, ModRegistry.exampleLeggings, ModRegistry.exampleBoots,
                ModItems.luckyRedBalloon,
                ModItems.BALLOON_CLOUD, ModItems.BALLOON_FART,
                ModItems.fartCloudBalloon,
                ModItems.balloonBundle
        );
    }

    private static void registerModels(Item... values) {
        for(Item entry : values) {
            ModelLoader.setCustomModelResourceLocation(entry, 0, new ModelResourceLocation(entry.getRegistryName(), "inventory"));
        }
    }
}