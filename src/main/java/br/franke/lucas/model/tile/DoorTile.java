package br.franke.lucas.model.tile;

import br.franke.lucas.model.InteractiveTile;
import br.franke.lucas.model.Tile;
import br.franke.lucas.type.Direction;
import br.franke.lucas.type.DoorType;

public class DoorTile extends InteractiveTile {

    private DoorType doorType;
    public boolean isOpen;

    public DoorTile(DoorType doorType) {
        this.doorType = doorType;
        this.direction = Direction.NORTH;
        loadImage();
    }

    public DoorTile(DoorType doorType, Direction direction) {
        this.doorType = doorType;
        this.direction = direction;
        loadImage();
    }

    public void setDoorType(DoorType doorType) {
        this.doorType = doorType;
    }

    @Override
    public void interact() {
        isOpen = !isOpen;
        loadImage();
    }

    @Override
    public void loadImage() {
        switch (doorType) {
            case OPEN: {
                imagePath = "door01";
                isOpen = true;
                break;
            }
            case CLOSED: {
                imagePath = "door02";
                isOpen = false;
                break;
            }
        }
        super.loadImage();
    }

    @Override
    public Tile clone(int row, int col) {
        DoorTile clone = new DoorTile(doorType, direction);
        clone.setRow(row);
        clone.setCol(col);
        return clone;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + super.toString();
    }
}
