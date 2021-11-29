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
            String fileName = file.getName().replace(".toml", "");
            panels.put(fileName, new Toml().read(file).to(Panel.class));
        }

        velocityGUI.getLogger().info(panels.get("example").toString());
    }

    public class Panel {

        @Getter private String name;
        @Getter private String perm;
        @Getter private int rows;
        @Getter private String title;
        @Getter private String empty;
        @Getter private HashMap<Integer, Item> items;

        @Override
        public String toString() {
            return "Panel{" +
                    "name='" + name + '\'' +
                    ", perm='" + perm + '\'' +
                    ", rows=" + rows +
                    ", title='" + title + '\'' +
                    ", empty='" + empty + '\'' +
                    ", items=" + items +
                    '}';
        }
    }

    public class Item {

        @Getter private String name;
        @Getter private String material;
        @Getter private byte stack;
        @Getter private String[] lore;
        @Getter private boolean enchanted;


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
