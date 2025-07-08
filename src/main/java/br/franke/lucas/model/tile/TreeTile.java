package br.franke.lucas.model.tile;

import br.franke.lucas.model.Tile;

public class TreeTile extends Tile {

    public TreeTile() {
        this.setCollided(true);
        loadImage();
    }

    @Override
    public void loadImage() {
        imagePath = "tree01";
        super.loadImage();
    }

    @Override
    public Tile clone(int row, int col) {
        TreeTile clone = new TreeTile();
        clone.setRow(row);
        clone.setCol(col);
        return clone;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + super.toString();
    }
}
