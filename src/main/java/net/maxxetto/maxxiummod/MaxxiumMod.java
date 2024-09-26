package net.maxxetto.maxxiummod;

import net.maxxetto.maxxiummod.item.ModItems;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.maxxetto.maxxiummod.handlers.ModRegistry;
import net.maxxetto.maxxiummod.proxy.CommonProxy;
import net.maxxetto.maxxiummod.recipe.ModdedAnvilRecipes;

@Mod(modid = MaxxiumMod.MODID, version = MaxxiumMod.VERSION, name = MaxxiumMod.NAME, dependencies = "required-after:fermiumbooter")
public class MaxxiumMod {
    public static final String MODID = "maxxiummod";  // You can also update the MODID to a unique identifier.
    public static final String VERSION = "1.0";  // Update version as needed
    public static final String NAME = "Maxxium RLCraft Expansion Mod";  // Updated mod name
    public static final Logger LOGGER = LogManager.getLogger();

    @SidedProxy(clientSide = "net.maxxetto.maxxiummod.proxy.ClientProxy", serverSide = "net.maxxetto.maxxiummod.proxy.CommonProxy")
    public static CommonProxy PROXY;

    @Mod.Instance(MaxxiumMod.MODID)
    public static MaxxiumMod instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModRegistry.init();
        MaxxiumMod.PROXY.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        ModdedAnvilRecipes.registerRecipes();  // Register the custom anvil recipes
    }
}
