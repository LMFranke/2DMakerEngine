package br.franke.lucas.model.tile;

import br.franke.lucas.model.Tile;

public class EarthTile extends Tile {

    public EarthTile() {
        loadImage();
    }

    @Override
    public void loadImage() {
        imagePath = "earth";
        super.loadImage();
    }

    @Override
    public Tile clone(int row, int col) {
        EarthTile clone = new EarthTile();
        clone.setRow(row);
        clone.setCol(col);
        return clone;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + super.toString();
    }

}
