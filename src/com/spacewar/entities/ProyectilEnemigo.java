package com.spacewar.entities;

public class ProyectilEnemigo extends Proyectil {
    private int danio;

    public ProyectilEnemigo(int x, int y) {
        super(x, y);
        this.danio = 1;
    }

    @Override
    public void actualizar() {
        setY(getY() + getVelocidad());
        if (getY() > 800) {
            setActivo(false);
        }
    }

    public int getDanio() { return danio; }
}