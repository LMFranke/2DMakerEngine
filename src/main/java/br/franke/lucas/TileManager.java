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
        int counter = 0;

        int maxRowZoom = EnginePanel.maxWorldRow / EnginePanel.scaleOfZoom;
        int maxColZoom = EnginePanel.maxWorldCol / EnginePanel.scaleOfZoom;

        for (int i = row, indexY = 0; indexY < maxRowZoom; i++, indexY++) {
            for (int j = col, indexX = 0; indexX < maxColZoom; j++, counter++, indexX++) {

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

    private Tile foundTileByNumberRepresented(int number) {
        return switch (number) {
            case 10 -> new GrassTile(GrassType.NORMAL);
            case 11 -> new GrassTile(GrassType.GRASSED);
            case 12 -> new WaterTile(WaterType.MIDDLE_WATER);
            case 13 -> new WaterTile(WaterType.MIDDLE_WATER_DETAILED);
            case 14 -> new WaterTile(WaterType.RIGHT_TO_BOTTOM_WATER);
            case 15 -> new WaterTile(WaterType.TOP_WATER);
            case 16 -> new WaterTile(WaterType.LEFT_TO_BOTTOM_WATER);
            case 17 -> new WaterTile(WaterType.LEFT_WATER);
            case 18 -> new WaterTile(WaterType.RIGHT_WATER);
            case 19 -> new WaterTile(WaterType.RIGHT_TO_UP_WATER);
            case 20 -> new WaterTile(WaterType.BOTTOM_WATER);
            case 21 -> new WaterTile(WaterType.LEFT_TO_UP_WATER);
            case 22 -> new WaterTile(WaterType.CORNER_RIGHT_UP);
            case 23 -> new WaterTile(WaterType.CORNER_LEFT_UP);
            case 24 -> new WaterTile(WaterType.CORNER_LEFT_BOTTOM);
            case 25 -> new WaterTile(WaterType.CORNER_RIGHT_BOTTOM);
            case 26 -> new RoadTile(RoadType.MIDDLE_ROAD);
            case 27 -> new RoadTile(RoadType.RIGHT_TO_DOWN_CURVE_ROAD);
            case 28 -> new RoadTile(RoadType.BOTTOM_ROAD);
            case 29 -> new RoadTile(RoadType.LEFT_TO_DOWN_CURVE_ROAD);
            case 30 -> new RoadTile(RoadType.RIGHT_ROAD);
            case 31 -> new RoadTile(RoadType.LEFT_ROAD);
            case 32 -> new RoadTile(RoadType.RIGHT_TO_UP_CURVE_ROAD);
            case 33 -> new RoadTile(RoadType.TOP_ROAD);
            case 34 -> new RoadTile(RoadType.LEFT_TO_UP_CURVE_ROAD);
            case 35 -> new RoadTile(RoadType.CORNER_TOP_LEFT_ROAD);
            case 36 -> new RoadTile(RoadType.CORNER_TOP_RIGHT_ROAD);
            case 37 -> new RoadTile(RoadType.CORNER_BOTTOM_LEFT_ROAD);
            case 38 -> new RoadTile(RoadType.CORNER_BOTTOM_RIGHT_ROAD);
            case 39 -> new EarthTile();
            case 40 -> new WallTile();
            case 41 -> new TreeTile();
            case 42 -> new Tile();
            case 43 -> new Tile();
            case 44 -> new Tile();
            case 45 -> new Tile();
            case 46 -> new Tile();
            case 47 -> new Tile();
            case 48 -> new Tile();
            case 49 -> new Tile();
            case 50 -> new FloorTile();
            case 51 -> new Tile();
            case 52 -> new Tile();
            case 53 -> new DoorTile(DoorType.CLOSED);
            case 54 -> new Tile();
            case 55 -> new Tile();
            case 56 -> new Tile();
            case 57 -> new Tile();
            case 58 -> new Tile();
            case 59 -> new Tile();
            case 60 -> new Tile();
            default -> null;
        };
    }

}
