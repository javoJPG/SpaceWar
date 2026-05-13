package com.spacewar.entities;

public class Explosion {
    private int x;
    private int y;
    private int frame;
    private int totalFrames;
    private boolean activa;
    private int tamano;

    public Explosion(int x, int y) {
        this.x = x;
        this.y = y;
        this.frame = 0;
        this.totalFrames = 20;
        this.activa = true;
        this.tamano = 10;
    }

    public void actualizar() {
        frame++;
        tamano += 2;
        if (frame >= totalFrames) {
            activa = false;
        }
    }

    public boolean isActiva() { return activa; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getFrame() { return frame; }
    public int getTamano() { return tamano; }
}