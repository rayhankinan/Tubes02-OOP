# Minecraft: Aether Realm Wars
> Tugas Besar 2 Mata Kuliah IF2210 Pemrograman Berorientasi Objek
> Built by Kelompok 8 - FAAAK

## Table of Contents
* [General Information](#general-information)
* [Structures](#structures)
* [Technologies Used](#technologies-used)
* [Setup](#setup)
* [Usage](#usage)
* [Screenshots](#screenshots)
* [Contacts](#contacts)

## General Information
Aplikasi ini merupakan sebuah aplikasi game kartu _turn based_ untuk 2 pemain. 
Pemain bermain secara bergantian pada 1 layar yang sama. 
Tujuan dari _game_ ini adalah menghabiskan _health points_ (HP) musuh. 
HP dapat berkurang apabila terkena serangan dari kartu karakter yang diletakkan di _board_.

## Structures
```
.
│   .gitignore
│   build.gradle
│   gradlew
│   gradlew.bat
│   README.md
│   settings.gradle
│
├───gradle
│   └───wrapper
│           gradle-wrapper.jar
│           gradle-wrapper.properties
│
└───src
    ├───main
    │   ├───java
    │   │   └───com
    │   │       └───aetherwars
    │   │           │   AetherWars.java
    │   │           │
    │   │           ├───controller
    │   │           │       BoardController.java
    │   │           │       CardController.java
    │   │           │       DrawController.java
    │   │           │       EndController.java
    │   │           │       FieldCardController.java
    │   │           │
    │   │           ├───model
    │   │           │   ├───board
    │   │           │   │       Board.java
    │   │           │   │       Phase.java
    │   │           │   │       TakeTurns.java
    │   │           │   │
    │   │           │   ├───card
    │   │           │   │   │   Card.java
    │   │           │   │   │   CardDatabase.java
    │   │           │   │   │   CardException.java
    │   │           │   │   │
    │   │           │   │   ├───character
    │   │           │   │   │       Affectable.java
    │   │           │   │   │       Attackable.java
    │   │           │   │   │       Character.java
    │   │           │   │   │       Summonable.java
    │   │           │   │   │       SummonedCharacter.java
    │   │           │   │   │       Type.java
    │   │           │   │   │       TypeComparator.java
    │   │           │   │   │
    │   │           │   │   └───spell
    │   │           │   │       │   Applicable.java
    │   │           │   │       │   Revertible.java
    │   │           │   │       │   Spell.java
    │   │           │   │       │
    │   │           │   │       ├───level
    │   │           │   │       │       Level.java
    │   │           │   │       │
    │   │           │   │       ├───morph
    │   │           │   │       │       Morph.java
    │   │           │   │       │
    │   │           │   │       ├───potion
    │   │           │   │       │       Potion.java
    │   │           │   │       │
    │   │           │   │       └───swap
    │   │           │   │               Swap.java
    │   │           │   │
    │   │           │   ├───deck
    │   │           │   │       Deck.java
    │   │           │   │       DeckException.java
    │   │           │   │
    │   │           │   └───player
    │   │           │           Player.java
    │   │           │           PlayerException.java
    │   │           │
    │   │           └───util
    │   │                   CSVReader.java
    │   │
    │   └───resources
    │       └───com
    │           └───aetherwars
    │               ├───material
    │               │       att.png
    │               │       attack.png
    │               │       att_2.png
    │               │       att_3.png
    │               │       BelweBoldBT.ttf
    │               │       bg-gvg.png
    │               │       card.png
    │               │       health.png
    │               │       hp.png
    │               │       mana.png
    │               │       material-design.css
    │               │       piloted.png
    │               │       race.png
    │               │       rarity-common.png
    │               │       title.png
    │               │
    │               ├───model
    │               │   ├───card
    │               │   │   ├───character
    │               │   │   │   ├───data
    │               │   │   │   │       character.csv
    │               │   │   │   │
    │               │   │   │   └───image
    │               │   │   │           Creeper.png
    │               │   │   │           Drowned.png
    │               │   │   │           Ender Dragon.png
    │               │   │   │           Enderman.png
    │               │   │   │           Endermite.png
    │               │   │   │           Ghast.png
    │               │   │   │           Magma Cube.png
    │               │   │   │           Obsidian.png
    │               │   │   │           Piglin Brute.png
    │               │   │   │           Sheep.png
    │               │   │   │           Shulker.png
    │               │   │   │           Skeleton.png
    │               │   │   │           Slime.png
    │               │   │   │           Villager.png
    │               │   │   │           Warden.png
    │               │   │   │           Wither Skeleton.png
    │               │   │   │           Wither.png
    │               │   │   │           Zombie.png
    │               │   │   │
    │               │   │   └───spell
    │               │   │       ├───level
    │               │   │       │   ├───data
    │               │   │       │   │       spell_lvl.csv
    │               │   │       │   │
    │               │   │       │   └───image
    │               │   │       │           Bottle O' Disenchanting.png
    │               │   │       │           Bottle O' Enchanting.png
    │               │   │       │
    │               │   │       ├───morph
    │               │   │       │   ├───data
    │               │   │       │   │       spell_morph.csv
    │               │   │       │   │
    │               │   │       │   └───image
    │               │   │       │           Creeper... Aw Man.png
    │               │   │       │           Crybaby Dominion.png
    │               │   │       │           Drowning.png
    │               │   │       │           Malin Kundang.png
    │               │   │       │           Sheepify.png
    │               │   │       │           Sugondese.png
    │               │   │       │
    │               │   │       ├───potion
    │               │   │       │   ├───data
    │               │   │       │   │       spell_ptn.csv
    │               │   │       │   │
    │               │   │       │   └───image
    │               │   │       │           Aromatic Ginger Rice.png
    │               │   │       │           Bad Alcohol.png
    │               │   │       │           Contract of Living Space.png
    │               │   │       │           Deathly Magic.png
    │               │   │       │           Divine Wind.png
    │               │   │       │           GPU Very Good.png
    │               │   │       │           Grabkeun.png
    │               │   │       │           Halal Porkchop.png
    │               │   │       │           Herobrine's Blessing.png
    │               │   │       │           Honey Bottle.png
    │               │   │       │           Kordas' Curse.png
    │               │   │       │           Mother's Prayer.png
    │               │   │       │           Sadikin Elixir.png
    │               │   │       │           SNMPTN Acceptance Letter.png
    │               │   │       │           SNMPTN Rejection Letter.png
    │               │   │       │           Spectral's Power.png
    │               │   │       │           Witch's Eye.png
    │               │   │       │           Yoasobi.png
    │               │   │       │
    │               │   │       └───swap
    │               │   │           ├───data
    │               │   │           │       spell_swap.csv
    │               │   │           │
    │               │   │           └───image
    │               │   │                   Axolotl Blood.png
    │               │   │                   Bone Marrow.png
    │               │   │                   Cat Food.png
    │               │   │                   Cooked Beef Juice.png
    │               │   │                   Detergent.png
    │               │   │                   Ghast Tears.png
    │               │   │                   Morning's Blessing.png
    │               │   │                   Potion of Bargaining.png
    │               │   │                   Potion of Turtle Master.png
    │               │   │                   Swab Test.png
    │               │   │
    │               │   ├───deck
    │               │   │   ├───data
    │               │   │   │       deck_1.csv
    │               │   │   │       deck_2.csv
    │               │   │   │
    │               │   │   └───image
    │               │   └───player
    │               │       ├───data
    │               │       └───image
    │               │               Alex.png
    │               │               Steve.png
    │               │
    │               └───view
    │                       Board.fxml
    │                       Card.fxml
    │                       Draw.fxml
    │                       End.fxml
    │                       FieldCard.fxml
    │
    └───test
        ├───java
        │       BoardTest.java
        │       CardTest.java
        │       DeckTest.java
        │       PlayerTest.java
        │
        └───resources
```

## Technologies Used
* JDK Amazon Corretto version 1.8.0
* JavaFX version 8.0.202
* JUnit version 4.13.2

## Setup

## Usage

## Screenshots

## Contacts
Created by:
* [@azkazkazka](https://github.com/azkazkazka)
* [@apwic](https://github.com/apwic)
* [@rayhankinan](https://github.com/rayhankinan)
* [@fikrikhoironn](https://github.com/fikrikhoironn)
* [@blueguy42](https://github.com/blueguy42)