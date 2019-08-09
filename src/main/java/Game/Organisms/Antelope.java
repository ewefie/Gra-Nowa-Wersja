package Game.Organisms;

import Game.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Antelope extends Animal {
    Random random = new Random();

    public Antelope(Tile actualLocation, Board board) {
        this.board = board;
        this.actualLocation = actualLocation;
        this.strength = 4;
        this.initiative = 4;
        ImageIcon img = new ImageIcon("images/antylopa.png");
        this.image = img.getImage();
        this.organismType = OrganismType.ANTELOPE;
    }

    public Image getImage() {
        return image;
    }

    @Override
    public void action()  {
        super.action();
    }

    @Override
    public Tile getNewLocation() {
        int newX, newY;
        Tile newTile;
        List<Tile> tileList = new ArrayList<>();
        for (int i = -2; i <= 2; i += 2) {
            for (int j = -2; j <= 2; j += 2) {
                newX = this.actualLocation.getTileX() + i;
                newY = this.actualLocation.getTileY() + j;
                newTile = new Tile(newX, newY);
                if (!newTile.equals(this.getNewTile()) && newX >= 0 && newX < this.board.getBlockNumber() && newY >= 0 && newY < this.board.getBlockNumber())
                    tileList.add(new Tile(newX, newY));
            }
        }
        int rand = random.nextInt(tileList.size());
        newTile = tileList.get(rand);
        return newTile;
    }

    @Override
    public void collision()  {
        super.collision();
    }

    @Override
    public String toString() {
        return "Antelope " + actualLocation;
    }
}
