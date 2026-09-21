package com.keluonuosi.eternallovemod.Tag;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModCuriosItemTags {
    public static final TagKey<Item> NECKLACE = TagKey.create(
            net.minecraft.core.registries.Registries.ITEM, ResourceLocation.fromNamespaceAndPath("curios", "necklace"));
    public static final TagKey<Item> RING = TagKey.create(
            net.minecraft.core.registries.Registries.ITEM, ResourceLocation.fromNamespaceAndPath("curios", "ring"));
    public static final TagKey<Item> BELT = TagKey.create(
            net.minecraft.core.registries.Registries.ITEM, ResourceLocation.fromNamespaceAndPath("curios", "belt"));
    public static final TagKey<Item> HEAD = TagKey.create(
            net.minecraft.core.registries.Registries.ITEM, ResourceLocation.fromNamespaceAndPath("curios", "head"));
    public static final TagKey<Item> BACK = TagKey.create(
            net.minecraft.core.registries.Registries.ITEM, ResourceLocation.fromNamespaceAndPath("curios", "back"));
    public static final TagKey<Item> BODY = TagKey.create(
            net.minecraft.core.registries.Registries.ITEM, ResourceLocation.fromNamespaceAndPath("curios", "body"));
    public static final TagKey<Item> CURIO = TagKey.create(
            net.minecraft.core.registries.Registries.ITEM, ResourceLocation.fromNamespaceAndPath("curios", "curio"));
    public static final TagKey<Item> ETERNAL_LOVE_RING = TagKey.create(
            net.minecraft.core.registries.Registries.ITEM, ResourceLocation.fromNamespaceAndPath("curios", "eternal_love_ring"));
}
