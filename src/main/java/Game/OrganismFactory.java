package Game;

import Game.Organisms.*;

public class OrganismFactory {
    private static OrganismFactory instance = new OrganismFactory();

    private OrganismFactory() {
    }

    public static OrganismFactory getInstance() {
        return instance;
    }


    public static Organism create(OrganismType organismType, Tile location, Board board) {
        switch (organismType) {
            case ANTELOPE:
                return new Antelope(location, board);
            case DANDELION:
                return new Dandelion(location, board);
            case FOX:
                return new Fox(location, board);
            case GRASS:
                return new Grass(location, board);
            case GUARANA:
                return new Guarana(location, board);
            case SHEEP:
                return new Sheep(location, board);
            case TORTOISE:
                return new Tortoise(location, board);
            case WOLF:
                return new Wolf(location, board);
            case WOLFBERRY:
                return new Wolfberry(location, board);
            default:
                throw new IllegalArgumentException();
        }
    }
}
