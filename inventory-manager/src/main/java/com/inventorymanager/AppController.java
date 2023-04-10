package com.inventorymanager;

import com.inventorymanager.dao.CharacterDao;
import com.inventorymanager.dao.JdbcCharacterDao;
import com.inventorymanager.dao.JdbcItemDao;
import com.inventorymanager.dao.ItemDao;
import com.inventorymanager.model.Character;
import com.inventorymanager.model.Item;
import com.inventorymanager.util.BasicConsole;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class AppController {

    private final BasicConsole console;
    private final AppView view;

    private final CharacterDao characterDao;
    private final ItemDao itemDao;

    private Character currentCharacter;
    private Item currentItem;

    public AppController(BasicConsole console) throws SQLException {
        this.console = console;
        // Set View for controller
        view = new AppView(console);
        // Set DataSource for inventory database and assign to all the JDBC-DAOs
        DataSource dataSource = setupDataSource();
        characterDao = new JdbcCharacterDao(dataSource);
        itemDao = new JdbcItemDao(dataSource);
    }

    /**
     * This method is called to begin the main menu loop. It's usually called from the application's
     * entry-point method (e.g., main)
     */
    public void run() {
        displayMainMenu();
    }

    public void displayMainMenu() {

        final String CHARACTER_SELECT = "Character - select";
        final String ITEM_SELECT = "Item - create, read, update, delete";
        final String EXIT = "Exit the program";
        final String[] MENU_OPTIONS = {CHARACTER_SELECT, ITEM_SELECT, EXIT};

        console.printBlankLine();
        console.printMessage("Welcome to the Golden Dragon Inventory Management System.");

        boolean finished = false;
        while (!finished) {
            console.printBlankLine();
            String mainMenuTitle = "Main Menu\n" + selectedBreadCrumbs();
            String selection = view.getMenuSelection(mainMenuTitle, MENU_OPTIONS);
            if (selection.equals(CHARACTER_SELECT)) {
                console.printBlankLine();
                List<Character> characters = characterDao.getAllCharacters();
                Character selectedCharacter = view.getCharacterSelection(characters);
                if (selectedCharacter == null) {
                    // Deselect the current character and item
                    currentCharacter = null;
                    currentItem = null;
                } else if (selectedCharacter.equals(currentCharacter) == false) {
                    // Switch to selected character and deselect item
                    currentCharacter = selectedCharacter;
                    currentItem = null;
                }
            } else if (selection.equals(ITEM_SELECT)) {
                console.printBlankLine();
                if (currentCharacter != null) {
                    itemMenu();
                } else {
                    console.printErrorMessage("No character selected.");
                }
            } else if (selection.equals(EXIT)) {
                finished = true;
            }
        }
    }

    private void itemMenu() {

        final String SELECT = "Select item";
        final String CREATE = "Create new item";
        final String UPDATE = "Update selected item";
        final String DELETE = "Delete selected item";
        final String RETURN = "Return to Main Menu";
        final String[] MENU_OPTIONS = {SELECT, CREATE, UPDATE, DELETE, RETURN};

        boolean finished = false;
        while (!finished) {
            console.printBlankLine();
            String title = "Item Menu\n" + selectedBreadCrumbs();
            String selection = view.getMenuSelection(title, MENU_OPTIONS);
            console.printDivider();
            console.printBlankLine();

            if (selection.equals(SELECT)) {
                List<Item> items = itemDao.getItemsByCharacterId(currentCharacter.getCharacterId());
                Item selectedItem = view.getItemSelection(items);
                if (selectedItem == null) {
                    // Deselect item
                    currentItem = null;
                } else if (selectedItem.equals(currentItem) == false) {
                    // Switch to selected item
                    currentItem = selectedItem;
                }
            } else if (selection.equals(CREATE)) {
                Item newItem = view.createItem();
                if (newItem != null) {
                    newItem = itemDao.createItem(newItem);
                    console.printBlankLine();
                    console.printMessage(newItem.toString() + " CREATED !!!");
                    currentItem = newItem;
                }
            } else if (selection.equals(UPDATE)) {
                if (currentItem != null) {
                    Item updatedItem = view.updateItem(currentItem);
                    if (updatedItem != null) {
                        if (itemDao.updateItem(updatedItem)) {
                            console.printBlankLine();
                            console.printMessage(updatedItem.toString() + " UPDATED !!!");
                            currentItem = updatedItem;
                        }
                    }
                } else {
                    console.printErrorMessage("No item selected.");
                }
            } else if (selection.equals(DELETE)) {
                if (currentItem != null) {
                    if (view.deleteItem(currentItem)) {
                        // DAO in action !!!
                        itemDao.deleteItem(currentItem.getItemId());
                        console.printBlankLine();
                        console.printMessage(currentItem.toString() + " DELETED !!!");
                        currentItem = null;
                    }
                } else {
                    console.printErrorMessage("No iem selected.");
                }
            } else if (selection.equals(RETURN)) {
                finished = true;
            }
        }
    }

    private DataSource setupDataSource() throws SQLException {

        // Drop and then recreate the application database under separate "admin" connection
        SingleConnectionDataSource adminDataSource = new SingleConnectionDataSource();
        adminDataSource.setUrl("jdbc:postgresql://localhost:5432/postgres");
        adminDataSource.setUsername("postgres");
        adminDataSource.setPassword("postgres1");
        JdbcTemplate adminJdbcTemplate = new JdbcTemplate(adminDataSource);
        adminJdbcTemplate.update("DROP DATABASE IF EXISTS \"InventoryManager\";");
        adminJdbcTemplate.update("CREATE DATABASE \"InventoryManager\";");

        // Setup up the application connection
        SingleConnectionDataSource dataSource = new SingleConnectionDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/InventoryManager");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");

        //  Refresh the application database by running the setup script
        ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("InventoryManager.sql"));

        return dataSource;
    }

    private String selectedBreadCrumbs() {

        return "Selected: " +
                (currentCharacter != null ? currentCharacter.getCharacterId() + ":" + currentCharacter.getCharacterName() : "---") + " >> " +
                (currentItem != null ? currentItem.getItemId() + ":" + currentItem.getItemName() + ":" + currentItem.getItemDescription() : "---");
    }
}
