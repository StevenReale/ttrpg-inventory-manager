package com.inventorymanager.model;

import java.math.BigDecimal;

public class Item {

    private int itemId;
    private String item_name;
    private String item_description;
    private String item_effect;
    private double item_value;

    public Item () {

    }

    public Item(int itemId, String item_name, String item_description, String item_effect, double item_value) {
        this.itemId = itemId;
        this.item_name = item_name;
        this.item_description = item_description;
        this.item_effect = item_effect;
        this.item_value = item_value;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return item_name;
    }

    public void setItemName(String item_name) {
        this.item_name = item_name;
    }

    public String getItemDescription() {
        return item_description;
    }

    public void setItemDescription(String item_description) {
        this.item_description = item_description;
    }

    public String getItemEffect() {
        return item_effect;
    }

    public void setItemEffect(String item_effect) {
        this.item_effect = item_effect;
    }

    public double getItemValue() {
        return item_value;
    }

    public void setItemValue(double item_value) {
        this.item_value = item_value;
    }
}
