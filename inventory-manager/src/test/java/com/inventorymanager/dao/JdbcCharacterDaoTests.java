package com.inventorymanager.dao;

import com.inventorymanager.model.Character;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class JdbcCharacterDaoTests extends BaseDaoTests {

    private JdbcCharacterDao jdbcCharacterDao;

    private final Character CHARACTER_1 = new Character(1, "Char 1", "Monk", 10, "A Known Tester");
    private final Character CHARACTER_2 = new Character(2, "Char 2", "Thief", 12, "A Known Hester");
    private final Character CHARACTER_3 = new Character(3, "Char 3", "Fighter", 14, "A Known Lester");
    private final List<Character> ALL_CHARACTERS = Arrays.asList(CHARACTER_1, CHARACTER_2, CHARACTER_3);

    @Before
    public void setup() {
        jdbcCharacterDao = new JdbcCharacterDao(dataSource);
    }

    @Test
    public void get_character_by_id_method_returns_correct_character() {
        //Act
        Character char1 = jdbcCharacterDao.getCharacter(CHARACTER_1.getCharacterId());

        //Assert
        assertCharactersMatch("Get character should return correct character", CHARACTER_1, char1);
    }

    @Test
    public void get_character_by_id_should_return_null_for_invalid_character_id() {
        //Act
        Character nullChar = jdbcCharacterDao.getCharacter(100);

        //Assert
        Assert.assertNull(nullChar);
    }

    @Test
    public void get_all_characters_returns_correct_and_fully_populated_list() {
        //Act
        List<Character> getChars = jdbcCharacterDao.getAllCharacters();

        //Assert
        Assert.assertEquals("List sizes should match", ALL_CHARACTERS.size(), getChars.size());
        assertCharactersMatch("First characters on lists should match", ALL_CHARACTERS.get(0), getChars.get(0));
        assertCharactersMatch("Last characters on lists should match", ALL_CHARACTERS.get(ALL_CHARACTERS.size()-1), getChars.get(getChars.size()-1));
    }

    @Test
    public void create_character_returns_character_with_correct_id_and_values() {
        //Arrange
        Character newChar = new Character();
        newChar.setCharacterName("New Char");
        newChar.setCharacterClass("New Class");
        newChar.setCharacterDescription("Exceedingly new");
        newChar.setCharacterLevel(1);

        //Act
        Character createdChar = jdbcCharacterDao.createCharacter(newChar);
        newChar.setCharacterId(createdChar.getCharacterId());

        //Assert
        assertCharactersMatch("Created character should match character to create", newChar, createdChar);
    }

    @Test
    public void update_character_correctly_updates_in_database() {
        //Arrange
        CHARACTER_1.setCharacterName("Changed Name");
        CHARACTER_1.setCharacterDescription("Changed Description");
        CHARACTER_1.setCharacterLevel(800);
        CHARACTER_1.setCharacterClass("Throgueizard");

        //Act
        boolean updated = jdbcCharacterDao.updateCharacter(CHARACTER_1);
        Character changedChar = jdbcCharacterDao.getCharacter(CHARACTER_1.getCharacterId());

        //Assert
        Assert.assertTrue(updated);
        assertCharactersMatch("Updated character should match changed details", CHARACTER_1, changedChar);

    }

    @Test
    public void deleted_character_cannot_be_retrieved_from_database() {
        //Act
        jdbcCharacterDao.deleteCharacter(CHARACTER_1.getCharacterId());
        Character character = jdbcCharacterDao.getCharacter(CHARACTER_1.getCharacterId());

        //Assert
        Assert.assertNull(character);
    }

    private void assertCharactersMatch(String message, Character expected, Character actual) {
        Assert.assertEquals(message + ": character IDs should match", expected.getCharacterId(), actual.getCharacterId());
        Assert.assertEquals(message + ": character names should match", expected.getCharacterName(), actual.getCharacterName());
        Assert.assertEquals(message + ": character classes should match", expected.getCharacterClass(), actual.getCharacterClass());
        Assert.assertEquals(message + ": character levels should match", expected.getCharacterLevel(), actual.getCharacterLevel());
        Assert.assertEquals(message + ": character descriptions should match", expected.getCharacterDescription(), actual.getCharacterDescription());
    }




}
