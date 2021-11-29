package com.james090500.VelocityGUI.commands;

import com.james090500.VelocityGUI.VelocityGUI;
import com.james090500.VelocityGUI.config.Configs;
import com.james090500.VelocityGUI.helpers.InventoryLauncher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.ParsedArgument;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class CommandHandler {

    private VelocityGUI velocityGUI;

    public CommandHandler(VelocityGUI velocityGUI) {
        this.velocityGUI = velocityGUI;
    }

    public int panel(CommandContext<CommandSource> commandSourceCommandContext) {
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

    public int reload(CommandContext<CommandSource> commandSourceCommandContext) {
        Configs.loadConfigs(velocityGUI);
        CommandSource source = commandSourceCommandContext.getSource();
        source.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(velocityGUI.PREFIX + "Reloaded"));
        velocityGUI.getLogger().info("VelocityGUI Reloaded");
        return 1;
    }

    public int about(CommandContext<CommandSource> commandSourceCommandContext) {
        CommandSource source = commandSourceCommandContext.getSource();
        source.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(velocityGUI.PREFIX + "VelocityGUI by james090500"));
        return 1;
    }
}
