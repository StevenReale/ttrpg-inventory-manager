package com.inventorymanager;

import com.inventorymanager.model.Character;
import com.inventorymanager.model.Item;
import com.inventorymanager.util.BasicConsole;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class AppView {

    private final BasicConsole console;

    public AppView(BasicConsole console) {
        this.console = console;
    }

    public String getMenuSelection(String menuTitle, String[] options) {
        console.printBanner(menuTitle);
        return console.getMenuSelection(options);
    }

    public Character getCharacterSelection(List<Character> characters) {
        Character selectedCharacter = null;

        if (characters.size() > 0) {
            console.printMessage("Character List");
            console.printDivider();
            String[] options = new String[characters.size()];
            for (int i = 0; i < characters.size(); i++) {
                options[i] = characters.get(i).getCharacterName(); //.toString();
            }
            Integer selection = console.getMenuSelectionIndex(options, true);
            if (selection != null) {
                selectedCharacter = characters.get(selection);
            }
        } else {
            console.printErrorMessage("No characters found.");
        }
        return selectedCharacter;
    }

    public Item getItemSelection(List<Item> items) {
        Item selectedItem = null;

        if (items.size() > 0) {
            console.printMessage("Item List");
            console.printDivider();
            String[] options = new String[items.size()];
            for (int i = 0; i < items.size(); i++) {
                options[i] = items.get(i).toString();
            }
            Integer selection = console.getMenuSelectionIndex(options, true);
            if (selection != null) {
                selectedItem = items.get(selection);
            }
        } else {
            console.printErrorMessage("No items for character.");
        }
        return selectedItem;
    }

    public Item createItem() {

        Item newItem = null;

        console.printMessage("Enter new item information");
        console.printDivider();
        String name = console.promptForString("Item name: ");
        String description = console.promptForString("Item description: ");
        String effect = console.promptForString("Item effect: ");
        Integer value = console.promptForInteger("Item value: ");
        console.printDivider();

        console.printBlankLine();
        boolean createNewItem = console.promptForYesNo("Are you sure you wish to create the new item (Y/N)?: ");
        if (createNewItem) {
            newItem = new Item();
            newItem.setItemName(name);
            newItem.setItemDescription(description);
            newItem.setItemEffect(effect);
            newItem.setItemValue(value);
        }
        return newItem;
    }

    public void displayItem(Item item) {
        console.printMessage("Current item information");
        console.printDivider();
        displayItemInfo(item);
    }

    public void displayProposedUpdate(Item item){
        console.printMessage("Proposed update to item");
        console.printDivider();
        displayItemInfo(item);
    }

    public void displayItemInfo(Item item){
        console.printMessage("Item Id: " + item.getItemId());
        console.printMessage("Name: " + item.getItemName());
        console.printMessage("Description: " + item.getItemDescription());
        console.printMessage("Effect: " + item.getItemEffect());
        console.printMessage("Value: " + item.getItemValue());
    }

    public Item updateItem(Item item) {

        displayItem(item);
        console.printDivider();

        console.printBlankLine();
        console.printMessage("Update item information");
        console.printDivider();
        Item updatedItem = new Item();
        updatedItem.setItemId(item.getItemId());
        updatedItem.setItemName(defaultOnEnter(console.promptForString("Name: "), item.getItemName()));
        updatedItem.setItemDescription(defaultOnEnter(console.promptForString("Description: "), item.getItemDescription()));
        updatedItem.setItemEffect(defaultOnEnter(console.promptForString("Effect: "), item.getItemEffect()));
        updatedItem.setItemValue(defaultOnEnter(console.promptForInteger("Value: "), (int) item.getItemValue()));
        console.printDivider();

        displayProposedUpdate(updatedItem);
        console.printDivider();

        console.printBlankLine();
        boolean okToUpdate = console.promptForYesNo("Are you sure you wish to update the item (Y/N)?: ");
        if (! okToUpdate) {
            updatedItem = null;
        }
        return updatedItem;
    }

    public boolean deleteItem(Item item) {
        displayItem(item);
        console.printDivider();

        console.printBlankLine();
        return console.promptForYesNo("Are you sure you wish to delete the item (Y/N)?: ");
    }

    private String defaultOnEnter(String response, String defaultValue) {
        if (response.isBlank()) {
            return defaultValue;
        }
        else {
            return response;
        }
    }

    private LocalDate defaultOnEnter(LocalDate response, LocalDate defaultValue) {
        if (response == null) {
            return defaultValue;
        }
        else {
            return response;
        }
    }

    private Integer defaultOnEnter(Integer response, Integer defaultValue) {
        if (response == null) {
            return defaultValue;
        }
        else {
            return response;
        }
    }

    private BigDecimal defaultOnEnter(BigDecimal response, BigDecimal defaultValue) {
        if (response == null) {
            return defaultValue;
        }
        else {
            return response;
        }
    }
}
