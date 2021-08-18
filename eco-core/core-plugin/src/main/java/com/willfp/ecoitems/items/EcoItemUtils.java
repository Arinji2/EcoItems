package com.willfp.ecoitems.items;

import com.willfp.eco.core.items.CustomItem;
import com.willfp.ecoitems.EcoItemsPlugin;
import lombok.experimental.UtilityClass;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@UtilityClass
public class EcoItemUtils {
    /**
     * Instance of EcoItems.
     */
    private static final EcoItemsPlugin PLUGIN = EcoItemsPlugin.getInstance();

    /**
     * Get item ID from an item.
     *
     * @param itemStack The itemStack to check.
     * @return The item ID, or null if no item is found.
     */
    @Nullable
    public String getItemID(@Nullable final ItemStack itemStack) {
        if (itemStack == null) {
            return null;
        }

        ItemMeta meta = itemStack.getItemMeta();

        if (meta == null) {
            return null;
        }

        PersistentDataContainer container = meta.getPersistentDataContainer();

        if (!container.has(PLUGIN.getNamespacedKeyFactory().create("recipe_item"), PersistentDataType.STRING)) {
            return null;
        }

        return container.get(PLUGIN.getNamespacedKeyFactory().create("recipe_item"), PersistentDataType.STRING);
    }

    /**
     * Get item from an item.
     *
     * @param itemStack The itemStack to check.
     * @return The item, or null if no item is found.
     */
    @Nullable
    public CustomItem getItem(@NotNull final ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();

        if (meta == null) {
            return null;
        }

        return getItem(meta);
    }

    /**
     * Get item on an item.
     *
     * @param meta The itemStack to check.
     * @return The item, or null if no item is found.
     */
    @Nullable
    public CustomItem getItem(@NotNull final ItemMeta meta) {
        PersistentDataContainer container = meta.getPersistentDataContainer();
        String itemID = container.get(PLUGIN.getNamespacedKeyFactory().create("recipe_item"), PersistentDataType.STRING);

        if (itemID == null) {
            return null;
        }

        return EcoItems.getByName(itemID);
    }
}
