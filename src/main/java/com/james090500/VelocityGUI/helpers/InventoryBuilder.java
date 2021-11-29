package com.james090500.VelocityGUI.helpers;

import com.james090500.VelocityGUI.config.Configs;
import com.velocitypowered.api.network.ProtocolVersion;
import dev.simplix.protocolize.api.inventory.Inventory;
import dev.simplix.protocolize.api.item.ItemStack;
import dev.simplix.protocolize.data.ItemType;
import dev.simplix.protocolize.data.inventory.InventoryType;
import lombok.Getter;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.querz.nbt.tag.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InventoryBuilder {

    @Getter private int protocolVersion;
    @Getter private InventoryType rows;
    @Getter private TextComponent title;
    @Getter private List<ItemStack> emptyItems = new ArrayList<>();
    @Getter private HashMap<Integer, ItemStack> items = new HashMap<>();

    /**
     * The builder
     * @param protocolVersion
     */
    public InventoryBuilder(ProtocolVersion protocolVersion) {
        this.protocolVersion = protocolVersion.getProtocol();
    }

    /**
     * Sets the rows of the GUI to display
     * @param rows
     */
    public void setRows(int rows) {
        this.rows = getInventoryType(rows);
    }

    /**
     * Sets the title and converts a string to a TextComponent
     * @param title
     */
    public void setTitle(String title) {
        this.title = LegacyComponentSerializer.legacyAmpersand().deserialize(title);
    }

    /**
     * Set the empty item
     * @param item
     */
    public void setEmpty(String item) {
        ItemStack itemStack = new ItemStack(ItemType.valueOf(item));
        itemStack.displayName("");
        itemStack.amount((byte) 1);

        int totalSlots = this.getRows().getTypicalSize(protocolVersion);
        for(int i = 0; i < totalSlots; i++) {
            emptyItems.add(itemStack);
        }
    }

    /**
     * Add items to the panel
     * @param guiItems
     */
    public void setItems(HashMap<Integer, Configs.Item> guiItems) {
        guiItems.forEach((index, guiItem) -> {
            //Set the item Material, Name and Amount
            ItemStack itemStack = new ItemStack(ItemType.valueOf(guiItem.getMaterial()));
            itemStack.displayName(LegacyComponentSerializer.legacyAmpersand().deserialize(guiItem.getName()));
            itemStack.amount(guiItem.getStack());

            //Set any lore on the item
            for(String lore : guiItem.getLore()) {
                itemStack.addToLore(LegacyComponentSerializer.legacyAmpersand().deserialize(lore));
            }

            //Set enchantment on the item if needed
            CompoundTag tag = itemStack.nbtData();
            if(guiItem.isEnchanted()) {
                ListTag<CompoundTag> enchantments = new ListTag<>(CompoundTag.class);
                CompoundTag enchantment = new CompoundTag();
                enchantment.put("id", new StringTag("minecraft:unbreaking"));
                enchantment.put("lvl", new ShortTag((short) 1));
                enchantments.add(enchantment);
                tag.put("Enchantments", enchantments);
            }
            tag.put("HideFlags", new IntTag(99));
            tag.put("overrideMeta", new ByteTag((byte)1));

            //Add to a hashmap
            items.put(index, itemStack);
        });
    }

    /**
     * Get the type of inventory by the rows
     * @param value
     * @return
     */
    private InventoryType getInventoryType(int value) {
        switch(value) {
            case 1:
                return InventoryType.GENERIC_9X1;
            case 2:
                return InventoryType.GENERIC_9X2;
            case 3:
                return InventoryType.GENERIC_9X3;
            case 4:
                return InventoryType.GENERIC_9X4;
            case 5:
                return InventoryType.GENERIC_9X5;
            default:
                return InventoryType.GENERIC_9X6;
        }
    }

    /**
     * Build the inventory
     * @return
     */
    public Inventory build() {
        Inventory inventory = new Inventory(this.getRows());
        inventory.title(this.getTitle());
        inventory.items(this.getEmptyItems());

        this.getItems().forEach((index, item) -> {
            inventory.item(index, item);
        });

        return inventory;
    }

}
