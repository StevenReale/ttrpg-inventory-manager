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

    public Character createCharacter(Character currentCharacter) {

        Character newCharacter = null;

        console.printMessage("Enter new character information");
        console.printDivider();
        String name = console.promptForString("Character name: ");
        String characterClass = console.promptForString("Character class: ");
        int level = console.promptForInteger("Character level: ");
        String description = console.promptForString("Character description: ");
        console.printDivider();

        console.printBlankLine();
        boolean createNewCharacter = console.promptForYesNo("Are you sure you wish to create the new character (Y/N)?: ");
        if (createNewCharacter) {
            newCharacter = new Character();
            newCharacter.setCharacterName(name);
            newCharacter.setCharacterClass(characterClass);
            newCharacter.setCharacterLevel(level);
            newCharacter.setCharacterDescription(description);
        }
        return newCharacter;
    }

    public void displayCharacter(Character character) {
        console.printMessage("Current character information");
        console.printDivider();
        displayCharacterInfo(character);
    }

    public void displayProposedUpdate(Character character){
        console.printMessage("Proposed update to character");
        console.printDivider();
        displayCharacterInfo(character);
    }

    public void displayCharacterInfo(Character character) {
        console.printMessage("Character ID: " + character.getCharacterId());
        console.printMessage("Character Name: " + character.getCharacterName());
        console.printMessage("Character Class: " + character.getCharacterClass());
        console.printMessage("Character Level: " + character.getCharacterLevel());
        console.printMessage("Character Description: " + character.getCharacterDescription());
        console.printDivider();
    }

    public Character updateCharacter(Character character) {
        displayCharacter(character);
        console.printDivider();

        console.printBlankLine();
        console.printMessage("Update character information");
        console.printDivider();
        Character updatedCharacter = new Character();
        updatedCharacter.setCharacterId(character.getCharacterId());
        updatedCharacter.setCharacterName(defaultOnEnter(console.promptForString("Name: "), character.getCharacterName()));
        updatedCharacter.setCharacterClass(defaultOnEnter(console.promptForString("Class: "), character.getCharacterClass()));
        updatedCharacter.setCharacterLevel(defaultOnEnter(console.promptForInteger("Level: "), character.getCharacterLevel()));
        updatedCharacter.setCharacterDescription(defaultOnEnter(console.promptForString("Description: "), character.getCharacterDescription()));
        console.printDivider();

        displayProposedUpdate(updatedCharacter);
        console.printDivider();

        console.printBlankLine();
        boolean updateCharacter = console.promptForYesNo("Are you sure you wish to update the character (Y/N)?: ");
        if (updateCharacter) {
            return updatedCharacter;
        } else {
            return null;
        }
    }

    public boolean deleteCharacter(Character character) {
        displayCharacter(character);
        console.printDivider();

        console.printBlankLine();
        boolean deleteCharacter = console.promptForYesNo("Are you sure you wish to delete the character (Y/N)?: ");
        return deleteCharacter;
    }

    //Item methods

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
