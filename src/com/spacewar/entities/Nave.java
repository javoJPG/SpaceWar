package com.spacewar.entities;

import java.util.ArrayList;
import java.util.List;

public class Nave {
    private int x;
    private int y;
    private int velocidad;
    private int ancho;
    private int alto;
    private List<Proyectil> proyectiles;

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
        proyectiles.add(new Proyectil(x + ancho / 2, y));
    }

    public void actualizar() {
        proyectiles.removeIf(p -> !p.isActivo());
        for (Proyectil p : proyectiles) {
            p.actualizar();
        }
    }

    public List<Proyectil> getProyectiles() { return proyectiles; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getAncho() { return ancho; }
    public int getAlto() { return alto; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
}