package Game.Organisms;

import Game.*;

import java.awt.*;
import java.util.Random;

public abstract class Organism {
    protected int strength;
    protected int initiative;
    protected boolean defeated;
    protected int age;
    protected Image image;
    protected Board board;
    protected Tile actualLocation;
    protected Tile newTile;
    protected OrganismType organismType;

    public Organism() {
        this.age = 0;
        this.defeated = false;
        this.actualLocation = new Tile();
        this.newTile = new Tile();
    }

    public OrganismType getOrganismType() {
        return organismType;
    }

    public boolean isDefeated() {
        return defeated;
    }

    public void setDefeated(boolean defeated) {
        this.defeated = defeated;
    }

    public Image getImage() {
        return image;
    }

    public Tile getActualLocation() {
        return actualLocation;
    }

    public void setActualLocation(Tile actualLocation) {
        this.actualLocation.setTileX(actualLocation.getTileX());
        this.actualLocation.setTileY(actualLocation.getTileY());
    }

    public Board getBoard() {
        return board;
    }

    public void setNewTile(Tile newTile) {
        this.newTile.setTileX(newTile.getTileX());
        this.newTile.setTileY(newTile.getTileY());
    }

    public int getAge() {
        return age;
    }

    public int getStrength() {
        return strength;
    }

    public int getInitiative() {
        return initiative;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public abstract void collision();

    public abstract void action();

    public Tile getNewTile() {
        return newTile;
    }

    protected static boolean getBiasedRandom(float bias) {
        Random random = new Random();
        float rand = random.nextFloat();
        if (rand < bias) {
            return true;
        } else {
            return false;
        }
    }

    public Tile getNewLocation() {
        Random random = new Random();
        int newTileX = random.nextInt((this.actualLocation.getTileX() + 1) - (this.actualLocation.getTileX() - 1) + 1) + (this.actualLocation.getTileX() - 1);
        int newTileY = random.nextInt((this.actualLocation.getTileY() + 1) - (this.actualLocation.getTileY() - 1) + 1) + (this.actualLocation.getTileY() - 1);
        while (new Tile(newTileX, newTileY).equals(this.actualLocation)
                || newTileX < 0 || newTileX >= this.board.getBlockNumber() || newTileY < 0
                || newTileY >= this.board.getBlockNumber()) {
            newTileX = random.nextInt((this.actualLocation.getTileX() + 1) - (this.actualLocation.getTileX() - 1) + 1) + (this.actualLocation.getTileX() - 1);
            newTileY = random.nextInt((this.actualLocation.getTileY() + 1) - (this.actualLocation.getTileY() - 1) + 1) + (this.actualLocation.getTileY() - 1);
        }
        return new Tile(newTileX, newTileY);
    }

    @Override
    public String toString() {
        return this.getClass() +
                "" + actualLocation;
    }
}
