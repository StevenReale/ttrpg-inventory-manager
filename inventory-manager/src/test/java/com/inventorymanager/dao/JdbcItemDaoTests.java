package com.inventorymanager.dao;

import com.inventorymanager.model.Item;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class JdbcItemDaoTests extends BaseDaoTests {



    private JdbcItemDao jdbcItemDao;

    @Before
    public void setup() {

        // Arrange - new instance of JdbcItemDao before each and every test
        jdbcItemDao = new JdbcItemDao(dataSource);
    }

    @Test
    public void getItem_returns_correct_item_for_id() {

    }

    @Test
    public void getItemsByCharacterId_returns_items_for_character_id() {


    }

    @Test
    public void createItem_returns_item_with_id_and_expected_values() {


    }

    @Test
    public void updateItem_has_expected_values_when_retrieved() {

    }

    @Test
    public void deleted_item_cant_be_retrieved() {


    }


    private static Item mapValuesToItem() {
        return null;

    }

    private void assertItemsMatch(String message, Item expected, Item actual) {


    }
}
