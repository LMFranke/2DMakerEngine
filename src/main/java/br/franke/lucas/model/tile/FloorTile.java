package br.franke.lucas.model.tile;

import br.franke.lucas.model.Tile;

public class FloorTile extends Tile {

    public FloorTile() {
        this.setCollided(true);
        loadImage();
    }

    @Override
    public void loadImage() {
        imagePath = "floor";
        super.loadImage();
    }

    @Override
    public Tile clone() {
        FloorTile clone = new FloorTile();
        clone.setRow(this.getRow());
        clone.setCol(this.getCol());
        return clone;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + super.toString();
    }
}
