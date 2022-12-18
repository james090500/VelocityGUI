package com.james090500.VelocityGUI.helpers;

import com.velocitypowered.api.proxy.Player;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;

public class LuckPermsHelper {

    public static String getMeta(Player player, String queryOption) {
        if(!doesClassExist("net.luckperms.api.LuckPerms")) {
            return null;
        }

        LuckPerms api = LuckPermsProvider.get();
        String metaValue = api.getPlayerAdapter(Player.class).getMetaData(player).getMetaValue(queryOption);
        return metaValue == null ? "" : metaValue;
    }

    /**
     * Checks if a class exists or not
     * @param name
     * @return
     */
    private static boolean doesClassExist(String name) {
        try {
            Class c = Class.forName(name);
            if (c != null) {
                return true;
            }
        } catch (ClassNotFoundException e) {}
        return false;
    }

}
