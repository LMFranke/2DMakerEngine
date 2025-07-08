package br.franke.lucas.model.tile;

import br.franke.lucas.model.Tile;
import br.franke.lucas.type.WaterType;

public class WaterTile extends Tile {

    private WaterType waterType;
    
    public WaterTile(WaterType waterType) {
        this.waterType = waterType;
        this.setCollided(true);
        loadImage();
    }

    public WaterType getWaterType() {
        return waterType;
    }

    public void setWaterType(WaterType waterType) {
        this.waterType = waterType;
    }

    @Override
    public void loadImage() {
        switch (waterType) {
            case MIDDLE_WATER -> imagePath = "water00";
            case MIDDLE_WATER_DETAILED -> imagePath = "water01";
            case RIGHT_TO_BOTTOM_WATER -> imagePath = "water02";
            case TOP_WATER -> imagePath = "water03";
            case LEFT_TO_BOTTOM_WATER -> imagePath = "water04";
            case LEFT_WATER -> imagePath = "water05";
            case RIGHT_WATER -> imagePath = "water06";
            case RIGHT_TO_UP_WATER -> imagePath = "water07";
            case BOTTOM_WATER -> imagePath = "water08";
            case LEFT_TO_UP_WATER -> imagePath = "water09";
            case CORNER_RIGHT_UP -> imagePath = "water10";
            case CORNER_LEFT_UP -> imagePath = "water11";
            case CORNER_LEFT_BOTTOM -> imagePath = "water12";
            case CORNER_RIGHT_BOTTOM -> imagePath = "water13";
        }
        super.loadImage();
    }

    @Override
    public Tile clone(int row, int col) {
        WaterTile clone = new WaterTile(waterType);
        clone.setRow(row);
        clone.setCol(col);
        return clone;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + super.toString();
    }
}
