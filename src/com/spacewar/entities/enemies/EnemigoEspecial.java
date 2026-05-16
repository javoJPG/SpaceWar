package com.spacewar.entities.enemies;

public class EnemigoEspecial extends Enemigo {

    private final int anchoPantalla;

    public EnemigoEspecial(int x, int y, int anchoPantalla, int limiteDescensoY) {
        super(x, y, 10, limiteDescensoY, 110);
        this.anchoPantalla = anchoPantalla;
        this.velocidad = 2;
        this.direccionHorizontal = Math.random() < 0.5 ? -1 : 1;
    }

    @Override
    public boolean puedeRebotarConEnemigos() {
        return true;
    }

    @Override
    public void actualizar() {
        if (y < posicionLimite) {
            y += velocidad;
        }
        moverHorizontalConRebote();
        actualizarDisparo();
    }

    private void moverHorizontalConRebote() {
        x += direccionHorizontal * 2;
        if (x <= 0) {
            x = 0;
            direccionHorizontal = 1;
        } else if (x + getAncho() >= anchoPantalla) {
            x = anchoPantalla - getAncho();
            direccionHorizontal = -1;
        }
    }
}
