package com.james090500.VelocityGUI.helpers;

import com.james090500.VelocityGUI.VelocityGUI;
import com.james090500.VelocityGUI.config.Configs;
import com.velocitypowered.api.proxy.Player;
import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.api.SoundCategory;
import dev.simplix.protocolize.api.inventory.Inventory;
import dev.simplix.protocolize.api.player.ProtocolizePlayer;
import dev.simplix.protocolize.data.Sound;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class InventoryLauncher {

    private VelocityGUI velocityGUI;

    public InventoryLauncher(VelocityGUI velocityGUI) {
        this.velocityGUI = velocityGUI;
    }

    /**
     * Launches an inventory instance from a panel
     * @param panelName
     * @param player
     */
    public boolean execute(String panelName, Player player) {
        Configs.Panel panel = Configs.getPanels().get(panelName);
        
        boolean returnState = true;
        if(panel == null) {
            player.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(velocityGUI.PREFIX + "Panel not found"));
            return false;
        }

        //Stop players with no permissions
        if(!panel.getPerm().equalsIgnoreCase("default") && !player.hasPermission(panel.getPerm())) {
            player.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(velocityGUI.PREFIX + "Panel not found"));
            returnState = false;
        }

        InventoryBuilder inventoryBuilder = new InventoryBuilder(velocityGUI, player);
        inventoryBuilder.setRows(panel.getRows());
        inventoryBuilder.setTitle(panel.getTitle());
        inventoryBuilder.setEmpty(panel.getEmpty());
        inventoryBuilder.setItems(panel.getItems());
        Inventory inventory = inventoryBuilder.build();
        if (!returnState) {inventory.onClick(click -> {
            click.cancelled(true);
            Configs.Item item = panel.getItems().get(click.slot());
            if(item != null && item.getCommands() != null) {
                new InventoryClickHandler(velocityGUI).execute(item.getCommands(), click);
            }
        });}

        ProtocolizePlayer protocolizePlayer = Protocolize.playerProvider().player(player.getUniqueId());
        protocolizePlayer.openInventory(inventory);

        if(panel.getSound() != null) {
            protocolizePlayer.playSound(Sound.valueOf(panel.getSound()), SoundCategory.MASTER, 1f, 1f);
        }
        
        returnState returnState;
    }
}
