package br.franke.lucas.model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

public class Tile implements Serializable {

    protected String imagePath;
    private transient BufferedImage image;

    private boolean isCollided;
    private int row;
    private int col;

    public Tile(int col, int row) {
        this.col = col;
        this.row = row;
    }

    public Tile() {}

    public void loadImage() {
        try {
            this.image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/image/tiles/" + imagePath + ".png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isCollided() {
        return isCollided;
    }

    public void setCollided(boolean collided) {
        isCollided = collided;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void draw(Graphics g, int x, int y, int size) {
        g.drawImage(image, x, y, size, size, null);
    }

    @Override
    public String toString() {
        return "Tile{" +
                "imagePath='" + imagePath + '\'' +
                ", col=" + col +
                ", row=" + row +
                '}';
    }

    public Tile clone(int row, int col) {
        return new Tile(row, col);
    }
}
