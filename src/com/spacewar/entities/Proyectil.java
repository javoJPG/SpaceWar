package com.spacewar.entities;

public class Proyectil {
    private int x;
    private int y;
    protected int velocidad;
    private boolean activo;

    public Proyectil(int x, int y) {
        this.x = x;
        this.y = y;
        this.velocidad = 10;
        this.activo = true;
    }

    public void actualizar() {
        y -= velocidad;
        if (y < 0) activo = false;
    }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    public int getX() { return x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    public int getVelocidad() { return velocidad; }
}