package br.franke.lucas.model.tile;

import br.franke.lucas.model.Tile;
import br.franke.lucas.type.GrassType;

public class GrassTile extends Tile {

    private GrassType grassType;

    public GrassTile(GrassType grassType) {
        super();
        this.grassType = grassType;
         loadImage();
    }

    @Override
    public void loadImage() {
        switch (grassType) {
            case NORMAL -> imagePath = "grass00";
            case GRASSED -> imagePath = "grass01";
        }
        super.loadImage();
    }

    @Override
    public Tile clone(int row, int col) {
        GrassTile clone = new GrassTile(grassType);
        clone.setRow(row);
        clone.setCol(col);
        return clone;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + super.toString();
    }
}
