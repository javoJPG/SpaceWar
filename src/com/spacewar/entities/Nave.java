package com.spacewar.entities;

import java.util.ArrayList;
import java.util.List;

public class Nave {
    private static final int COOLDOWN_DISPARO = 15;
    private static final int COOLDOWN_DISPARO_RAPIDO = 6;
    private static final int DURACION_DISPARO_RAPIDO_FRAMES = 300;

    private int x;
    private int y;
    private int velocidad;
    private int ancho;
    private int alto;
    private List<Proyectil> proyectiles;
    private int disparoCooldown;
    private int disparoRapidoRestante;
    private int escudoCargas;

    public Nave(int x, int y) {
        this.x = x;
        this.y = y;
        this.velocidad = 5;
        this.ancho = 50;
        this.alto = 50;
        this.proyectiles = new ArrayList<>();
    }

    public void moverIzquierda() {
        if (x > 0) x -= velocidad;
    }

    public void moverDerecha(int anchoPantalla) {
        if (x + ancho < anchoPantalla) x += velocidad;
    }

    public void moverArriba() {
        if (y > 0) y -= velocidad;
    }

    public void moverAbajo(int altoPantalla) {
        if (y + alto < altoPantalla) y += velocidad;
    }

    public void disparar() {
        if (disparoCooldown > 0) {
            return;
        }
        proyectiles.add(new Proyectil(x + ancho / 2, y));
        disparoCooldown = disparoRapidoRestante > 0 ? COOLDOWN_DISPARO_RAPIDO : COOLDOWN_DISPARO;
    }

    public void actualizar() {
        if (disparoCooldown > 0) {
            disparoCooldown--;
        }
        if (disparoRapidoRestante > 0) {
            disparoRapidoRestante--;
        }
        proyectiles.removeIf(p -> !p.isActivo());
        for (Proyectil p : proyectiles) {
            p.actualizar();
        }
    }

    public void activarDisparoRapido() {
        disparoRapidoRestante = DURACION_DISPARO_RAPIDO_FRAMES;
    }

    public void agregarEscudo(int cargas) {
        escudoCargas += cargas;
    }

    /** Si hay escudo absorbe un golpe y devuelve true. */
    public boolean intentarAbsorberConEscudo() {
        if (escudoCargas <= 0) {
            return false;
        }
        escudoCargas--;
        return true;
    }

    public List<Proyectil> getProyectiles() { return proyectiles; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getAncho() { return ancho; }
    public int getAlto() { return alto; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
}