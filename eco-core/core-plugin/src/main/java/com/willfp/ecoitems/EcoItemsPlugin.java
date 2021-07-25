package com.willfp.ecoitems;

import com.willfp.eco.core.EcoPlugin;
import com.willfp.eco.core.command.impl.PluginCommand;
import com.willfp.eco.core.display.DisplayModule;
import com.willfp.ecoitems.commands.CommandEcoitems;
import com.willfp.ecoitems.config.ItemsJson;
import com.willfp.ecoitems.display.EcoItemDisplay;
import com.willfp.ecoitems.items.EcoItems;
import com.willfp.ecoitems.listeners.AntiUseListener;
import com.willfp.ecoitems.listeners.DiscoverRecipeListener;
import lombok.Getter;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class EcoItemsPlugin extends EcoPlugin {
    /**
     * Instance of EcoItems.
     */
    @Getter
    private static EcoItemsPlugin instance;

    /**
     * items.json.
     */
    @Getter
    private final ItemsJson itemsJson;

    /**
     * Internal constructor called by bukkit on plugin load.
     */
    public EcoItemsPlugin() {
        super(94630, 12205, "&e");
        instance = this;

        this.itemsJson = new ItemsJson(this);
        this.getLogger().info(EcoItems.values().size() + " Items Loaded!");
    }

    @Override
    protected List<Listener> loadListeners() {
        return Arrays.asList(
                new AntiUseListener(this),
                new DiscoverRecipeListener(this)
        );
    }

    @Override
    protected List<PluginCommand> loadPluginCommands() {
        return Arrays.asList(
                new CommandEcoitems(this)
        );
    }

    @Override
    protected @Nullable DisplayModule createDisplayModule() {
        return new EcoItemDisplay(this);
    }
}
