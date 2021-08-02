package com.willfp.ecoitems.items;


import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import com.willfp.eco.core.config.interfaces.JSONConfig;
import com.willfp.eco.core.config.updating.ConfigUpdater;
import com.willfp.eco.core.display.Display;
import com.willfp.eco.core.items.CustomItem;
import com.willfp.eco.core.items.builder.ItemBuilder;
import com.willfp.eco.core.items.builder.ItemStackBuilder;
import com.willfp.eco.core.recipe.Recipes;
import com.willfp.ecoitems.EcoItemsPlugin;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@UtilityClass
public class EcoItems {
    /**
     * Registered weapons.
     */
    private static final BiMap<String, CustomItem> BY_NAME = HashBiMap.create();

    /**
     * Instance of EcoItems.
     */
    private static final EcoItemsPlugin PLUGIN = EcoItemsPlugin.getInstance();

    /**
     * Get all registered {@link CustomItem}s.
     *
     * @return A list of all {@link CustomItem}s.
     */
    public static List<CustomItem> values() {
        return ImmutableList.copyOf(BY_NAME.values());
    }

    /**
     * Update all {@link CustomItem}s.
     *
     * @param plugin Instance of EcoItems.
     */
    @ConfigUpdater
    public static void update(@NotNull final EcoItemsPlugin plugin) {
        for (CustomItem item : values()) {
            removeItem(item);
        }

        for (JSONConfig setConfig : plugin.getItemsJson().getSubsections("items")) {
            addNewItem(buildCustomItem(setConfig));
        }
    }

    private static CustomItem buildCustomItem(@NotNull final JSONConfig config) {
        String id = config.getString("id");
        Material material = Material.getMaterial(config.getString("material").toUpperCase());
        if (material == null) {
            PLUGIN.getLogger().warning("Invalid material specified in " + id);
        }
        assert material != null;

        ItemBuilder builder = new ItemStackBuilder(material);

        builder.setDisplayName(config.getString("displayName"))
                .addItemFlag(
                        config.getStrings("flags").stream()
                                .map(s -> ItemFlag.valueOf(s.toUpperCase()))
                                .toArray(ItemFlag[]::new)
                )
                .setUnbreakable(config.getBool("unbreakable"))
                .addLoreLines(config.getStrings("lore").stream().map(s -> Display.PREFIX + s).collect(Collectors.toList()))
                .setCustomModelData(() -> {
                    int data = config.getInt("customModelData");
                    return data != -1 ? data : null;
                })
                .writeMetaKey(
                        PLUGIN.getNamespacedKeyFactory().create("recipe_item"),
                        PersistentDataType.STRING,
                        id
                );

        Map<Enchantment, Integer> enchants = new HashMap<>();

        for (JSONConfig enchantSection : config.getSubsections("enchants")) {
            Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(enchantSection.getString("id")));
            int level = enchantSection.getInt("level");
            enchants.put(enchantment, level);
        }

        enchants.forEach(builder::addEnchantment);

        ItemStack itemStack = builder.build();

        CustomItem customItem = new CustomItem(PLUGIN.getNamespacedKeyFactory().create(id.toLowerCase()), test -> Objects.equals(id, EcoItemUtils.getItemID(test)), itemStack);
        customItem.register();

        int amount = !config.has("recipeGiveAmount") ? 1 : config.getInt("recipeGiveAmount");
        itemStack.setAmount(amount);

        if (config.getBool("craftable")) {
            Recipes.createAndRegisterRecipe(
                    PLUGIN,
                    id,
                    itemStack,
                    config.getStrings("recipe")
            );
        }

        return customItem;
    }

    /**
     * Get item by id.
     *
     * @param id The id.
     * @return The item.
     */
    public static CustomItem getByName(@NotNull final String id) {
        return BY_NAME.get(id);
    }

    /**
     * Add new {@link CustomItem} to EcoItems.
     *
     * @param item The {@link CustomItem} to add.
     */
    public static void addNewItem(@NotNull final CustomItem item) {
        BY_NAME.remove(item.getKey().getKey());
        BY_NAME.put(item.getKey().getKey(), item);
    }

    /**
     * Remove {@link CustomItem} from EcoItems.
     *
     * @param item The {@link CustomItem} to remove.
     */
    public static void removeItem(@NotNull final CustomItem item) {
        BY_NAME.remove(item.getKey().getKey());
    }
}
