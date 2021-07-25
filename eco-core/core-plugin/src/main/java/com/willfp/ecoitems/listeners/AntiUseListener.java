package com.willfp.ecoitems.listeners;

import com.willfp.eco.core.EcoPlugin;
import com.willfp.eco.core.PluginDependent;
import com.willfp.ecoitems.items.EcoItemUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.jetbrains.annotations.NotNull;

public class AntiUseListener extends PluginDependent<EcoPlugin> implements Listener {
    /**
     * Pass an {@link EcoPlugin} in order to interface with it.
     *
     * @param plugin The plugin to manage.
     */
    public AntiUseListener(@NotNull final EcoPlugin plugin) {
        super(plugin);
    }

    /**
     * Prevent block place.
     *
     * @param event The event.
     */
    @EventHandler
    public void onBlockPlace(@NotNull final BlockPlaceEvent event) {
        if (EcoItemUtils.getRecipeItem(event.getItemInHand()) != null) {
            event.setCancelled(true);
            event.setBuild(false);
        }
    }
}
