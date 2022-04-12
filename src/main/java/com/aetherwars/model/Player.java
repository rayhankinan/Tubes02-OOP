package com.aetherwars.model;

public class Player {
    private static final int MAX_HP = 80;
    private static final int MAX_MANA = 10;
    private static final int MIN_HP = 0;
    private static final int MIN_MANA = 0;
    // attribute
    private static int numOfPlayer = 0;
    private int id;
    private String name;
    private int health;
    private int mana;
    // private deck
    // private hand

    // methods
    Player()
    {
        Player.numOfPlayer++;
        this.id = Player.numOfPlayer;
        this.name = "No name";
        this.health = MAX_HP;
        this.mana = 1;
    }

    Player(String name) {
        Player.numOfPlayer++;
        this.id = Player.numOfPlayer;
        this.name = name;
        this.health = MAX_HP;
        this.mana = 1;
    }

    public int getId(){
        return this.id;
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
        int res = this.health + health;
        if (res < MIN_HP || res > MAX_HP) {
            return false;
        }
        else {
            return true;
        }
    }

    public boolean isValidMana(int mana) {
        int res = this.mana + mana;
        if (res < MIN_MANA || res > MAX_MANA) {
            return false;
        }
        else {
            return true;
        }
    }

    public void damage(int dmg){
        if (this.isValidHP((-1)*dmg)) {
            this.setHealth(this.getHealth() - dmg);
        }
        // EXCEPTION
    }

    public void display()
    {
        System.out.printf("Player %d:\n", this.getId());
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
//    public useCard
}
