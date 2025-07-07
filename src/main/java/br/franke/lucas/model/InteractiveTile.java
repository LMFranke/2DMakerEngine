package br.franke.lucas.model;

import br.franke.lucas.type.Direction;

import java.awt.image.BufferedImage;

public abstract class InteractiveTile extends Tile {

    public void interact() {}
    public Direction direction;

    @Override
    public void loadImage() {
        super.loadImage();
        for (int i = 0; i < direction.ordinal(); i++) {
            rotate90Clockwise();
        }
    }

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
        int w = getImage().getWidth();
        int h = getImage().getHeight();
        BufferedImage rotated = new BufferedImage(h, w, getImage().getType());

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                rotated.setRGB(h - 1 - y, x, getImage().getRGB(x, y));
            }
        }

        setImage(rotated);
    }

}
