package Game.Organisms;

import Game.*;

import javax.swing.*;
import java.awt.*;

public class Guarana extends Plant {

    private Image image;

    public Guarana(Tile actualLocation, Board board) {
        this.board = board;
        this.actualLocation = actualLocation;
        ImageIcon img = new ImageIcon("images/guarana.png");
        this.image = img.getImage();
        this.newTile = actualLocation;
        this.organismType = OrganismType.GUARANA;
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
            board.getOrganismMap().put(location, new Guarana(location, board));
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
        return "Guarana " + actualLocation;
    }
}
