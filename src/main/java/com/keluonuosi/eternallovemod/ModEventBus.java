package com.keluonuosi.eternallovemod;

import com.mojang.logging.LogUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.*;

@Mod.EventBusSubscriber(modid = EternalLoveMod.MOD_ID) // 默认就是 Bus.FORGE
public class ModEventBus {

    public static final Map<UUID,ItemStack> PENDING_FIRST_JOIN_ITEMS = new HashMap<>();
    public static final Logger LOGGER_EventBus = LogUtils.getLogger();


    public static List<ItemStack> getItemsInSlot(Player player, String slotId, Item itemType) {
        List<ItemStack> result = new ArrayList<>();
        CuriosApi.getCuriosInventory(player).ifPresent(handler -> {
            ICurioStacksHandler stacksHandler = handler.getCurios().get(slotId);
            if (stacksHandler == null) return;
            for (int i = 0; i < stacksHandler.getSlots(); i++) {
                ItemStack stack = stacksHandler.getStacks().getStackInSlot(i);
                if (stack.is(itemType)) {
                    result.add(stack);
                }
            }
        });
        return result;
    }



}
