package com.spacewar.logic;

public class Puntaje {
    private int puntaje;
    private int multiplicador;
    private int record;

    public Puntaje() {
        this.puntaje = 0;
        this.multiplicador = 1;
        this.record = 0;
    }

    public void sumar(int puntos) {
        puntaje += puntos * multiplicador;
        if (puntaje > record) {
            record = puntaje;
        }
    }

    public void aumentarMultiplicador() {
        multiplicador++;
    }

    public void resetearMultiplicador() {
        multiplicador = 1;
    }

    public void resetear() {
        puntaje = 0;
        multiplicador = 1;
    }

    public int getPuntaje() { return puntaje; }
    public int getMultiplicador() { return multiplicador; }
    public int getRecord() { return record; }
}