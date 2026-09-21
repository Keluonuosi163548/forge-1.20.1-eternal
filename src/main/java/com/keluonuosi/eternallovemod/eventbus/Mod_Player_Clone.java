package com.keluonuosi.eternallovemod.eventbus;

import com.keluonuosi.eternallovemod.EternalLoveMod;
import com.keluonuosi.eternallovemod.data.ModSavedData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = EternalLoveMod.MOD_ID) // 默认就是 Bus.FORGE
public class Mod_Player_Clone {


    //监听玩家重生事件，将保留的物品还给玩家
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) return;

        // 获取重生玩家与uuid
        Player player = event.getEntity();

        if (player.level().isClientSide()) return;

        ServerLevel serverLevel = (ServerLevel) player.level();
        ModSavedData savedData = ModSavedData.get(serverLevel);

        UUID uuid = player.getUUID();

        // 获取该uuid玩家死亡时保留的物品
        List<ItemStack> keepItems = savedData.getDeathKeepItems().remove(uuid);
        if (keepItems != null) {
            // 遍历并还给玩家
            for (ItemStack stack : keepItems) {
                player.getInventory().placeItemBackInInventory(stack.copy());
            }
        }

        // 获取该uuid玩家保留的丢失物品
        List<ItemStack> ownerkeepItems = savedData.getOwnerKeepItems().remove(uuid);
        savedData.setDirty();
        if (ownerkeepItems != null) {
            // 遍历并还给玩家
            for (ItemStack stack : ownerkeepItems) {
                player.getInventory().placeItemBackInInventory(stack.copy());
            }
        }
        savedData.setDirty();

    }
}
