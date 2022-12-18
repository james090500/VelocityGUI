package com.james090500.VelocityGUI.config;

import com.james090500.VelocityGUI.VelocityGUI;
import com.moandjiezana.toml.Toml;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;

public class Configs {

    @Getter private static HashMap<String, Panel> panels = new HashMap<>();

    /**
     * Loads the config files.
     * @param velocityGUI
     */
    public static void loadConfigs(VelocityGUI velocityGUI) {
        //Create data directory
        if(!velocityGUI.getDataDirectory().toFile().exists()) {
            velocityGUI.getDataDirectory().toFile().mkdir();
        }

        //Create panel directory
        File panelDir = new File(velocityGUI.getDataDirectory().toFile() + "/panels");
        if(!panelDir.exists()) {
            panelDir.mkdir();
        }

        if(panelDir.listFiles().length == 0) {
            try (InputStream in = VelocityGUI.class.getResourceAsStream("/example.toml")) {
                Files.copy(in, new File(panelDir + "/example.toml").toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for(File file : panelDir.listFiles()) {
            Panel panel = new Toml().read(file).to(Panel.class);
            panels.put(panel.getName(), panel);
        }
    }

    public class Panel {

        @Getter private String name;
        @Getter private String perm;
        @Getter private int rows ;
        @Getter private String title;
        @Getter private String empty;
        @Getter private String sound;
        @Getter private String[] commands;
        @Getter private HashMap<Integer, Item> items;

        @Override
        public String toString() {
            return "Panel{" +
                    "name='" + name + '\'' +
                    ", perm='" + perm + '\'' +
                    ", rows=" + rows +
                    ", title='" + title + '\'' +
                    ", empty='" + empty + '\'' +
                    ", sound='" + sound + '\'' +
                    ", items=" + items +
                    '}';
        }
    }

    public class Item {

        private String name;
        @Getter private String material;
        private byte stack;
        @Getter private String[] lore;
        @Getter private boolean enchanted;
        @Getter private String[] commands;

        /**
         * Return name or make empty if missed from config
         * @return
         */
        public String getName() {
            return (name != null) ? name : "&f";
        }

        /**
         * If stack is missed from config make it 1
         * @return
         */
        public byte getStack() {
            return (stack > 0) ? stack : 1;
        }

        @Override
        public String toString() {
            return "GuiItem{" +
                    "name='" + name + '\'' +
                    ", material='" + material + '\'' +
                    ", stack=" + stack +
                    ", lore=" + Arrays.toString(lore) +
                    ", enchanted=" + enchanted +
                    '}';
        }
    }

}
