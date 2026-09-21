package com.keluonuosi.eternallovemod.item;

import com.keluonuosi.eternallovemod.EternalLoveMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    //创建创造模式选项卡延迟注册表
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, EternalLoveMod.MOD_ID);

    //创建选项卡并添加物品
    public static final RegistryObject<CreativeModeTab> ETERNAL_LOVE_TAB =
            // 创建选项卡
            CREATIVE_MODE_TABS.register("eternal_love_tab",() -> CreativeModeTab.builder()
                    // 设置选项卡图标
                    .icon(() -> new ItemStack(ModItems.ETERNAL_LOVE_RING.get()))
                    //设置翻译键   translatable为翻译键  literal为死名，不接受语言文件翻译
                    .title(Component.translatable("itemGroup.eternal_love"))
                    // 添加选项卡物品
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.LOVE_RING.get());
                    }).build());



    //注入总线
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
