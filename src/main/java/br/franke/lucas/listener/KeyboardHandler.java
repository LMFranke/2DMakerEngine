package br.franke.lucas.listener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardHandler extends KeyAdapter implements KeyListener {

    public boolean isRPressed = false;

    @Override
    public void keyTyped(KeyEvent e) {
        super.keyTyped(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);

        if (e.getKeyCode() == KeyEvent.VK_R) {
            isRPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);

        if (e.getKeyCode() == KeyEvent.VK_R) {
            isRPressed = false;
        }
    }
}
