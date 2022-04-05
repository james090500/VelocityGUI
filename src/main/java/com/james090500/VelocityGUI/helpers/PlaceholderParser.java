package com.james090500.VelocityGUI.helpers;

import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class PlaceholderParser {

    public static Component of(Player player, String rawString) {
        if(rawString.contains("%chatcontrolred_nick%")) {
            String nickname = ChatControlHelper.getNick(player);
            rawString = rawString.replaceAll("%chatcontrolred_nick%", nickname);
        }

        Component component = LegacyComponentSerializer.legacyAmpersand().deserialize(rawString);
        return component;
    }

}
