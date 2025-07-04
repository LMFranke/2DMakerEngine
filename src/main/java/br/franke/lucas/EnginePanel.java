package br.franke.lucas;

import br.franke.lucas.listener.KeyboardHandler;
import br.franke.lucas.listener.MouseHandler;
import br.franke.lucas.model.InteractiveTile;
import br.franke.lucas.model.Tile;
import br.franke.lucas.model.tile.DoorTile;
import br.franke.lucas.model.tile.GrassTile;
import br.franke.lucas.model.tile.RoadTile;
import br.franke.lucas.model.tile.WaterTile;
import br.franke.lucas.type.*;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class EnginePanel extends JPanel implements Runnable {

    private final int FPS = 60;
    private Thread thread;

    public static final int tileOriginalSquare = 16;

    public static final int scale = 1;
    public static final int scaleOfZoom = scale * 2;
    public static final int scaleOfPick = 4;

    public static int tileSquare = tileOriginalSquare * scale;
    public static final int realTileSquare = tileOriginalSquare * scale;
    public static final int tileSquareZoom = tileSquare * scaleOfZoom;
    public static final int tileSquarePicker = tileOriginalSquare * scaleOfPick;
    public static final int tileSpacer = 8;
    public static final int mapToPickerDivisor = 32;

    public static final int maxMap = 10;
    public static final int currentMap = 0;

    public static final int maxWorldRow = 50;
    public static final int maxWorldCol = 50;

    public static final int topPadding = tileSquare * 2;
    public static final int bottomPadding = tileSquare * 8;
    public static final int leftPadding = tileSquare * 2;

    public static final int WIDTH = tileSquare * maxWorldCol + (5 * tileOriginalSquare * scaleOfPick) + leftPadding;
    public static final int HEIGHT = tileSquare * maxWorldRow + bottomPadding + topPadding;

    public static final int startMapX = leftPadding;
    public static final int endMapX = leftPadding + maxWorldCol * tileSquare;
    public static final int startMapY = topPadding;
    public static final int endMapY = topPadding + maxWorldRow * tileSquare;

    public static final int startXPicker = maxWorldCol * tileSquare + mapToPickerDivisor + leftPadding;
    public static final int startYPicker = topPadding;

    public static final int centerMapX = leftPadding + (maxWorldCol * tileSquare / 2);
    public static final int centerMapY = topPadding + (maxWorldRow * tileSquare / 2);

    public static int zoomX = centerMapX;
    public static int zoomY = centerMapY;

    private int selectedTileX;
    private int selectedTileY;
    private int hoverTileX;
    private int hoverTileY;
    private Tile selectedTile;

    private int selectedTilePickerX;
    private int selectedTilePickerY;
    private Tile selectedTilePicker;

    private Tile[] variantsPicker;
    private Tile selectedVariantTilePicker;
    private int selectedVariantTilePickerX;
    private int selectedVariantTilePickerY;

    private final int startXPickerVariant = leftPadding;
    private final int startYPickerVariant = topPadding * 2 + maxWorldRow * tileSquare;

    private final TileManager tileManager;
    private final MouseHandler mouseHandler;
    private final KeyboardHandler keyboardHandler;

    private double coolDownClick = 0.0;
    private double coolDownKey = 0.0;

    private final JButton saveButton;

    public EnginePanel() {
        this.tileManager = new TileManager();
        this.mouseHandler = new MouseHandler();
        this.keyboardHandler = new KeyboardHandler();
        this.variantsPicker = new Tile[]{new Tile()};

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.saveButton = new JButton("Salvar");
        this.saveButton.setPreferredSize(new Dimension(100, 40));
        add(saveButton, BorderLayout.SOUTH);

        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
        addMouseWheelListener(mouseHandler);
        addKeyListener(keyboardHandler);
        addButtonListeners();
    }

    public void startMapEdit() {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (thread != null && thread.isAlive()) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update(delta);
                repaint();
                delta--;
            }
        }
    }

    private void update(double deltaTime) {

        if (coolDownClick > 0) {
            coolDownClick -= deltaTime;
            if (coolDownClick < 0) {
                coolDownClick = 0;
            }
        }

        if (coolDownKey > 0) {
            coolDownKey -= deltaTime;
            if (coolDownKey < 0) {
                coolDownKey = 0;
            }
        }

        int col = (mouseHandler.x - leftPadding) / tileSquare;
        int row = (mouseHandler.y - topPadding) / tileSquare;

        if (coolDownClick == 0) {
            if (mouseHandler.isClicked && mouseHandler.isMouseOnTheArea) {
                insertTile(col, row);
                coolDownClick = 0.2;
            }

            if (mouseHandler.isClickedOutside) {
                if (mouseHandler.y < startYPickerVariant) {
                    foundSelectedTile();
                } else {
                    foundSelectedVariantTile();
                }
                coolDownClick = 10;
            }

            if (mouseHandler.isRightClicked && mouseHandler.isMouseOnTheArea) {
                if (selectedTileX == col * tileSquare && selectedTileY == row * tileSquare) {
                    selectedTile = null;
                }
                coolDownClick = 0.2;
            }
        }

        if (mouseHandler.isMouseOnTheArea) {
            hoverTileX = col * tileSquare;
            hoverTileY = row * tileSquare;
        }

        if (mouseHandler.isWheelMoved) {
            selectedTile = null;
            if (mouseHandler.isZoom) {
                zoomX = mouseHandler.zoomX;
                zoomY = mouseHandler.zoomY;
                tileSquare = tileSquareZoom;
            } else {
                tileSquare = realTileSquare;
            }
            mouseHandler.isWheelMoved = false;
        }

        if (coolDownKey == 0 && selectedTile != null && keyboardHandler.isRPressed) {

            if (selectedTile instanceof InteractiveTile) {
                ((InteractiveTile) selectedTile).switchDirection();
                coolDownKey = 50;
            }

        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        tileManager.draw(g2d, mouseHandler.isZoom);

        if (mouseHandler.isMouseOnTheArea) {
            drawSelection(hoverTileX + leftPadding, hoverTileY + topPadding, g2d, tileSquare, 2);
        }

        if (selectedTile != null && selectedTilePicker == null) {
            drawSelection(selectedTileX + leftPadding, selectedTileY + topPadding, g2d, tileSquare, 2);
        }

        if (selectedTilePicker != null) {
            drawSelection(selectedTilePickerX, selectedTilePickerY, g2d, tileSquarePicker, tileSpacer);
            drawTileVariants(g2d);
        }

        if (selectedVariantTilePicker != null) {
            drawSelection(selectedVariantTilePickerX, selectedVariantTilePickerY, g2d, tileSquarePicker, tileSpacer);
        }

        drawTileOptions(g2d);
        drawSelection(leftPadding, topPadding, g2d, realTileSquare * maxWorldCol, 5);
    }

    private void drawTileOptions(Graphics2D g) {
        for (int i = 0; i < tileManager.tileList.size(); i++) {
            int y = EnginePanel.startYPicker + (EnginePanel.tileSquarePicker * i) + (EnginePanel.tileSpacer * i);
            g.drawImage(tileManager.tileList.get(i).getImage(), EnginePanel.startXPicker, y, EnginePanel.tileSquarePicker, EnginePanel.tileSquarePicker, null);
        }
    }

    private void drawSelection(int selectionX, int selectionY, Graphics2D g, int size, float width) {
        g.setColor(Color.LIGHT_GRAY);
        g.setStroke(new BasicStroke(width));
        g.drawRect(selectionX, selectionY, size, size);
    }

    private void drawTileVariants(Graphics2D g) {
        drawVariants(g, WaterTile.class, WaterType.values(), WaterTile::new);
        drawVariants(g, RoadTile.class, RoadType.values(), RoadTile::new);
        drawVariants(g, GrassTile.class, GrassType.values(), GrassTile::new);
        drawVariants(g, DoorTile.class, DoorType.values(), DoorTile::new);
    }

    private <T> void drawVariants(Graphics2D g2d, Class<?> tileClass, T[] variants, Function<T, Tile> tileFactory) {
        if (!selectedTilePicker.getClass().equals(tileClass)) {
            return;
        }

        boolean isNew = false;
        if (!variantsPicker[0].getClass().equals(tileClass)) {
            variantsPicker = new Tile[variants.length];
            isNew = true;
        }

        for (int i = 0; i < variants.length; i++) {
            if (isNew) {
                Tile tile = tileFactory.apply(variants[i]);
                variantsPicker[i] = tile;
            }
            int x = startXPickerVariant + i * tileSpacer + i * tileSquarePicker;
            g2d.drawImage(variantsPicker[i].getImage(), x, startYPickerVariant, EnginePanel.tileSquarePicker, EnginePanel.tileSquarePicker, null);
        }
    }

    private void insertTile(int col, int row) {

        if (!mouseHandler.isZoom) {
            selectedTileX = col * tileSquare;
            selectedTileY = row * tileSquare;
        } else {
            int handleX = zoomX / tileSquare;
            int handleY = zoomY / tileSquare;

            System.out.println(handleX + " " + handleY);
        }

        if (col >= maxWorldCol || col < 0 || row >= maxWorldRow || row < 0) {
            return;
        }

        if (selectedTilePicker != null) {
            tileManager.mapTile[row][col] = selectedTilePicker.clone();
        } else {
            selectedTile = tileManager.mapTile[row][col];
        }
    }

    private void foundSelectedTile() {
        int pickerCol = (mouseHandler.x - startXPicker) / tileSquarePicker;
        int pickerRow = (mouseHandler.y - startYPicker) / tileSquarePicker;

        int realX = (mouseHandler.x - startXPicker) - pickerCol * tileSpacer;
        int realY = (mouseHandler.y - startYPicker) - pickerRow * tileSpacer;

        pickerCol = realX / tileSquarePicker;
        pickerRow = realY / tileSquarePicker;

        if (pickerRow < tileManager.tileList.size() && pickerRow >= 0 && pickerCol == 0) {
            selectedTilePickerX = startXPicker + pickerCol * tileSquarePicker + (pickerCol) * tileSpacer;
            selectedTilePickerY = startYPicker + pickerRow * tileSquarePicker + (pickerRow) * tileSpacer;

            Tile handle = tileManager.tileList.get(pickerRow + pickerCol);

            selectedTilePicker = handle.equals(selectedTilePicker) ? null : handle;

            selectedVariantTilePicker = null;
        }
    }

    private void foundSelectedVariantTile() {
        int pickerCol = (mouseHandler.x - startXPickerVariant) / tileSquarePicker;

        if (pickerCol > 8) {
            pickerCol--;
        }

        int realX = (mouseHandler.x - startXPickerVariant) - pickerCol * tileSpacer;

        pickerCol = realX / tileSquarePicker;

        if (pickerCol < variantsPicker.length && pickerCol >= 0) {
            selectedVariantTilePicker = variantsPicker[pickerCol];
            selectedTilePicker = selectedVariantTilePicker;

            selectedVariantTilePickerX = startXPickerVariant + pickerCol * (tileSquarePicker + tileSpacer);
            selectedVariantTilePickerY = startYPickerVariant;
        }
    }

    private void addButtonListeners() {
        saveButton.addActionListener(e -> tileManager.saveMap("src/main/resources/map/map.data"));
    }

}
