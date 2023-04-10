package com.inventorymanager.model;

public class Character {

    private int characterId;
    private String character_name;
    private String character_class;
    private int character_level;
    private String character_description;

    public int getCharacterId() {
        return characterId;
    }

    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }

    public String getCharacterName() {
        return character_name;
    }

    public void setCharacterName(String character_name) {
        this.character_name = character_name;
    }

    public String getCharacterClass() {
        return character_class;
    }

    public void setCharacterClass(String character_class) {
        this.character_class = character_class;
    }

    public int getCharacterLevel() {
        return character_level;
    }

    public void setCharacterLevel(int character_level) {
        this.character_level = character_level;
    }

    public String getCharacterDescription() {
        return character_description;
    }

    public void setCharacterDescription(String character_description) {
        this.character_description = character_description;
    }

    @Override
    public String toString() {
        return "Character{" +
                "characterId=" + characterId +
                ", character_name='" + character_name + '\'' +
                ", character_class='" + character_class + '\'' +
                ", character_level=" + character_level +
                ", character_description='" + character_description + '\'' +
                '}';
    }

}