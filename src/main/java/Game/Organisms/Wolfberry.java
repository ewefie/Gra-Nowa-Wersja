package Game.Organisms;

import Game.*;

import javax.swing.*;
import java.awt.*;

public class Wolfberry extends Plant {
    private Image image;

    public Wolfberry(Tile actualLocation, Board board) {
        this.board = board;
        this.actualLocation = actualLocation;
        this.strength = 99;
        ImageIcon img = new ImageIcon("images/wolfberry.png");
        this.image = img.getImage();
        this.newTile = actualLocation;
        this.organismType = OrganismType.WOLFBERRY;

    }

    @Override
    public void action() {
        super.action();
    }

    @Override
    public void replicate() {
        if (getBiasedRandom(0.01f)) {//dodaję nowe rośliny prosto do mapy
            Tile location = board.findLocationForAnimal();
            board.getOrganismMap().put(location, new Wolfberry(location, board));
            Logger.log( "A new " + this.getClass() + " has been planted.");
        }
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
        return "Wolfberry " + actualLocation;
    }
}
