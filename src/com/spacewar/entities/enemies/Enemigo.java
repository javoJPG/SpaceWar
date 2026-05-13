package com.spacewar.entities.enemies;

import com.spacewar.entities.ProyectilEnemigo;

import java.util.ArrayList;
import java.util.List;

public abstract class Enemigo {
    protected int x;
    protected int y;
    protected int velocidad;
    protected int vida;
    protected boolean activo;
    protected int posicionLimite;
    protected List<ProyectilEnemigo> proyectiles;
    protected int cooldownDisparo;
    protected int timerDisparo;

    public Enemigo(int x, int y, int vida, int posicionLimite, int cooldownDisparo) {
        this.x = x;
        this.y = y;
        this.vida = vida;
        this.activo = true;
        this.posicionLimite = posicionLimite;
        this.proyectiles = new ArrayList<>();
        this.cooldownDisparo = cooldownDisparo;
        this.timerDisparo = 0;
    }

    public abstract void actualizar();

    protected void actualizarDisparo() {
        proyectiles.removeIf(p -> !p.isActivo());
        for (ProyectilEnemigo p : proyectiles) {
            p.actualizar();
        }
        if (y >= posicionLimite) {
            timerDisparo++;
            if (timerDisparo >= cooldownDisparo) {
                disparar();
                timerDisparo = 0;
            }
        }
    }

    protected void disparar() {
        proyectiles.add(new ProyectilEnemigo(x, y));
    }

    public void recibirDanio(int danio) {
        vida -= danio;
        if (vida <= 0) activo = false;
    }

    public boolean isActivo() { return activo; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getVida() { return vida; }
    public List<ProyectilEnemigo> getProyectiles() { return proyectiles; }
}