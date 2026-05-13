package com.spacewar.logic;

public class Vida {
    private int vidaActual;
    private final int vidaMaxima;

    public Vida(int vidaMaxima) {
        this.vidaMaxima = vidaMaxima;
        this.vidaActual = vidaMaxima;
    }

    public void recibirDanio(int danio) {
        vidaActual -= danio;
        if (vidaActual < 0) {
            vidaActual = 0;
        }
    }

    public void curar(int cantidad) {
        vidaActual += cantidad;
        if (vidaActual > vidaMaxima) {
            vidaActual = vidaMaxima;
        }
    }

    public boolean estaVivo() {
        return vidaActual > 0;
    }

    public int getVidaActual() { return vidaActual; }
    public int getVidaMaxima() { return vidaMaxima; }
}