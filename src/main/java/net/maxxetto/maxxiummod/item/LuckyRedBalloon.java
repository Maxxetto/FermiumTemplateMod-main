package net.maxxetto.maxxiummod.item;

import artifacts.common.item.BaubleBase;
import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.baubleeffect.IJumpBoost;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.init.SoundEvents;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.init.MobEffects;
import net.minecraft.util.math.MathHelper;

// this item increases jump height by 1 extra block compared to the Red Balloon and completely negates fall damage

@EventBusSubscriber
public class LuckyRedBalloon extends BaubleBase implements IJumpBoost {

    public LuckyRedBalloon(String name) {
        super(name, BaubleType.TRINKET);
        this.setCreativeTab(BountifulBaubles.TAB);
        this.setMaxStackSize(1);
    }

    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        if (event.getEntity() instanceof EntityPlayer && BaublesApi.isBaubleEquipped((EntityPlayer)event.getEntity(), ModItems.luckyRedBalloon) != -1) {
            if (event.getDistance() > 5.0F) {
                PotionEffect potioneffect = event.getEntityLiving().getActivePotionEffect(MobEffects.JUMP_BOOST); // func_70660_b || field_76430_j
                float f = potioneffect == null ? 0.0F : (float)(potioneffect.getAmplifier() + 1); // func_76458_c
                int i = MathHelper.ceil((event.getDistance() - 3.0F - f) * event.getDamageMultiplier()); // func_76123_f <- ceil(float)
                if (i > 0) {
                    event.getEntity().playSound(SoundEvents.ENTITY_GENERIC_SMALL_FALL, 1.0F, 1.0F); // func_184185_a || field_187545_bE
                }
            }

            event.setCanceled(true);
        }
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.TRINKET;  // Define it as a trinket bauble
    }

    public float getJumpBoost() {
        return 0.3F;
    }

    public float getFallResist() {
        return 5.0F;
    }
}
