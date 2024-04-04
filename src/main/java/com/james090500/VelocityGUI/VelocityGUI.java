package com.james090500.VelocityGUI;

import com.google.inject.Inject;
import com.james090500.VelocityGUI.commands.CommandHandler;
import com.james090500.VelocityGUI.config.Configs;
import com.james090500.VelocityGUI.helpers.InventoryLauncher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(id = "velocitygui", name = "VelocityGUI", version = "1.2.6", description = "GUIs for the entire Proxy", authors = { "james095000" })
public class VelocityGUI {

    public final String PREFIX = "&a[VelocityGUI] ";
    @Getter private final ProxyServer server;
    @Getter private final Logger logger;
    @Getter private final Path dataDirectory;

    @Inject
    public VelocityGUI(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
        this.server = server;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        Configs.loadConfigs(this);

        //Setup command flow
        final CommandHandler handler = new CommandHandler(this);
        server.getCommandManager().register(server.getCommandManager().metaBuilder("vgui").build(), new BrigadierCommand(
            LiteralArgumentBuilder.<CommandSource>literal("vgui").executes(handler::about)
                .then(LiteralArgumentBuilder.<CommandSource>literal("panel").executes(handler::panel))
                .then(LiteralArgumentBuilder.<CommandSource>literal("panel").then(RequiredArgumentBuilder.<CommandSource, String>argument("name", StringArgumentType.word()).executes(handler::panel)))
                .then(LiteralArgumentBuilder.<CommandSource>literal("reload").requires(source -> source.hasPermission("vgui.admin")).executes(handler::reload))
        ));

        //Register command aliases for panels
        Configs.getPanels().forEach((name, panel) -> {
            String[] commands = panel.getCommands();
            if(commands == null || commands.length == 0) return;

            CommandMeta.Builder commandBuilder = server.getCommandManager().metaBuilder(commands[0]);
            for(String commannd : commands) {
                commandBuilder.aliases(commannd);
            }

            server.getCommandManager().register(commandBuilder.build(), (SimpleCommand) invocation -> {
                new InventoryLauncher(this).execute(panel.getName(), (Player) invocation.source());
            });
        });
    }
}
