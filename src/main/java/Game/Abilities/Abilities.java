package Game.Abilities;

import Game.Logger;
import Game.Player;

public class Abilities {
    protected boolean active;
    protected int abilityDuration;
    protected int abilityCooldown;
    protected String name;

    public Abilities() {
        this.active = false;
        this.abilityDuration = 0;
        this.abilityCooldown = 0;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getAbilityDuration() {
        return abilityDuration;
    }

    public void setAbilityDuration(int abilityDuration) {
        this.abilityDuration = abilityDuration;
    }

    public int getAbilityCooldown() {
        return abilityCooldown;
    }

    public void setAbilityCooldown(int abilityCooldown) {
        this.abilityCooldown = abilityCooldown;
    }

    public boolean canBeUsed() {
        if (this.isActive() || this.getAbilityCooldown() > 0) {
            Logger.log("You can't use " + this.name + " ability yet.");
            return false;
        } else {
            return true;
        }
    }

    public void activate() {
        Logger.log("Special ability: "+ this.name + " activated.");
        setActive(true);
        setAbilityDuration(5);
    }

    public void activate(Player player) {
    }

    public void deactivate(Player player) {
        Logger.log("Special ability: " + name + " deactivated.");
        setActive(false);
        setAbilityCooldown(5);
    }
}
