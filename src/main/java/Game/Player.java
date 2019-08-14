package Game;

import Game.Abilities.*;
import Game.Organisms.*;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class Player extends Animal {

    private List<Abilities> abilitiesList;
    private Image image;

    public Player(Board board) {
        super();
        ImageIcon img = new ImageIcon("images/player.png");
        this.image = img.getImage();
        this.board = board;

        this.actualLocation.setTileX(0);
        this.actualLocation.setTileY(0);

        this.newTile.setTileX(0);
        this.newTile.setTileY(0);

        this.strength = 5;
        this.initiative = 4;
        this.abilitiesList = new LinkedList<>(Arrays.asList(new Immortality(), new Ignition(), new AlzursShield(), new AntelopesSpeed(), new MagicalPotion()));
    }

    public List<Abilities> getAbilitiesList() {
        return abilitiesList;
    }

    public Image getImage() {
        return image;
    }

    @Override
    public int getStrength() {
        return strength;
    }

    @Override
    public void setStrength(int strength) {
        this.strength = strength;
    }

    @Override
    public int getInitiative() {
        return initiative;
    }

    public void reduceCooldowns() {
        for (Abilities ability : abilitiesList) {
            if (ability.isActive()) {
                if (ability.getAbilityDuration() == 0) {
                    ability.deactivate(this);
                } else {
                    ability.setAbilityDuration(ability.getAbilityDuration() - 1);
                }
            } else {
                if (ability.getAbilityCooldown() > 0) {
                    ability.setAbilityCooldown(ability.getAbilityCooldown() - 1);
                }
            }
        }
    }

    public void move(int tx, int ty) {
        newTile.setTileX(actualLocation.getTileX() + tx);
        newTile.setTileY(actualLocation.getTileY() + ty);
    }

    public void setAbilitiesList(List<Abilities> abilitiesList) {
        this.abilitiesList = abilitiesList;
    }

    @Override
    public void action() {
        if (this.isDefeated()) {
            this.board.setLooser(true);
            this.board.setInGame(false);

        } else if (board.getOrganismMap().containsKey(this.newTile)) {
            if (board.getOrganismMap().get(this.newTile).getClass().equals(Player.class)) {
                return;
            } else {
                collision();
            }
        } else {
            board.getOrganismMap().remove(this.getActualLocation());//usuwam z mapy zwierzę gdzie kluczem byłą stara lokalizacja
            this.setActualLocation(this.newTile);
            board.getOrganismMap().put(this.getActualLocation(), this);//dodaję zwierze ponownie do mapy z nowym kluczem
        }
    }

    public void ignition() {
        int x;
        int y;
        Tile temptile;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                x = this.getNewTile().getTileX() + i;
                y = this.getNewTile().getTileY() + j;
                temptile = new Tile(x, y);

                if (this.getBoard().getOrganismMap().get(temptile) != null && this.getBoard().getOrganismMap().get(temptile).getClass() != Player.class
                        && x >= 0 && x < this.getBoard().getBlockNumber() && y >= 0 && y < this.getBoard().getBlockNumber()) {
                    Logger.log(this.toString() + " has been burned by Palyer's Ignition.");
                    this.getBoard().getOrganismMap().remove(temptile);
                    for (Organism organism : this.board.getTempList()) {
                        if (organism.getActualLocation().equals(temptile)) {
                            organism.setDefeated(true);

                        }
                    }
                }
            }
        }
        board.getOrganismMap().remove(this.getActualLocation());//usuwam z mapy zwierzę gdzie kluczem byłą stara lokalizacja
        this.setActualLocation(this.getNewTile());//ustawiam aktualną lokalizację na nową
        board.getOrganismMap().put(this.getActualLocation(), this);//dodaję zwierze ponownie do mapy z nowym kluczem
    }

    /**
     * Ustaliłam hierarchię specjalnych umiejętności:
     * 1.Ignition
     * 2.Alzur's Shield
     * 3.Immortality
     * pozostałe umiejętności działają niezależnie od powyższych
     */
    @Override
    public void collision() {
        Organism anotherOrganism = this.board.getOrganismMap().get(this.getNewTile());//pobieram z mapy zwierzę o tej samej lokalizacji co nowowylosowana
        if (this.abilitiesList.get(1).isActive()) {
            ignition();//sprawdzam czy jest aktywne całospalenie
        } else if (this.abilitiesList.get(2).isActive()) {//sprawdzam czy jest aktywna tarcza alzura
            alzursShield(anotherOrganism);
        } else if (this.abilitiesList.get(0).isActive()) {//sprawdzam czy jest aktywne immortality
            immortality(anotherOrganism);
        } else {
            if (anotherOrganism.getClass().equals(Wolfberry.class)) {
                Logger.log(this.toString() + " has been deadly poisoned by wolfberries.");
                Logger.log("GAME OVER");
                this.board.setLooser(true);
                this.board.setInGame(false);
            } else if (anotherOrganism.getClass().equals(Guarana.class)) {
                powerFromGuarana();
            } else if (anotherOrganism.getClass().equals(Tortoise.class)) {
                collisionWithTortoise();
            } else if (anotherOrganism.getClass().equals(Antelope.class)) {
                collisionWithAntelope();
            } else {
                regularCollision();
            }
        }
    }

    private void immortality(Organism anotherOrganism) {
        if (this.getStrength() >= anotherOrganism.getStrength()) {
            this.board.getTempList()
                    .stream()
                    .forEach(organism -> {
                        if (organism.getActualLocation().equals(this.getNewTile())) {
                            organism.setDefeated(true);
                            return;
                        }
                    });//wyszukuję pokonane zwierze z listy i ustawiamu mu defeated true zeby nie wywoływać na nim już w tej turze akcji

            this.board.getOrganismMap().remove(this.getActualLocation());//usuwam z mapy zwierzę atakujące gdzie kluczem byłą stara lokalizacja
            this.setActualLocation(this.getNewTile());//ustawiam aktualną lokalizację na nową
            this.board.getOrganismMap().replace(this.getNewTile(), this);
            Logger.log(this.toString() + " defeated " + anotherOrganism.toString() + ".");
        } else {
            //tutaj następuje przesunięcie człowieka na sąsiednie pole
            this.setNewTile(locationForNewborn());
            this.board.getOrganismMap().remove(this.getActualLocation());//usuwam z mapy człowieka z aktualną lokalizacją
            this.setActualLocation(this.getNewTile());//ustawiam aktualną lokalizację na nową
            this.board.getOrganismMap().put(this.getNewTile(), this);//dodaję człowieka z nową lokalizacją
            Logger.log("Game.Player had to move to another tile, because of " + anotherOrganism.toString() + ".");
        }
    }

    private void alzursShield(Organism anotherOrganism) {
        if (anotherOrganism.getClass().getSuperclass().equals(Animal.class)) {//obsługa walki ze zwierzętami = taka samaa sytuacja jak podczas ucieczki antylopy
            this.board.getTempList()// wyszukuję kolizyjne zwierze z mapy
                    .stream()
                    .forEach(organism -> {
                        if (organism.getActualLocation().equals(this.getNewTile())) {
                            organism.setActualLocation(locationForNewborn());
                            //todo: opcja gdy nie ma żadnego wolnego miejsca??
                            this.board.getOrganismMap().put(organism.getActualLocation(), organism);//dodaję zmienione zwierze do mapy
                            this.board.getOrganismMap().remove(this.getActualLocation());//usuwam z mapy człowieka gdzie kluczem byłą stara lokalizacja
                            this.setActualLocation(this.getNewTile());//ustawiam aktualną lokalizację na nową
                            this.board.getOrganismMap().put(this.getActualLocation(), this);//dodaję człowieka ze zmienioną lokacją do mapy
                            Logger.log("Alzur's Shield moved " + organism.toString() + " to another tile.");
                        }
                    });
        } else {//obsługa walki z roślinami
            if (anotherOrganism.getClass().equals(Wolfberry.class)) {
                Logger.log(this.toString() + " has been deadly poisoned by wolfberries.");
                Logger.log("GAME OVER");
                this.board.setLooser(true);
                this.board.setInGame(false);
            } else if (anotherOrganism.getClass().equals(Guarana.class)) {
                powerFromGuarana();
            } else {
                board.getOrganismMap().remove(this.getActualLocation());//usuwam z mapy zwierzę gdzie kluczem byłą stara lokalizacja
                this.setActualLocation(this.getNewTile());//ustawiam aktualną lokalizację na nową
                board.getOrganismMap().put(this.getActualLocation(), this);//dodaję zwierze ponownie do mapy z nowym kluczem
            }
        }
    }

    protected void collisionWithTortoise() {
        if (this.getStrength() < 5) {
            this.setNewTile(getActualLocation());
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
                            this.board.getOrganismMap().remove(this.getActualLocation());//usuwam z mapy człowieka gdzie kluczem byłą stara lokalizacja
                            this.setActualLocation(this.getNewTile());//ustawiam aktualną lokalizację na nową
                            this.board.getOrganismMap().put(this.getActualLocation(), this);//dodaję zwierze ze zmienioną lokacją do mapy
                            Logger.log("Antelope escaped from fight.");
                        }
                    });
        } else {
            regularCollision();
        }
    }

    @Override
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
                    });//wyszukuję pokonane zwierze z listy i ustawiamu mu defeated true zeby nie wywoływać na nim już w tej turze akcji

            this.board.getOrganismMap().remove(this.getActualLocation());//usuwam z mapy zwierzę atakujące gdzie kluczem byłą stara lokalizacja
            this.setActualLocation(this.getNewTile());//ustawiam aktualną lokalizację na nową
            this.board.getOrganismMap().replace(this.getNewTile(), this);
            Logger.log(this.toString() + " defeated " + anotherOrganism.toString() + ".");
        } else {
            Logger.log(this.toString() + " has been defeated by " + anotherOrganism.toString() + ".");
            Logger.log(" GAME OVER");
            this.board.setLooser(true);
            this.board.setInGame(false);
        }
    }

    @Override
    public String toString() {
        return "Game.Player " + actualLocation;
    }
}
