package Game;

import java.util.Objects;

public class Tile extends Object {
    int tileX;
    int tileY;

    public void setTileX(int tileX) {
        this.tileX = tileX;
    }

    public Tile() {
    }

    public void setTileY(int tileY) {
        this.tileY = tileY;
    }

    public Tile(int tileX, int tileY) {
        this.tileX = tileX;
        this.tileY = tileY;
    }

    public int getTileX() {
        return tileX;
    }

    public int getTileY() {
        return tileY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return tileX == tile.tileX &&
                tileY == tile.tileY;
    }

    @Override
    public String toString() {
        return
                " X = " + tileX +
                        ", Y = " + tileY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tileX, tileY);
    }
}
