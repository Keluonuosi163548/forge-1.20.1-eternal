package com.keluonuosi.eternallovemod.datagen;

import com.keluonuosi.eternallovemod.EternalLoveMod;
import com.keluonuosi.eternallovemod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;


//en_us语言文件
public class ModEnUsLangProvider extends LanguageProvider {
    public ModEnUsLangProvider(PackOutput output) {
        super(output, EternalLoveMod.MOD_ID, "en_us");
    }

    //添加翻译   使用add方法，第一个参数为翻译的键，第二个参数为翻译的值
    @Override
    protected void addTranslations() {
        add(ModItems.ETERNAL_LOVE_RING.get(), "Eternal Love");
        add(ModItems.LOVE_RING.get(), "Love Ring");

        add("itemGroup.eternal_love", "Eternal Love");

        add("curios.identifier.eternal_love_ring", "Eternal Love Ring");
        add(" curios.modifiers.eternal_love_ring", "Eternal Love Ring");

        add("advancement.eternal_love_mod.new_begin.title","§dNew Begin");
        add("advancement.eternal_love_mod.new_begin.description","§dWe never lack the courage to start over, no matter how many times we reincarnate, the first thing we see when we open our eyes is each other");
        add("advancement.eternal_love_mod.kill_wither.title","§dLife and Death?");
        add("advancement.eternal_love_mod.kill_wither.description","§dWe killed the messenger of the underworld\nbut is it really right to break the boundaries between life and death? \n§2 Carrying the Ring of Love to kill the Wither");
        add("advancement.eternal_love_mod.kill_dragon.title","§dOnce again, the end of the journey");
        add("advancement.eternal_love_mod.kill_dragon.description","§dEnded.\nBut really ended?\n§2Kill the End Dragon with the Love Ring");



        add("eternal_love_ring.owner_message0","§dWelcome back");
        add("eternal_love_ring.owner_message1","§dThis ring doesn't belong to you!");
        add("love_ring.owner_message1","§dThis ring doesn't belong to you!");

        add("eternal_love_ring.command_clear","§4Eternal_Love_Ring does not allow the clear command!");
        add("death_message.eternal_love_ring.playerdeath","§4The Eternal_Love_Ring won't accept your death!");

        add("death_message.love_ring.playerdeath","§4 The power of the Love_Ring reversed life and death!");

        add("death_message.love_ring.dragon_kill","§4 defeated the world's guardian, and the Ring of Love regained its power that was suppressed by the dimensions!");
        add("death_message.love_ring.wither_kill","§4 The Love_Ring has siphoned the life force from the messenger who balances life and death!");

        add("tooltip.eternallovemod.love_ring_item", "§7Hold down §r§8SHIFT§r§7 to see more\n");
        add("tooltip.eternallovemod.love_ring_item.shift", "§dDo you believe love can transcend time and space?§r\n§dI believe it can~§r\n§6This ring is the proof§r\n");
        add("tooltip.eternallovemod.love_ring_item.shift1", "§dDo you believe love can transcend time and space?§r\n§dI believe it can~§r\n§6This ring is the proof§r\n\n§4This item is bound forever and will never drop on death§r\n§4Can't interact with blocks/entities when in the main hand§r\n§8Cannot be destroyed by §r§cfire§r§4, lava, §r§eexplosions§r§4, §r§blightning§r§4, §r§acacti§r\n§r§8Immune to the clear command when worn§r\n\n§r§4This item is permanently bound to");

        add("tooltip.eternallovemod.love_ring_item.shift_goety_revelation","§4This item is immune to Apollyon - The Apocalypse accessory stealing");

        add("tooltip.eternallovemod.love_ring_item.shift2","\n\n§4This ring longs to go on adventures with you§r");
        add("tooltip.eternallovemod.love_ring_item.shift3", "\n\n§4This ring witnesses your journey§r");
        add("tooltip.eternallovemod.love_ring_item.shift4","\n§dWither Token: When the player takes fatal damage, they are immune to this damage and gain 10 seconds of invincibility, with a cooldown of 600 seconds");
        add("tooltip.eternallovemod.love_ring_item.shift5","\n§dProof of Dragon Slaying: All attribute bonuses of the Love_Ring unlocked");

        add("tooltip.eternallovemod.eternal_love_ring_item", "§7Hold down §r§8SHIFT§r§7 to see more\n");
        add("tooltip.eternallovemod.eternal_love_ring_item.shift", "§dWhether in past lives or this one, the one I love is only you.\n§6In this life and forevermore, through all eternity, never leaving, never giving up, growing old together.  §r\n§cWith this crystal ring, let's witness our eternal love.§r\n\n\n\n§4This item is bound forever and will never drop on death§r\n§4Can't interact with blocks/entities when in the main hand§r\n§8Cannot be destroyed by §r§cfire§r§4, lava, §r§eexplosions§r§4, §r§blightning§r§4, §r§acacti§r\n§r§5Falling into the void§r§8, §r§5being killed§r§8, §r§5being sucked up by a block or entity container§r§8, or §r§5When it naturally disappears\n§r§4Automatically returns to the inventory§r\n§d When the player takes fatal damage, they are immune to this damage and gain 10 seconds of invincibility, with a cooldown of 600 seconds\n\n§r§4This item is permanently bound to Keluonuosi");
        add("tooltip.eternallovemod.eternal_love_ring_item.shift_goety_revelation","§4This item is immune to Apollyon - The Apocalypse accessory stealing");

    }
}
