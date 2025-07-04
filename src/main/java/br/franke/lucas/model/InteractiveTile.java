package br.franke.lucas.model;

import br.franke.lucas.type.Direction;

public abstract class InteractiveTile extends Tile {

    public void interact() {}
    public Direction direction;

    public void switchDirection() {
        switch (direction) {
            case NORTH -> this.direction = Direction.EAST;
            case EAST -> this.direction = Direction.SOUTH;
            case SOUTH -> this.direction = Direction.WEST;
            case WEST -> this.direction = Direction.NORTH;
        }
        rotate90Clockwise();
    }


    public void rotate90Clockwise() {
        int width = getImage().getWidth();
        int height = getImage().getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                getImage().setRGB(height - y - 1, y, getImage().getRGB(x, y));
            }
        }
    }

}
