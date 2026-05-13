package com.spacewar.entities;

public class PowerUp {
    public enum Tipo { VIDA, DISPARO_RAPIDO, ESCUDO }

    private int x;
    private int y;
    private int velocidad;
    private boolean activo;
    private Tipo tipo;

    public PowerUp(int x, int y, Tipo tipo) {
        this.x = x;
        this.y = y;
        this.velocidad = 3;
        this.activo = true;
        this.tipo = tipo;
    }

    public void actualizar() {
        y += velocidad;
        if (y > 800) activo = false;
    }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    public int getX() { return x; }
    public int getY() { return y; }
    public Tipo getTipo() { return tipo; }
}