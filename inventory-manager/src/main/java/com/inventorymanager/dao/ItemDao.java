package com.inventorymanager.dao;

import com.inventorymanager.model.Item;

import java.util.List;

public interface ItemDao {

    Item getItem(int itemId);

    List<Item> getItemsByCharacterId(int characterId);

    Item createItem(Item item);

    boolean updateItem(Item item);

    void deleteItem(int itemId);
}
