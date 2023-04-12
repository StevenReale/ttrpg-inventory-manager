package com.inventorymanager.dao;

import com.inventorymanager.model.Character;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcCharacterDao implements CharacterDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCharacterDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Character getCharacter(int characterId) {

        Character character = null;
        String sql = "SELECT * FROM character WHERE character_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, characterId);
        if (results.next()) {
            character = mapRowToCharacter(results);
        }
        return character;
    }

    @Override
    public List<Character> getAllCharacters() {

        List<Character> characters = new ArrayList<>();
        String sql = "SELECT * FROM character ORDER BY character_name;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Character character = mapRowToCharacter(results);
            characters.add(character);
        }
        return characters;
    }

    @Override
    public Character createCharacter(Character character) {
        String sql = "INSERT INTO character (character_name, character_class, character_level, character_description) VALUES (?, ?, ?, ?)";
        int CharacterId = jdbcTemplate.queryForObject(sql, Integer.class, character.getCharacterName(), character.getCharacterClass(), character.getCharacterLevel(), character.getCharacterDescription());
        character.setCharacterId(CharacterId);
        return character;
    }

    @Override
    public boolean updateCharacter(Character character) {
        String sql = "UPDATE character SET character_name = ?, character_class = ?, character_level = ?, character_description = ? WHERE character_id = ?";
        return jdbcTemplate.update(sql, character.getCharacterName(), character.getCharacterClass(), character.getCharacterLevel(), character.getCharacterDescription(), character.getCharacterId()) > 0;

    }

    @Override
    public void deleteCharacter(int characterId) {
        String sql = "DELETE FROM character WHERE character_id = ?";
        jdbcTemplate.update(sql, characterId);
    }

    private Character mapRowToCharacter(SqlRowSet rowSet) {
        Character character = new Character();
        character.setCharacterId(rowSet.getInt("character_id"));
        character.setCharacterName(rowSet.getString("character_name"));
        character.setCharacterClass(rowSet.getString("character_class"));
        character.setCharacterLevel(rowSet.getInt("character_level"));
        character.setCharacterDescription(rowSet.getString("character_description"));
        return character;
    }
}
