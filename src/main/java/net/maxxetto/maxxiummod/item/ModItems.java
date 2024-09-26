package net.maxxetto.maxxiummod.item;

import artifacts.common.item.BaubleBase;
import net.maxxetto.maxxiummod.MaxxiumMod;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = MaxxiumMod.MODID)
public class ModItems {
    public static final BaubleBase BALLOON_CLOUD;
    public static final BaubleBase BALLOON_FART;
    public static final BaubleBase luckyRedBalloon;
    public static final BaubleBase fartCloudBalloon;
    public static final BaubleBase balloonBundle;

    @SubscribeEvent
    public static void registerItemEvent(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                luckyRedBalloon,
                BALLOON_FART,
                BALLOON_CLOUD,
                fartCloudBalloon,
                balloonBundle
        );
    }

    static {
        BALLOON_CLOUD = (new FartBalloon("balloon_cloud", false));
        BALLOON_FART = (new FartBalloon("balloon_fart", true));
        luckyRedBalloon = (new LuckyRedBalloon("lucky_red_balloon"));
        fartCloudBalloon = (new FartCloudBalloon("fart_cloud_balloon", true));
        balloonBundle = (new BalloonBundle("balloon_bundle", true));
    }
}
