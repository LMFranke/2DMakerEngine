package br.franke.lucas.model.tile;

import br.franke.lucas.model.Tile;
import br.franke.lucas.type.RoadType;

public class RoadTile extends Tile {

    private RoadType roadType;

    public RoadTile(RoadType roadType) {
        this.roadType = roadType;
        loadImage();
    }

    public RoadType getRoadType() {
        return roadType;
    }

    public void setRoadType(RoadType roadType) {
        this.roadType = roadType;
    }

    @Override
    public void loadImage() {
        switch (roadType) {
            case MIDDLE_ROAD -> imagePath = "road00";
            case RIGHT_TO_DOWN_CURVE_ROAD -> imagePath = "road01";
            case BOTTOM_ROAD -> imagePath = "road02";
            case LEFT_TO_DOWN_CURVE_ROAD -> imagePath = "road03";
            case RIGHT_ROAD -> imagePath = "road04";
            case LEFT_ROAD -> imagePath = "road05";
            case RIGHT_TO_UP_CURVE_ROAD -> imagePath = "road06";
            case TOP_ROAD -> imagePath = "road07";
            case LEFT_TO_UP_CURVE_ROAD -> imagePath = "road08";
            case CORNER_TOP_LEFT_ROAD -> imagePath = "road09";
            case CORNER_TOP_RIGHT_ROAD -> imagePath = "road10";
            case CORNER_BOTTOM_LEFT_ROAD -> imagePath = "road11";
            case CORNER_BOTTOM_RIGHT_ROAD -> imagePath = "road12";
        }
        super.loadImage();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + super.toString();
    }

}
