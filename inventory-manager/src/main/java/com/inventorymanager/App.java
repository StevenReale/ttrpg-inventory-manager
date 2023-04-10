package com.inventorymanager;

import com.inventorymanager.dao.CharacterDao;
import com.inventorymanager.dao.ItemDao;
import com.inventorymanager.util.SystemInOutConsole;

public class App {

    private ItemDao itemDao;
    private CharacterDao characterDao;

    public static void main(String[] args) {
        try {
            SystemInOutConsole systemInOutConsole = new SystemInOutConsole();
            AppController controller = new AppController(systemInOutConsole);
            controller.run();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
