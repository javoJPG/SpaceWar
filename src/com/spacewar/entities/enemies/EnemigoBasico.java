package com.spacewar.entities.enemies;

public class EnemigoBasico extends Enemigo {

    public EnemigoBasico(int x, int y) {
        super(x, y, 1, 200, 90);
    }

    @Override
    public void actualizar() {
        if (y < posicionLimite) {
            y += velocidad;
        }
        actualizarDisparo();
    }
}