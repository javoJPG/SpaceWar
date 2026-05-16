package com.spacewar.entities.enemies;

import com.spacewar.entities.ProyectilEnemigo;

import java.util.ArrayList;
import java.util.List;

public abstract class Enemigo {
    public static final int TAMANO_HITBOX = 40;

    protected int x;
    protected int y;
    protected int velocidad;
    protected int vida;
    protected boolean activo;
    protected int posicionLimite;
    protected List<ProyectilEnemigo> proyectiles;
    protected int cooldownDisparo;
    protected int timerDisparo;
    /** Dirección horizontal (-1 / +1) para enemigos de formación; 0 = no usa este rebote. */
    protected int direccionHorizontal;

    public Enemigo(int x, int y, int vida, int posicionLimite, int cooldownDisparo) {
        this.x = x;
        this.y = y;
        this.vida = vida;
        this.activo = true;
        this.posicionLimite = posicionLimite;
        this.proyectiles = new ArrayList<>();
        this.cooldownDisparo = cooldownDisparo;
        this.timerDisparo = 0;
        this.direccionHorizontal = 0;
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
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public int getAncho() { return TAMANO_HITBOX; }
    public int getAlto() { return TAMANO_HITBOX; }
    public int getVida() { return vida; }
    public List<ProyectilEnemigo> getProyectiles() { return proyectiles; }

    /** Enemigos de oleada rebotan entre sí al solaparse. */
    public boolean puedeRebotarConEnemigos() {
        return false;
    }

    /**
     * Tras un choque con otro enemigo, toma dirección horizontal alejándose de él
     * (evita que dos que iban en la misma dirección sigan pegados).
     */
    public void empujarHorizontalLejosDe(Enemigo otro) {
        if (!puedeRebotarConEnemigos()) {
            return;
        }
        int cxa = getX() + getAncho() / 2;
        int cxb = otro.getX() + otro.getAncho() / 2;
        if (cxa < cxb) {
            direccionHorizontal = -1;
        } else if (cxa > cxb) {
            direccionHorizontal = 1;
        } else {
            direccionHorizontal = direccionHorizontal == 0 ? 1 : -direccionHorizontal;
        }
    }
}