package Game.Organisms;

import Game.*;

import javax.swing.*;
import java.awt.*;

public class Dandelion extends Plant {


    public Image getImage() {
        return image;
    }

    @Override
    public void collision() {

    }

    public Dandelion(Tile actualLocation, Board board) {
        this.board = board;
        this.actualLocation = actualLocation;
        ImageIcon img = new ImageIcon("images/mlecz.png");
        this.image = img.getImage();
        this.newTile = actualLocation;
        this.organismType = OrganismType.DANDELION;
    }

    @Override
    public void action() {
        for (int i = 0; i < 3; i++) {
            replicate();
        }
    }

    public void replicate() {
        if (getBiasedRandom(0.01f)) {//dodaję nowe dandeliony prosto do mapy
            Tile location = board.findLocationForAnimal();
            board.getOrganismMap().put(location, new Dandelion(location, board));
            Logger.log("A new " + this.getClass() + " has been planted.");
        }
    }

    @Override
    public Tile getNewLocation() {
        return super.getNewLocation();
    }

    @Override
    public String toString() {
        return "Dandelion " + actualLocation;
    }
}
