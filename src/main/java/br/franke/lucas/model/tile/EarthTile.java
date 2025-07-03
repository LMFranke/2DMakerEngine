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
    public String toString() {
        return getClass().getSimpleName() + super.toString();
    }

}
