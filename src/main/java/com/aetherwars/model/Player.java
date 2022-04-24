package com.aetherwars.model;

public class Player {
    private static final int MAX_HP = 80;
    private static final int MAX_MANA = 10;
    private static final int MIN_HP = 0;
    private static final int MIN_MANA = 0;
    // attribute
    private String name;
    private int health;
    private int mana;

    // methods
    public Player() {
        this.name = "No name";
        this.health = MAX_HP;
        this.mana = 1;
    }

    public Player(String name) {
        this.name = name;
        this.health = MAX_HP;
        this.mana = 1;
    }

    public String getName() {
        return this.name;
    }

    public int getHealth() {
        return this.health;
    }

    public int getMana() {
        return this.mana;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHealth(int hp){
        this.health = hp;
    }

    public void setMana(int mana){
        this.mana = mana;
    }

    public boolean isValidHP(int health) {
        return this.health + health >= MIN_HP && this.health + health <= MAX_HP;
    }

    public boolean isValidMana(int mana) {
        return this.mana + mana >= MIN_MANA && this.mana + mana <= MAX_MANA;
    }

    public void damage(int dmg) {
        if (this.isValidHP((-1)*dmg)) {
            this.setHealth(this.getHealth() - dmg);
        }
        // EXCEPTION
    }

    public void display() {
        System.out.printf("Name: %s\n", this.getName());
        System.out.printf("Mana: %d\n", this.getMana());
        System.out.printf("Health: %d\n\n", this.getHealth());
    }

    public static void main(String[] args)
    {
        Player Player1 = new Player("Ancha");
        Player Player2 = new Player("Aska");
        Player1.display();
        Player2.display();

        Player1.damage(30);
        System.out.println("ANCHA ABIS DIGEBOK ASKA, SKRG ANCHA STATSNYA BEGINI...");
        Player1.display();
    }
}
