package com.inventorymanager.dao;

import com.inventorymanager.model.Character;

import java.util.List;

public interface CharacterDao {

    Character getCharacter(int characterId);

    List<Character> getAllCharacters();

    Character addCharacter(Character character);

    boolean updateCharacter(Character character);

    void deleteCharacter(int characterId);

}
