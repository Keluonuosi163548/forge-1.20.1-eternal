package com.keluonuosi.eternallovemod.eventbus;

import com.keluonuosi.eternallovemod.EternalLoveMod;
import com.keluonuosi.eternallovemod.data.ModSavedData;
import com.keluonuosi.eternallovemod.item.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static com.keluonuosi.eternallovemod.ModEventBus.PENDING_FIRST_JOIN_ITEMS;
import static com.keluonuosi.eternallovemod.item.ModCurios.EternalLoveringItem.ELRI_UUID_STRING;

@Mod.EventBusSubscriber(modid = EternalLoveMod.MOD_ID) // 默认就是 Bus.FORGE
public class Mod_Player_join {


    //监听玩家加入事件
    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        // 只在服务器端执行
        if (event.getEntity().level().isClientSide()) return;
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) return;

        if (serverPlayer.getStats().getValue(Stats.CUSTOM.get(Stats.PLAY_TIME)) == 0) {
            if (ELRI_UUID_STRING.equals(serverPlayer.getStringUUID())) {
                PENDING_FIRST_JOIN_ITEMS.put(serverPlayer.getUUID(), new ItemStack(ModItems.ETERNAL_LOVE_RING.get()));
            } else {
                PENDING_FIRST_JOIN_ITEMS.put(serverPlayer.getUUID(), new ItemStack(ModItems.LOVE_RING.get()));
            }
        }

        // 获取该uuid玩家保留的丢失物品
        // 从保存数据中获取该uuid玩家保留的丢失物品
        ServerLevel serverLevel = (ServerLevel) serverPlayer.level();
        ModSavedData savedData = ModSavedData.get(serverLevel);

        List<ItemStack> ownerkeepItems = savedData.getOwnerKeepItems().remove(serverPlayer.getUUID());

        if (ownerkeepItems != null) {
            // 遍历并还给玩家
            for (ItemStack stack : ownerkeepItems) {
                serverPlayer.getInventory().placeItemBackInInventory(stack.copy());
            }
            savedData.setDirty();
        }
    }
}
