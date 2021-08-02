package com.willfp.ecoitems.display;

import com.willfp.eco.core.EcoPlugin;
import com.willfp.eco.core.display.DisplayModule;
import com.willfp.eco.core.display.DisplayPriority;
import com.willfp.eco.core.items.CustomItem;
import com.willfp.ecoitems.items.EcoItemUtils;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class EcoItemDisplay extends DisplayModule {
    /**
     * Create weapons display.
     *
     * @param plugin Instance of EcoItems.
     */
    public EcoItemDisplay(@NotNull final EcoPlugin plugin) {
        super(plugin, DisplayPriority.LOWEST);
    }

    @Override
    protected void display(@NotNull final ItemStack itemStack,
                           @NotNull final Object... args) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) {
            return;
        }

        CustomItem item = EcoItemUtils.getItem(meta);

        if (item == null) {
            return;
        }

        ItemMeta weaponMeta = item.getItem().getItemMeta();
        assert weaponMeta != null;

        List<String> lore = weaponMeta.hasLore() ? new ArrayList<>(weaponMeta.getLore()) : new ArrayList<>();

        if (meta.hasLore()) {
            lore.addAll(meta.getLore());
        }

        meta.setLore(lore);
        meta.setDisplayName(weaponMeta.getDisplayName());
        meta.addItemFlags(weaponMeta.getItemFlags().toArray(new ItemFlag[0]));

        itemStack.setItemMeta(meta);
    }
}
