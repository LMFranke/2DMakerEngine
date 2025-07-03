package br.franke.lucas;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;

public class MouseHandler extends MouseAdapter implements MouseMotionListener {

    public boolean isMouseOnTheArea = false;
    public boolean isClickedOutside = false;
    public boolean isClicked = false;
    public boolean isRightClicked = false;
    public boolean isZoom = false;
    public int x = 0;
    public int y = 0;
    public int zoomX = 0;
    public int zoomY = 0;

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (e.getX() > EnginePanel.endMapX || e.getY() > EnginePanel.endMapY
            || e.getX() < EnginePanel.startMapX || e.getY() < EnginePanel.startMapY) {
                isClickedOutside = true;
            } else {
                isClicked = true;
            }
            x = e.getX();
            y = e.getY();
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            isRightClicked = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);

        if (e.getButton() == MouseEvent.BUTTON1) {
            isClicked = false;
            isClickedOutside = false;
        }

        if (e.getButton() == MouseEvent.BUTTON3) {
            isRightClicked = false;
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);
        if (e.getX() > EnginePanel.leftPadding && e.getX() < EnginePanel.maxWorldCol * EnginePanel.tileSquare + EnginePanel.leftPadding
                && e.getY() > EnginePanel.topPadding && e.getY() < EnginePanel.maxWorldRow * EnginePanel.tileSquare + EnginePanel.topPadding) {
            isMouseOnTheArea = true;
            x = e.getX();
            y = e.getY();
        } else {
            isMouseOnTheArea = false;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        super.mouseWheelMoved(e);
        if (e.getWheelRotation() < 0) {
            isZoom = true;
            zoomX = e.getX();
            zoomY = e.getY();
        } else {
            isZoom = false;
        }

    }
}
