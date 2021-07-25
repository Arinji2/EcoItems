package com.willfp.ecoitems.config;

import com.willfp.eco.core.EcoPlugin;
import com.willfp.eco.core.config.json.JSONStaticBaseConfig;
import org.jetbrains.annotations.NotNull;

public class ItemsJson extends JSONStaticBaseConfig {
    /**
     * Create tiers.json.
     *
     * @param plugin Instance of EcoItems.
     */
    public ItemsJson(@NotNull final EcoPlugin plugin) {
        super("items", plugin);
    }
}
