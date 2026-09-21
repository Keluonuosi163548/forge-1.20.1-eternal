package com.keluonuosi.eternallovemod.eventbus;

import com.keluonuosi.eternallovemod.EternalLoveMod;
import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static com.keluonuosi.eternallovemod.ModEventBus.PENDING_FIRST_JOIN_ITEMS;

@Mod.EventBusSubscriber(modid = EternalLoveMod.MOD_ID) // 默认就是 Bus.FORGE
public class Mod_Server_Tick {

    //监听服务器 tick 事件，给第一个加入的玩家 give 恒爱之戒或爱之戒
    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        // 只在服务器端执行
        if (event.phase != TickEvent.Phase.END) return;
        // 如果待给物品列表为空，则返回
        if (PENDING_FIRST_JOIN_ITEMS.isEmpty()) return;

        // 获取在线玩家列表
        List<ServerPlayer> players = event.getServer().getPlayerList().getPlayers();
        // 如果在线玩家列表为空，则返回
        if (players.isEmpty()) return;

        for (ServerPlayer serverPlayer : players) {
            ItemStack firstJoinItem = PENDING_FIRST_JOIN_ITEMS.remove(serverPlayer.getUUID());
            if (firstJoinItem != null) {
                // 遍历并给予玩家
                serverPlayer.getInventory().placeItemBackInInventory(firstJoinItem.copy());

                Advancement advancement = serverPlayer.server.getAdvancements()
                        .getAdvancement(ResourceLocation.fromNamespaceAndPath(EternalLoveMod.MOD_ID, "new_begin"));
                if (advancement != null) {
                    serverPlayer.getAdvancements().award(advancement, "impossible");
                }
            }
        }
    }
}
