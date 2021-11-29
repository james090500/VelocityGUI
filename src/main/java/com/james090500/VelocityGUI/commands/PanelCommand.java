package com.james090500.VelocityGUI.commands;

import com.james090500.VelocityGUI.VelocityGUI;
import com.james090500.VelocityGUI.config.Configs;
import com.james090500.VelocityGUI.helpers.InventoryBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.ParsedArgument;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.api.inventory.Inventory;
import dev.simplix.protocolize.api.player.ProtocolizePlayer;

public class PanelCommand {

    public void execute(VelocityGUI velocityGUI, CommandContext<CommandSource> commandSourceCommandContext) {
        ParsedArgument<CommandSource, ?> nameArgument = commandSourceCommandContext.getArguments().get("name");
        if(nameArgument == null) return;

        Player player = (Player) commandSourceCommandContext.getSource();

        Configs.Panel panel = Configs.getPanels().get((String) nameArgument.getResult());
        if(panel == null) return;

        InventoryBuilder inventoryBuilder = new InventoryBuilder(player.getProtocolVersion());
        inventoryBuilder.setRows(panel.getRows());
        inventoryBuilder.setTitle(panel.getTitle());
        inventoryBuilder.setEmpty(panel.getEmpty());
        inventoryBuilder.setItems(panel.getItems());
        Inventory inventory = inventoryBuilder.build();

        ProtocolizePlayer protocolizePlayer = Protocolize.playerProvider().player(player.getUniqueId());
        protocolizePlayer.openInventory(inventory);
    }

}
