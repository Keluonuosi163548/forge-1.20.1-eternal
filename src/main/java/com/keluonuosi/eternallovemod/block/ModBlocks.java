package com.keluonuosi.eternallovemod.block;

import com.keluonuosi.eternallovemod.EternalLoveMod;
import com.keluonuosi.eternallovemod.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    //创建方块注册表BLOCKS
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, EternalLoveMod.MOD_ID);

    //方块掉落一般采用战利品形式，路径为assets/data/<modid>/loot_tables/blocks  名称为方块注册名.json
    //方块战利品表中，可以在原版标签的命名空间下写入pickaxe.json等来设置对应的工具类型，和写入needs_<等级（stone/iron/diamond）>_tool.json来设置对应的工具等级
    //路径分别为assets/data/minecraft/tags/blocks/pickaxe.json   assets/data/minecraft/tags/blocks/needs_stone_tool.json
    //在设置战利品表的时候，也可以通过以下方法设置特殊的凋落物属性
    /*
    一、函数（function 字段）
    用于在掉落时修改物品属性：
    函数名
            作用
    minecraft:set_count
    设置掉落数量（固定值或随机范围）

    minecraft:set_damage
    设置物品耐久损耗（如半损的剑）

    minecraft:set_enchantments
    设置附魔（指定附魔和等级）

    minecraft:enchant_with_levels
    按等级随机附魔（模拟附魔台）

    minecraft:enchant_randomly
            随机附加一个附魔

    minecraft:set_nbt
    设置自定义 NBT 数据

    minecraft:set_name
    设置物品显示名称

    minecraft:set_lore
    设置物品描述（lore）

    minecraft:set_attributes
    设置属性修饰符（如攻击力、生命值）***

    minecraft:apply_bonus
    按附魔等级加成数量（如时运）

    minecraft:explosion_decay
    爆炸破坏时概率减少掉落

    minecraft:limit_count
    限制总掉落数量上限

    minecraft:apply_smelting
    将掉落物替换为烧炼产物（如铁矿→铁锭）

    minecraft:looting_enchant
    按抢夺附魔等级增加掉落（用于生物）

    minecraft:copy_name
    复制方块实体的自定义名称到掉落物

    minecraft:copy_state
    复制方块状态到掉落物 NBT

    minecraft:copy_nbt
    复制 NBT 数据到掉落物

    minecraft:set_contents
    设置容器物品内容（如给潜影盒装东西）

    minecraft:set_stew_effect
    设置谜之炖菜的效果

    minecraft:fill_player_head
    填充玩家头颅的皮肤

    二、条目类型（type 字段）
    用于定义"掉落什么"：
    类型  作用
    minecraft:item
    掉落指定物品

    minecraft:alternatives
    多个子条目，只掉落第一个满足条件的

    minecraft:sequence
    按顺序执行所有子条目

    minecraft:group
    掉落所有子条目（合并为一组）

    minecraft:dynamic
    动态掉落（如旗帜图案、生物特殊掉落）

    minecraft:empty
    什么都不掉

    minecraft:loot_table
    引用另一张战利品表

    minecraft:tag
    掉落某个物品标签中的随机物品

    三、条件（condition 字段）
    用于判断"是否掉落"：
    条件    作用
    minecraft:match_tool
    匹配工具（物品、附魔等）

    minecraft:survives_explosion
    爆炸时有概率通过

    minecraft:block_state_property
    匹配方块状态（如成熟的小麦）

    minecraft:random_chance
    固定概率通过

    minecraft:random_chance_with_looting
    按抢夺等级调整概率

    minecraft:killed_by_player
    被玩家击杀

    minecraft:entity_properties
    匹配实体属性（如是否在燃烧）

    minecraft:inverted
    反转另一个条件

    minecraft:alternative
    多个条件满足其一即可

    minecraft:time_check
    检查游戏时间

    minecraft:weather_check
    检查天气

    minecraft:location_check
    检查位置（维度、群系等）

    minecraft:reference
    引用另一个条件谓词

    minecraft:table_bonus
    按附魔等级给概率
     */
    //调用注册方块方法注册方块
    //方块状态保存在BlockState中，负责指定模型所在位置 multipart为组合式列举（各部分模型满足时显示）  variants为穷举（列举出所有状态下的模型位置）
    //常用方法如下
    //1.设定方块在地图上的颜色 mapColor(DyeColor pMapColor)       mapColor(MapColor pMapColor)              mapColor(Function<BlockState, MapColor> pMapColor)
    //2.没有碰撞箱且不阻碍后面的方块渲染noCollission()
    //3.不阻碍后面的方块渲染noOcclusion()   设置为非实心，以实现透明，若不设置会导致透明贴图部分显示为黑块，若有相邻方块则不渲染相邻面（透视）
    //4.设定摩擦系数，越高保留的速度越多，控制滑行距离friction(float pFriction)
    //5.速度因子，越高能达到的最大速度越高，控制最高移动速度和起步的加速度speedFactor(float pSpeedFactor)
    //6.跳跃因子，控制在这个方块上起跳能跳到的高度jumpFactor(float pJumpFactor)
    //7.玩家在这个方块上走动听到的声音sound(SoundType pSoundType)
    //8.光照等级lightLevel(ToIntFunction<BlockState> pLightEmission)
    //9.硬度（所需挖掘时间）和爆炸抗性strength(float pDestroyTime, float pExplosionResistance)
    //10.调整为能够瞬间破坏（甘蔗，草等）instabreak()
    //11.调整硬度和爆炸抗性（都为传入的参数）strength(float pStrength)
    //12.可以重写实现方块随着时间变化，（农作物等）randomTicks()
    //13.告诉系统动态更新碰撞箱，每次与这个方块交互的时候都更新碰撞箱dynamicShape()
    //14.设定为无掉落物，可以通过其他方式添加掉落物noLootTable()
    //15.指定某类方块，挖掘此方块的时候掉落指定方块的战利品列表            dropsLike(Block pBlock) 过时的方法           lootFrom(java.util.function.Supplier<? extends Block> blockIn)
    //16.能被岩浆点燃ignitedByLava()
    //17.指定这是流体liquid()
    //18.强制要求此方块为实心或非实心的      实心方块可以放置火把、红石，阻挡关照         forceSolidOn()        forceSolidOff()
    //19.设定被活塞推拉的行为pushReaction(PushReaction pPushReaction)
    //20.是否为空气air()
    //21.是否允许生物生成isValidSpawn(BlockBehaviour.StateArgumentPredicate<EntityType<?>> pIsValidSpawn)
    //22.是否传递红石信号isRedstoneConductor(BlockBehaviour.StatePredicate pIsRedstoneConductor)
    //23.是否能让生物窒息isSuffocating(BlockBehaviour.StatePredicate pIsSuffocating)
    //24.是否阻挡生物视线isSuffocating(BlockBehaviour.StatePredicate pIsSuffocating)
    //25.是否进行后期处理hasPostProcess(BlockBehaviour.StatePredicate pHasPostProcess)
    //26.渲染时是否发光（看起来发亮）emissiveRendering(BlockBehaviour.StatePredicate pEmissiveRendering)
    //27.设定偏移类型，如半透明offsetType(BlockBehaviour.OffsetType pOffsetType)
    //28.需要合适工具requiresCorrectToolForDrops()
    //29.破坏需要的挖掘时间destroyTime(float pDestroyTime)
    //30.爆炸抗性explosionResistance(float pExplosionResistance)







    //调用物品注册类ModItems中的ITEMS.register方法，注册方块对应的物品
    private static <T extends Block> void registerBlockItems (String name, RegistryObject<T> block){
        ModItems.ITEMS.register(name,() -> new BlockItem(block.get(), new Item.Properties()));
    }

    //注册方块
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
        //调用BLOCKS注册方块本体
        RegistryObject<T> blocks = BLOCKS.register(name,block);
        //调用registerBlockItems方法注册方块对应的物品
        registerBlockItems(name,blocks);
        return blocks;
    }

    //将注册表注入总线
    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
