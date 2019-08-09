package Game.Abilities;

import Game.*;

public class MagicalPotion extends Abilities {


    public MagicalPotion() {
        super();
        this.name = "Magical potion";
    }

    public void deactivate(Player player) {
        super.deactivate(player);
        player.setStrength(player.getStrength() - 5);
    }

    @Override
    public void activate(Player player) {
        super.activate();
        player.setStrength(player.getStrength() + 5);//celowo nie ustawiam na sztywno na 10, bo jesli np zje guaranę to będzie miał dodatkowe +3
    }
}
