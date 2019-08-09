package Game.Organisms;
import Game.*;

import javax.swing.*;
import java.awt.*;

public class Grass extends Plant {

    public Grass(Tile actualLocation, Board board) {
        this.board = board;
        this.actualLocation = actualLocation;
        ImageIcon img = new ImageIcon("images/trawa.png");
        image = img.getImage();
        this.newTile = actualLocation;
        this.organismType = OrganismType.GRASS;
    }

    public Image getImage() {
        return image;
    }

    @Override
    public void collision() {

    }

    @Override
    public void replicate() {
        if (getBiasedRandom(0.01f)) {//dodaję nowe rośliny prosto do mapy
            Tile location = board.findLocationForAnimal();
            board.getOrganismMap().put(location, new Grass(location, board));
            Logger.log("A new " + this.getClass() + " has been planted.");
        }
    }

    @Override
    public void action() {
        super.action();
    }

    @Override
    public Tile getNewLocation() {
        return super.getNewLocation();
    }

    @Override
    public String toString() {
        return "Grass " + actualLocation;
    }
}
