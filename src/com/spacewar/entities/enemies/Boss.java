package com.spacewar.entities.enemies;

import com.spacewar.entities.ProyectilEnemigo;

public class Boss extends Enemigo {
    private int direccion;
    private int fase;
    private int anchoPantalla;

    public Boss(int x, int y) {
        super(x, y, 20, 150, 30);
        this.direccion = 1;
        this.fase = 1;
        this.anchoPantalla = 800;
    }

    @Override
    public void actualizar() {
        if (y < posicionLimite) {
            y += 2;
        } else {
            moverHorizontal();
        }
        verificarFase();
        actualizarDisparo();
    }

    private void moverHorizontal() {
        x += (velocidad + fase) * direccion;
        if (x <= 0 || x >= anchoPantalla - getAncho()) {
            direccion *= -1;
        }
    }

    private void verificarFase() {
        if (vida <= 10 && fase == 1) {
            fase = 2;
            cooldownDisparo = 15;
        }
    }

    @Override
    protected void disparar() {
        if (fase == 1) {
            proyectiles.add(new ProyectilEnemigo(x + 30, y + 60, true));
        } else {
            proyectiles.add(new ProyectilEnemigo(x, y + 60, true));
            proyectiles.add(new ProyectilEnemigo(x + 30, y + 60, true));
            proyectiles.add(new ProyectilEnemigo(x + 60, y + 60, true));
        }
    }

    public int getFase() { return fase; }

    @Override
    public int getAncho() { return 70; }

    @Override
    public int getAlto() { return 80; }
}