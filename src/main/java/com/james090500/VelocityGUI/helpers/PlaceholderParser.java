package com.james090500.VelocityGUI.helpers;

import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class PlaceholderParser {

    public static Component of(Player player, String rawString) {
        //Username
        if(rawString.contains("%username%")) {
            rawString = rawString.replaceAll("%username%", player.getUsername());
        }

        //Server Name
        if(rawString.contains("%server_name%")) {
            rawString = rawString.replaceAll("%server_name%", player.getCurrentServer().get().getServerInfo().getName());
        }

        //ChatControlRed
        if(rawString.contains("%chatcontrolred_nick%")) {
            String nickname = ChatControlHelper.getNick(player);
            rawString = rawString.replaceAll("%chatcontrolred_nick%", nickname);
        }

        //LuckPerms Meta
        if(rawString.startsWith("%luckperms_meta")) {
            String queryOption = rawString.replaceAll("%", "").replaceAll("luckperms_meta_", "");
            LuckPermsHelper.getMeta(player, queryOption);
        }

        Component component = LegacyComponentSerializer.legacyAmpersand().deserialize(rawString);
        return component;
    }
}
