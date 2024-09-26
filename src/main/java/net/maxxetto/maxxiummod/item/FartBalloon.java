package net.maxxetto.maxxiummod.item;

import artifacts.common.item.BaubleBase;
import artifacts.common.item.BaubleBottledCloud;
import baubles.api.BaubleType;
import baubles.api.IBauble;
import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.baubleeffect.IJumpBoost;
import net.minecraft.item.ItemStack;
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
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

@EventBusSubscriber
public class FartBalloon extends BaubleBase implements IJumpBoost {
    public final boolean isFart;
    @SideOnly(Side.CLIENT)
    private static boolean canDoubleJump;
    @SideOnly(Side.CLIENT)
    private static boolean hasReleasedJumpKey;

    public FartBalloon(String name, boolean isFart) {
        super(name, BaubleType.TRINKET);
        this.isFart = isFart;
        this.setCreativeTab(BountifulBaubles.TAB);
        this.setMaxStackSize(1);
    }

    public float getJumpBoost() {
        return 0.3F;
    }

    public float getFallResist() {
        return 7.0F;
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            EntityPlayerSP player = Minecraft.getMinecraft().player;
            if (player != null) {
                if ((player.onGround || player.isOnLadder()) && !player.isInWater()) {
                    hasReleasedJumpKey = false;
                    canDoubleJump = true;
                } else if (!player.movementInput.jump) {
                    hasReleasedJumpKey = true;
                } else if (!player.capabilities.isFlying && canDoubleJump && hasReleasedJumpKey) {
                    canDoubleJump = false;
                    ItemStack stack = BaublesApi.getBaublesHandler(player).getStackInSlot(BaubleType.TRINKET.getValidSlots()[0]);
                    if (stack.getItem() instanceof FartBalloon) {
                        ModNetworkHandler.NETWORK_HANDLER_INSTANCE.sendToServer(new PacketBottledCloudJump(((FartBalloon)stack.getItem()).isFart));
                        player.jump();
                        player.fallDistance = 0.0F;
                        if (((FartBalloon)stack.getItem()).isFart) {
                            player.playSound(ModSoundEvents.FART, 1.3F, 0.8F + player.getRNG().nextFloat() * 0.4F);
                        } else {
                            player.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.3F, 0.8F + player.getRNG().nextFloat() * 0.4F);
                        }
                    }
                }
            }
        }

    }
}
