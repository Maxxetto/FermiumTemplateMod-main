package net.maxxetto.maxxiummod.item;

import artifacts.common.item.BaubleBase;
import baubles.api.BaubleType;
import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.baubleeffect.IJumpBoost;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import artifacts.common.init.ModNetworkHandler;
import artifacts.common.init.ModSoundEvents;
import artifacts.common.network.PacketBottledCloudJump;
import baubles.api.BaublesApi;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.SoundEvents;

@EventBusSubscriber
public class BalloonBundle extends BaubleBase implements IJumpBoost {

    public final boolean isFart;
    @SideOnly(Side.CLIENT)
    private static boolean canDoubleJump;
    @SideOnly(Side.CLIENT)
    private static boolean canTripleJump;
    @SideOnly(Side.CLIENT)
    private static boolean canQuadrupleJump;
    @SideOnly(Side.CLIENT)
    private static boolean hasReleasedJumpKey;
    @SideOnly(Side.CLIENT)
    private static int tickSinceLastJump;
    @SideOnly(Side.CLIENT)
    private static int tickSinceLastJumpTWO;

    public BalloonBundle(String name, boolean isFart) {
        super(name, BaubleType.TRINKET);
        this.isFart = isFart;
        this.setCreativeTab(BountifulBaubles.TAB);
        this.setMaxStackSize(1);
    }

    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        if (event.getEntity() instanceof EntityPlayer && BaublesApi.isBaubleEquipped((EntityPlayer)event.getEntity(), ModItems.balloonBundle) != -1) {
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

    public float getJumpBoost() {
        return 0.3F;
    }

    public float getFallResist() {
        return 12.0F;
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onClientTick(TickEvent.ClientTickEvent event) {

        if (canDoubleJump || !hasReleasedJumpKey) {
            tickSinceLastJump = 0;
        } else {
            tickSinceLastJump++;
        }

        if (!canDoubleJump && canTripleJump) {
            tickSinceLastJumpTWO = 0;
        } else {
            tickSinceLastJumpTWO++;
        }

        if (event.phase == TickEvent.Phase.END) {
            EntityPlayerSP player = Minecraft.getMinecraft().player;
            if (player != null) {
                if ((player.onGround || player.isOnLadder()) && !player.isInWater()) {
                    hasReleasedJumpKey = false;
                    canDoubleJump = true;
                    canTripleJump = false;
                    canQuadrupleJump = false;
                } else if (!player.movementInput.jump) {
                    hasReleasedJumpKey = true;
                } else if (!player.capabilities.isFlying && canDoubleJump && hasReleasedJumpKey) {
                    canDoubleJump = false;
                    canTripleJump = true;
                    canQuadrupleJump = false;
                    tickSinceLastJump = 0;
                    ItemStack stack = BaublesApi.getBaublesHandler(player).getStackInSlot(BaubleType.TRINKET.getValidSlots()[0]);
                    if (stack.getItem() instanceof BalloonBundle) {
                        ModNetworkHandler.NETWORK_HANDLER_INSTANCE.sendToServer(new PacketBottledCloudJump(((BalloonBundle) stack.getItem()).isFart));
                        player.jump();
                        player.fallDistance = 0.0F;
                        player.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1F, 0.8F + player.getRNG().nextFloat() * 0.4F);
                    }
                } else if (!player.capabilities.isFlying && canTripleJump && hasReleasedJumpKey) {
                    if (tickSinceLastJump > 25) {
                        canTripleJump = false;
                        canQuadrupleJump = true;
                        tickSinceLastJumpTWO = 0;
                        ItemStack stack = BaublesApi.getBaublesHandler(player).getStackInSlot(BaubleType.TRINKET.getValidSlots()[0]);
                        if (stack.getItem() instanceof BalloonBundle) {
                            ModNetworkHandler.NETWORK_HANDLER_INSTANCE.sendToServer(new PacketBottledCloudJump(((BalloonBundle) stack.getItem()).isFart));
                            player.jump();
                            player.fallDistance = 0.0F;
                            player.playSound(ModSoundEvents.FART, 1.3F, 0.8F + player.getRNG().nextFloat() * 0.4F);
                        }
                    }
                } else if (!player.capabilities.isFlying && canQuadrupleJump && hasReleasedJumpKey) {
                    if (tickSinceLastJumpTWO > 25) {
                        canQuadrupleJump = false;
                        ItemStack stack = BaublesApi.getBaublesHandler(player).getStackInSlot(BaubleType.TRINKET.getValidSlots()[0]);
                        if (stack.getItem() instanceof BalloonBundle) {
                            ModNetworkHandler.NETWORK_HANDLER_INSTANCE.sendToServer(new PacketBottledCloudJump(((BalloonBundle) stack.getItem()).isFart));
                            player.jump();
                            player.fallDistance = 0.0F;
                            player.playSound(SoundEvents.ENTITY_HORSE_JUMP, 1.3F, 0.8F + player.getRNG().nextFloat() * 0.4F);
                        }
                    }
                }
            }

        }
    }
}
