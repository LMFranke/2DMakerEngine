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
    public Tile clone() {
        WallTile clone = new WallTile();
        clone.setRow(this.getRow());
        clone.setCol(this.getCol());
        return clone;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + super.toString();
    }
}
