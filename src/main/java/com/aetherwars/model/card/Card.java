package com.aetherwars.model.card;

public class Card {
    protected int id;
    protected String name;
    protected String description;
    protected String imagepath;
    protected int mana;

    public Card(int id, String name, String description, String imagepath, int mana) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imagepath = imagepath;
        this.mana = mana;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getImagepath() {
        return this.imagepath;
    }

    public int getMana() {
        return this.mana;
    }
}
