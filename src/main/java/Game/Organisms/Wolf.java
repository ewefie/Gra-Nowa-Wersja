package Game.Organisms;

import Game.*;

import javax.swing.*;
import java.awt.*;

public class Wolf extends Animal {

    public Wolf(Tile actualLocation, Board board) {
        this.board = board;
        this.actualLocation = actualLocation;
        this.strength = 9;
        this.initiative = 5;
        ImageIcon img = new ImageIcon("images/wolf.png");
        this.image = img.getImage();
        this.organismType = OrganismType.WOLF;
    }
    public Image getImage() {
        return image;
    }

    @Override
    public void action() {
        super.action();
    }

    @Override
    public void collision() {
        super.collision();
    }

    @Override
    public Tile getNewLocation() {
        return super.getNewLocation();
    }

    @Override
    public String toString() {
        return "Wolf " + actualLocation;
    }
}
