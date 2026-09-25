package com.keluonuosi.eternallovemod.eventbus;

import com.keluonuosi.eternallovemod.EternalLoveMod;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.keluonuosi.eternallovemod.ModEventBus.LIT;

@Mod.EventBusSubscriber(modid = EternalLoveMod.MOD_ID) // 默认就是 Bus.FORGE
public class Mod_Player_tick {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (event.player.level().isClientSide()) return;

        CompoundTag data = event.player.getPersistentData();
        int ticks = data.getCompound(EternalLoveMod.MOD_ID).getInt(LIT);
        if (ticks > 0 ) {
            data.getCompound(EternalLoveMod.MOD_ID).putInt(LIT, ticks - 1);
        }
    }

}
