package com.willfp.ecoitems.commands;


import com.willfp.eco.core.EcoPlugin;
import com.willfp.eco.core.command.CommandHandler;
import com.willfp.eco.core.command.impl.PluginCommand;
import org.jetbrains.annotations.NotNull;

public class CommandEcoitems extends PluginCommand {
    /**
     * Instantiate a new command handler.
     *
     * @param plugin The plugin for the commands to listen for.
     */
    public CommandEcoitems(@NotNull final EcoPlugin plugin) {
        super(plugin, "ecoitems", "ecoitems.command.ecoitems", false);

        this.addSubcommand(new CommandReload(plugin))
                .addSubcommand(new CommandGive(plugin));
    }

    @Override
    public CommandHandler getHandler() {
        return (sender, args) -> {
            sender.sendMessage(this.getPlugin().getLangYml().getMessage("invalid-command"));
        };
    }
}
