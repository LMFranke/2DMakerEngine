package br.franke.lucas.model.tile;

import br.franke.lucas.model.Tile;

public class WallTile extends Tile {

    public WallTile() {
        this.setCollided(true);
        loadImage();
    }

    @Override
    public void loadImage() {
        imagePath = "wall";
        super.loadImage();
    }

    @Override
    public Tile clone(int row, int col) {
        WallTile clone = new WallTile();
        clone.setRow(row);
        clone.setCol(col);
        return clone;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + super.toString();
    }
}
