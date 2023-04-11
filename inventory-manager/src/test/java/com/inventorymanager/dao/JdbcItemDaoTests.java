package com.inventorymanager.dao;

import com.inventorymanager.model.Item;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class JdbcItemDaoTests extends BaseDaoTests {

    private JdbcItemDao jdbcItemDao;

    private final Item ITEM_1 = new Item (1, "Item 1", "The First Item", "Appears first on a list of items", 100.0);
    private final Item ITEM_2 = new Item (2, "Item 2", "The Second Item", "Appears second on a list of items", 20.0);
    private final Item ITEM_3 = new Item (3, "Item 3", "The Third Item", "Appears third on a list of items", 3000.0);
    private final Item ITEM_4 = new Item (4, "Item 4", "The Fourth Item", "Appears fourth on a list of items", 4.0);
    private final List<Item> ITEMS_CARRIED_BY_CHAR_1 = Arrays.asList(ITEM_1, ITEM_2);
    @Before
    public void setup() {

        // Arrange - new instance of JdbcItemDao before each and every test
        jdbcItemDao = new JdbcItemDao(dataSource);
    }

    @Test
    public void getItem_returns_correct_item_for_id() {
        // Act
        Item item1 = jdbcItemDao.getItem(ITEM_1.getItemId());

        //Assert
        assertItemsMatch("Item returned from getItem() method should match", ITEM_1, item1);

    }

    @Test
    public void getItem_returns_null_item_for_invalid_id() {
        //Act
        Item nullItem = jdbcItemDao.getItem(10);

        //Assert
        Assert.assertNull(nullItem);
    }

    @Test
    public void getItemsByCharacterId_returns_items_for_character_id() {
        //Act
        List<Item> char1Items = jdbcItemDao.getItemsByCharacterId(1);

        //Assert
        Assert.assertEquals("List size should be the same", ITEMS_CARRIED_BY_CHAR_1.size(), char1Items.size());
        assertItemsMatch("First element of list should match", ITEMS_CARRIED_BY_CHAR_1.get(0), char1Items.get(0));
        assertItemsMatch("Last element of list should match", ITEMS_CARRIED_BY_CHAR_1.get(ITEMS_CARRIED_BY_CHAR_1.size()-1), char1Items.get(char1Items.size()-1));

    }

    @Test
    public void getItemsByCharacterId_returns_empty_list_for_invalid_character_id() {
        //Act
        List<Item> char1Items = jdbcItemDao.getItemsByCharacterId(10);

        //Assert
        Assert.assertEquals("List size should be size zero", 0, char1Items.size());


    }

    @Test
    public void createItem_returns_item_with_id_and_expected_values() {
        //Arrange
        Item newItem = new Item ();
        newItem.setItemName("Item 4");
        newItem.setItemDescription("Description");
        newItem.setItemEffect("Item does a thing");
        newItem.setItemValue(55.00);

        //Act
        Item createdItem = jdbcItemDao.createItem(newItem);
        newItem.setItemId(createdItem.getItemId());

        //Assert
        assertItemsMatch("Created item should have correct id and values", newItem, createdItem);

    }

    @Test
    public void updateItem_has_expected_values_when_retrieved() {
        //Arrange
        ITEM_3.setItemName("new name");
        ITEM_3.setItemDescription("new description");
        ITEM_3.setItemEffect("new effect");
        ITEM_3.setItemValue(66.00);

        //Act
        boolean updateHappened = jdbcItemDao.updateItem(ITEM_3);
        Item newItem3 = jdbcItemDao.getItem(3);

        //Assert
        Assert.assertTrue(updateHappened);
        assertItemsMatch("updated item should be updated", ITEM_3, newItem3 );
    }

    @Test
    public void deleted_item_cant_be_retrieved() {
        //Act
        jdbcItemDao.deleteItem(ITEM_4.getItemId());
        Item shouldBeDeleted = jdbcItemDao.getItem(ITEM_4.getItemId());

        //Assert
        Assert.assertNull(shouldBeDeleted);


    }

//Method not used
//    private static Item mapValuesToItem() {
//        return null;
//
//    }

    private void assertItemsMatch(String message, Item expected, Item actual) {
        Assert.assertEquals(message + ": item ids should match", expected.getItemId(), actual.getItemId() );
        Assert.assertEquals(message + ": item names should match", expected.getItemName(), actual.getItemName());
        Assert.assertEquals(message + ": item descriptions should match", expected.getItemDescription(), actual.getItemDescription());
        Assert.assertEquals(message + ": item effects should match", expected.getItemEffect(), actual.getItemEffect());
        Assert.assertEquals(message + ": item values should match", expected.getItemValue(), actual.getItemValue(), .001);
    }
}
