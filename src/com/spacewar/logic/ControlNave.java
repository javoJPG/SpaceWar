package com.spacewar.logic;

import com.spacewar.entities.Nave;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ControlNave implements KeyListener {
    private Nave nave;
    private int anchoPantalla;
    private int altoPantalla;

    private boolean izquierda;
    private boolean derecha;
    private boolean arriba;
    private boolean abajo;
    private boolean disparo;

    public ControlNave(Nave nave, int anchoPantalla, int altoPantalla) {
        this.nave = nave;
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
    }

    public void actualizar() {
        if (izquierda) nave.moverIzquierda();
        if (derecha)   nave.moverDerecha(anchoPantalla);
        if (arriba)    nave.moverArriba();
        if (abajo)     nave.moverAbajo(altoPantalla);
        if (disparo)   nave.disparar();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT, KeyEvent.VK_A  -> izquierda = true;
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> derecha   = true;
            case KeyEvent.VK_UP, KeyEvent.VK_W    -> arriba    = true;
            case KeyEvent.VK_DOWN, KeyEvent.VK_S  -> abajo     = true;
            case KeyEvent.VK_SPACE                 -> disparo   = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT, KeyEvent.VK_A  -> izquierda = false;
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> derecha   = false;
            case KeyEvent.VK_UP, KeyEvent.VK_W    -> arriba    = false;
            case KeyEvent.VK_DOWN, KeyEvent.VK_S  -> abajo     = false;
            case KeyEvent.VK_SPACE                 -> disparo   = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}