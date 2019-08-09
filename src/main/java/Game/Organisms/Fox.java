package Game.Organisms;

import Game.*;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Fox extends Animal {

    public Image getImage() {
        return image;
    }

    public Fox(Tile actualLocation, Board board) {
        this.board = board;
        this.actualLocation = actualLocation;
        this.strength = 3;
        this.initiative = 7;
        ImageIcon img = new ImageIcon("images/fox.png");
        this.image = img.getImage();
        this.organismType = OrganismType.FOX;
    }

    @Override
    public void action()  {
        super.action();
    }

    @Override
    public Tile getNewLocation() {
        Random random = new Random();
        int newTileX;
        int newTileY;
        Tile tile;
        do {
            newTileX = random.nextInt((this.getActualLocation().getTileX() + 1) - (this.getActualLocation().getTileX() - 1) + 1) + (this.getActualLocation().getTileX() - 1);
            newTileY = random.nextInt((this.getActualLocation().getTileY() + 1) - (this.getActualLocation().getTileY() - 1) + 1) + (this.getActualLocation().getTileY() - 1);
            tile = new Tile(newTileX, newTileY);
        } while ((this.board.getOrganismMap().get(tile) != null && this.board.getOrganismMap().get(tile).getStrength() > this.getStrength())
                || tile.equals(this.actualLocation) || newTileX < 0 || newTileX >= this.board.getBlockNumber() || newTileY < 0 || newTileY >= this.board.getBlockNumber());
        return tile;
    }

    @Override
    public void collision() {
        super.collision();
    }

    @Override
    public String toString() {
        return "Fox " + actualLocation;
    }
}
