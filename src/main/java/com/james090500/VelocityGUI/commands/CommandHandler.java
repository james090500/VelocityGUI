package com.james090500.VelocityGUI.commands;

import com.james090500.VelocityGUI.VelocityGUI;
import com.james090500.VelocityGUI.config.Configs;
import com.james090500.VelocityGUI.helpers.InventoryLauncher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.ParsedArgument;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class CommandHandler {

    private VelocityGUI velocityGUI;

    public CommandHandler(VelocityGUI velocityGUI) {
        this.velocityGUI = velocityGUI;
    }

    /**
     * The command for /vgui panel
     * Handles listing panel and passes a valid argument to the InventoryLauncher
     * @param commandSourceCommandContext
     * @return
     */
    public int panel(CommandContext<CommandSource> commandSourceCommandContext) {
        if(!(commandSourceCommandContext.getSource() instanceof Player)) {
            Component error = LegacyComponentSerializer.legacyAmpersand().deserialize(velocityGUI.PREFIX + "Only a player can run these commands");
            commandSourceCommandContext.getSource().sendMessage(error);
            return 0;
        }

        Player player = (Player) commandSourceCommandContext.getSource();
        ParsedArgument<CommandSource, ?> nameArgument = commandSourceCommandContext.getArguments().get("name");
        if(nameArgument == null) {
            player.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(velocityGUI.PREFIX + "Available panels"));
            Configs.getPanels().forEach((title, panel) -> {
                //Hide panels with no permissions
                if(panel.getPerm().equalsIgnoreCase("default") || player.hasPermission(panel.getPerm())) {
                    player.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(velocityGUI.PREFIX + title));
                }
            });
            return 1;
        }

        new InventoryLauncher(velocityGUI).execute((String) nameArgument.getResult(), player);
        return 1;
    }

    /**
     * Reloads the configs
     * @param commandSourceCommandContext
     * @return
     */
    public int reload(CommandContext<CommandSource> commandSourceCommandContext) {
        Configs.loadConfigs(velocityGUI);
        CommandSource source = commandSourceCommandContext.getSource();
        source.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(velocityGUI.PREFIX + "Reloaded"));
        velocityGUI.getLogger().info("VelocityGUI Reloaded");
        return 1;
    }

    /**
     * A bit of basic about information
     * @param commandSourceCommandContext
     * @return
     */
    public int about(CommandContext<CommandSource> commandSourceCommandContext) {
        CommandSource source = commandSourceCommandContext.getSource();
        source.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(velocityGUI.PREFIX + "VelocityGUI by james090500"));
        return 1;
    }
}
