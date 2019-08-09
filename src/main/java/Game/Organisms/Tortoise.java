package Game.Organisms;

import Game.*;

import javax.swing.*;
import java.awt.*;

public class Tortoise extends Animal {


    public Tortoise(Tile actualLocation, Board board) {
        this.board = board;
        this.actualLocation = actualLocation;
        this.strength = 2;
        this.initiative = 1;
        ImageIcon img = new ImageIcon("images/tortoise.png");
        this.image = img.getImage();
        this.organismType = OrganismType.TORTOISE;
    }

    public Image getImage() {
        return image;
    }

    @Override
    public void collision() {
        super.collision();
    }

    @Override
    public void action() {
        if (this.isDefeated()) {//muszę jedynie sprawdzić czy atakujący nie jest defeated bo tylko tak mogę je oznaczyc na liści na któej pracuję
            return;
        }
        if (getBiasedRandom(0.75f)) {
            this.setNewTile(this.getActualLocation());//czy muszę to robić?
            this.board.getOrganismMap().put(this.getActualLocation(), this);
            Logger.log(this.toString() + " didn't move.");
            return;
        }
        Tile tempLoc = getNewLocation();//szukam nowej lokalizacji dla zwierzęcia
        this.setNewTile(tempLoc);//zmieniam nową lokalizację na wylosowaną

        if (board.getOrganismMap().containsKey(tempLoc) && this != board.getOrganismMap().get(tempLoc)) {//nie muszę sprawdzać czy zwierzę atakowane jest defeated bo jeśli jest to od razu usuwam je z mapy
            collision();
        } else {
            board.getOrganismMap().remove(this.getActualLocation());//usuwam z mapy zwierzę gdzie kluczem byłą stara lokalizacja
            this.setActualLocation(tempLoc);//ustawiam aktualną lokalizację na nową
            board.getOrganismMap().put(tempLoc, this);//dodaję zwierze ponownie do mapy z nowym kluczem
        }
    }

    @Override
    public Tile getNewLocation() {
        return super.getNewLocation();
    }

    @Override
    public String toString() {
        return "Tortoise " + actualLocation;
    }
}

