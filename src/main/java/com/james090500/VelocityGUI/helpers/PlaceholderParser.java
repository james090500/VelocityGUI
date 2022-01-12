package com.james090500.VelocityGUI.helpers;

import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class PlaceholderParser {

    public static TextComponent of(Player player, String rawString) {
        if(rawString.contains("%chatcontrolred_nick%")) {
            String nickname = ChatControlHelper.getNick(player);
            rawString = rawString.replaceAll("%chatcontrolred_nick%", nickname);
        }

        TextComponent textComponent = LegacyComponentSerializer.legacyAmpersand().deserialize(rawString);
        return textComponent;
    }

}
