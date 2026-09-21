package com.keluonuosi.eternallovemod.eventbus;

import com.keluonuosi.eternallovemod.EternalLoveMod;
import com.keluonuosi.eternallovemod.item.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EternalLoveMod.MOD_ID) // 默认就是 Bus.FORGE
public class Mod_Use {
    //监听实体交互事件，防止恒爱之戒&爱之戒被错误使用&交互 最先执行
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        ItemStack stack = event.getItemStack();
        if (stack.is(ModItems.ETERNAL_LOVE_RING.get()) || stack.is(ModItems.LOVE_RING.get())) {
            event.setCanceled(true);
        }
    }

    //监听右键方块事件，防止恒爱之戒&爱之戒被错误使用&交互 最先执行
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        ItemStack stack = event.getItemStack();
        if (stack.is(ModItems.ETERNAL_LOVE_RING.get()) || stack.is(ModItems.LOVE_RING.get())) {
            event.setCanceled(true);
        }
    }

}
