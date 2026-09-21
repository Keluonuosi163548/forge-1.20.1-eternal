package com.keluonuosi.eternallovemod.Tag;

import com.keluonuosi.eternallovemod.EternalLoveMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {

    public static final TagKey<Item> ETERNAL_LOVE_SOUL_BOUND = bind("eternal_love_soul_bound");

    private static TagKey<Item> bind(String pName) {
        return TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath(EternalLoveMod.MOD_ID,pName));//25年后的forge可以使用ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID,pName)方法返回
    }

}
