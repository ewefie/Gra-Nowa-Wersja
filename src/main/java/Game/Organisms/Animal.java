package Game.Organisms;


import Game.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public abstract class Animal extends Organism {

    public Animal() {
    }

    public void setNewTile(Tile newTile) {
        this.newTile = newTile;
    }

    protected void breeding() {
        Tile locationForOrganism = locationForNewborn();
        if (locationForOrganism.equals(this.getNewTile())) {
            Logger.log("Not enough space for new animal: " + this.getClass().toString() + ".");
            return;
        } else {
            this.board.getOrganismMap().put(locationForOrganism, OrganismFactory.create(this.getOrganismType(),locationForOrganism,this.board ));//dodaję nowe zwierzę do mapy
            Logger.log("A new animal: " + this.getClass() + " has been created.");
            this.setNewTile(getActualLocation());//nie musze zmieniać obiektu w mapie}
        }
    }

    protected void collisionWithWolfberry() {
        this.setDefeated(true);
        Logger.log(this.toString() + " has been deadly poisoned by wolfberries.");
        this.board.getOrganismMap().remove(this.getActualLocation());//usuwam pokonane zwierze z mapy
    }

    protected void powerFromGuarana() {
        Logger.log(this.toString() + " has been empowered by guarana.");
        this.board.getOrganismMap().remove(this.getActualLocation());//usuwam z mapy zwierzę gdzie kluczem byłą stara lokalizacja
        this.setActualLocation(this.getNewTile());//ustawiam aktualną lokalizację na nową
        this.setStrength(this.getStrength() + 3);//zwiększam siłę zwierzęcia
        this.board.getOrganismMap().put(this.getActualLocation(), this);//dodaję zaktualizowane zwierze do mapy z nową lokalizacją jako kluczem
    }

    protected void collisionWithTortoise() {
        if (this.getStrength() < 5) {
            this.setNewTile(getActualLocation());//nie musze zmieniać obiektu w mapie
            Logger.log("Tortoise pushed back the attack from " + this.toString() + ".");
        } else {
            regularCollision();
        }
    }

    protected void collisionWithAntelope() {
        if (getBiasedRandom(0.5f)) {
            this.board.getTempList()// wyszukuję pokonaną antylopę z listy i zmieniam jej lokalizację
                    .stream()
                    .forEach(organism -> {
                        if (organism.getActualLocation().equals(this.getNewTile())) {
                            organism.setActualLocation(locationForNewborn()); //zmieniam lokację na niezajęte sąsiednie pole
                            this.board.getOrganismMap().put(organism.getActualLocation(), organism);//dodaję zmienioną antylopę do mapy
                            this.board.getOrganismMap().remove(this.getActualLocation());//usuwam z mapy zwierzę gdzie kluczem byłą stara lokalizacja
                            this.setActualLocation(this.getNewTile());//ustawiam aktualną lokalizację na nową
                            this.board.getOrganismMap().put(this.getActualLocation(), this);//dodaję zwierze ze zmienioną lokacją do mapy
                            Logger.log("Antelope escaped from fight.");
                        }
                    });
        } else {
            regularCollision();
        }
    }

    protected void regularCollision() {
        Organism anotherOrganism = this.board.getOrganismMap().get(this.getNewTile());//pobieram z mapy zwierzę o tej samej lokalizacji co nowowylosowana
        if (this.getStrength() >= anotherOrganism.getStrength()) {
            this.board.getTempList()
                    .stream()
                    .forEach(organism -> {
                        if (organism.getActualLocation().equals(this.getNewTile())) {
                            organism.setDefeated(true);
                            return;
                        }
                    });//wyszukuję pokonane zwierze z listy i ustawiamu mu defeated na true true zeby nie wywoływać na nim już w tej turze akcji

            this.board.getOrganismMap().remove(this.getActualLocation());//usuwam z mapy zwierzę atakujące gdzie kluczem byłą stara lokalizacja
            this.setActualLocation(this.getNewTile());//ustawiam aktualną lokalizację na nową
            this.board.getOrganismMap().replace(this.getNewTile(), this);
            Logger.log(this.toString() + " defeated " + anotherOrganism.toString() + ".");
        } else {
            this.setDefeated(true);
            this.board.getOrganismMap().remove(this.getActualLocation());//usuwam pokonane zwierze z mapy
            Logger.log(this.toString() + " has been defeated by " + anotherOrganism.toString() + ".");
        }
    }

    @Override
    public void collision() {
        Organism anotherOrganism = this.board.getOrganismMap().get(this.getNewTile());//pobieram z mapy zwierzę o tej samej lokalizacji co nowowylosowana

        if (anotherOrganism.getClass().equals(this.getClass())) {//sprawdzam czy są tej samej klasy
            breeding();
        } else if (anotherOrganism.getClass().equals(Wolfberry.class)) {//WOLFBERRY
            collisionWithWolfberry();
        } else if (anotherOrganism.getClass().equals(Guarana.class)) {//GUARANA
            powerFromGuarana();
        } else if (anotherOrganism.getClass().equals(Player.class)) {
            collisionWithPlayer();
        } else if (anotherOrganism.getClass().equals(Tortoise.class)) {
            collisionWithTortoise();
        } else if (anotherOrganism.getClass().equals(Antelope.class)) {//antylopa ma 50% szansy na ucieczkę przed walką
            collisionWithAntelope();
        } else {
            regularCollision();
        }
    }

    private void collisionWithPlayer() {
        Player player = (Player) this.board.getOrganismMap().get(this.getNewTile());//pobieram z mapy zwierzę o tej samej lokalizacji co nowowylosowana
        if (player.getAbilitiesList().get(1).isActive()) {//sprawdzam czy jest aktywne całospalenie
            this.setDefeated(true);
            Logger.log(this.toString() + " has been burned by Palyer's Ignition.");
            this.board.getOrganismMap().remove(this.getActualLocation());//usuwam pokonane zwierze z mapy
        } else if (player.getAbilitiesList().get(2).isActive()) {//sprawdzam czy jest aktywna tarcza alzura
            //zwierze musi sie przesunąć, człowiek zostaje na miejscu

            this.board.getOrganismMap().remove(this.getActualLocation());//usuwam z mapy zwierze ze starą lokalizacją
            this.setActualLocation(locationForNewborn());//znajduję dla zwierzęcia nową lokalizację
            this.board.getOrganismMap().put(this.getActualLocation(), this);//dodaję do mapy zwierzę ze zmienioną lokalizacją

            Logger.log(this.toString() + " had to move to another tile becouse of Alzur's Shield.");
        } else if (player.getAbilitiesList().get(0).isActive()) {//sprawdzam czy jest aktywne immortality
            //todo: actual czy new location?
            this.board.getPlayer().setActualLocation(locationForNewborn());//szukam nowej lokacji dla człowieka
            this.board.getOrganismMap().put(this.board.getPlayer().getActualLocation(), this.board.getPlayer()); //dodaje gracza ze zmienioną lokalizacja do mapy

            this.board.getOrganismMap().remove(this.getActualLocation());//usuwam z mapy zwierzę atakujące gdzie kluczem byłą stara lokalizacja
            this.setActualLocation(this.getNewTile());//ustawiam aktualną lokalizację na nową
            this.board.getOrganismMap().replace(this.getNewTile(), this);//dodaję zwierzę na miejsce gracza

            Logger.log("Game.Player had to move to another tile, because of " + this.toString() + ".");
        } else {
            regularCollision();
        }
    }

    /**
     * This method checks if new location for an animal is occupied by another organism.
     */
    @Override
    public void action() {
        Tile tempLoc = getNewLocation();//szukam nowej lokalizacji dla zwierzęcia
        this.setNewTile(tempLoc);//zmieniam nową lokalizację na wylosowaną

        if (this.isDefeated()) {//muszę jedynie sprawdzić czy atakujący nie jest defeated bo tylko tak mogę je oznaczyc na liści na któej pracuję
            return;
        }
        if (board.getOrganismMap().containsKey(tempLoc) && this != board.getOrganismMap().get(tempLoc)) {//nie muszę sprawdzać czy zwierzę atakowane jest defeated bo jeśli jest to od razu usuwam je z mapy
            collision();
        } else {
            board.getOrganismMap().remove(this.getActualLocation());//usuwam z mapy zwierzę gdzie kluczem byłą stara lokalizacja
            this.setActualLocation(tempLoc);//ustawiam aktualną lokalizację na nową
            board.getOrganismMap().put(tempLoc, this);//dodaję zwierze ponownie do mapy z nowym kluczem
        }
    }

    /**
     * This method returns new random tile for animal.
     */
    @Override
    public Tile getNewLocation() {
        Random random = new Random();
        int newTileX;
        int newTileY;
        Tile tile;
        do {
            newTileX = random.nextInt((this.getActualLocation().getTileX() + 1) - (this.getActualLocation().getTileX() - 1) + 1) + (this.getActualLocation().getTileX() - 1);
            newTileY = random.nextInt((this.getActualLocation().getTileY() + 1) - (this.getActualLocation().getTileY() - 1) + 1) + (this.getActualLocation().getTileY() - 1);
            tile = new Tile(newTileX, newTileY);
        } while (tile.equals(this.getActualLocation()) || newTileX < 0
                || newTileX >= this.board.getBlockNumber() || newTileY < 0 || newTileY >= this.board.getBlockNumber());
        return tile;
    }

    /**
     * This method returns new random FREE tile for animal.
     */
    public Tile locationForNewborn() {//szuka WOLNEGO miejsca
        Random random = new Random();
        int newTileX;
        int newTileY;
        Tile tile = new Tile();
        List<Tile> tileList = new ArrayList<>();
//        boolean isFree = false;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                newTileX = this.actualLocation.getTileX() + i;
                newTileY = this.actualLocation.getTileY() + j;
                tile = new Tile(newTileX, newTileY);
                if (!tile.equals(this.getNewTile()) && !this.board.getOrganismMap().containsKey(tile) && newTileX >= 0 && newTileX < this.board.getBlockNumber() && newTileY >= 0 && newTileY < this.board.getBlockNumber())
                    tileList.add(new Tile(newTileX, newTileY));
            }
        }
        if (tileList.isEmpty()) {
            return this.getNewTile();
        } else {
            tile = tileList.get(random.nextInt(tileList.size()));
        }
        return tile;
    }

    @Override
    public String toString() {
        return this.getClass() +
                "" + actualLocation;
    }
}
