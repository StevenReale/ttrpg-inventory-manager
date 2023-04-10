package com.inventorymanager.dao;

import com.inventorymanager.model.Item;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class JdbcItemDaoTests extends BaseDaoTests {

    // Step One: Add constants for Madge
    private static final int MADGE_CUSTOMER_ID = 3;
    private static final int MADGE_FIRST_SALE_ID = 5;

    // Step Two: Add constants for customer without sale and non-existent customer
    private static final int CUSTOMER_WITHOUT_SALES_ID = 5;
    private static final int NON_EXISTENT_CUSTOMER_ID = 7;

    private JdbcItemDao jdbcSaleDao;

    @Before
    public void setup() {

        // Arrange - new instance of JdbcItemDao before each and every test
        jdbcSaleDao = new JdbcItemDao(dataSource);
    }

    @Test
    public void getSale_returns_correct_sale_for_id() {

        // Step One: Replace Assert.fail("Test not implemented.")
        // Assert.fail("Test not implemented.");

        // Arrange - Create an instance of Madge's first Item object.
        Item madgeFirstItem = mapValuesToSale(MADGE_FIRST_SALE_ID, new BigDecimal("23.98"), true, MADGE_CUSTOMER_ID);

        // Act - retrieve Madge's first item
        Item item = jdbcSaleDao.getItem(MADGE_FIRST_SALE_ID);

        // Assert - retrieved item is not null and matches expected item
        Assert.assertNotNull("getSale(" + MADGE_FIRST_SALE_ID + ") returned null", item);
        assertSalesMatch("getSale(" + MADGE_FIRST_SALE_ID + ") returned wrong or partial data", madgeFirstItem, item);
    }

    @Test
    public void getSalesByCustomerId_returns_sales_for_customer_id() {

        // Step Two: Replace Assert.fail("Test not implemented.")
        // Assert.fail("Test not implemented.");

        // Act - retrieve items for Madge
        List<Item> items = jdbcSaleDao.getItemsbyCharacterId(MADGE_CUSTOMER_ID);
        // Assert - Madge has two existing items
        Assert.assertEquals("getSalesByCustomerId(" + MADGE_CUSTOMER_ID + ") returned wrong number of items",
                2, items.size());

        // Act - retrieve customer with no items
        items = jdbcSaleDao.getItemsbyCharacterId(CUSTOMER_WITHOUT_SALES_ID);
        // Assert - list of items is empty for customer with no items
        Assert.assertEquals("getSalesByCustomerId(" + CUSTOMER_WITHOUT_SALES_ID +
                ") without items returned wrong number of items", 0, items.size());

        // Act - retrieve customer that doesn't exist
        items = jdbcSaleDao.getItemsbyCharacterId(NON_EXISTENT_CUSTOMER_ID);
        // Assert - list of items is empty for customer that doesn't exist
        Assert.assertEquals("Customer doesn't exist, getSalesByCustomerId(" + NON_EXISTENT_CUSTOMER_ID +
                ") returned the wrong number of items", 0, items.size());
    }

    @Test
    public void createSale_returns_sale_with_id_and_expected_values() {

        // Step Three: Replace Assert.fail("Test not implemented.")
        // Assert.fail("Test not implemented.");

        // Arrange - instantiate a new Item object for Madge
        Item newItem = new Item();
        newItem.setTotal(new BigDecimal("34.56"));
        newItem.setDelivery(true);
        newItem.setCustomerId(MADGE_CUSTOMER_ID);

        // Act - create sale from instantiated Item object
        Item createdItem = jdbcSaleDao.createSale(newItem);

        // Assert - created sale is correct
        Assert.assertNotEquals("saleId not set when created, remained 0", 0, createdItem.getSaleId());
        Assert.assertEquals(newItem.getTotal(), createdItem.getTotal());
        Assert.assertEquals(newItem.isDelivery(), createdItem.isDelivery());
        Assert.assertEquals(newItem.getCustomerId(), createdItem.getCustomerId());
    }

    @Test
    public void updateSale_has_expected_values_when_retrieved() {

        // Step Four: Replace Assert.fail("Test not implemented.")
        // Assert.fail("Test not implemented.");

        // Arrange - retrieve Madge's first sale and change values
        Item itemToUpdate = jdbcSaleDao.getItem(MADGE_FIRST_SALE_ID);
        itemToUpdate.setTotal(new BigDecimal("89.32"));
        itemToUpdate.setDelivery(false);

        // Act - update the existing sale with changed values and re-retrieve
        jdbcSaleDao.updateSale(itemToUpdate);
        Item updatedItem = jdbcSaleDao.getItem(MADGE_FIRST_SALE_ID);

        // Assert - sale has been updated correctly
        assertSalesMatch("Updated Madge's first sale returned wrong or partial data", itemToUpdate, updatedItem);
    }

    @Test
    public void deleted_sale_cant_be_retrieved() {

        // Step Five: Replace Assert.fail("Test not implemented.")
        // Assert.fail("Test not implemented.");

        // Act - delete existing first item for Madge
        jdbcSaleDao.deleteItem(MADGE_FIRST_SALE_ID);

        // Assert - Madge's deleted item can't be retrieved
        Item item = jdbcSaleDao.getItem(MADGE_FIRST_SALE_ID);
        Assert.assertNull(item);
    }

    // Convenience method in lieu of a Item constructor with all the fields as parameters.
    // Similar to mapRowToSale() in JdbcItemDao.
    private static Item mapValuesToSale(int saleId, BigDecimal total, boolean delivery, Integer customerId) {

        Item item = new Item();
        item.setSaleId(saleId);
        item.setTotal(total);
        item.setDelivery(delivery);
        item.setCustomerId(customerId);
        return item;
    }

    private void assertSalesMatch(String message, Item expected, Item actual) {

        Assert.assertEquals(message, expected.getSaleId(), actual.getSaleId());
        Assert.assertEquals(message, expected.getTotal(), actual.getTotal());
        Assert.assertEquals(message, expected.isDelivery(), actual.isDelivery());
        Assert.assertEquals(message, expected.getCustomerId(), actual.getCustomerId());
    }
}
