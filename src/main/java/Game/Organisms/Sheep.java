package Game.Organisms;

import Game.*;

import javax.swing.*;
import java.awt.*;

public class Sheep extends Animal {


    public Sheep(Tile actualLocation, Board board) {
        this.board = board;
        this.actualLocation = actualLocation;
        this.strength = 4;
        this.initiative = 4;
        ImageIcon img = new ImageIcon("images/sheep.png");
        this.image = img.getImage();
        this.organismType = OrganismType.SHEEP;
    }

    @Override
    public void action()  {
        super.action();
    }

    public Image getImage() {
        return image;
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
        return "Sheep " + actualLocation;
    }
}