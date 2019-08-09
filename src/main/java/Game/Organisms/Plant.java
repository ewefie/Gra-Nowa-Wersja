package Game.Organisms;

import Game.*;

import java.util.Random;

public abstract class Plant extends Organism {
    public Plant() {
        this.initiative = 0;
        this.strength = 0;
    }

    @Override
    public void collision() {

    }

    @Override
    public void action() {
        replicate();
    }

    public void replicate() {
        if (getBiasedRandom(0.01f)) {//dodaję nowe rosliny
            Tile location = findLocationForNewPlant();
            if (location.equals(new Tile(-1, -1))) {
                Logger.log("Not enough space for new plant: " + this.getClass().toString() + ".");
            }
            board.getOrganismMap().put(location, new Dandelion(location, board));
        }
    }

    public Tile findLocationForNewPlant() {
        Random random = new Random();
        int tileX;
        int tileY;
        Tile tile;

        if (this.getBoard().getOrganismMap().size() >= Math.pow(this.getBoard().getBlockNumber(), 2.0)) {
            tileX = -1;
            tileY = -1;
            tile = new Tile(tileX, tileY);
        } else {
            do {
                tileX = random.nextInt(this.getBoard().getBlockNumber());
                tileY = random.nextInt(this.getBoard().getBlockNumber());
                tile = new Tile(tileX, tileY);
            } while (this.getBoard().getOrganismMap().containsKey(tile));
        }
        return tile;
    }

    @Override
    public Tile getNewLocation() {//rośliny się nie przemieszczają!
        return this.actualLocation;
    }

    @Override
    public String toString() {
        return this.getClass() +
                "" + actualLocation;
    }
}
