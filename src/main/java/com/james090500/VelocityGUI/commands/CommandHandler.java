package com.james090500.VelocityGUI.commands;

import com.james090500.VelocityGUI.VelocityGUI;
import com.james090500.VelocityGUI.config.Configs;
import com.mojang.brigadier.context.CommandContext;
import com.velocitypowered.api.command.CommandSource;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class CommandHandler {

    private VelocityGUI velocityGUI;

    public CommandHandler(VelocityGUI velocityGUI) {
        this.velocityGUI = velocityGUI;
    }

    public int panel(CommandContext<CommandSource> commandSourceCommandContext) {
        new PanelCommand().execute(velocityGUI, commandSourceCommandContext);
        return 1;
    }

    public int reload(CommandContext<CommandSource> commandSourceCommandContext) {
        Configs.loadConfigs(velocityGUI);
        CommandSource source = commandSourceCommandContext.getSource();
        source.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize("&aVelocityGUI Reloaded"));
        velocityGUI.getLogger().info("VelocityGUI Reloaded");
        return 1;
    }

    public int about(CommandContext<CommandSource> commandSourceCommandContext) {
        CommandSource source = commandSourceCommandContext.getSource();
        source.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize("&aVelocityGUI by james090500"));
        return 1;
    }
}
