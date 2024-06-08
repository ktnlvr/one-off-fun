package net.ktnlvr.one;

import net.ktnlvr.one.enchantments.FrostbiteEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents.modifyEntriesEvent;

public class Items {
    public static final Item CROW_FOOD = registerItem("crow_food", new Item(new Item.Settings()));
    public static Enchantment FROST = new FrostbiteEnchantment();

    private static Item registerItem(String name, Item item) {
        modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(item));
        KtnlvrMod.LOGGER.info("Registering mod item: " + name);
        return Registry.register(Registries.ITEM, new Identifier(KtnlvrMod.MOD_ID, name), item);
    }

    public static void registerMod() {
        Registry.register(Registries.ENCHANTMENT, new Identifier(KtnlvrMod.MOD_ID, "frost"), FROST);
    }
}
