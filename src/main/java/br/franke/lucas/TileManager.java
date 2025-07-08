package br.franke.lucas;

import br.franke.lucas.model.Tile;
import br.franke.lucas.model.tile.*;
import br.franke.lucas.type.DoorType;
import br.franke.lucas.type.GrassType;
import br.franke.lucas.type.RoadType;
import br.franke.lucas.type.WaterType;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TileManager {

    public Tile[][] mapTile;
    public Tile[][] mapTileZoom;
    public List<Tile> tileList;
    public Tile centerTileOfZoom;

    public TileManager() {
        mapTile = new Tile[EnginePanel.maxWorldRow][EnginePanel.maxWorldCol];
        mapTileZoom = new Tile[EnginePanel.maxWorldRow / EnginePanel.scaleOfZoom][EnginePanel.maxWorldCol /  EnginePanel.scaleOfZoom];
        tileList = new ArrayList<>();
        loadDefaultList();
        loadMap("src/main/resources/map/map.data");
    }

    public void draw(Graphics g, boolean zoom) {
        if (zoom) {
            drawZoom(g);
        } else {
            drawAllMap(g);
        }
    }

    private void drawAllMap(Graphics g) {
        for (int i = 0; i < EnginePanel.maxWorldRow; i++) {
            for (int j = 0; j < EnginePanel.maxWorldCol; j++) {
                int worldX = j * EnginePanel.realTileSquare + EnginePanel.leftPadding;
                int worldY = i * EnginePanel.realTileSquare + EnginePanel.topPadding;
                mapTile[i][j].draw(g, worldX, worldY, EnginePanel.realTileSquare);
            }
        }
    }

    private void drawZoom(Graphics g) {

        double x = EnginePanel.zoomX;
        double y = EnginePanel.zoomY;

        double maxCol = EnginePanel.maxWorldCol;
        double maxRow = EnginePanel.maxWorldRow;

        double dCol = (x - EnginePanel.leftPadding) / EnginePanel.realTileSquare;
        double dRow = (y - EnginePanel.topPadding) / EnginePanel.realTileSquare;

        if (dCol - maxCol / 4 < 0) {
            dCol = 0;
        } else if (dCol + maxCol / 4 > maxCol) {
            dCol = maxCol / 2;
        } else {
            dCol = dCol - maxCol / 4;
        }

        if (dRow - maxRow / 4 < 0) {
            dRow = 0;
        } else if (dRow + maxRow / 4 > maxRow) {
            dRow = maxRow / 2;
        } else {
            dRow = dRow - maxRow / 4;
        }

        int col = (int) dCol;
        int row = (int) dRow;

        int maxRowZoom = EnginePanel.maxWorldRow / EnginePanel.scaleOfZoom;
        int maxColZoom = EnginePanel.maxWorldCol / EnginePanel.scaleOfZoom;

        for (int i = row, indexY = 0; indexY < maxRowZoom; i++, indexY++) {
            for (int j = col, indexX = 0; indexX < maxColZoom; j++, indexX++) {

                mapTileZoom[indexY][indexX] = mapTile[i][j];

                int imageX = (j - col) * EnginePanel.tileSquareZoom + EnginePanel.leftPadding;
                int imageY = (i - row) * EnginePanel.tileSquareZoom + EnginePanel.topPadding;

                mapTile[i][j].draw(g, imageX, imageY, EnginePanel.tileSquareZoom);
            }
        }

    }

    public void saveMap(String fileName) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
            oos.writeObject(mapTile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadMap(String filePath) {
        boolean isMapEmpty = false;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath));
            mapTile = (Tile[][]) ois.readObject();
        } catch (EOFException e) {
            System.out.println("Map is empty");
            isMapEmpty = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < mapTile.length; i++) {
            for (int j = 0; j < mapTile[i].length; j++) {
                if (isMapEmpty) {
                    mapTile[i][j] = new GrassTile(GrassType.NORMAL);
                } else {
                    mapTile[i][j].loadImage();
                }
                mapTile[i][j].setRow(i);
                mapTile[i][j].setCol(j);
            }
        }

    }

    private void loadDefaultList() {
        tileList.add(new GrassTile(GrassType.NORMAL));
        tileList.add(new WaterTile(WaterType.MIDDLE_WATER));
        tileList.add(new RoadTile(RoadType.MIDDLE_ROAD));
        tileList.add(new EarthTile());
        tileList.add(new WallTile());
        tileList.add(new TreeTile());
        tileList.add(new FloorTile());
        tileList.add(new DoorTile(DoorType.CLOSED));
    }

}
